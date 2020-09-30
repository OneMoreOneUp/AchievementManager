/**
 * View for the category window. This is where a single category of achievements can be seen.
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

import java.awt.event.ActionListener;

public interface CategoryView extends ActionListener {

    /**
     * Register argument as observer/listener of this; this must be done first,
     * before any other methods of this class are called.
     * 
     * @param controller
     *            controller to register
     */
    void registerObserver(CategoryController controller);

    /**
     * Closes the window
     */
    void closeWindow();
}
