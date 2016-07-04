package com.suime.library.service;

import com.suime.context.model.StudentDocument;
import com.suime.library.dto.StudentDocumentDto;

public interface PermissionService {
	public StudentDocumentDto updateAndGetInfo(Long studenId,StudentDocument studentDocument);

}
