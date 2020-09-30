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
import achievement_image.AchieveImageController;
import achievement_image.AchieveImageController1;
import achievement_image.AchieveImageView;
import achievement_image.AchieveImageView1;
import mainmenu.MainMenuController;
import mainmenu.MainMenuController1;
import mainmenu.MainMenuView;
import mainmenu.MainMenuView1;

public final class ImageUploadController1 implements ImageUploadController {

    /**
     * View objects.
     */
    private ImageUploadView view;

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public ImageUploadController1(ImageUploadView view) {
        this.view = view;
    }
    
    @Override
    public void processAchievementEvent(AchieveSettings settings, String title, String category) {
    	AchieveImageView view = new AchieveImageView1(settings, title, category);
        AchieveImageController controller = new AchieveImageController1(view);
        
        view.registerObserver(controller);
    }
   
    
    @Override
    public void processBackEvent(AchieveSettings settings) {
    	MainMenuView view = new MainMenuView1(settings);
        MainMenuController controller = new MainMenuController1(view);
        
        view.registerObserver(controller);
        this.view.closeWindow();
    }

}
