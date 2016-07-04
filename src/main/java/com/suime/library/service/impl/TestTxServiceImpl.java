package com.suime.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.suime.context.model.Student;
import com.suime.context.model.StudentDocument;
import com.suime.library.dao.StudentDocumentMapper;
import com.suime.library.dao.StudentMapper;
import com.suime.library.service.TestTxService;

@Service("testTxService")
public class TestTxServiceImpl implements TestTxService {

	@Autowired
	@Qualifier("studentMapper")
	private StudentMapper studentMapper;

	@Autowired
	@Qualifier("studentDocumentMapper")
	private StudentDocumentMapper studentDocumentMapper;

	@Override
	public void saveTest() {
		Student s = new Student();
		s.setId(8123L);
		s.setNickName("鹰射手1");
		studentMapper.update(s);
		StudentDocument sd = new StudentDocument();
		sd.setId(87017L);
		sd.setFileName("随米测试文档123.docx");
		studentDocumentMapper.update(sd);
	}

}
