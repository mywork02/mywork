package com.suime.library.service;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.Admin;
import com.suime.library.dto.AdminDto;
import com.suime.library.dto.pack.AdminBean;
import com.suime.library.dto.pack.SearchAdminBean;

/**
 * adminService
 * Created by ice 17/02/2016.
 */
public interface AdminService extends GenericService<Admin> {

    /**
     * 根据id获取信息
     *
     * @param id adminId
     * @return admindto
     */
    AdminDto fetchDtoById(Long id);

    /**
     * 根据 cellphone 获取信息
     *
     * @param cellphone cellphone
     * @return admindto
     */
    AdminDto fetchDtoByCellphone(String cellphone);

    /**
     * 添加管理员
     *
     * @param adminBean adminBean
     */
    void addByItem(AdminBean adminBean);

    /**
     * 分页获取管理员
     *
     * @param searchAdminBean searchAdminBean
     * @param currentPage     currentPage
     * @param pageSize        pageSize
     * @return page
     */
    Page pageByItem(SearchAdminBean searchAdminBean, Integer currentPage, Integer pageSize);

    /**
     * 更新
     *
     * @param adminBean adminBean
     */
    void updateByItem(AdminBean adminBean);

    /**
     * 根据id删除,启用，冻结
     *
     * @param status status
     * @param id     id
     */
    void removeOrUpdateById(Byte status, Long id);
}
