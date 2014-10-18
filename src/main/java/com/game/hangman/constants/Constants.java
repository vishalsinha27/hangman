package com.game.hangman.constants;

/**
 * 
 * @author vishalsinha 
 * This class is used to hold Constants.
 */
public final class Constants {

	/*
	 * Game constants.
	 */
	public static final int NUMBER_OF_RETRIES = 11;
	public static final String GAME_STATE_NEW = "new";
	public static final String GAME_STATE_IN_PROGRESS = "busy";
	public static final String GAME_STATE_COMPLETED = "success";
	public static final String GAME_STATE_FAILED = "fail";
	public static final String ERROR = "error";
	public static final String SUCCESS = "success";

	/*
	 * constants used to send the status code.
	 */
	final public static int WORD_LIST_EMPTY = 100;
	final public static int INVALID_CHARACTER = 103;
	final public static int ATTEMPTS_EXHAUSTED = 106;
	final public static int GAME_COMPLETED = 110;
	final public static int GAME_ID_NOT_FOUND = 102;
	final public static int INVALID_GAME_ID = 101;

}
