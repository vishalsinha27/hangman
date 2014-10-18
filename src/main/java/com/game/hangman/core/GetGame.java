package com.game.hangman.core;

import java.util.Collection;

import com.game.hangman.exception.GameException;
import com.game.hangman.helpers.GameUtil;
import com.game.hangman.model.GameState;

/**
 * 
 * @author vishalsinha 
 * This class returns the stats of individual or all the
 * games played.
 */
public class GetGame {
	/**
	 * 
	 * @return Collection<GameState> Returns stats of all the games.
	 */
	public static Collection<GameState> getAllGames() {
		return GameUtil.getGameMap().values();
	}

	/**
	 * 
	 * @param id - Game id
	 * @return GameState
	 * @throws GameException
	 *  This method will return game state for the passed game id
	 */
	public static GameState getGameState(String id) throws GameException {
		return GameUtil.getGameMap().get(Integer.parseInt(id));

	}
}
