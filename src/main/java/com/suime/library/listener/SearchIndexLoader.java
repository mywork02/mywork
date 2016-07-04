package com.suime.library.listener;

import com.suime.library.service.StudentDocumentSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Provide some basic functions to load / check the search index.
 *
 * @author Fan Lin
 */
@Component
public class SearchIndexLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger logger = LoggerFactory.getLogger(SearchIndexLoader.class);

    @Autowired
    private StudentDocumentSearchService searchService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (!searchService.hasCreatedIndex()) {
            try {
                searchService.createIndex();
            } catch (IOException e) {
                logger.error("Faild to create search index", e);
            }
        }
    }
}
