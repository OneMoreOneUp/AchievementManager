/**
 * Controller for the achievement deletion window. This is just a small Yes, No window to confirm the deletion of an achievement.
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

import _main.AchieveSettings;

public interface DeleteAchieveController {

    /**
     * Deletes the achievement in the given $category with the $title
     * 
     */
    void processYesEvent(AchieveSettings settings, String category, String title);

    /**
     * Closes the window
     */
    void processNoEvent();

}
