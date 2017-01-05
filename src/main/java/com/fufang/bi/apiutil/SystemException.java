package com.fufang.bi.apiutil;

/**
 * 系统异常类，在抛出此类及其子类异常时，需要做报警处理
 * @author minhua.wang
 *
 */
public class SystemException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SystemException() {
		super();
	}

	public SystemException(final String message) {
		super(message);
	}

	public SystemException(final Throwable cause) {
		super(cause);
	}

	public SystemException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
