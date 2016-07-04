package com.suime.library.rest;

import com.confucian.framework.support.Constants;
import com.confucian.framework.utils.DateUtil;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.util.FileTypeUtil;
import com.suime.context.model.Document;
import com.suime.context.model.File;
import com.suime.context.model.PrintfilePrintshopRef;
import com.suime.context.model.StudentDocument;
import com.suime.library.service.DocumentService;
import com.suime.library.service.FileService;
import com.suime.library.service.PrintfilePrintshopRefService;
import com.suime.library.service.StudentDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.confucian.framework.web.AbstractRestController;

import java.sql.Timestamp;
import java.util.List;

/**
 * printfilePrintshopRefRestController
 *
 * @author ice
 */
@RestController
@RequestMapping("/printfilePrintshopRef")
public class PrintfilePrintshopRefRestController extends AbstractRestController {

    /**
     * printfilePrintshopRefService
     */
    @Autowired
    private PrintfilePrintshopRefService printfilePrintshopRefService;

    /**
     * studentDocumentService
     */
    @Autowired
    private StudentDocumentService studentDocumentService;

    /**
     * documentService
     */
    @Autowired
    private DocumentService documentService;

    /**
     * fileService
     */
    @Autowired
    private FileService fileService;

    // @RequestMapping(path = "/printFile2Document", method = {RequestMethod.POST})
    public Object printFile2Document(@RequestParam(name = "all", defaultValue = "all") String all) {
        int page = 1;
        int pageSize = Constants.VALUE_PAGE_SIZE;
        List<PrintfilePrintshopRef> printfilePrintshopRefs = printfilePrintshopRefService.fetchSearchByPage(null, null, page, pageSize);
        Byte actived = 1;
        Byte source = 2;
        Integer index = 0;
        Integer addIndex = 0;
        Integer errorIndex = 0;
        while (printfilePrintshopRefs != null && !printfilePrintshopRefs.isEmpty()) {
            for (PrintfilePrintshopRef printfilePrintshopRef : printfilePrintshopRefs) {
                index++;
                try { 
                    this.logger.info("page:" + page + ",index:" + index + ",addIndex:" + addIndex + ",errorIndex:" + errorIndex);
                    Long fileId = printfilePrintshopRef.getFileId();
                    System.out.println(fileId);
                    File file = fileService.fetchById(fileId);
                    String fileKey = file.getKey();
                    Conds docConds = new Conds();
                    docConds.equal("file_key", fileKey);
                    docConds.equalOr("file_id", fileId);
                    Document document = documentService.fetchSearchByConds(docConds);
                    Timestamp sqlTimestamp = DateUtil.getSqlTimestamp();
                    String fileName = printfilePrintshopRef.getName();
                    if (file.getPageCount() == null) {
                        continue;
                    }
                    if (document != null) {
                        Conds documentConds = new Conds();
                        documentConds.equal("document_id", document.getId());
                        StudentDocument studentDocument = studentDocumentService.fetchSearchByConds(documentConds);
                        if (studentDocument != null) {
                            continue;
                        }
                    } else {
                        document = new Document();
                        document.setFileName(fileName);
                        document.setName(FileTypeUtil.getNameWithoutExtension(fileName));
                        document.setActived(actived);
                        document.setFileId(fileId);
                        document.setFileKey(fileKey);
                        document.setCreatedAt(sqlTimestamp);
                        document.setUpdatedAt(sqlTimestamp);
                        documentService.save(document);
                    }
                    Long documentId = document.getId();
                    this.logger.info("----documentId:" + documentId);

                    StudentDocument studentDocument = new StudentDocument();
                    sqlTimestamp = DateUtil.getSqlTimestamp();
                    studentDocument.setCreatedAt(sqlTimestamp);
                    studentDocument.setUpdatedAt(sqlTimestamp);
                    studentDocument.setFileKey(fileKey);
                    studentDocument.setStudentNickName("随米用户_21");
                    studentDocument.setStudentId(21L);
                    studentDocument.setSource(source);
                    studentDocument.setShareCount(0);
                    studentDocument.setReviewState(actived);//1:待审核
                    studentDocument.setReadCount(0);
                    studentDocument.setPageCount(file.getPageCount());
                    studentDocument.setRatings(0);
                    studentDocument.setRatingCount(0);
                    studentDocument.setPreview(actived);
                    studentDocument.setName(document.getName());
                    studentDocument.setFileName(fileName);
                    studentDocument.setFileKey(fileKey);
                    studentDocument.setFileId(fileId);
                    studentDocument.setDocumentId(documentId);
                    studentDocument.setActived(actived);
                    studentDocument.setPermission(actived);
                    studentDocumentService.save(studentDocument);
                    addIndex++;

                    this.logger.info("page:" + page + ",index:" + index + ",addIndex:" + addIndex + ",errorIndex:" + errorIndex);
                    this.logger.info("----studentDocumentId:" + studentDocument.getId());
                    if ("all".equals(all)) {
                        return studentDocument;
                    }
                } catch (Exception e) {
                    errorIndex++;
                    this.logger.error("printfilePrintshopRef:" + printfilePrintshopRef.getId());
                }
            }
            page++;
            printfilePrintshopRefs = printfilePrintshopRefService.fetchSearchByPage(null, null, page, pageSize);
        }
        return null;
    }
}
