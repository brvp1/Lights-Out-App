package com.zybooks.lightsout;
import android.util.Log;

import java.sql.SQLOutput;
import java.util.Random;
/*
 * Author: Bhairavi Patel
 * Date: 09/20/2022
 * @author Bhairavi Patel
 */

// Model
public class LightOutGame {
    public static final int GRID_SIZE = 3;
    public static final String GAME = "LightsOutGame";

    // Lights that make up the grid declaration
    private final boolean[][] mLightsGrid;

    // constructor
    public LightOutGame() {
        // initialize grid
        mLightsGrid = new boolean[GRID_SIZE][GRID_SIZE];
    }

    /*
    * Randomly turns on and off the grid lights.
     */
    public void newGame() {
        Random randomNumberGenerator = new Random();

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mLightsGrid[row][col] = randomNumberGenerator.nextBoolean();
            }
        }
    } // end newGame

    /**
     * Returns the on/off status of the light.
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isLightOn(int row, int col) {
        return mLightsGrid[row][col];
    } // end isLightOn

    /**
     * Inverts the selected light and adjacent lights.
     *
     * @param row
     * @param col
     */
    // flip lights around a particular light
    public void selectLight(int row, int col) {
        // flip its own value
        mLightsGrid[row][col] = !mLightsGrid[row][col];

        // flip the lights around
        if (row > 0) {
            mLightsGrid[row - 1][col] = !mLightsGrid[row -1][col];
        }
        if (row < GRID_SIZE - 1) {
            mLightsGrid[row + 1][col] = !mLightsGrid[row + 1][col];
        }
        if (col > 0) {
            mLightsGrid[row][col - 1] = !mLightsGrid[row][col - 1];
        }
        if (col < GRID_SIZE - 1) {
            mLightsGrid[row][col + 1] = !mLightsGrid[row][col + 1];
        }

    } // end selectLight

    /**
     * Determines if all the lights are off.
     *
     * @return
     */
    public boolean isGameOver() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (mLightsGrid[row][col]) {
                    return false;  // there is a light that is on
                }
            }
        }

        return true;
    } // end isGameOver

    /**
     * Method to set the state of the game
     *
     * @param gameState Contains the game state as String
     *                  TTTFFFTFT
     */
    public void setState(String gameState) {
        Log.d(GAME, gameState);
        int index = 0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mLightsGrid[row][col] = gameState.charAt(index) == 'T';
                index++;
            }
        }

    } // end setState

    /**
     *  Get the current state of the game from the 2D array
     *
     * @return
     */
    public String getState() {
        StringBuilder boardString = new StringBuilder();
        for (int row =0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                char value = mLightsGrid[row][col] ? 'T' : 'F';
                boardString.append(value);
            }
        }
        Log.d(GAME, boardString.toString());
        return boardString.toString();
    } // end getState

} // end of class
