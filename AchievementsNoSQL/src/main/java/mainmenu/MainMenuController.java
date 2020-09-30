/**
 * Controller for the main menu window. This is where each individual category can be accessed: accounts, categories, image requests, database options, quit.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package mainmenu;

import _main.AchieveSettings;

public interface MainMenuController {

    /**
     * Processes account even and goes to the account menu
     *
     * @ensures this.view is disposed and a new AccountView is created
     */
    void processAccountEvent(AchieveSettings settings);
    
    /**
     * Processes categories event and goes to the categories menu
     *
     * @ensures this.view is disposed and a new CategoriesView is created
     */
    void processCategoriesEvent(AchieveSettings settings);
    
    /**
     * Processes image request event and goes to the image request menu
     *
     * @ensures this.view is disposed and a new ImageRequestView is created
     */
    void processImageRequestEvent(AchieveSettings settings);
    
    /**
     * Processes database event and goes to the database menu
     *
     * @ensures this.view is disposed and a new DatabaseView is created
     */
    void processDatabaseEvent(AchieveSettings settings);

    /**
     * Processes quit event and exits the program
     *
     * @ensures this.view is disposed
     */
    void processQuitEvent();

}
