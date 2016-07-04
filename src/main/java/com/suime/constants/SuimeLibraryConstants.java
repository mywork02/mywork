package com.suime.constants;

/**
 * SuimeConstants 系统变量
 * @author ice
 */
public class SuimeLibraryConstants {

	/**
	 * actived 字段 有效状态值
	 */
	public static final Byte COMMON_ACTIVED_VALID = 1;

	/**
	 * actived 字段 无效状态值
	 */
	public static final Byte COMMON_ACTIVED_INVALID = 0;

	/**
	 * 公司推荐文档
	 */
	public static final Byte STUDENT_DOCUMENT_STICK = 1;

	/**
	 * 收藏记录,文档type
	 */
	public static final Byte MARK_RECORD_TYPE_DOCUMENT = 1;

	/**
	 * 收藏记录,文集type
	 */
	public static final Byte MARK_RECORD_TYPE_COLLECTED_WORKS = 2;

	// 操作类型，1：上传，2：修改，3：公开，4：阅读，5：收藏
	/**
	 * 操作记录,数据类型，1:文档(暂时只有文档)
	 */
	public static final Byte DOC_OPERATE_DATA_TYPE_RECORD_UPLOAD = 1;
	/**
	 * 操作记录,上传文档
	 */
	public static final Byte DOC_OPERATE_OP_TYPE_RECORD_UPLOAD = 1;
	/**
	 * 操作记录,修改文档
	 */
	public static final Byte DOC_OPERATE_OP_TYPE_RECORD_UPDATE = 2;
	/**
	 * 操作记录,公开文档
	 */
	public static final Byte DOC_OPERATE_OP_TYPE_RECORD_PUBLIC = 3;
	/**
	 * 操作记录,阅读文档
	 */
	public static final Byte DOC_OPERATE_OP_TYPE_RECORD_READ = 4;
	/**
	 * 操作记录,收藏文档
	 */
	public static final Byte DOC_OPERATE_OP_TYPE_RECORD_MARK = 5;

	/**
	 * 文件夹－文档(文件夹)－关联记录:文档类型
	 */
	public static final Byte DIR_CONTENT_RELS_TYPE_DOCUMENT = 1;

	/**
	 * 文件夹－文档(文件夹)－关联记录:文件夹类型
	 */
	public static final Byte DIR_CONTENT_RELS_TYPE_DIRECTORY = 2;

}
