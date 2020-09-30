/**
 * Controller for the create achievement window. Achievement is made up of a title, description (optional), current/max progress, a category, and an image (optional).
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package create_achievement;

import java.io.File;

import _main.AchieveSettings;

public interface CreateAchieveController {
    
	/**
	 * Creates a new upload image window
	 */
	void processUploadImageEvent(CreateAchieveView view);
	
    /**
     * Attempts to create an achievement
     */
    void processCreateEvent(AchieveSettings settings, String title, String description, int maxProg, String category, String imageURL, File image);
    
    /**
     * Disposes this window and creates a new main menu
     */
    void processBackEvent(AchieveSettings settings);

}
