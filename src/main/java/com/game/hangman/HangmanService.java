package com.game.hangman;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.game.hangman.core.CreateGame;
import com.game.hangman.core.GetGame;
import com.game.hangman.core.UpdateGame;
import com.game.hangman.exception.GameException;
import com.game.hangman.helpers.GameUtil;

/**
 * 
 * @author vishalsinha 
 * This class will serve all the request. There can be 4
 * different request which each of the 4 methods will serve Requests are
 * 1) start a new game 2) Get Stats of all the game 3) Get Stats of a
 * particular game and 4) Play a game based on game id.
 */

@Path("/games")
public class HangmanService {

	Gson gson = GameUtil.getGSON();

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * This method creates a new game and returns the success or failure json
	 * @return String
	 */
	public String startNewGame() {

		try {
			return gson.toJson(CreateGame.create());

		} catch (GameException ge) {
			return gson.toJson(ge);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{param}")
	/**
	 * This method gets the status of individual game
	 * @param id
	 * @return json for individual game
	 */
	public String getGame(@PathParam("param") String id) {

		try {
			return gson.toJson(GetGame.getGameState(id));

		} catch (GameException ge) {
			return gson.toJson(ge);
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * This method gets the status of all the games
	 * @return json for all the games state
	 */
	public String getAllGames() {
		return gson.toJson(GetGame.getAllGames());
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{param}/{param1}")
	/**
	 * This is called to update the status of the game played by user. 
	 * @param id
	 * @param c
	 * @return json for updated status
	 */
	public String playGame(@PathParam("param") String id,
			@PathParam("param1") String c) {

		try {
			return gson.toJson(UpdateGame.update(id, c));
		} catch (GameException ge) {
			return gson.toJson(ge);
		}
	}

}
