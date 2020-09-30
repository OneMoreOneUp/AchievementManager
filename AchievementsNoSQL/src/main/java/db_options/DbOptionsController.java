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

public interface DbOptionsController {
    
    /**
     * Sets the local property of the AWS Access Key, AWS Secret Key, AWS Endpoint, AWS Region
     * 
     * @updates settings
     */
    void processSaveEvent(AchieveSettings settings, String awsAccessKey, String awsSecretKey, String awsEndpoint, String awsRegion,
    		String kmsAccessKey, String kmsSecretKey, String kmsEndpoint, String kmsRegion, String driveClientId, String driveClientSecret);
    
    /**
     * Sets the local property to use local files
     * Updates if the AWS Fields are editable
     * 
     * @updates settings
     */
    void processUseFilesEvent(AchieveSettings settings, boolean useLocal);
    
    /**
     * Opens the admin panel and disposes of this window
     * 
     */
    void processAdminEvent(AchieveSettings settings);
    
    /**
     * Disposes this window and creates a new main menu
     */
    void processBackEvent(AchieveSettings settings);

}
