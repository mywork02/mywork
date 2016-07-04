
package com.suime.library.dto.pack;

/**
 * DocumentMoveBean
 * @author lishiwei
 */
public class DocumentMoveBean {

	/**
	 * directoryId
	 */
	private Long directoryId;

	/**
	 * ids
	 */
	private Long[] ids;
	/**
	 * studentId
	 */
	private Long studentId;

	public Long getDirectoryId() {
		return directoryId;
	}

	public void setDirectoryId(Long directoryId) {
		this.directoryId = directoryId;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

}
