/**
 * View for the admin panel window. This is where achievement storage can be initialized.
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

import java.awt.event.ActionListener;

public interface AdminPanelView extends ActionListener {

    /**
     * Register argument as observer/listener of this; this must be done first,
     * before any other methods of this class are called.
     * 
     * @param controller
     *            controller to register
     */
    void registerObserver(AdminPanelController controller);

    /**
     * Closes the window
     */
    void closeWindow();
}
