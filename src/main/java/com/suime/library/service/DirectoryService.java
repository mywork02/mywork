package com.suime.library.service;

import com.confucian.mybatis.support.service.GenericService;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.context.model.Directory;
import com.suime.library.dto.DirectoryDto;
import com.suime.library.dto.pack.DirectoryBean;
import com.suime.library.dto.pack.DocPermissionBean;
import com.suime.library.dto.pack.DocumentMoveBean;

import java.util.List;

import javax.print.Doc;

/**
 * directoryService
 * Created by ice 12/03/2016.
 */
public interface DirectoryService extends GenericService<Directory> {

    /**
     * 添加目录
     *
     * @param directoryBean
     */
    void addByItem(DirectoryBean directoryBean);

    /**
     * 软删除目录
     *
     * @param directoryBean
     */
    void removeById(DirectoryBean directoryBean);

    /**
     * 修改目录名
     *
     * @param directoryBean
     */
    void updateNameByItem(DirectoryBean directoryBean);

    /**
     * 分页查询 dto
     *
     * @param conds    条件
     * @param sort     排序
     * @param page     起始条数
     * @param pageSize 分页大小
     * @return list directory dto
     */
    List<DirectoryDto> fetchDtoSearchByPage(Conds conds, Sort sort, int page, int pageSize);
    
    
    /**
     * 修改目录公开
     * @param directoryBean
     */
    void changePermission(DocPermissionBean docPermissionBean);
    
    /**
     *将目录移动到某目录下面
     */
    void changeDireMove(DocumentMoveBean documentMoveBean);
    
    /***
     *修改目录私有
     */
    void changePrivateDire(DocPermissionBean docPermissionBean);
    
    /**
     * 查询目录信息
     */
    DirectoryDto directoryDtoInfo(DirectoryBean directoryBean);
    
    /**
     * 仅删除目录
     */
    void removeByDirectory(DirectoryBean directoryBean);
}
