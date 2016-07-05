package me.sui.api.dto;

import java.io.Serializable;
import java.util.List;

public class GetSendDocMsgPageModel implements Serializable {
	private List<GetSendDocMsgDataDto> getSendDocMsgDataDtos;

	private GetSendDocMsgPageInfo getSendDocMsgPageInfo;

	public List<GetSendDocMsgDataDto> getGetSendDocMsgDataDtos() {
		return getSendDocMsgDataDtos;
	}

	public void setGetSendDocMsgDataDtos(List<GetSendDocMsgDataDto> getSendDocMsgDataDtos) {
		this.getSendDocMsgDataDtos = getSendDocMsgDataDtos;
	}

	public GetSendDocMsgPageInfo getGetSendDocMsgPageInfo() {
		return getSendDocMsgPageInfo;
	}

	public void setGetSendDocMsgPageInfo(GetSendDocMsgPageInfo getSendDocMsgPageInfo) {
		this.getSendDocMsgPageInfo = getSendDocMsgPageInfo;
	}

}
