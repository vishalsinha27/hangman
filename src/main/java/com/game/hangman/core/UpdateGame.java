package com.game.hangman.core;

import java.util.List;

import com.game.hangman.constants.Constants;
import com.game.hangman.exception.GameException;
import com.game.hangman.helpers.GameUtil;
import com.game.hangman.model.GameState;

/**
 * 
 * @author vishalsinha 
 * This class is used to update the game status.
 */
public class UpdateGame {
	/**
	 * 
	 * @param id
	 *            - game id
	 * @param s
	 *            - character guessed
	 * @return GameState
	 * @throws GameException
	 *             This method will take the guessed char and update the
	 *             gamestate based on whether the guessed character is correct
	 *             or not or if the game is completed.
	 */
	public static GameState update(String id, String s) throws GameException {
		if (s == null || s.trim().length() == 0) {
			throw new GameException("Character is null or space.",
					Constants.INVALID_CHARACTER);

		}

		// pick up the first character if multiple characters are entered
		char input = s.trim().toLowerCase().toCharArray()[0];

		int gameid = Integer.parseInt(id);

		/*
		 * Check for error condition. If an entry is made for already failed or
		 * finished game.
		 */
		GameState gs = GameUtil.getGameMap().get(gameid);
		if (gs.getState().equals(Constants.GAME_STATE_FAILED)) {
			throw new GameException("Number of retries are exhausted.",
					Constants.ATTEMPTS_EXHAUSTED);

		} else if (gs.getState().equals(Constants.GAME_STATE_COMPLETED)) {
			throw new GameException("Game is already successfully completed.",
					Constants.GAME_COMPLETED);

		}
		System.out.println("magic word for game id " + gameid + " is "
				+ gs.getMagicWord());

		/*
		 * check if guess made is correct or not. Note that correctList will
		 * only contain dot initially and then as the guess is made the dot will
		 * be replaced with correct letter.
		 */
		List<Character> correctList = gs.getCorrectGuess();

		/*
		 * magicword contains the words that needs to be guessed.
		 */
		String magicword = gs.getMagicWord();
		int index = magicword.indexOf(input, 0);

		/*
		 * if the guessed letter (input) is present in magic word but not in
		 * correctList than it means a correct guess is made and its not a
		 * repetition.
		 */
		if (index != -1 && !correctList.contains(input)) {
			while (index != -1) {
				correctList.remove(index);// remove the dot.
				correctList.add(index, input); // add the character
				index = magicword.indexOf(input, index + 1);
			}
			/*
			 * no dots present implies the game is completed.
			 */
			if (!correctList.contains('.')) {
				gs.setState(Constants.GAME_STATE_COMPLETED);
				gs.setEntryType(Constants.SUCCESS);
				return gs;
			} else {
				gs.setState(Constants.GAME_STATE_IN_PROGRESS);
				gs.setEntryType(Constants.SUCCESS);

				return gs;
			}
		} else {
			/*
			 * if it is not a correct guess than reduce the number of retries
			 * and update the game state.
			 */
			gs.setNumberOfRetries(gs.getNumberOfRetries() - 1);
			gs.getIncorrectGuess().add(input);
			gs.setEntryType(Constants.ERROR);
			if (gs.getNumberOfRetries() <= 0) {
				gs.setState(Constants.GAME_STATE_FAILED);

			} else {
				gs.setState(Constants.GAME_STATE_IN_PROGRESS);
			}
			return gs;

		}
	}
}
