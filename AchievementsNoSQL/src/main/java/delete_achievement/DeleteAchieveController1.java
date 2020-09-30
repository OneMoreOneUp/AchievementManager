/**
 * Controller for the achievement deletion window. This is just a small Yes, No window to confirm the deletion of an achievement.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package delete_achievement;

import _main.AchieveSettings;
import achievement.AchievementView;

public final class DeleteAchieveController1 implements DeleteAchieveController {

    /**
     * View objects.
     */
    private DeleteAchieveView view;
    private AchievementView parentView;

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public DeleteAchieveController1(DeleteAchieveView view, AchievementView parentView) {
        this.view = view;
        this.parentView = parentView;
    }

    @Override
    public void processYesEvent(AchieveSettings settings, String category, String title) {
    	/*
    	 * Delete achievement
    	 */
    	settings.storage.deleteAchievement(title, category);

    	//Close window
    	this.parentView.closeWindow();
        this.view.closeWindow();
    }

    @Override
    public void processNoEvent() {
    	this.view.closeWindow();
    }

}
