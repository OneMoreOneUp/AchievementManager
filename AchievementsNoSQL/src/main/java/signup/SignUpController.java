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

import _main.AchieveSettings;

public interface SignUpController {
    
    /**
     * Attempts to create a new login with the username and password. The default account type is set to artist.
     */
    void processSignUpEvent(AchieveSettings settings, String username, String email, char[] password1, char[] password2);
    
    /**
     * Disposes this window and creates a new main menu
     */
    void processBackEvent(AchieveSettings settings);

}
