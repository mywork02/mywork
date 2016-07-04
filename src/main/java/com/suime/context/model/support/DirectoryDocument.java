package com.suime.context.model.support;

import com.suime.context.model.Directory;
import com.suime.context.model.StudentDocument;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenqy on 2016/4/14.
 */
public class DirectoryDocument implements Serializable {

    /**
	 * sid
	 */
	private static final long serialVersionUID = 2404717836758861655L;

	/**
	 * directory
	 */
	private Directory directory;

	/**
	 * documents
	 */
    private List<StudentDocument> documents;

    /**
     * subDirectorys
     */
    private List<DirectoryDocument> subDirectorys;

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public List<StudentDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<StudentDocument> documents) {
        this.documents = documents;
    }

    public List<DirectoryDocument> getSubDirectorys() {
        return subDirectorys;
    }

    public void setSubDirectorys(List<DirectoryDocument> subDirectorys) {
        this.subDirectorys = subDirectorys;
    }
}
