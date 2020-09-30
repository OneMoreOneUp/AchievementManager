/**
 * Controller for the category deletion window. This is just a small Yes, No window to confirm the deletion of an category.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package delete_category;

import _main.AchieveSettings;
import category.CategoryController;

public final class DeleteCategoryController1 implements DeleteCategoryController {

    /**
     * View objects.
     */
    private DeleteCategoryView view;
    private CategoryController parentController;

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public DeleteCategoryController1(DeleteCategoryView view, CategoryController parentController) {
        this.view = view;
        this.parentController = parentController;
    }

    @Override
    public void processYesEvent(AchieveSettings settings, String category) {
    	/*
    	 * Delete achievement
    	 */
    	settings.storage.deleteCategory(category);

    	//Close window
    	this.parentController.processBackEvent(settings);
        this.view.closeWindow();
    }

    @Override
    public void processNoEvent() {
    	this.view.closeWindow();
    }

}
