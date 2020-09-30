/**
 * Controller for the image uploading window. This shows every achievement that currently does not have an image and how many do not have images.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package image_upload;

import _main.AchieveSettings;

public interface ImageUploadController {
    
    /**
     * Opens a new achievement image upload window for the achievement named $achievement
     */
    void processAchievementEvent(AchieveSettings settings, String title, String category);
    
    /**
     * Disposes this window and creates a new main menu
     */
    void processBackEvent(AchieveSettings settings);

}
