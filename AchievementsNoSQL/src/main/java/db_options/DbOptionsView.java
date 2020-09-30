/**
 * View for the database options window. From here settings for how to store the achievement data, images, and password encryption for user account passwords can be set.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package db_options;

import java.awt.event.ActionListener;

public interface DbOptionsView extends ActionListener {

    /**
     * Register argument as observer/listener of this; this must be done first,
     * before any other methods of this class are called.
     * 
     * @param controller
     *            controller to register
     */
    void registerObserver(DbOptionsController controller);

    /**
     * Updates display of whether categories operation is allowed.
     * 
     * @param allowed
     *            true iff create is allowed
     */
    void updateAwsFieldsAllowed(boolean allowed);

    /**
     * Closes the window
     */
    void closeWindow();
}
