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

public interface DeleteCategoryController {

    /**
     * Deletes all achievements in the given $category
     * 
     */
    void processYesEvent(AchieveSettings settings, String category);

    /**
     * Closes the window
     */
    void processNoEvent();

}
