package me.sui.api.dto.doc;

import java.util.List;

import me.sui.api.dto.base.BaseReqDto;

public class AddDocToSetReqDto extends BaseReqDto {

	/**
	 * 添加文件或者文件夹的bean
	 */
	private List<DocToSetData> docToSetDatas;

	/**
	 * @see #docToSetDatas
	 */
	public List<DocToSetData> getDocToSetDatas() {
		return docToSetDatas;
	}

	/**
	 * @see #docToSetDatas
	 */
	public void setDocToSetDatas(List<DocToSetData> docToSetDatas) {
		this.docToSetDatas = docToSetDatas;
	}


}