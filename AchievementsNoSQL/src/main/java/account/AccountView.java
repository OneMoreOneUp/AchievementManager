/**
 * The account view. This is where login and signup options are.
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

import java.awt.event.ActionListener;

public interface AccountView extends ActionListener {

    /**
     * Register argument as observer/listener of this; this must be done first,
     * before any other methods of this class are called.
     * 
     * @param controller
     *            controller to register
     */
    void registerObserver(AccountController controller);
    
    /**
     * Updates the text that is in the password field.
     * 
     * @param password
     * 		The text to put in the password field
     */
    void updatePasswordField(String password);
    
    /**
     * Updates if remember me checkbox is checked.
     * 
     * @param allowed
     *            true iff create is allowed
     */
    void updateRememberMeCheck(boolean checked);
    
    /**
     * Updates display of whether logging out is allowed.
     * 
     * @param allowed
     *            true iff create is allowed
     */
    void updateLogoutAllowed(boolean allowed);

    /**
     * Closes the window
     */
    void closeWindow();
}
