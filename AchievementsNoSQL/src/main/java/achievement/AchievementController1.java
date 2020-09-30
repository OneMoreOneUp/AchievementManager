/**
 * Controller for the achievement window. Process deletion and editing of achievements.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package achievement;

import _main.AchieveSettings;
import delete_achievement.DeleteAchieveController;
import delete_achievement.DeleteAchieveController1;
import delete_achievement.DeleteAchieveView;
import delete_achievement.DeleteAchieveView1;

public final class AchievementController1 implements AchievementController {

    /**
     * View objects.
     */
    private AchievementView view;

    /**
     * Constructor.
     *
     * @param view
     *            view to connect to
     */
    public AchievementController1(AchievementView view) {
        this.view = view;
    }

    @Override
    public void processEditEvent(AchieveSettings settings) {
    }

    @Override
    public void processDeleteEvent(AchieveSettings settings, String achievement, String category) {
    	DeleteAchieveView view = new DeleteAchieveView1(settings, category, achievement);
        DeleteAchieveController controller = new DeleteAchieveController1(view, this.view);
        
        view.registerObserver(controller);
    }
    
    @Override
    public void processBackEvent(AchieveSettings settings) {
        this.view.closeWindow();
    }

}
