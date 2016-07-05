package me.sui;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;

import me.sui.api.dto.PushSendDocMsgReqDto;
import me.sui.api.service.IDocService;

public class HessianAccessTest {

    //	static IPointService pointService;
    static IDocService docService;

    static {
        try {
            //pointService = HessianServerClientHolder.create(IPointService.class, "http://127.0.0.1:10040/suime-inn-service-webapp/api/pointService.htm");
            // pointService = HessianServerClientHolder.create(IPointService.class, "http://120.27.156.254:8080/suime-inn-service-webapp/api/pointService.htm");// 指定节点,没有走负载均衡
            // docService = HessianServerClientHolder.create(IDocService.class, "http://127.0.0.1:10040/suime-inn-service-webapp/api/docService.htm");// 指定节点,没有走负载均衡
            // docService = HessianServerClientHolder.create(IDocService.class, "http://120.27.156.254:8080/suime-inn-service-webapp/api/docService.htm");// 指定节点,没有走负载均衡

            HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
            hessianProxyFactory.setOverloadEnabled(true);
            docService = (IDocService) hessianProxyFactory.create(IDocService.class, "http://120.27.156.254:8080/suime-inn-service-webapp/api/docService.htm");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDoc() {
        PushSendDocMsgReqDto req = new PushSendDocMsgReqDto();
        req.setDesc("test");
        List<Long> docIds = new ArrayList<Long>();
        docIds.add(1L);
        req.setDocIds(docIds);
        List<Long> dirIds = new ArrayList<Long>();
        dirIds.add(2L);
        req.setDirIds(dirIds);
		List<Long> receiverStudentIds = new ArrayList<Long>();
		receiverStudentIds.add(1L);
        req.setSenderStudentId(2L);
        docService.pushDoc(req);
    }


//	@Test
//	public void testpoint() {
//		System.out.println(pointService.point(PointTypeEnum.REGISTER, 123L, 123L, 123).getResStatusEnum().getCode());
//		System.out.println(pointService.point(PointTypeEnum.PERFECT, 123L, 123L, 123).getResStatusEnum().getCode());
//		System.out.println(pointService.point(PointTypeEnum.SIGN_IN, 123L, 123L, 123).getResStatusEnum().getCode());
//		System.out.println(pointService.point(PointTypeEnum.CLICK_ADS, 123L, 123L, 123).getResStatusEnum().getCode());
//		System.out.println(pointService.point(PointTypeEnum.SHARE, 123L, 123L, 123).getResStatusEnum().getCode());
//		System.out.println(pointService.point(PointTypeEnum.MARK, 123L, 123L, 123).getResStatusEnum().getCode());
//		System.out.println(pointService.point(PointTypeEnum.REPLY, 123L, 123L, 123).getResStatusEnum().getCode());
//		System.out.println(pointService.point(PointTypeEnum.PRINT, 123L, 123L, 123).getResStatusEnum().getCode());
//		System.out.println(pointService.point(PointTypeEnum.RECHARGE, 123L, 123L, 123).getResStatusEnum().getCode());
//		System.out.println(pointService.point(PointTypeEnum.UPLOAD_DOCUMENT, 123L, 123L, 123).getResStatusEnum().getCode());
//		System.out.println(pointService.point(PointTypeEnum.LEVEL_UP, 123L, 123L, 123).getResStatusEnum().getCode());
//		System.out.println(pointService.point(PointTypeEnum.ORDER_CANCEL_RETURN, 123L, 123L, 123).getResStatusEnum().getCode());
//		System.out.println(pointService.point(PointTypeEnum.ORDER_REFUND_RETURN, 123L, 123L, 123).getResStatusEnum().getCode());
//		System.out.println(pointService.point(PointTypeEnum.ORDER_DEDUCT, 123L, 123L, 123).getResStatusEnum().getCode());
//		System.out.println(pointService.point(PointTypeEnum.CONSUME, 123L, 123L, 123).getResStatusEnum().getCode());
//	}

}
