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
import upload_image.UploadImageView1;

public final class AchieveImageController1 implements AchieveImageController {

    /**
     * View objects.
     */
    private AchieveImageView view;

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public AchieveImageController1(AchieveImageView view) {
        this.view = view;
    }
    
    @Override
	public void processUploadImageEvent(AchieveImageView imageView) {
    	new UploadImageView1(null, imageView);
    }
	
    @Override
    public void processUploadEvent(AchieveSettings settings, String title, String category, String imageURL, File image) {
    	if(settings.getUseLocal()) {
    		//Create achievement and image file
    		//TODO: Upload achievement to file system
    	} else {
    		//Upload image to database and generate a new imageURL
        	if(imageURL == null && image != null) {
        		imageURL = settings.storage.createImageURL(title, category, image);
        	}
        	//Upload achievement to database
    		settings.storage.changeAchievementImage(title, category, imageURL);
    	}
    	this.processBackEvent(settings);
    }
    
    @Override
    public void processBackEvent(AchieveSettings settings) {
        this.view.closeWindow();
    }

}
