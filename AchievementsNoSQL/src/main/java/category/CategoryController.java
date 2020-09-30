/**
 * Controller for the category window. This is where a single category of achievements can be seen.
 * From here the viewed category can be deleted and an individual achievement can be opened.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package category;

import _main.AchieveSettings;
import _main.AchieveStorage.Achievement;

public interface CategoryController {

	/**
	 * Deletes all achievements in this category
	 * 
	 * Closes the window and opens the categories window 
	 */
    void processDeleteCategoryEvent(AchieveSettings settings, String category);
    
    /**
     * Opens a new achievement window for the achievement named $achievement
     */
    void processAchievementEvent(AchieveSettings settings, String title, Achievement achievement, String category);
    
    /**
     * Disposes this window and creates a new main menu
     */
    void processBackEvent(AchieveSettings settings);

}
