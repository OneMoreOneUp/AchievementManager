/**
 * Controller for the achievement image window. This is where an image can be uploaded to attach to an achievement.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package achievement_image;

import java.io.File;

import _main.AchieveSettings;

public interface AchieveImageController {
    
	/**
	 * Creates a new upload image window
	 */
	void processUploadImageEvent(AchieveImageView view);
	
    /**
     * Attempts to set the image for the achievement $title, $category
     */
    void processUploadEvent(AchieveSettings settings, String title, String category, String imageURL, File image);
    
    /**
     * Disposes this window
     */
    void processBackEvent(AchieveSettings settings);

}
