/**
 * Controller to process the events of the account window
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package account;

import _main.AchieveSettings;

public interface AccountController {

    /**
     * Attempts to see if there is a valid login within the database.
     * Saves any changes locally to accountConfig
     * 
     * @updates settings
     */
    void processLoginEvent(AchieveSettings settings, String username, char[] password, boolean rememberMe);
    
    /**
	 * Removes any locally saved password, sets remember login to false, and sets isLoggedIn to false
     * 
     * @updates settings
     */
    void processLogoutEvent(AchieveSettings settings);
    
    /**
     * Opens a new SignUp panel.
     * Disposes of this window
     */
    void processSignUpEvent(AchieveSettings settings);
    
    /**
     * Disposes this window and creates a new main menu
     */
    void processBackEvent(AchieveSettings settings);

}
