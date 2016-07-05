package me.sui.api.service;

import me.sui.api.dto.base.BaseResDto;
import me.sui.api.dto.doc.AddDocToSetReqDto;
import me.sui.api.dto.doc.CreateDocSetReqDto;
import me.sui.api.dto.doc.CreateDocSetResDto;
import me.sui.api.dto.doc.DeleteDocSetReqDto;
import me.sui.api.dto.doc.GetDocSetReqDto;
import me.sui.api.dto.doc.GetDocSetResDto;
import me.sui.api.dto.doc.OpenDocSetReqDto;
import me.sui.api.dto.doc.OpenDocSetResDto;
import me.sui.api.dto.doc.RemoveDocFromSetReqDto;
import me.sui.api.dto.doc.UpdateDocSetReqDto;

public interface IDocSetService {

	/**
	 * 打开文集
	 * @param req
	 * @return
	 */
	public OpenDocSetResDto openDocSet(OpenDocSetReqDto req);

	/**
	 * 从文集中移除文档/文件夹
	 * @param req
	 * @return
	 */
	public BaseResDto removeDocFromSet(RemoveDocFromSetReqDto req);

	/**
	 * 添加文档到文集中
	 * @param req
	 * @return
	 */
	public BaseResDto addDocToSet(AddDocToSetReqDto req);

	/**
	 * 获取文集
	 * @param req
	 * @return
	 */
	public GetDocSetResDto getDocSet(GetDocSetReqDto req);

	/**
	 * 更新文集
	 * @param req
	 * @return
	 */
	public BaseResDto updateDocSet(UpdateDocSetReqDto req);

	/**
	 * 删除文集
	 * @param req
	 * @return
	 */
	public BaseResDto deleteDocSet(DeleteDocSetReqDto req);

	/**
	 * 创建文集
	 * @param req
	 * @return
	 */
	public CreateDocSetResDto createDocSet(CreateDocSetReqDto req);

}
