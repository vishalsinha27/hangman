package com.game.hangman.exception;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author vishalsinha
 *  GameException class used in case of exception.
 */
public class GameException extends Exception {

	private static final long serialVersionUID = 1L;
	@Expose
	private String message;
	@Expose
	private int code;

	public GameException(String message, int code) {
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
