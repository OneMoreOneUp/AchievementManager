/**
 * View for the achievement deletion window. This is just a small Yes, No window to confirm the deletion of an achievement.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package delete_achievement;

import java.awt.event.ActionListener;

public interface DeleteAchieveView extends ActionListener {

    /**
     * Register argument as observer/listener of this; this must be done first,
     * before any other methods of this class are called.
     * 
     * @param controller
     *            controller to register
     */
    void registerObserver(DeleteAchieveController controller);

    /**
     * Closes the window
     */
    void closeWindow();
}
