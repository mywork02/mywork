package com.suime.library.service.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.confucian.framework.support.Page;
import com.confucian.framework.utils.StringUtils;
import com.suime.common.support.Configure;
import com.suime.library.dto.pack.SearchBean;
import com.suime.library.helper.SearchIndexHelper;
import com.suime.library.service.StudentDocumentSearchService;
import com.suime.library.service.StudentDocumentService;

/**
 * Created by LinFan on 16/4/24.
 */
@Component
public class StudentDocumentSearchServiceImpl implements StudentDocumentSearchService {

	private static final Logger logger = LoggerFactory.getLogger(StudentDocumentSearchServiceImpl.class);

	// Fileds
	private static final String ID_FIELD = "id";
	private static final String NAME_FIELD = "name";
	private static final String TAGS_FIELD = "tags";

	private static final int MAX_RESULT_COUNT = 1000;

	@Value("${studentDocumentSearch.indexDirectory}")
	private String indexDirectory;

	@Autowired
	private StudentDocumentService studentDocumentService;

	private boolean indexCreated = false;

	private Boolean isLuceneUse = false;

	/**
	 * Just check if the index directory is empty.
	 *
	 * @return Whether the index has been created or not.
	 */
	@Override
	public boolean hasCreatedIndex() {
		return indexCreated;
	}

	/**
	 * Dump all the documents and store them into search index.
	 */
	@Override
	public void createIndex() throws IOException {
		isLuceneUse = Boolean.valueOf(Configure.getPropertyValue("sys.lucene.use", "false"));
		// // Create the index directory
		// File dir = new File(indexDirectory);
		// if (dir.exists()) {
		// FileUtils.deleteDirectory(dir);
		// }
		// dir.mkdirs();
		//
		// // Dump all the documents from the DB
		// List<StudentDocumentDto> studentDocuments = studentDocumentService.getAllStudentDocument();
		// List<Document> documents = new ArrayList<>();
		// for (StudentDocumentDto studentDocument : studentDocuments) {
		// Document document = new Document();
		// document.add(new StringField(ID_FIELD, studentDocument.getId().toString(), Field.Store.YES));
		// document.add(new TextField(NAME_FIELD, studentDocument.getName(), Field.Store.YES));
		// document.add(new TextField(TAGS_FIELD, studentDocument.getTags() == null ? "" : studentDocument.getTags(), Field.Store.YES));
		// documents.add(document);
		// }
		//
		// // Store all documents to index
		// SearchIndexHelper.createIndex(indexDirectory, documents);
		indexCreated = true;
	}

	@Override
	public Page search(SearchBean searchBean, int curPage, int pageSize) {
		if (!isLuceneUse) {//主要用于开发环境下可以使用
			return studentDocumentService.pageByItem(searchBean, curPage, pageSize);
		}
		if (StringUtils.isNotBlank(searchBean.getText()) && indexCreated) {
			try (Directory directory = FSDirectory.open(Paths.get(indexDirectory)); DirectoryReader ireader = DirectoryReader.open(directory)) {

				// Parse text to query
				// "复旦数学" will be parsed as "复旦" and "数tory学"
				QueryParser nameParser = new QueryParser(NAME_FIELD, SearchIndexHelper.ANALYZER);
				nameParser.setDefaultOperator(QueryParser.Operator.AND);
				Query nameQuery = nameParser.parse(searchBean.getText());
				QueryParser tagsParser = new QueryParser(TAGS_FIELD, SearchIndexHelper.ANALYZER);
				tagsParser.setDefaultOperator(QueryParser.Operator.AND);
				Query tagsQuery = tagsParser.parse(searchBean.getText());

				// it should match one field at least, either name or tags is ok
				BooleanQuery.Builder mainQuery = new BooleanQuery.Builder();
				mainQuery.add(nameQuery, BooleanClause.Occur.SHOULD);
				mainQuery.add(tagsQuery, BooleanClause.Occur.SHOULD);
				mainQuery.setMinimumNumberShouldMatch(1);

				IndexSearcher searcher = new IndexSearcher(ireader);
				ScoreDoc[] hits = searcher.search(mainQuery.build(), MAX_RESULT_COUNT).scoreDocs;
				List<Long> docIds = new ArrayList<>();
				for (int i = 0; i < hits.length; ++i) {
					Document hitDoc = searcher.doc(hits[i].doc);
					docIds.add(Long.parseLong(hitDoc.get(ID_FIELD)));
				}
				if (docIds != null && !docIds.isEmpty()) {
					searchBean.setIds(docIds);
					searchBean.setText(null);
				}
			} catch (IOException e) {
				// TODO(Fan Lin): handle the exception, like throwing a suime exception
				logger.error("Failed to search " + searchBean.getText(), e);
			} catch (ParseException e) {
				// TODO(Fan Lin): handle the exception
				logger.error("Failed to parse " + searchBean.getText(), e);
			}
		}
		return studentDocumentService.pageByItem(searchBean, curPage, pageSize);
	}
}
