/**
 * Controller for the category window. This is where a single category of achievements can be seen.
 * From here the viewed category can be deleted and an individual achievement can be opened.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package category;

import _main.AchieveSettings;
import _main.AchieveStorage.Achievement;
import achievement.AchievementController;
import achievement.AchievementController1;
import achievement.AchievementView;
import achievement.AchievementView1;
import categories.CategoriesController;
import categories.CategoriesController1;
import categories.CategoriesView;
import categories.CategoriesView1;
import delete_category.DeleteCategoryController;
import delete_category.DeleteCategoryController1;
import delete_category.DeleteCategoryView;
import delete_category.DeleteCategoryView1;

public final class CategoryController1 implements CategoryController {

    /**
     * View objects.
     */
    private CategoryView view;

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public CategoryController1(CategoryView view) {
        this.view = view;
    }
    
    @Override
    public void processDeleteCategoryEvent(AchieveSettings settings, String category) {
    	DeleteCategoryView view = new DeleteCategoryView1(settings, category);
        DeleteCategoryController controller = new DeleteCategoryController1(view, this);
        
        view.registerObserver(controller);
    }
    
    @Override
    public void processAchievementEvent(AchieveSettings settings, String title, Achievement achievement, String category) {
    	AchievementView view = new AchievementView1(settings, title, achievement, category);
        AchievementController controller = new AchievementController1(view);
        
        view.registerObserver(controller);
    }
   
    
    @Override
    public void processBackEvent(AchieveSettings settings) {
    	CategoriesView view = new CategoriesView1(settings);
        CategoriesController controller = new CategoriesController1(view);
        
        view.registerObserver(controller);
        this.view.closeWindow();
    }

}
