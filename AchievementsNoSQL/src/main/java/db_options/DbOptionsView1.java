/**
 * View for the database options window. From here settings for how to store the achievement data, images, and password encryption for user account passwords can be set.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package db_options;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import _main.AchieveSettings;

public final class DbOptionsView1 extends JFrame implements DbOptionsView {

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
    private DbOptionsController controller;

    /**
     * JComponents
     */
    private final JLabel lUseFiles, lDynamoDbAccess, lDynamoDbSecret, lDynamoDbEndpoint, lDynamoDbRegion, lKmsAccess, lKmsSecret, lKmsEndpoint, lKmsRegion, lDriveClientId, lDriveClientSecret;
    private final JTextField tDynamoDbAccess, tDynamoDbSecret, tDynamoDbEndpoint, tDynamoDbRegion, tKmsAccess, tKmsSecret, tKmsEndpoint, tKmsRegion, tDriveClientId, tDriveClientSecret;
    private final JTextArea tNote;
    private final JButton bBack, bAdmin, bSave;
    private final JCheckBox cUseFiles;

    /**
     * Useful constants.
     */
    private static final int DYNAMODB_PANEL_GRID_ROWS = 4, DYNAMODB_PANEL_GRID_COLUMNS = 2,
    		KMS_PANEL_GRID_ROWS = 4, KMS_PANEL_GRID_COLUMNS = 2,
    		DRIVE_PANEL_GRID_ROWS = 2, DRIVE_PANEL_GRID_COLUMNS = 2,
    		MINI_PANEL_GRID_ROWS = 1, MINI_PANEL_GRID_COLUMNS = 2;

    /**
     * Default constructor.
     */
    public DbOptionsView1(AchieveSettings settings) {
        // Create the JFrame being extended

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Database Options");
        
        //Set local settings
    	this.settings = settings;

        // Set up the GUI widgets --------------------------------------------

        /*
         * Create widgets
         */
    	//Labels
        this.lUseFiles = new JLabel("Use Filesystem: ");
        this.lDynamoDbAccess = new JLabel("Access Key: ");
        this.lDynamoDbSecret = new JLabel("Secret Key: ");
        this.lDynamoDbEndpoint = new JLabel("Endpoint: ");
        this.lDynamoDbRegion = new JLabel("Region: ");
        this.lKmsAccess = new JLabel("Access Key: ");
        this.lKmsSecret = new JLabel("Secret Key: ");
        this.lKmsEndpoint = new JLabel("Endpoint: ");
        this.lKmsRegion = new JLabel("Region: ");
        this.lDriveClientId = new JLabel("Client ID: ");
        this.lDriveClientSecret = new JLabel("Client Secret: ");
        
        //Buttons
        this.bBack = new JButton("Back");
        this.bAdmin = new JButton("Admin Panel");
        this.bSave = new JButton("Save");
        
        //Checkbox
        this.cUseFiles = new JCheckBox();
        
        //Text areas
        this.tNote = new JTextArea("Note: This uses AWS with BasicCredentials to log in.");
        this.tNote.setLineWrap(true);
        this.tNote.setEditable(false);
        
        //Text fields
        this.tDynamoDbAccess = new JTextField(settings.getDynamoDbAccessKey());;
        this.tDynamoDbSecret = new JTextField(settings.getDynamoDbSecretKey());
        this.tDynamoDbEndpoint = new JTextField(settings.getDynamoDbEndpoint());
        this.tDynamoDbRegion = new JTextField(settings.getDynamoDbRegion());
        this.tKmsAccess = new JTextField(settings.getKmsAccessKey());
        this.tKmsSecret = new JTextField(settings.getKmsSecretKey());
        this.tKmsEndpoint = new JTextField(settings.getKmsEndpoint());
        this.tKmsRegion = new JTextField(settings.getKmsRegion());
        this.tDriveClientId = new JTextField(settings.getDriveClientId());
        this.tDriveClientSecret = new JTextField(settings.getDriveSecretId());

        // Set up the GUI widgets --------------------------------------------

        /*
         * AWS Fields will be disabled and checkbox will be checked if useLocal is trues
         */
        if(settings.getUseLocal()) {
        	this.cUseFiles.setSelected(true);
        	updateAwsFieldsAllowed(false);
        }
        
        /*
         * Make all labels right aligned
         */
        this.lUseFiles.setHorizontalAlignment(SwingConstants.RIGHT);
        this.lDynamoDbAccess.setHorizontalAlignment(SwingConstants.RIGHT);
        this.lDynamoDbSecret.setHorizontalAlignment(SwingConstants.RIGHT);
        this.lDynamoDbEndpoint.setHorizontalAlignment(SwingConstants.RIGHT);
        this.lDynamoDbRegion.setHorizontalAlignment(SwingConstants.RIGHT);
        this.lKmsAccess.setHorizontalAlignment(SwingConstants.RIGHT);
        this.lKmsSecret.setHorizontalAlignment(SwingConstants.RIGHT);
        this.lKmsEndpoint.setHorizontalAlignment(SwingConstants.RIGHT);
        this.lKmsRegion.setHorizontalAlignment(SwingConstants.RIGHT);

        /*
         * Create dynamoDb panel
         */
        
        GridLayout dynamoDbLayout = new GridLayout(DYNAMODB_PANEL_GRID_ROWS, DYNAMODB_PANEL_GRID_COLUMNS);
        dynamoDbLayout.setVgap(10);
        dynamoDbLayout.setHgap(10);
        Border blackline = BorderFactory.createLineBorder(Color.black);
        TitledBorder dynamoDbBorder = BorderFactory.createTitledBorder(blackline, "DynamoDb Settings");
        JPanel dynamoDbPanel = new JPanel(dynamoDbLayout);
        dynamoDbPanel.setBorder(dynamoDbBorder);
        

        /*
         * Add widgets to dynamoDB panel
         */
        dynamoDbPanel.add(this.lDynamoDbAccess);
        dynamoDbPanel.add(this.tDynamoDbAccess);
        dynamoDbPanel.add(this.lDynamoDbSecret);
        dynamoDbPanel.add(this.tDynamoDbSecret);
        dynamoDbPanel.add(this.lDynamoDbEndpoint);
        dynamoDbPanel.add(this.tDynamoDbEndpoint);
        dynamoDbPanel.add(this.lDynamoDbRegion);
        dynamoDbPanel.add(this.tDynamoDbRegion);
        
        /*
         * Create KMS panel
         */
        GridLayout kmsLayout = new GridLayout(KMS_PANEL_GRID_ROWS, KMS_PANEL_GRID_COLUMNS);
        kmsLayout.setVgap(10);
        kmsLayout.setHgap(10);   
        TitledBorder kmsBorder = BorderFactory.createTitledBorder(blackline, "KMS Settings");
        JPanel kmsPanel = new JPanel(kmsLayout);
        kmsPanel.setBorder(kmsBorder);
        
        /*
         * Add widgets to KMS panel
         */
        kmsPanel.add(this.lKmsAccess);
        kmsPanel.add(this.tKmsAccess);
        kmsPanel.add(this.lKmsSecret);
        kmsPanel.add(this.tKmsSecret);
        kmsPanel.add(this.lKmsEndpoint);
        kmsPanel.add(this.tKmsEndpoint);
        kmsPanel.add(this.lKmsRegion);
        kmsPanel.add(this.tKmsRegion);
        
        /*
         * Create Google Drive panel
         */
        GridLayout driveLayout = new GridLayout(DRIVE_PANEL_GRID_ROWS, DRIVE_PANEL_GRID_COLUMNS);
        driveLayout.setVgap(10);
        driveLayout.setHgap(10);   
        TitledBorder driveBorder = BorderFactory.createTitledBorder(blackline, "Google Drive Settings");
        JPanel drivePanel = new JPanel(driveLayout);
        drivePanel.setBorder(driveBorder);
        
        /*
         * Add widgets to Google Drive Panel
         */
        drivePanel.add(this.lDriveClientId);
        drivePanel.add(this.tDriveClientId);
        drivePanel.add(this.lDriveClientSecret);
        drivePanel.add(this.tDriveClientSecret);

        
        /*
         * Create mini panels
         */
        GridLayout miniLayout = new GridLayout(MINI_PANEL_GRID_ROWS, MINI_PANEL_GRID_COLUMNS);
        miniLayout.setVgap(10);
        miniLayout.setHgap(10);
        JPanel miniPanel1 = new JPanel(miniLayout);
        JPanel miniPanel2 = new JPanel(miniLayout);
        
        /*
         * Add widgets to mini panels
         */
        miniPanel1.add(this.lUseFiles);
        miniPanel1.add(this.cUseFiles);
        miniPanel2.add(this.bSave);
        miniPanel2.add(this.bAdmin);
        
        
        /*
         * Create main panel
         */
        JPanel mainPanel = new JPanel();
        BoxLayout mainLayout = new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS);
        mainPanel.setLayout(mainLayout);
        
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        
        /*
         * Add widgets to main panel
         */
        mainPanel.add(miniPanel1);
        mainPanel.add(dynamoDbPanel);
        mainPanel.add(kmsPanel);
        mainPanel.add(drivePanel);
        mainPanel.add(miniPanel2);
        mainPanel.add(this.tNote);
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
        this.bAdmin.addActionListener(this);
        this.bSave.addActionListener(this);
        this.cUseFiles.addActionListener(this);
        
        // Set up the main application window --------------------------------

        /*
         * Make sure the main window is appropriately sized, centered, exits this program
         * on close, and becomes visible to the user
         */
        this.setPreferredSize(new Dimension(512, 512));
        this.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        

    }

    @Override
    public void registerObserver(DbOptionsController controller) {

        this.controller = controller;

    }

    @Override
    public void updateAwsFieldsAllowed(boolean allowed) {
    	this.tDynamoDbAccess.setEditable(allowed);
    	this.tDynamoDbSecret.setEditable(allowed);
    	this.tDynamoDbEndpoint.setEditable(allowed);
    	this.tDynamoDbRegion.setEditable(allowed);
    	this.tKmsAccess.setEditable(allowed);
    	this.tKmsSecret.setEditable(allowed);
    	this.tKmsEndpoint.setEditable(allowed);
    	this.tKmsRegion.setEditable(allowed);
    	this.tDriveClientId.setEditable(allowed);
    	this.tDriveClientSecret.setEditable(allowed);
    	this.bSave.setEnabled(allowed);
    	this.bAdmin.setEnabled(allowed);
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
        if (source == this.bSave) {
        	this.controller.processSaveEvent(settings, this.tDynamoDbAccess.getText(), this.tDynamoDbSecret.getText(), this.tDynamoDbEndpoint.getText(), this.tDynamoDbRegion.getText(),
        			this.tKmsAccess.getText(), this.tKmsSecret.getText(), this.tKmsEndpoint.getText(), this.tKmsRegion.getText(), this.tDriveClientId.getText(), this.tDriveClientSecret.getText());
        } else if (source == this.cUseFiles) {
        	this.controller.processUseFilesEvent(settings, this.cUseFiles.isSelected());
        } else if (source == this.bAdmin){
        	this.controller.processAdminEvent(settings);
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
