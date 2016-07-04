package com.suime.library.service;

import com.confucian.framework.support.Page;
import com.suime.library.dto.pack.SearchBean;

import java.io.IOException;

/**
 * Provide some interfaces to search student documents.
 *
 * @author Fan Lin
 */
public interface StudentDocumentSearchService {

    boolean hasCreatedIndex();

    void createIndex() throws IOException;

    Page search(SearchBean searchBean, int curPage, int pageSize);
}
