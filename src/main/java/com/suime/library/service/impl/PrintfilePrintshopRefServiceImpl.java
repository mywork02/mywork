package com.suime.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.PrintfilePrintshopRefMapper;
import com.suime.context.model.PrintfilePrintshopRef;
import com.suime.library.service.PrintfilePrintshopRefService;

/**
 * printfilePrintshopRefService
 * Created by ice 19/02/2016.
 */
@Service("printfilePrintshopRefService")
public class PrintfilePrintshopRefServiceImpl extends GenericServiceImpl<PrintfilePrintshopRef> implements PrintfilePrintshopRefService {

    /**
     * printfilePrintshopRefMapper
     */
    @Autowired
    @Qualifier("printfilePrintshopRefMapper")
    private PrintfilePrintshopRefMapper printfilePrintshopRefMapper;

    @Override
    public GenericMapper<PrintfilePrintshopRef> getGenericMapper() {
        return printfilePrintshopRefMapper;
    }

//    public void printshopFile2Document() {
//
//    }
}
