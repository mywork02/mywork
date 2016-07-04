package com.suime.library.helper;

import com.confucian.framework.utils.StringUtils;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * For CRDU the index and search content using lucene.
 * Please refer to https://lucene.apache.org/core/6_0_0/core/overview-summary.html#overview_description
 *
 * @author Fan Ouyang
 */
public class SearchIndexHelper {
    private static final Logger logger = LoggerFactory.getLogger(SearchIndexHelper.class);

    // TODO(Fan Lin): use injector
    public static final SmartChineseAnalyzer ANALYZER = new SmartChineseAnalyzer();

    public static IndexWriter getIndexWriter(Directory directory) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(ANALYZER);
        return new IndexWriter(directory, config);
    }

    public static void createIndex(String indexDir, List<Document> documents) throws IOException {
        if (StringUtils.isNotBlank(indexDir) && documents != null && !documents.isEmpty()) {
            try (Directory directory = FSDirectory.open(Paths.get(indexDir));
                 IndexWriter indexWriter = getIndexWriter(directory)) {
                for (Document document : documents) {
                    indexWriter.addDocument(document);
                }
            }
        }
    }
}
