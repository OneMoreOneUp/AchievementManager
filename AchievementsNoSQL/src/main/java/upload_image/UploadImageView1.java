/**
 * View for the image uploading window. This is where an image is uploaded for a specific achievement.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package upload_image;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import achievement_image.AchieveImageView;
import create_achievement.CreateAchieveView;

public final class UploadImageView1 extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Local stored settings
	 */
	private AchieveImageView imageView;
	private CreateAchieveView createView;

    /**
     * JComponents
     */
    private final JLabel lURL;
    private final JTextField tURL;
    private final JButton bGetImage, bBrowse;
    private final JFileChooser fBrowse;

    /**
     * Default constructor.
     */
    public UploadImageView1(CreateAchieveView createView, AchieveImageView imageView) {
        // Create the JFrame being extended

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Upload Image");
        
        //Set local settings
    	this.createView = createView;
    	this.imageView = imageView;

        // Set up the GUI widgets --------------------------------------------

        /*
         * Create widgets
         */
    	//Labels
    	this.lURL = new JLabel("Image URL:", SwingConstants.RIGHT);
        
        //Buttons
        this.bGetImage = new JButton("Get Image from URL");
        this.bBrowse = new JButton("Browse Files");
        
        //Text fields
        this.tURL = new JTextField();
        
        //File browser
        this.fBrowse = new JFileChooser();
        
        //Make file browser only show image files
        FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        this.fBrowse.setFileFilter(imageFilter);
        

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
    	mainPanel.add(this.lURL, mainConstraints);
    		mainConstraints.weightx = 1;
    		mainConstraints.gridx = 1;
    	mainPanel.add(this.tURL, mainConstraints);
    		mainConstraints.weightx = 0;
    		mainConstraints.gridx = 0;
    		mainConstraints.gridy = 1;
    	mainPanel.add(this.bGetImage, mainConstraints);
    		mainConstraints.gridx = 1;
    	mainPanel.add(this.bBrowse, mainConstraints);

        /*
         * Add panel to this
         */
        this.add(mainPanel);
        
        // Set up the observers ----------------------------------------------

        /*
         * Register this object as the observer for all GUI events
         */
        this.bGetImage.addActionListener(this);
        this.bBrowse.addActionListener(this);
        
        // Set up the main application window --------------------------------

        /*
         * Make sure the main window is appropriately sized, centered, exits this program
         * on close, and becomes visible to the user
         */
        this.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        

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
        if (source == this.bGetImage) {
        	if(this.createView != null) {
        		this.createView.setImageFromURL(this.tURL.getText());
        	} else {
        		this.imageView.setImageFromURL(this.tURL.getText());
        	}
        	this.dispose();
        } else if (source == this.bBrowse) {
        	int returnVal = this.fBrowse.showSaveDialog(this);
        	if(returnVal == JFileChooser.APPROVE_OPTION) {
        		File file = this.fBrowse.getSelectedFile();
        		if(this.createView != null) {
        			this.createView.setImageFromFile(file);
        		} else {
        			this.imageView.setImageFromFile(file);
        		}
        		this.dispose();
        	}
        }
        /*
         * Set the cursor back to normal (because we changed it at the beginning
         * of the method body)
         */
        this.setCursor(Cursor.getDefaultCursor());
    }

}
