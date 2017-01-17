package com.ttjkst.fileSystem.exception;

public class EsRuntimeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EsRuntimeException() {
		super();
	}

	public EsRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EsRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public EsRuntimeException(String message) {
		super(message);
	}

	public EsRuntimeException(Throwable cause) {
		super(cause);
	}
	

}
