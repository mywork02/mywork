package me.sui.api.service;

import me.sui.api.dto.GetSendDocMsgReqDto;
import me.sui.api.dto.GetSendDocMsgResDto;
import me.sui.api.dto.PushSendDocMsgReqDto;
import me.sui.api.dto.PushSendDocMsgResDto;
import me.sui.api.dto.ReceiveSendDocReqDto;
import me.sui.api.dto.ReceiveSendDocResDto;
import me.sui.api.dto.search.PushSearchKeyReqDto;
import me.sui.api.dto.search.PushSearchKeyResDto;

public interface IDocService {

	/**
	 * 发送文档的消息(发送文档的动作是异步处理)
	 * @param req
	 * @return
	 */
	public PushSendDocMsgResDto pushDoc(PushSendDocMsgReqDto req);

	/**
	 * 查询发送消息
	 * @param req
	 * @return
	 */
	public GetSendDocMsgResDto getMsg(GetSendDocMsgReqDto req);

	/**
	 * 接受消息
	 * @param req
	 * @return
	 */
	public ReceiveSendDocResDto receiveDoc(ReceiveSendDocReqDto req);

	/**
	 * 推送搜索key
	 * @param req
	 * @return
	 */
	public PushSearchKeyResDto PushSearchKey(PushSearchKeyReqDto req);

}
