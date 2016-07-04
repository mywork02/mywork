package com.suime.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.RoleMapper;
import com.suime.context.model.Role;
import com.suime.library.service.RoleService;

/**
 * roleService
 * Created by ice 17/02/2016.
 */
@Service("roleService")
public class RoleServiceImpl extends GenericServiceImpl<Role> implements RoleService {

	/**
	 * roleMapper
	 */
	@Autowired
	@Qualifier("roleMapper")
	private RoleMapper roleMapper;

	@Override
	public GenericMapper<Role> getGenericMapper() {
		return roleMapper;
	}
}
