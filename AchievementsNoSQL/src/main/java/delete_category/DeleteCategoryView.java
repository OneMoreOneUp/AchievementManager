/**
 * View for the category deletion window. This is just a small Yes, No window to confirm the deletion of an category.
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

import java.awt.event.ActionListener;

public interface DeleteCategoryView extends ActionListener {

    /**
     * Register argument as observer/listener of this; this must be done first,
     * before any other methods of this class are called.
     * 
     * @param controller
     *            controller to register
     */
    void registerObserver(DeleteCategoryController controller);

    /**
     * Closes the window
     */
    void closeWindow();
}
