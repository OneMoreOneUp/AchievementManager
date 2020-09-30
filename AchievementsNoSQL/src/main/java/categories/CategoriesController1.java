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
import category.CategoryController;
import category.CategoryController1;
import category.CategoryView;
import category.CategoryView1;
import create_achievement.CreateAchieveController;
import create_achievement.CreateAchieveController1;
import create_achievement.CreateAchieveView;
import create_achievement.CreateAchieveView1;
import mainmenu.MainMenuController;
import mainmenu.MainMenuController1;
import mainmenu.MainMenuView;
import mainmenu.MainMenuView1;

public final class CategoriesController1 implements CategoriesController {

    /**
     * View objects.
     */
    private CategoriesView view;

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public CategoriesController1(CategoriesView view) {
        this.view = view;
    }

    @Override
    public void processNewAchieveEvent(AchieveSettings settings) {	
    	CreateAchieveView view = new CreateAchieveView1(settings);
        CreateAchieveController controller = new CreateAchieveController1(view);
        
        view.registerObserver(controller);
    	this.view.closeWindow();
    }
    
    @Override
    public void processCategoryEvent(AchieveSettings settings, String category, double percentage) {
    	CategoryView view = new CategoryView1(settings, category, percentage);
        CategoryController controller = new CategoryController1(view);
        
        view.registerObserver(controller);
    	this.view.closeWindow();
    }
    
    @Override
    public void processBackEvent(AchieveSettings settings) {
    	MainMenuView view = new MainMenuView1(settings);
        MainMenuController controller = new MainMenuController1(view);
        
        view.registerObserver(controller);
        this.view.closeWindow();
    }

}
