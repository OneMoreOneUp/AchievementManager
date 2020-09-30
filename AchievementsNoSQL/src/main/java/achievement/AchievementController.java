/**
 * Controller for the achievement window. Process deletion and editing of achievements.
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

import _main.AchieveSettings;

public interface AchievementController {
    
    /**
     * Opens a new instance of an edit window
     */
    void processEditEvent(AchieveSettings settings);
    
    /**
     * Deletes achievement from database and closes the window
     */
    void processDeleteEvent(AchieveSettings settings, String achievement, String category);
    
    /**
     * Disposes this window and any open edit windows
     */
    void processBackEvent(AchieveSettings settings);

}
