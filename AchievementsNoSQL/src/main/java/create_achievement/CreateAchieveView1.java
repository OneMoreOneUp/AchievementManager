/**
 * View for the create achievement window. Achievement is made up of a title, description (optional), current/max progress, a category, and an image (optional).
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */


package create_achievement;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import _main.AchieveSettings;

public final class CreateAchieveView1 extends JFrame implements CreateAchieveView {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Local stored settings
	 */
	private AchieveSettings settings;
	private File image;
	private String imageURL;
	
	/**
	 * Max file size
	 */
	private static final int MAX_FILE_SIZE_BYTES = 4000000;
	private static final int BYTE = 1000000;
	private static final int IMAGE_SIZE_PIXELS = 256;

	/**
     * Controller object registered with this view to observe user-interaction
     * events.
     */
    private CreateAchieveController controller;

    /**
     * JComponents
     */
    private final JLabel lTitle, lDescription, lCategory, lMaxProg, lImage;
    private final JTextField tTitle, tCategory;
    private final JTextArea tDescription, tNote;
    private final JButton bUploadImage, bDeleteImage, bCreate, bBack;
    private final JSpinner tMaxProg;

    /**
     * Default constructor.
     */
    public CreateAchieveView1(AchieveSettings settings) {
        // Create the JFrame being extended

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Create New Achievement");
        
        //Set local settings
    	this.settings = settings;

        // Set up the GUI widgets --------------------------------------------

        /*
         * Create widgets
         */
    	//Labels
        this.lTitle = new JLabel("Title: ", SwingConstants.RIGHT);
        this.lDescription = new JLabel("Description:", SwingConstants.RIGHT);
        this.lMaxProg = new JLabel("Progress Max: ", SwingConstants.RIGHT);
        this.lCategory = new JLabel("Category: ", SwingConstants.RIGHT);
        this.lImage = new JLabel();
        
        //Sets default image to be the default locked image
        resetImage();
        
        //Buttons
        this.bUploadImage = new JButton("Upload Image");
        this.bDeleteImage = new JButton("Delete Image");
        this.bCreate = new JButton("Create");
        this.bBack = new JButton("Back");
        
        //Text fields
        this.tTitle = new JTextField();
        this.tCategory = new JTextField();
        
        //Text areas
        this.tDescription = new JTextArea();
        this.tNote = new JTextArea("Note: Image files must be at most " + 1.0 * MAX_FILE_SIZE_BYTES / BYTE + " MB.");
        this.tNote.setEditable(false);
        
        //Spinners
        this.tMaxProg = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        

        // Set up the GUI widgets --------------------------------------------
        
        /*
         * Create main panel
         */
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        GridBagConstraints mainConstraints = new GridBagConstraints();
        mainConstraints.insets = new Insets(3,3,3,3);
    	mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        
        /*
         * Add widgets to main panel
         */
    	mainPanel.add(this.lTitle, mainConstraints);
    		mainConstraints.gridy = 1;
    	mainPanel.add(this.lDescription, mainConstraints);
    		mainConstraints.gridy = 3;
    	mainPanel.add(this.lMaxProg, mainConstraints);
    		mainConstraints.gridy = 4;
    	mainPanel.add(this.lCategory, mainConstraints);
    		mainConstraints.gridy = 5;
    	mainPanel.add(this.bUploadImage, mainConstraints);
    		mainConstraints.gridy = 6;
    	mainPanel.add(this.bDeleteImage, mainConstraints);
    		mainConstraints.gridy = 7;
    	mainPanel.add(this.tNote, mainConstraints);
    		mainConstraints.weightx = 1;
    		mainConstraints.gridwidth = 3;
    		mainConstraints.gridy = 0;
    	mainPanel.add(this.tTitle, mainConstraints);
    		mainConstraints.weighty = 1;
    		mainConstraints.gridheight = 2;
    		mainConstraints.fill = GridBagConstraints.BOTH;
    		mainConstraints.gridy = 1;
    	mainPanel.add(this.tDescription, mainConstraints);
    		mainConstraints.weighty = 0;
			mainConstraints.gridheight = 1;
			mainConstraints.fill = GridBagConstraints.HORIZONTAL;
    		mainConstraints.gridy = 3;
    	mainPanel.add(this.tMaxProg, mainConstraints);
    		mainConstraints.gridy = 4;
    	mainPanel.add(this.tCategory, mainConstraints);
    		mainConstraints.fill = GridBagConstraints.BOTH;
    		mainConstraints.gridheight = 3;
    		mainConstraints.weighty = 1;
    		mainConstraints.gridy = 5;
    	mainPanel.add(this.lImage, mainConstraints);
    		mainConstraints.fill = GridBagConstraints.HORIZONTAL;
    		mainConstraints.weighty = 0;
    		mainConstraints.weightx = 0;
    		mainConstraints.gridheight = 1;
    		mainConstraints.gridwidth = 2;
    		mainConstraints.gridx = 0;
    		mainConstraints.gridy = 8;
    	mainPanel.add(this.bCreate, mainConstraints);
    		mainConstraints.gridx = 2;
    	mainPanel.add(this.bBack, mainConstraints);

        /*
         * Add panel to this
         */
        this.add(mainPanel);
        
        // Set up the observers ----------------------------------------------

        /*
         * Register this object as the observer for all GUI events
         */
        this.bUploadImage.addActionListener(this);
        this.bDeleteImage.addActionListener(this);
        this.bCreate.addActionListener(this);
        this.bBack.addActionListener(this);
        
        // Set up the main application window --------------------------------

        /*
         * Make sure the main window is appropriately sized, centered, exits this program
         * on close, and becomes visible to the user
         */
        this.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        

    }

