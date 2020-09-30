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
import mainmenu.MainMenuController;
import mainmenu.MainMenuController1;
import mainmenu.MainMenuView;
import mainmenu.MainMenuView1;
import signup.SignUpController;
import signup.SignUpController1;
import signup.SignUpView;
import signup.SignUpView1;

/**
 * Controller class.
 *
 * @author Matthew Crabtree
 */
public final class AccountController1 implements AccountController {

    /**
     * View objects.
     */
    private AccountView view;

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public AccountController1(AccountView view) {
        this.view = view;
    }

    @Override
    public void processLoginEvent(AchieveSettings settings, String username, char[] password, boolean rememberMe) {	
    	/*
    	 * Create string from password
    	 */
		String passwordStr = "";
		for(char c : password) {
			passwordStr += c;
		}
    	
    	//Check login with database
    	settings.storage.login(username, passwordStr, settings);
    	
    	//Save settings iff login was successful
    	if(settings.getIsLoggedIn() == true) {
    		settings.setUsername(username);
    		settings.setRememberLogin(rememberMe);
    		if(rememberMe) {
    			settings.setPassword(passwordStr);
    		}
    		this.view.updateLogoutAllowed(true);	//Make logout button available
    	}
    	
    	settings.saveAccountConfig();
    }
    
    @Override
    public void processLogoutEvent(AchieveSettings settings) {
    	/*
    	 * Change saved settings
    	 */
    	settings.setPassword("");
    	settings.setRememberLogin(false);
    	settings.setIsLoggedIn(false);
    	settings.saveAccountConfig();
    	
    	/*
    	 * Update fields and buttons
    	 */
    	this.view.updateLogoutAllowed(false);
    	this.view.updatePasswordField("");
    	this.view.updateRememberMeCheck(false);
    	
    	System.err.println("[SUCCESS] Successfully logged out.");
    }

    @Override
    public void processSignUpEvent(AchieveSettings settings) {
    	
    	SignUpView view = new SignUpView1(settings);
        SignUpController controller = new SignUpController1(view);
        
        view.registerObserver(controller);
        this.view.closeWindow();
    }
    
    @Override
    public void processBackEvent(AchieveSettings settings) {
    	MainMenuView view = new MainMenuView1(settings);
        MainMenuController controller = new MainMenuController1(view);
        
        view.registerObserver(controller);
        this.view.closeWindow();
    }

}
