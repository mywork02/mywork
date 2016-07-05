package me.sui.api.service;

import me.sui.api.dto.TestRequest;
import me.sui.api.dto.TestRtnDto;


/**
 * 
 * @author davidclj
 *
 */
public interface ITestService {


    /**
     * 测试接口
     * @return
     */
    public TestRtnDto testMethod(TestRequest testRequest);
}
