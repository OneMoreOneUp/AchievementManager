/**
 * Controller for the database options window. From here settings for how to store the achievement data, images, and password encryption for user account passwords can be set.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package db_options;

import _main.AchieveSettings;
import adminpanel.AdminPanelController;
import adminpanel.AdminPanelController1;
import adminpanel.AdminPanelView;
import adminpanel.AdminPanelView1;
import mainmenu.MainMenuController;
import mainmenu.MainMenuController1;
import mainmenu.MainMenuView;
import mainmenu.MainMenuView1;

public final class DbOptionsController1 implements DbOptionsController {

    /**
     * View objects.
     */
    private DbOptionsView view;

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public DbOptionsController1(DbOptionsView view) {
        this.view = view;
    }

    @Override
    public void processUseFilesEvent(AchieveSettings settings, boolean useLocal) {
    	settings.setUseLocal(useLocal);
    	settings.saveDbConfig();
    	this.view.updateAwsFieldsAllowed(!useLocal);
    }
    
    @Override
    public void processSaveEvent(AchieveSettings settings, String awsAccessKey, String awsSecretKey, String awsEndpoint, String awsRegion,
    		String kmsAccessKey, String kmsSecretKey, String kmsEndpoint, String kmsRegion, String driveClientId, String driveClientSecret) {
    	//Set DynamoDb settings
    	settings.setDynamoDbAccessKey(awsAccessKey);
    	settings.setDynamoDbSecretKey(awsSecretKey);
    	settings.setDynamoDbEndpoint(awsEndpoint);
    	settings.setDynamoDbRegion(awsRegion);
    	
    	//Save KMS settings
    	settings.setKmsAccessKey(kmsAccessKey);
    	settings.setKmsSecretKey(kmsSecretKey);
    	settings.setKmsEndpoint(kmsEndpoint);
    	settings.setKmsRegion(kmsRegion);
    	
    	//Save Google Drive Settings
    	settings.setDriveClientId(driveClientId);
    	settings.setDriveClientSecret(driveClientSecret);
    	
    	//Save any changes to the database settings and attempt connections
    	settings.saveDbConfig();	
    	settings.storage.connectDatabase(settings);
    	settings.storage.connectKMS(settings);
    	settings.storage.connectDrive(settings);
    }

    @Override
    public void processAdminEvent(AchieveSettings settings) {
    	AdminPanelView view = new AdminPanelView1(settings);
        AdminPanelController controller = new AdminPanelController1(view);
        
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
