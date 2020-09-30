/**
 * View for the sign up window. This is where a user can sign up for an account.
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

import java.awt.event.ActionListener;

public interface SignUpView extends ActionListener {

    /**
     * Register argument as observer/listener of this; this must be done first,
     * before any other methods of this class are called.
     * 
     * @param controller
     *            controller to register
     */
    void registerObserver(SignUpController controller);

    /**
     * Closes the window
     */
    void closeWindow();
}
