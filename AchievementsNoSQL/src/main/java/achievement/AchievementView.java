/**
 * View for the achievement window. This is where a single achievement can be viewed.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package achievement;

import java.awt.event.ActionListener;

public interface AchievementView extends ActionListener {

    /**
     * Register argument as observer/listener of this; this must be done first,
     * before any other methods of this class are called.
     * 
     * @param controller
     *            controller to register
     */
    void registerObserver(AchievementController controller);

    /**
     * Closes the window
     */
    void closeWindow();
}
