/**
 * Controller for the main menu window. This is where each individual category can be accessed: accounts, categories, image requests, database options, quit.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package mainmenu;

import _main.AchieveSettings;
import account.AccountController;
import account.AccountController1;
import account.AccountView;
import account.AccountView1;
import categories.CategoriesController;
import categories.CategoriesController1;
import categories.CategoriesView;
import categories.CategoriesView1;
import db_options.DbOptionsController;
import db_options.DbOptionsController1;
import db_options.DbOptionsView;
import db_options.DbOptionsView1;
import image_upload.ImageUploadController;
import image_upload.ImageUploadController1;
import image_upload.ImageUploadView;
import image_upload.ImageUploadView1;

public final class MainMenuController1 implements MainMenuController {

    /**
     * View objects.
     */
    private MainMenuView view;

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public MainMenuController1(MainMenuView view) {
        this.view = view;
    }

    @Override
    public void processAccountEvent(AchieveSettings settings) {
    	AccountView view = new AccountView1(settings);
        AccountController controller = new AccountController1(view);
        
        view.registerObserver(controller);
    	
    	this.view.closeWindow();
    }
    
    @Override
    public void processCategoriesEvent(AchieveSettings settings) {
    	CategoriesView view = new CategoriesView1(settings);
    	CategoriesController controller = new CategoriesController1(view);
    	
    	view.registerObserver(controller);
    	
    	this.view.closeWindow();
    }
    
    @Override
    public void processImageRequestEvent(AchieveSettings settings) {
    	ImageUploadView view = new ImageUploadView1(settings);
    	ImageUploadController controller = new ImageUploadController1(view);
    	
    	view.registerObserver(controller);
    	
    	this.view.closeWindow();
    }
    
    @Override
    public void processDatabaseEvent(AchieveSettings settings) {
    	DbOptionsView view = new DbOptionsView1(settings);
        DbOptionsController controller = new DbOptionsController1(view);
        
        view.registerObserver(controller);
    	
    	this.view.closeWindow();
    }

    @Override
    public void processQuitEvent() {
    	this.view.closeWindow();
    }

}
