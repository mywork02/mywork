package me.sui.user.remote.service;

import me.sui.user.pack.StudentRegisterBean;

/**
 * Created by ice on 16/4/2016.
 */
public interface StudentRemoteService {

    /**
     * 用户注册
     */
    boolean register(StudentRegisterBean registerBean);
}
