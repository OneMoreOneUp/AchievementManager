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
import categories.CategoriesController;
import categories.CategoriesController1;
import categories.CategoriesView;
import categories.CategoriesView1;
import upload_image.UploadImageView1;

public final class CreateAchieveController1 implements CreateAchieveController {

    /**
     * View objects.
     */
    private CreateAchieveView view;

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public CreateAchieveController1(CreateAchieveView view) {
        this.view = view;
    }
    
    @Override
	public void processUploadImageEvent(CreateAchieveView createView) {
    	new UploadImageView1(createView, null);
    }
	
    @Override
    public void processCreateEvent(AchieveSettings settings, String title, String description, int maxProg, String category, String imageURL, File image) {
    	if (title != null && category != null && settings.storage.isUnique(title, category)) {
    		if(settings.getUseLocal()) {
    			//Create achievement and image file
    			//TODO: Upload achievement to file system
    		} else {
    			//Upload image to database and generate a new imageURL
        		if(imageURL == null && image != null) {
        			imageURL = settings.storage.createImageURL(title, category, image);
        		}
        		//Upload achievement to database
    			settings.storage.createAchievement(title, category, description, maxProg, imageURL);
    		}
    	}
    	this.processBackEvent(settings);
    }
    
    @Override
    public void processBackEvent(AchieveSettings settings) {
    	CategoriesView view = new CategoriesView1(settings);
        CategoriesController controller = new CategoriesController1(view);
        
        view.registerObserver(controller);
        this.view.closeWindow();
    }

}
