/**
 * View for the categories window. This is where all of the different categories that contain achievements can be found.
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

import java.awt.event.ActionListener;

public interface CategoriesView extends ActionListener {

    /**
     * Register argument as observer/listener of this; this must be done first,
     * before any other methods of this class are called.
     * 
     * @param controller
     *            controller to register
     */
    void registerObserver(CategoriesController controller);

    /**
     * Closes the window
     */
    void closeWindow();
}
