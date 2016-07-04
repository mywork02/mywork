package com.suime.library.manager;

import com.confucian.framework.utils.DateUtil;
import com.confucian.framework.utils.StringUtils;
import com.confucian.mybatis.support.manager.GenericManager;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.DeviceToken;
import com.suime.context.model.Message;
import com.suime.library.dao.DeviceTokenMapper;
import com.suime.library.dao.MessageMapper;
import com.suime.library.dto.MessageDto;
import com.suime.library.dto.pack.MessageBean;
import com.suime.library.helper.XingePushHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ice on 22/3/2016.
 */
@Repository("messageManager")
public class MessageManager extends GenericManager<Message> {

	/**
	 * messageMapper
	 */
	@Autowired
	@Qualifier("messageMapper")
	private MessageMapper messageMapper;

	/**
	 * deviceTokenMapper
	 */
	@Autowired
	@Qualifier("deviceTokenMapper")
	private DeviceTokenMapper deviceTokenMapper;

	@Override
	public GenericMapper<Message> getGenericMapper() {
		return messageMapper;
	}

	/**
	 * add message
	 * @param messageBean
	 * @return boolean
	 */
	public boolean add(MessageBean messageBean) {
		if (!isValidMessage(messageBean)) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Message message = new Message();
		Timestamp sqlTimestamp = DateUtil.getSqlTimestamp();
		Byte actived = 1;
		Byte isReaded = 0;
		message.setCreatedAt(sqlTimestamp);
		message.setUpdatedAt(sqlTimestamp);
		message.setIsReaded(isReaded);
		message.setActived(actived);
		message.setSenderId(messageBean.getSenderId());
		message.setStudentDocumentName(messageBean.getStudentDocumentName());
		message.setMessageType(messageBean.getMessageType());
		message.setType(messageBean.getType());
		message.setContent(messageBean.getContent());
		message.setExtraKey(messageBean.getExtraKey());
		message.setExtra(messageBean.getExtra());
		message.setReceiverId(messageBean.getReceiverId());
		message.setRelatedId(messageBean.getRelateId());
		message.setStudentDocumentId(messageBean.getStudentDocumentId());
		boolean flag = this.save(message);

		messagePush(message);
		return flag;
	}

	/**
	 * message push
	 * @param message
	 */
	private void messagePush(Message message) {
		try {
			Conds messageConds = new Conds();
			messageConds.equal("id", message.getId());
			Map<String, Object> messageParams = new HashMap<>();
			List<MessageDto> messageDtos = this.messageMapper.fetchDtoSearchByPage(messageParams);
			if (messageDtos != null && !messageDtos.isEmpty()) {
				MessageDto messageDto = messageDtos.get(0);

				Conds conds = new Conds();
				// conds.equal("device_type", 1);//ios
				conds.equal("user_id", message.getReceiverId());// ios
				conds.equal("user_type", 1);// student

				Map<String, Object> params = new HashMap<>();
				params.put("conds", conds);
				List<DeviceToken> deviceTokens = deviceTokenMapper.fetchSearchByPage(params);
				if (deviceTokens != null && !deviceTokens.isEmpty()) {
					Long studentDocumentId = message.getStudentDocumentId();

					int messageType = message.getMessageType().intValue();
					String body = this.extractMessage(message.getMessageType().intValue(), messageDto.getSender().getNickName(), messageDto.getContent());
					String type = "preview";
					Long commentId = null;
					if (messageType == 6 || messageType == 7) {
						type = "comment";
						commentId = message.getRelatedId();
						// body = this.extractMessage(message.getMessageType().intValue(), messageDto.getSender().getNickName(),
						// messageDto.getCommentContent());
						// } else {
						// body = this.extractMessage(message.getMessageType().intValue(), messageDto.getSender().getNickName(), messageDto.getContent());
					}

					for (DeviceToken deviceToken : deviceTokens) {
						// XingePushHelper.iosPushMessage(studentDocumentId, commentId, body, deviceToken.getToken(), type);
						XingePushHelper.pushMessage(deviceToken.getDeviceType(), studentDocumentId, commentId, body, deviceToken.getToken(), type);
					}
				}
			}
		} catch (Exception ex) {

		}
	}

	/**
	 * extract message
	 * @param messageType
	 * @param senderName
	 * @param body
	 * @return message
	 */
	private String extractMessage(int messageType, String senderName, String body) {
		String message = ""; // 1:文档收藏,2:文档分享,3:文档打印成功,4:文档通过审核,5:文档下架,6:文档评论,7:回复评论
		String tempBody = body;
		final int messageMaxLength = 100;
		final int messageLength = 97;
		if (StringUtils.length(tempBody) > messageMaxLength) {
			tempBody = StringUtils.substring(tempBody, 0, messageLength) + "...";
		}
		String spacing = " ";
		switch (messageType) {
		case 1:
			message = senderName + spacing + tempBody;
			break;
		case 6:
			message = senderName + spacing + tempBody;
			break;
		case 7:
			message = senderName + spacing + tempBody;
			break;
		default:
			message = senderName + spacing + tempBody;
		}
		return message;
	}

	/**
	 * 验证
	 * @param messageBean
	 * @return boolean
	 */
	private boolean isValidMessage(MessageBean messageBean) {
		if (messageBean.getStudentDocumentId() == null) {
			return false;
		}
		if (messageBean.getMessageType() == null) {
			return false;
		}
		if (messageBean.getReceiverId() == null) {
			return false;
		}
		if (messageBean.getSenderId() == null) {
			return false;
		}
		if (messageBean.getRelateId() == null) {
			return false;
		}
		return true;
	}

}