    @Override
    public void registerObserver(CreateAchieveController controller) {
        this.controller = controller;
    }
    
    @Override
    public void setImageFromURL(String imageURL) {
    	try {
    		this.imageURL = imageURL;
			Image image = ImageIO.read((new URL(imageURL)));
			this.lImage.setIcon(new ImageIcon(image.getScaledInstance(IMAGE_SIZE_PIXELS, IMAGE_SIZE_PIXELS, Image.SCALE_FAST)));
		} catch (Exception e) {
			System.err.println("[ERROR] Could not read image from URL.");
			System.err.println(e.getMessage());
		}
    }
    
    @Override
    public void setImageFromFile(File imageFile) {
    	try {
    		if(imageFile.length() <= MAX_FILE_SIZE_BYTES) {
    			this.image = imageFile;
    			Image image = ImageIO.read(imageFile);
    			this.imageURL = null;
    			this.lImage.setIcon(new ImageIcon(image.getScaledInstance(IMAGE_SIZE_PIXELS, IMAGE_SIZE_PIXELS, Image.SCALE_FAST)));
    		} else {
    			System.err.println("[ERROR] File is too large. It must be at most " + 1.0 * MAX_FILE_SIZE_BYTES / BYTE + " MB.");
    		}
		} catch (Exception e) {
			System.err.println("[ERROR] Could not read image from file.");
			System.err.println(e.getMessage());
		}
    }
    
    /**
     * Nulls any saved image values and attempts to load the default locked image to this.lImage
     */
    private void resetImage() {
    	this.image = null;
    	this.imageURL = null;
    	
        try {
			this.lImage.setIcon(new ImageIcon(ImageIO.read(new File(settings.missingImageAddress)).getScaledInstance(IMAGE_SIZE_PIXELS, IMAGE_SIZE_PIXELS, Image.SCALE_FAST)));
		} catch (Exception e) {
			System.err.println("[WARNING] Failed to load default missing image: " + settings.missingImageAddress);
			System.err.println(e.getMessage());
		}
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
        if (source == this.bUploadImage) {
        	this.controller.processUploadImageEvent(this);
        } else if (source == this.bDeleteImage) {
        	this.resetImage();
        } else if (source == this.bCreate) {
        	this.controller.processCreateEvent(settings, this.tTitle.getText(), this.tDescription.getText(), (Integer) this.tMaxProg.getValue(), this.tCategory.getText(), this.imageURL, this.image);
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
