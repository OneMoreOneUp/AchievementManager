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

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import _main.AchieveSettings;

public final class DeleteCategoryView1 extends JFrame implements DeleteCategoryView {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Local stored settings
	 */
	private AchieveSettings settings;
	private String category;

	/**
     * Controller object registered with this view to observe user-interaction
     * events.
     */
    private DeleteCategoryController controller;

    /**
     * Widgets
     */
    private final JButton bYes, bNo;
    private final JLabel lAsk;
    

    /**
     * Default constructor.
     */
    public DeleteCategoryView1(AchieveSettings settings, String category) {
        // Create the JFrame being extended

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Delete Category: " + category + "?");

    	//Set local settings
    	this.settings = settings;
    	this.category = category;
        
        // Set up the GUI widgets --------------------------------------------

        /*
         * Create widgets
         */
        this.bYes = new JButton("Yes");
        this.bNo = new JButton("No");
        this.lAsk = new JLabel("Are you sure you want to delete: " + category + "?");

        // Set up the GUI widgets --------------------------------------------
        

        /*
         * Create main button panel
         */
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        GridBagConstraints mainConstraints = new GridBagConstraints();
        	mainConstraints.weightx = 1;
        	mainConstraints.insets = new Insets(3,3,3,3);
        	mainConstraints.fill = GridBagConstraints.HORIZONTAL;

        /*
         * Add the buttons to the main button panel, from left to right and top
         * to bottom
         */
        	mainConstraints.gridwidth = 2;
        mainPanel.add(this.lAsk, mainConstraints);
        	mainConstraints.gridy = 1;
        	mainConstraints.gridwidth = 1;
        mainPanel.add(this.bYes, mainConstraints);
        	mainConstraints.gridx = 1;
        mainPanel.add(this.bNo, mainConstraints);
        

        /*
         * Add scroll panes and button panel to main window, from left to right
         * and top to bottom
         */
        this.add(mainPanel);

        // Set up the observers ----------------------------------------------

        /*
         * Register this object as the observer for all GUI events
         */
        this.bYes.addActionListener(this);
        this.bNo.addActionListener(this);

        // Set up the main application window --------------------------------

        /*
         * Make sure the main window is appropriately sized, centered, exits this program
         * on close, and becomes visible to the user
         */
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        

    }

    @Override
    public void registerObserver(DeleteCategoryController controller) {

        this.controller = controller;

    }
    
    public void closeWindow() {
    	this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        /*
         * Set cursor to indicate computation on-going; this matters only if
         * processing the event might take a noticeable amount of time as seen
         * by the user
         */
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        /*
         * Determine which event has occurred that we are being notified of by
         * this callback; in this case, the source of the event (i.e, the widget
         * calling actionPerformed) is all we need because only buttons are
         * involved here, so the event must be a button press; in each case,
         * tell the controller to do whatever is needed to update the model and
         * to refresh the view
         */
        Object source = event.getSource();
        if (source == this.bYes) {
            this.controller.processYesEvent(this.settings, this.category);
        } else {
        	this.controller.processNoEvent();
        }
        /*
         * Set the cursor back to normal (because we changed it at the beginning
         * of the method body)
         */
        this.setCursor(Cursor.getDefaultCursor());
    }

}
