/**
 * Controller for the admin panel window. This is where achievement storage can be initialized.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package adminpanel;

import _main.AchieveSettings;
import db_options.DbOptionsController;
import db_options.DbOptionsController1;
import db_options.DbOptionsView;
import db_options.DbOptionsView1;

public final class AdminPanelController1 implements AdminPanelController {

    /**
     * View objects.
     */
    private AdminPanelView view;

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public AdminPanelController1(AdminPanelView view) {
        this.view = view;
    }

    @Override
    public void processCreateDbEvent(AchieveSettings settings) {
    	settings.storage.createDatabase();
    }
    
    @Override
    public void processCreateKeyEvent(AchieveSettings settings) {
    	settings.storage.createKey();
    }
    
    @Override
    public void processBackEvent(AchieveSettings settings) {
    	DbOptionsView view = new DbOptionsView1(settings);
        DbOptionsController controller = new DbOptionsController1(view);
        
        view.registerObserver(controller);
        this.view.closeWindow();
    }

}
