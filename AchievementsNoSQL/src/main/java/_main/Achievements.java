/**
 * A to-do list in the form of achievements. This program allows you to create achievements without an image (or with one if you want to use it solo) and then allows an artist to upload to those achievements without images.
 * The artist cannot see all the information regarding an achievement and cannot create their own achievements.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package _main;

import mainmenu.MainMenuController;
import mainmenu.MainMenuController1;
import mainmenu.MainMenuView;
import mainmenu.MainMenuView1;

/**
 * 
 * @author Matthew Crabtree
 * 
 */
public final class Achievements {

	private static final String DATABASE_CONFIG_ADDRESS = "config/databaseConfig.properties";
	private static final String ACCOUNT_CONFIG_ADDRESS = "config/accountConfig.properties";
	private static final String LOCKED_IMAGE_ADDRESS = "data/locked.png";
	private static final String MISSING_IMAGE_ADDRESS = "data/missing.png";
	
    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Achievements() {
    }

    /**
     * Main program that sets up main application window and starts user
     * interaction.
     * 
     * @param args
     *            command-line arguments; not used
     */
    public static void main(String[] args) {
    	/*
    	 * Load or initialize local properties
    	 */
    	AchieveSettings settings = new AchieveSettings(DATABASE_CONFIG_ADDRESS, ACCOUNT_CONFIG_ADDRESS, MISSING_IMAGE_ADDRESS, LOCKED_IMAGE_ADDRESS);
    	
    	/*
    	 * Attempt to connect to any saved settings
    	 */
    	if(!settings.getUseLocal()) {
    		settings.storage.connectDatabase(settings);
        	settings.storage.connectKMS(settings);
        	settings.storage.connectDrive(settings);
        	if(settings.getRememberLogin()) settings.storage.login(settings.getUsername(), settings.getPassword(), settings);	//Login if remember login was chosen
    	}
    	
        /*
         * Create instances of the model, view, and controller objects;
         * controller needs to know about model and view, and view needs to know
         * about controller
         */
    	MainMenuView view = new MainMenuView1(settings);
        MainMenuController controller = new MainMenuController1(view);
        
        view.registerObserver(controller);
    }
}
