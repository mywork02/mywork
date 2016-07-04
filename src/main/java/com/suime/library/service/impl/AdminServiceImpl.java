package com.suime.library.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.framework.support.Page;
import com.confucian.framework.utils.DateUtil;
import com.confucian.framework.utils.ValidatorUtil;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.Admin;
import com.suime.context.model.Role;
import com.suime.library.dao.AdminMapper;
import com.suime.library.dao.RoleMapper;
import com.suime.library.dto.AdminDto;
import com.suime.library.dto.pack.AdminBean;
import com.suime.library.dto.pack.SearchAdminBean;
import com.suime.library.error.RoleErrors;
import com.suime.library.service.AdminService;

/**
 * adminService
 * Created by ice 17/02/2016.
 */
@Service("adminService")
public class AdminServiceImpl extends GenericServiceImpl<Admin> implements AdminService {

	/**
	 * adminMapper
	 */
	@Autowired
	@Qualifier("adminMapper")
	private AdminMapper adminMapper;

	/**
	 * roleMapper
	 */
	@Autowired
	@Qualifier("roleMapper")
	private RoleMapper roleMapper;

	@Override
	public GenericMapper<Admin> getGenericMapper() {
		return adminMapper;
	}

	@Override
	public AdminDto fetchDtoById(Long id) {
		Conds conds = new Conds();
		conds.equal("t.id", id);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("conds", conds);
		List<AdminDto> list = this.adminMapper.fetchDtoSearch(params);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public AdminDto fetchDtoByCellphone(String cellphone) {
		Conds conds = new Conds();
		conds.equal("t.cellphone", cellphone);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("conds", conds);
		List<AdminDto> list = this.adminMapper.fetchDtoSearch(params);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void addByItem(AdminBean adminBean) {
		if (adminBean == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		if (!ValidatorUtil.validateMobile(adminBean.getCellphone())) {
			throw BusinessErrors.getInstance().mobileError(adminBean.getCellphone());
		}
		if (!ValidatorUtil.validatePassword(adminBean.getPassword())) {
			throw BusinessErrors.getInstance().passwordError();
		}
		Conds conds = new Conds();
		conds.equal("cellphone", adminBean.getCellphone());
		if (this.existByConds(conds)) {
			throw BusinessErrors.getInstance().alreadyRegisteredError(adminBean.getCellphone());
		}
		Admin admin = new Admin();
		admin.setCellphone(adminBean.getCellphone());
		admin.setPassword(adminBean.getPassword());
		admin.setName(adminBean.getName());
		if (adminBean.getSex() != null) {
			admin.setSex(adminBean.getSex());
		} else {
			// 默认性别男
			admin.setSex((byte) 1);
		}
		Role role = null;
		if (adminBean.getRoleId() != null) {
			role = roleMapper.fetchById(adminBean.getRoleId());
		}
		if (role == null) {
			throw RoleErrors.getInstance().roleNotFoundError(adminBean.getRoleId());
		}
		admin.setRoleId(role.getId());
		Timestamp currentTime = DateUtil.getSqlTimestamp();
		admin.setCreatedAt(currentTime);
		admin.setUpdatedAt(currentTime);
		// 默认启用状态
		Byte status = 2;
		admin.setStatus(status);
		this.save(admin);
	}

	@Override
	public Page pageByItem(SearchAdminBean searchAdminBean, Integer currentPage, Integer pageSize) {
		if (searchAdminBean == null) {
			return null;
		}
		Conds conds = new Conds();
		conds.notEqual("t.status", 0);
		if (StringUtils.isNotBlank(searchAdminBean.getName())) {
			conds.like("t.name", searchAdminBean.getName());
		}
		if (StringUtils.isNotBlank(searchAdminBean.getCellphone())) {
			conds.like("t.cellphone", searchAdminBean.getCellphone());
		}
		if (StringUtils.isNotBlank(searchAdminBean.getRoleCode())) {
			conds.equal("role.code", searchAdminBean.getRoleCode());
		}
		String sortField = searchAdminBean.getSortField();
		if (StringUtils.isNotBlank(sortField)) {
			sortField = "t.created_at";
		}
		OrderType orderType = searchAdminBean.getSort();
		if (orderType == null) {
			orderType = OrderType.DESC;
		}
		Sort sort = new Sort(sortField, orderType);
		Map<String, Object> params = this.generateParamsMap(conds, sort, currentPage, pageSize);
		int totalCount = this.adminMapper.countWidthRole(params);
		List<AdminDto> list = this.adminMapper.fetchDtoSearch(params);
		return generatePage(currentPage, pageSize, totalCount, list);
	}

	@Override
	public void updateByItem(AdminBean adminBean) {
		if (adminBean == null || adminBean.getId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Admin admin = this.fetchById(adminBean.getId());
		if (admin == null) {
			throw BusinessErrors.getInstance().userNotExistsError();
		}
		admin.setUpdatedAt(DateUtil.getSqlTimestamp());
		if (adminBean.getRoleId() != null) {
			Role role = this.roleMapper.fetchById(adminBean.getRoleId());
			if (role == null) {
				throw RoleErrors.getInstance().roleNotFoundError(adminBean.getRoleId());
			}
			admin.setRoleId(role.getId());
		}
		if (StringUtils.isNotBlank(adminBean.getName())) {
			admin.setName(adminBean.getName());
		}
		this.update(admin);

	}

	@Override
	public void removeOrUpdateById(Byte status, Long id) {
		if (id == null || status == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Admin admin = this.fetchById(id);
		if (admin == null) {
			throw BusinessErrors.getInstance().userNotExistsError();
		}
		admin.setStatus(status);
		admin.setUpdatedAt(DateUtil.getSqlTimestamp());
		this.update(admin);
	}
}
