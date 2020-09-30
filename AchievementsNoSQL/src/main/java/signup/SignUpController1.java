/**
 * Controller for the sign up window. This is where a user can sign up for an account.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package signup;

import java.util.Arrays;

import _main.AchieveSettings;
import account.AccountController;
import account.AccountController1;
import account.AccountView;
import account.AccountView1;

public final class SignUpController1 implements SignUpController {

    /**
     * View objects.
     */
    private SignUpView view;

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public SignUpController1(SignUpView view) {
        this.view = view;
    }

    @Override
    public void processSignUpEvent(AchieveSettings settings, String username, String email, char[] password1, char[] password2) {
    	if(Arrays.equals(password1, password2)) {
    		String password = "";
    		for(char c : password1) {
    			password += c;
    		}
    		if(settings.storage.createAccount(username, email, password, settings)) {
    			this.processBackEvent(settings);
    		}
    	} else {
    		System.err.println("[WARNING] Passwords do not match.");
    	}
    }
    
    @Override
    public void processBackEvent(AchieveSettings settings) {
    	AccountView view = new AccountView1(settings);
        AccountController controller = new AccountController1(view);
        
        view.registerObserver(controller);
        this.view.closeWindow();
    }

}
