package com.game.hangman.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import com.game.hangman.model.GameState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * @author vishalsinha
 * This class contains list of utility methods for the game...
 */
public class GameUtil {
	private static Gson gson = null;
	private static String[] words = null;
	private static String fileLocation = "/usr/local/hangman/words.english" ;
	private static HashMap<Integer, GameState> gameMap = new HashMap<Integer, GameState>();


	
	/**
	 * Load the date file.
	 */
	static {
		
		File f = new File(fileLocation);
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(f);
			byte[] b = new byte[fin.available()];
			fin.read(b);
			String data = new String(b);
			words = data.split("\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// make the gson object.
		final GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		gson = builder.create();
	}
	
	
	



	/**
	 * Returns the gson object to convert 
	 * java object to json or vice versa.
	 * @return GSon
	 */
	public static Gson getGSON() {

		return gson;

	}
	
	/**
	 * Returns the list of all the words present in the 
	 * data file...
	 * @return
	 */
	public static String[] getAllWords() {
		return words ;
	}
	
	/**
	 * returns the game map that contains 
	 * game state...
	 * @return
	 */
	public static HashMap<Integer, GameState> getGameMap() {
		return gameMap;
	}
	
	

}
