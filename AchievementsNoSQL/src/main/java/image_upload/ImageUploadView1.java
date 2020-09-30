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

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import _main.AchieveSettings;
import _main.AchieveStorage.AchievementPair;

public final class ImageUploadView1 extends JFrame implements ImageUploadView {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Local stored settings
	 */
	private AchieveSettings settings;
    private Set<AchievementPair> achievements;

	/**
     * Controller object registered with this view to observe user-interaction
     * events.
     */
    private ImageUploadController controller;

    /**
     * JComponents
     */
    private final JLabel lTodo;
    private final JButton bBack;
    private Map<AchievementPair, JButton> bAchievements;


    /**
     * Default constructor.
     */
    public ImageUploadView1(AchieveSettings settings) {
        // Create the JFrame being extended

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Image Upload");
        
        //Set local settings
    	this.settings = settings;
    	this.achievements = settings.storage.getNoImageAchievements();

        // Set up the GUI widgets --------------------------------------------

        /*
         * Create widgets
         */
    	//Labels
        this.lTodo = new JLabel("Todo: " + this.achievements.size());
        
        //Buttons
        this.bBack = new JButton("Back");
        this.bAchievements = new HashMap<AchievementPair, JButton>();
        
        //Achievement buttons

        // Set up the GUI widgets --------------------------------------------
        
        /*
         * Create achievement panel
         */
        JPanel achievePanel = new JPanel(new GridBagLayout());
        GridBagConstraints achieveConstraints = new GridBagConstraints();
        	achieveConstraints.weighty = 1;
        	achieveConstraints.weightx = 1;
            achieveConstraints.insets = new Insets(3,3,3,3);
        
        
        /*
         * Add widgets to achievement panel
         */
        int achieveWeightX = 0, achieveWeightY = 0;
        for(AchievementPair achievementPair : this.achievements) {
        	JButton bAchievement = new JButton(achievementPair.category + ": " + achievementPair.title);
        	this.bAchievements.put(achievementPair, bAchievement);
        		achieveConstraints.gridx = achieveWeightX;
        		achieveConstraints.gridy = achieveWeightY;
        	achievePanel.add(bAchievement, achieveConstraints);
        	if(achieveWeightX >= 2) {
        		achieveWeightX = 0;
        		achieveWeightY++;
        	} else {
        		achieveWeightX++;
        	}
        }
        
        /*
         * Make achievement panel scrollable
         */
        JScrollPane achieveScrollPane = new JScrollPane(achievePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        
        /*
         * Create main panel
         */
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints mainConstraints = new GridBagConstraints();
        	mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        	mainConstraints.insets = new Insets(3,3,3,3);
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        
        /*
         * Add widgets to main panel
         */
        mainPanel.add(this.bBack, mainConstraints);
        	mainConstraints.gridx = 1;
        	mainConstraints.gridwidth = 3;
        mainPanel.add(this.lTodo, mainConstraints);
        	mainConstraints.fill = GridBagConstraints.BOTH;
        	mainConstraints.weighty = 1;
        	mainConstraints.gridx = 0;
        	mainConstraints.gridy = 1;
        	mainConstraints.gridwidth = 4;
        mainPanel.add(achieveScrollPane, mainConstraints);
       

        /*
         * Add panel to this
         */
        this.add(mainPanel);
        
        // Set up the observers ----------------------------------------------

        /*
         * Register this object as the observer for all GUI events
         */
        this.bBack.addActionListener(this);
        for(JButton achievementButton : bAchievements.values()) {
        	achievementButton.addActionListener(this);
        }
        
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
    public void registerObserver(ImageUploadController controller) {

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
        if (source == this.bBack){
        	this.controller.processBackEvent(this.settings);
        } else {
            for(Map.Entry<AchievementPair, JButton> categoryButton : this.bAchievements.entrySet()) {
            	if(source == categoryButton.getValue()) {
            		this.controller.processAchievementEvent(this.settings, categoryButton.getKey().title, categoryButton.getKey().category);
            		break;
            	}
            }
        }
        /*
         * Set the cursor back to normal (because we changed it at the beginning
         * of the method body)
         */
        this.setCursor(Cursor.getDefaultCursor());
    }

}
