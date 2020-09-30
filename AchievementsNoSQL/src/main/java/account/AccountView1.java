/**
 * The account view. This is where login and signup options are.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package account;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import _main.AchieveSettings;

public final class AccountView1 extends JFrame implements AccountView {

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
    private AccountController controller;

    /**
     * JComponents
     */
    private final JLabel lUsername, lPassword, lRememberMe;
    private final JTextField tUsername;
    private final JPasswordField tPassword;
    private final JButton bLogin, bLogout, bSignUp, bBack;
    private final JCheckBox cRememberMe;

    /**
     * Useful constants.
     */
    private static final int MAIN_PANEL_GRID_ROWS = 5, MAIN_PANEL_GRID_COLUMNS = 1, 
    		LOGIN_PANEL_GRID_ROWS = 3, LOGIN_PANEL_GRID_COLUMNS = 2;

    /**
     * Default constructor.
     */
    public AccountView1(AchieveSettings settings) {
        // Create the JFrame being extended

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Account Login");
        
        //Set local settings
    	this.settings = settings;

        // Set up the GUI widgets --------------------------------------------

        /*
         * Create widgets
         */
    	//Labels
        this.lUsername = new JLabel("Username: ");
        this.lPassword = new JLabel("Password: ");
        this.lRememberMe = new JLabel("Remember Login? ");
        
        //Buttons
        this.bLogin = new JButton("Login");
        this.bLogout = new JButton("Logout");
        this.bSignUp = new JButton("Sign Up");
        this.bBack = new JButton("Back");
        
        //Checkbox
        this.cRememberMe = new JCheckBox();
        
        //Text fields
        this.tUsername = new JTextField(settings.getUsername());
        this.tPassword = new JPasswordField();
        

        // Set up the GUI widgets --------------------------------------------

        /*
         * Checks the remember login box based on local settings and fills in password if it's true
         * 
         * Logout button will be greyed out if not logged in
         */
        this.cRememberMe.setSelected(settings.getRememberLogin());
        if(settings.getRememberLogin()) this.tPassword.setText(settings.getPassword());
        if(!settings.getIsLoggedIn()) this.bLogout.setEnabled(false);
        
        
        /*
         * Create login panel
         */
        GridLayout loginLayout = new GridLayout(LOGIN_PANEL_GRID_ROWS, LOGIN_PANEL_GRID_COLUMNS);
        loginLayout.setVgap(10);
        loginLayout.setHgap(10);
        JPanel loginPanel = new JPanel(loginLayout);
        
        /*
         * Add widgets to login panel
         */
        loginPanel.add(this.lUsername);
        loginPanel.add(this.tUsername);
        loginPanel.add(this.lPassword);
        loginPanel.add(this.tPassword);
        loginPanel.add(this.lRememberMe);
        loginPanel.add(this.cRememberMe);
        
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
        mainPanel.add(loginPanel);
        mainPanel.add(this.bLogin);
        mainPanel.add(this.bLogout);
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
        this.bLogin.addActionListener(this);
        this.bLogout.addActionListener(this);
        this.bSignUp.addActionListener(this);
        
        // Set up the main application window --------------------------------

        /*
         * Make sure the main window is appropriately sized, centered, exits this program
         * on close, and becomes visible to the user
         */
        this.setPreferredSize(new Dimension(384, 384));
        this.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        

    }

    @Override
    public void registerObserver(AccountController controller) {

        this.controller = controller;

    }
    
    @Override
    public void updatePasswordField(String password) {
    	this.tPassword.setText(password);
    }
    
    @Override
    public void updateRememberMeCheck(boolean check) {
    	this.cRememberMe.setSelected(check);
    }
    
    @Override
    public void updateLogoutAllowed(boolean allowed) {
    	this.bLogout.setEnabled(allowed);
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
        if (source == this.bLogin) {
            this.controller.processLoginEvent(settings, this.tUsername.getText(), this.tPassword.getPassword(), this.cRememberMe.isSelected());
        } else if (source == this.bLogout) {
        	this.controller.processLogoutEvent(settings);
        } else if (source == this.bSignUp) {
        	this.controller.processSignUpEvent(settings);
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
