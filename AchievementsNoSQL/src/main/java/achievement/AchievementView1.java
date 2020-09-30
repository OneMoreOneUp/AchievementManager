/**
 * View for the achievement window. This is where a single achievement can be viewed.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package achievement;

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
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import _main.AchieveSettings;
import _main.AchieveStorage.Achievement;

public final class AchievementView1 extends JFrame implements AchievementView {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Local stored settings
	 */
	private AchieveSettings settings;
	private String title, category;

	/**
     * Controller object registered with this view to observe user-interaction
     * events.
     */
    private AchievementController controller;

    /**
     * JComponents
     */
    private final JLabel lImage, lTitle;
    private final JProgressBar pProgress;
    private final JTextArea tDescription;
    private final JButton bBack, bEdit, bDelete;

    /**
     * Useful constants.
     */
    private static final int TITLE_HEIGHT = 1, TITLE_PROGRESS_WIDTH = 4, PROGRESS_HEIGHT = 1, BUTTON_HEIGHT = 1, DESCRIPTION_HEIGHT = 3; 

    /**
     * Default constructor.
     */
    public AchievementView1(AchieveSettings settings, String title, Achievement achievement, String category) {
        // Create the JFrame being extended

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Achievement: " + title);
        
        //Set local settings
    	this.settings = settings;
    	this.category = category;
    	this.title = title;

        // Set up the GUI widgets --------------------------------------------

        /*
         * Create widgets
         */
    	//Labels
        this.lTitle = new JLabel(title);
    	ImageIcon iImage = new ImageIcon();
    	try {
    		if (achievement.currentProg < achievement.maxProg) {
    			iImage = new ImageIcon(ImageIO.read(new File(settings.lockedImageAddress)).getScaledInstance(128, 128, Image.SCALE_FAST));
    		} else {
    			if(!achievement.imageURL.equals(this.settings.storage.MISSING_IMAGE_TEXT)) {
        			iImage = new ImageIcon(ImageIO.read(new URL(achievement.imageURL)).getScaledInstance(128, 128, Image.SCALE_FAST));
        		}
        		else {
        			iImage = new ImageIcon(ImageIO.read(new File(settings.missingImageAddress)).getScaledInstance(128, 128, Image.SCALE_FAST));
        		}
    		}
		} catch (Exception e) {
			System.err.println("[WARNING] Could not load image for achievement: " + title);
			System.err.println(e.getMessage());
		}
    	this.lImage = new JLabel(iImage);
        
        //Progress bars
        this.pProgress = new JProgressBar(0, achievement.maxProg);
        this.pProgress.setValue(achievement.currentProg);
        this.pProgress.setStringPainted(true);
        
        //Buttons
        this.bBack = new JButton("Back");
        this.bEdit = new JButton("Edit");
        this.bDelete = new JButton("Delete");
        
        //Text areas
        this.tDescription = new JTextArea(achievement.description);
        this.tDescription.setLineWrap(true);
        this.tDescription.setEditable(false);

        // Set up the GUI widgets --------------------------------------------
        
        /*
         * Create main panel
         */
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints mainConstraints = new GridBagConstraints();
        	mainConstraints.weightx = 1;
        	mainConstraints.weighty = 1;
        	mainConstraints.fill = GridBagConstraints.BOTH;
        	mainConstraints.insets = new Insets(3,3,3,3);
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        
        /*
         * Add widgets to main panel
         */
        	mainConstraints.gridwidth = TITLE_HEIGHT + PROGRESS_HEIGHT;
        	mainConstraints.gridheight = TITLE_HEIGHT + PROGRESS_HEIGHT;
        mainPanel.add(this.lImage, mainConstraints);
        	mainConstraints.gridwidth = TITLE_PROGRESS_WIDTH;
        	mainConstraints.gridheight = TITLE_HEIGHT;
        	mainConstraints.gridx = TITLE_HEIGHT + PROGRESS_HEIGHT;
        mainPanel.add(this.lTitle, mainConstraints);
        	mainConstraints.gridy = TITLE_HEIGHT;
        mainPanel.add(this.pProgress, mainConstraints);
        	mainConstraints.gridwidth = TITLE_HEIGHT + PROGRESS_HEIGHT + TITLE_PROGRESS_WIDTH;
        	mainConstraints.gridheight = DESCRIPTION_HEIGHT;
        	mainConstraints.gridx = 0;
        	mainConstraints.gridy += TITLE_HEIGHT + PROGRESS_HEIGHT;
        mainPanel.add(this.tDescription, mainConstraints);
        	mainConstraints.gridwidth /= 3;
        	mainConstraints.gridheight = BUTTON_HEIGHT;
        	mainConstraints.gridy += DESCRIPTION_HEIGHT; 
        	mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(this.bBack, mainConstraints);
        	mainConstraints.gridx += mainConstraints.gridwidth;
        mainPanel.add(this.bEdit, mainConstraints);
        	mainConstraints.gridx += mainConstraints.gridwidth;
        mainPanel.add(this.bDelete, mainConstraints);
       

        /*
         * Add panel to this
         */
        this.add(mainPanel);
        
        // Set up the observers ----------------------------------------------

        /*
         * Register this object as the observer for all GUI events
         */
        this.bBack.addActionListener(this);
        this.bEdit.addActionListener(this);
        this.bDelete.addActionListener(this);
        
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
    public void registerObserver(AchievementController controller) {

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
        if (source == this.bEdit) {
        	this.controller.processEditEvent(settings);
        } else if (source == this.bDelete) {
        	this.controller.processDeleteEvent(settings, title, this.category);
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
