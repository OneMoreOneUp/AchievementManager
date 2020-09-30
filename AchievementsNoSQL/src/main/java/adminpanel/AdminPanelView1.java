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

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import _main.AchieveSettings;

public final class AdminPanelView1 extends JFrame implements AdminPanelView {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Local stored settings
	 */
	private AchieveSettings settings;

	/**
     * Controller object registered with this view to observe user-interaction
     * events.
     */
    private AdminPanelController controller;

    /**
     * JComponents
     */
    private final JButton bCreateDb, bCreateKey, bBack;

    /**
     * Useful constants.
     */
    private static final int MAIN_PANEL_GRID_ROWS = 3, MAIN_PANEL_GRID_COLUMNS = 1;

    /**
     * Default constructor.
     */
    public AdminPanelView1(AchieveSettings settings) {
        // Create the JFrame being extended

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Admin Panel");
        
        //Set local settings
    	this.settings = settings;

        // Set up the GUI widgets --------------------------------------------

        /*
         * Create widgets
         */
        this.bCreateDb = new JButton("New NoSQL Database");
        this.bCreateKey = new JButton("New KMS Key");
        this.bBack = new JButton("Back");
        
        /*
         * Create main panel
         */
        GridLayout mainLayout = new GridLayout(MAIN_PANEL_GRID_ROWS, MAIN_PANEL_GRID_COLUMNS);
        mainLayout.setVgap(10);
        mainLayout.setHgap(10);
        JPanel mainPanel = new JPanel(mainLayout);
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        
        /*
         * Add widgets to main panel
         */
        mainPanel.add(this.bCreateDb);
        mainPanel.add(this.bCreateKey);
        mainPanel.add(this.bBack);
       

        /*
         * Add panel to this
         */
        this.add(mainPanel);
        
        // Set up the observers ----------------------------------------------

        /*
         * Register this object as the observer for all GUI events
         */
        this.bBack.addActionListener(this);
        this.bCreateDb.addActionListener(this);
        this.bCreateKey.addActionListener(this);
        
        // Set up the main application window --------------------------------

        /*
         * Make sure the main window is appropriately sized, centered, exits this program
         * on close, and becomes visible to the user
         */
        this.setPreferredSize(new Dimension(256, 256));
        this.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        

    }

    @Override
    public void registerObserver(AdminPanelController controller) {

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
        if (source == this.bCreateDb) {
            this.controller.processCreateDbEvent(settings);
        } else if (source == this.bCreateKey) {
        	this.controller.processCreateKeyEvent(settings);
        } else {
        	this.controller.processBackEvent(settings);
        }
        /*
         * Set the cursor back to normal (because we changed it at the beginning
         * of the method body)
         */
        this.setCursor(Cursor.getDefaultCursor());
    }

}
