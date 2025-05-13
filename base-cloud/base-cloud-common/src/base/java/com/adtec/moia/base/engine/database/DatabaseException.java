package com.adtec.moia.base.engine.database;

public class DatabaseException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private Integer errorCode;
	
	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public DatabaseException() {
    }

	public DatabaseException(String message) {
        super(message);
    }
	public DatabaseException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
