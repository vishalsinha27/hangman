package com.game.hangman.test;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.game.hangman.constants.Constants;
import com.game.hangman.core.CreateGame;
import com.game.hangman.core.UpdateGame;
import com.game.hangman.exception.GameException;
import com.game.hangman.model.GameState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * 
 * @author vishalsinha 
 * This is a Junit test class used for unit testing 
 * various games method.
 */

public class HangmanServiceTest {

	private static WebResource service = null;
	private static Gson gson = null;
	private static int gameId = 1;

	@BeforeClass
	public static void setUp() throws Exception {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		service = client.resource(getBaseURI());
		final GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		gson = builder.create();

	}

	/*
	 * Test the create game call.
	 */
	@Test
	public void checkCreateGame() {
		String s = service.path("ws").path("games").post(String.class);
		GameState gs = gson.fromJson(s, GameState.class);
		assertEquals(true, gs.getGameId() > 0);
		gameId = gs.getGameId();
		assertEquals(Constants.GAME_STATE_NEW, gs.getState());

	}

	/*
	 * check get all games call
	 */
	@Test
	public void checkGetAllGames() {
		String s = service.path("ws").path("games").get(String.class);
		Type listType = new TypeToken<ArrayList<GameState>>() {
		}.getType();
		List<GameState> gameList = gson.fromJson(s, listType);
		assertEquals(true, gameList.size() > 0);
	}

	/*
	 * check verify game by id
	 */
	@Test
	public void checkGetGameById() {
		String json = service.path("ws").path("games").path("" + gameId)
				.get(String.class);
		GameState gs = gson.fromJson(json, GameState.class);
		assertEquals(true, gs.getGameId() != 0);

	}

	/*
	 * Verify the play game method. if guess is correct then entrytype should be
	 * updated to success else fail
	 */
	@Test
	public void checkPlayGame() {
		char guess = 'a';

		String json = service.path("ws").path("games").path("" + gameId)
				.path("" + guess).post(String.class);
		GameState gs1 = gson.fromJson(json, GameState.class);
		assertEquals(Constants.GAME_STATE_IN_PROGRESS, gs1.getState());
		if (gs1.getCorrectGuess().contains(guess)) {
			assertEquals(Constants.SUCCESS, gs1.getEntryType());

		} else {
			assertEquals(Constants.ERROR, gs1.getEntryType());
		}
	}

	/*
	 * Check the success case. If all the guess are properly made then game
	 * state should be appropriately updated.
	 */
	@Test
	public void checkSuccessPlay() {
		try {
			GameState gs = CreateGame.create();
			char[] magicChars = gs.getMagicWord().toCharArray();
			for (int i = 0; i < magicChars.length; i++) {
				gs = UpdateGame.update("" + gs.getGameId(), "" + magicChars[i]);
			}
			assertEquals(Constants.GAME_STATE_COMPLETED, gs.getState());
		} catch (GameException e) {
			assertEquals(Constants.GAME_COMPLETED, e.getCode());
		}

	}

	/*
	 * Check if the game is completed any further attempt to guess the character
	 * should result in GameException.
	 */
	@Test
	public void NoUpdatesForCompletedGame() {
		int errorCode = 0;
		try {
			GameState gs = CreateGame.create();
			char[] magicChars = gs.getMagicWord().toCharArray();
			for (int i = 0; i < magicChars.length; i++) {
				gs = UpdateGame.update("" + gs.getGameId(), "" + magicChars[i]);
			}
			gs = UpdateGame.update("" + gs.getGameId(), "a");
		} catch (GameException e) {
			errorCode = e.getCode();
		}
		assertEquals(Constants.GAME_COMPLETED, errorCode);

	}

	/*
	 * If retries are exhausten then the game state should be updated to failed.
	 */
	@Test
	public void checkFailedGamePlay() {
		char guess = 'a';
		String json = service.path("ws").path("games").path("" + gameId)
				.path("" + guess).post(String.class);
		GameState gs1 = gson.fromJson(json, GameState.class);
		int retries = gs1.getNumberOfRetries();

		for (int i = 0; i < retries; i++) {
			json = service.path("ws").path("games").path("" + gameId)
					.path("" + guess).post(String.class);
			gs1 = gson.fromJson(json, GameState.class);

			if (gs1.getNumberOfRetries() == 0) {
				break;
			}

		}
		assertEquals(Constants.GAME_STATE_FAILED, gs1.getState());

	}

	@AfterClass
	public static void tearDown() throws Exception {
	}

	private static String getBaseURI() {
		return "http://localhost:8080/Hangmann";
	}

}
