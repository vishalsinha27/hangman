package com.game.hangman.model;

import java.util.List;

import com.google.gson.annotations.Expose;
/**
 * 
 * @author vishalsinha
 * This is the Model class to hold the game state
 */
public class GameState {

	@Expose
	private int numberOfRetries;
	@Expose
	private int gameId;
	@Expose
	private String state;

	@Expose(serialize = false, deserialize = false)
	private String magicWord; // dont pass the magic word in json to client

	@Expose
	private List<Character> correctGuess;
	@Expose
	private List<Character> incorrectGuess;

	@Expose
	private String entryType; // if the guess is correct then update to success else fail.

	public int getNumberOfRetries() {
		return numberOfRetries;
	}

	public void setNumberOfRetries(int numberOfRetries) {
		this.numberOfRetries = numberOfRetries;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMagicWord() {
		return magicWord;
	}

	public void setMagicWord(String magicWord) {
		this.magicWord = magicWord;
	}

	public List<Character> getCorrectGuess() {
		return correctGuess;
	}

	public void setCorrectGuess(List<Character> correctGuess, int size) {
		this.correctGuess = correctGuess;
		defaultToDot(size);

	}

	public List<Character> getIncorrectGuess() {
		return incorrectGuess;
	}

	public void setIncorrectGuess(List<Character> incorrectGuess) {
		this.incorrectGuess = incorrectGuess;
	}

	private void defaultToDot(int size) {
		for (int i = 0; i < size; i++) {
			correctGuess.add('.');
		}
	}

	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

}
