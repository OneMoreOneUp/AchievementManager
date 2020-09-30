/**
 * View for the achievement image window. This is where an image can be uploaded to attach to an achievement.
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

import java.awt.event.ActionListener;
import java.io.File;

public interface AchieveImageView extends ActionListener {

    /**
     * Register argument as observer/listener of this; this must be done first,
     * before any other methods of this class are called.
     * 
     * @param controller
     *            controller to register
     */
    void registerObserver(AchieveImageController controller);

    /**
     * Closes the window
     */
    void closeWindow();

    /**
     * Sets the image url and attempts to set the image
     * 
     * @param imageURL
     * 	The url of the image
     */
	void setImageFromURL(String imageURL);

	/**
	 * Attempts to set the image
	 * 
	 * @param imageFile
	 * 	The file to read the image from
	 */
	void setImageFromFile(File imageFile);
}
