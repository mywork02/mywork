package me.sui.api.service;

import me.sui.api.dto.SendMessageReqDto;
import me.sui.api.dto.SendMessageResDto;

public interface ISendMessageService {

	public SendMessageResDto sendSendMessage(SendMessageReqDto req);

}
