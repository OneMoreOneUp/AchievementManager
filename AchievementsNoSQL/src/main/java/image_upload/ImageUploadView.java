/**
 * View for the image uploading window. This shows every achievement that currently does not have an image and how many do not have images.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package image_upload;

import java.awt.event.ActionListener;

public interface ImageUploadView extends ActionListener {

    /**
     * Register argument as observer/listener of this; this must be done first,
     * before any other methods of this class are called.
     * 
     * @param controller
     *            controller to register
     */
    void registerObserver(ImageUploadController controller);

    /**
     * Closes the window
     */
    void closeWindow();
}
