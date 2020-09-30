/**
 * View for the main menu window. This is where each individual category can be accessed: accounts, categories, image requests, database options, quit.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package mainmenu;

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

public final class MainMenuView1 extends JFrame implements MainMenuView {

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
    private MainMenuController controller;

    /**
     * Create and Cancel buttons
     */
    private final JButton bAccount, bCategories, bImageRequest, bDatabase, bQuit;

    /**
     * Useful constants.
     */
    private static final int BUTTON_PANEL_GRID_ROWS = 1, BUTTON_PANEL_GRID_COLUMNS = 5;

    /**
     * Default constructor.
     */
    public MainMenuView1(AchieveSettings settings) {
        // Create the JFrame being extended

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Main Menu");

    	//Set local settings
    	this.settings = settings;
        
        // Set up the GUI widgets --------------------------------------------

        /*
         * Create widgets
         */
        this.bAccount = new JButton("Account");
        this.bCategories = new JButton("Categories");
        this.bImageRequest = new JButton("Image Requests");
        this.bDatabase = new JButton("Database Options");
        this.bQuit = new JButton("Quit");

        // Set up the GUI widgets --------------------------------------------

        /*
         * Account button will be disabled if local filesystem is being used
         * 
         * Categories and Image request will be disabled if not logged in
         * 
         * Categories will be disabled if the account is an artist account
         */
        if(settings.getUseLocal()) {
        	this.bAccount.setEnabled(false);
        } else if (!settings.getIsLoggedIn()) {
        	this.bCategories.setEnabled(false);
        	this.bImageRequest.setEnabled(false);
        } else if (settings.getAccountType().equals("artist")) {
        	this.bCategories.setEnabled(false);
        }
        

        /*
         * Create main button panel
         */
        GridLayout layout = new GridLayout(BUTTON_PANEL_GRID_COLUMNS, BUTTON_PANEL_GRID_ROWS);
        layout.setVgap(10);
        JPanel buttonPanel = new JPanel(layout);
        buttonPanel.setBorder(new EmptyBorder(10,10,10,10));

        /*
         * Add the buttons to the main button panel, from left to right and top
         * to bottom
         */
        buttonPanel.add(this.bAccount);
        buttonPanel.add(this.bCategories);
        buttonPanel.add(this.bImageRequest);
        buttonPanel.add(this.bDatabase);
        buttonPanel.add(this.bQuit);
        

        /*
         * Add scroll panes and button panel to main window, from left to right
         * and top to bottom
         */
        this.add(buttonPanel);

        // Set up the observers ----------------------------------------------

        /*
         * Register this object as the observer for all GUI events
         */
        this.bAccount.addActionListener(this);
        this.bCategories.addActionListener(this);
        this.bImageRequest.addActionListener(this);
        this.bDatabase.addActionListener(this);
        this.bQuit.addActionListener(this);

        // Set up the main application window --------------------------------

        /*
         * Make sure the main window is appropriately sized, centered, exits this program
         * on close, and becomes visible to the user
         */
        this.setPreferredSize(new Dimension(256, 512));
        this.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        

    }

    @Override
    public void registerObserver(MainMenuController controller) {

        this.controller = controller;

    }

    @Override
    public void updateCategoriesAllowed(boolean allowed) {

        this.bCategories.setEnabled(allowed);

    }
    
    @Override
    public void updateImageRequestAllowed(boolean allowed) {

        this.bImageRequest.setEnabled(allowed);

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
        if (source == this.bAccount) {
            this.controller.processAccountEvent(this.settings);
        } else if (source == this.bCategories) {
        	this.controller.processCategoriesEvent(this.settings);
        } else if (source == this.bImageRequest) {
        	this.controller.processImageRequestEvent(this.settings);
        } else if (source == this.bDatabase) {
        	this.controller.processDatabaseEvent(this.settings);
        } else {
        	this.controller.processQuitEvent();
        }
        /*
         * Set the cursor back to normal (because we changed it at the beginning
         * of the method body)
         */
        this.setCursor(Cursor.getDefaultCursor());
    }

}
