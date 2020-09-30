/**
 * View for the sign up window. This is where a user can sign up for an account.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package signup;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import _main.AchieveSettings;

public final class SignUpView1 extends JFrame implements SignUpView {

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
    private SignUpController controller;

    /**
     * JComponents
     */
    private final JLabel lUsername, lPassword, lConfirmPassword, lEmail;
    private final JTextField tUsername, tEmail;
    private final JPasswordField tPassword, tConfirmPassword;
    private final JButton bSignUp, bBack;

    /**
     * Useful constants.
     */
    private static final int MAIN_PANEL_GRID_ROWS = 5, MAIN_PANEL_GRID_COLUMNS = 2;

    /**
     * Default constructor.
     */
    public SignUpView1(AchieveSettings settings) {
        // Create the JFrame being extended

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Account Sign Up");
        
        //Set local settings
    	this.settings = settings;

        // Set up the GUI widgets --------------------------------------------

        /*
         * Create widgets
         */
    	//Labels
        this.lUsername = new JLabel("Username: ");
        this.lEmail = new JLabel("Email: ");
        this.lPassword = new JLabel("Password: ");
        this.lConfirmPassword = new JLabel("Retype Password: ");
        
        //Buttons
        this.bSignUp = new JButton("Sign Up");
        this.bBack = new JButton("Back");
        
        //Text fields
        this.tUsername = new JTextField();
        this.tEmail = new JTextField();
        this.tPassword = new JPasswordField();
        this.tConfirmPassword = new JPasswordField();
        
        

        // Set up the GUI widgets --------------------------------------------
        
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
        mainPanel.add(this.lUsername);
        mainPanel.add(this.tUsername);
        mainPanel.add(this.lEmail);
        mainPanel.add(this.tEmail);
        mainPanel.add(this.lPassword);
        mainPanel.add(this.tPassword);
        mainPanel.add(this.lConfirmPassword);
        mainPanel.add(this.tConfirmPassword);
        mainPanel.add(this.bSignUp);
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
        this.bSignUp.addActionListener(this);
        
        // Set up the main application window --------------------------------

        /*
         * Make sure the main window is appropriately sized, centered, exits this program
         * on close, and becomes visible to the user
         */
        this.setPreferredSize(new Dimension(320, 256));
        this.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        

    }

    @Override
    public void registerObserver(SignUpController controller) {

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
        if (source == this.bSignUp) {
        	this.controller.processSignUpEvent(settings, this.tUsername.getText(), this.tEmail.getText(), this.tPassword.getPassword(), this.tConfirmPassword.getPassword());
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
