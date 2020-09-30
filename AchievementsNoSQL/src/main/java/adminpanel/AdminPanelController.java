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

public interface AdminPanelController {

    /**
     * Attempts to create two new tables for the database using properties stored in $settings
     * 
     * The following tables will attempt to be created:
     * 		Achieve_Accounts(username, password, type)
     * 		Achieve_Achievements(title, description, curr_prog, max_prog, image_url, category)
     */
    void processCreateDbEvent(AchieveSettings settings);
    
    /**
     * Attempts to create a new password encryption key in KMS
     */
    void processCreateKeyEvent(AchieveSettings settings);
    
    /**
     * Disposes this window and creates a database options panel
     */
    void processBackEvent(AchieveSettings settings);

}
