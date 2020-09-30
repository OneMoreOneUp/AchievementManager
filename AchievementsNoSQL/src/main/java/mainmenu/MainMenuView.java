/**
 * View for the main menu window. This is where each individual category can be accessed: accounts, categories, image requests, database options, quit.
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

import java.awt.event.ActionListener;

public interface MainMenuView extends ActionListener {

    /**
     * Register argument as observer/listener of this; this must be done first,
     * before any other methods of this class are called.
     * 
     * @param controller
     *            controller to register
     */
    void registerObserver(MainMenuController controller);

    /**
     * Updates display of whether categories operation is allowed.
     * 
     * @param allowed
     *            true iff create is allowed
     */
    void updateCategoriesAllowed(boolean allowed);
    
    /**
     * Updates display of whether image request operation is allowed.
     * 
     * @param allowed
     *            true iff create is allowed
     */
    void updateImageRequestAllowed(boolean allowed);

    /**
     * Closes the window
     */
    void closeWindow();
}
