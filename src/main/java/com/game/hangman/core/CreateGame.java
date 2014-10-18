package com.game.hangman.core;

import java.util.ArrayList;
import java.util.Random;

import com.game.hangman.constants.Constants;
import com.game.hangman.exception.GameException;
import com.game.hangman.helpers.GameUtil;
import com.game.hangman.model.GameState;

public class CreateGame {

	/*
	 * Gameid initialized to 1. 
	 * For each new game, this will be incremented. 
	 */
	private static int gameId = 1;

	/**
	 * 
	 * @return GameState
	 * @throws GameException
	 *             Create a new game and store it in memory.
	 */
	public static GameState create() throws GameException {
		
		while (GameUtil.getGameMap().containsKey(gameId)) {
			gameId++;
		}

		if (GameUtil.getAllWords() == null || GameUtil.getAllWords().length == 0) {

			throw new GameException("Hangman word list is empty",
					Constants.WORD_LIST_EMPTY);

		}

		GameState gs = new GameState();
		gs.setGameId(gameId);
		gs.setNumberOfRetries(Constants.NUMBER_OF_RETRIES);
		gs.setState(Constants.GAME_STATE_NEW);
		setNewWord(gs);

		GameUtil.getGameMap().put(gameId, gs);
		return gs;

	}
	
	/**
	 * 
	 * @param gs - 
	 * GameState Helper method to select and set the magic word for
	 * the game
	 */
	private static void setNewWord(GameState gs) {
		Random randIndex = new Random();
		int index = randIndex.nextInt(GameUtil.getAllWords().length - 1);
		String mWord = GameUtil.getAllWords()[index];
		gs.setMagicWord(mWord.toLowerCase());
		gs.setCorrectGuess(new ArrayList<Character>(), mWord.length());
		gs.setIncorrectGuess(new ArrayList<Character>());

	}

}
