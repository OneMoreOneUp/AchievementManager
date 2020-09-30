/**
 * Controller for the categories window. This is where all of the different categories that contain achievements can be found.
 * From here a new achievement can be created or we can open a category.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package categories;

import _main.AchieveSettings;

public interface CategoriesController {
    
    /**
	 * Opens a new window to create a new achievement
     * 
     */
    void processNewAchieveEvent(AchieveSettings settings);
    
    /**
	 * Opens a new window to view an achievement
     * 
     */
    void processCategoryEvent(AchieveSettings settings, String category, double percentage);
    
    
    /**
     * Disposes this window and creates a new main menu
     */
    void processBackEvent(AchieveSettings settings);

}
