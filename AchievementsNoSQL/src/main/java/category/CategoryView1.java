/**
 * View for the category window. This is where a single category of achievements can be seen.
 * From here the viewed category can be deleted and an individual achievement can be opened.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package category;

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
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import _main.AchieveSettings;
import _main.AchieveStorage.Achievement;

public final class CategoryView1 extends JFrame implements CategoryView {

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
    private CategoryController controller;

    /**
     * JComponents
     */
    private final JLabel lCompleted;
    private final JButton bDelete, bBack;
    private Map<String, JButton> bAchievements;
    private Map<String, Achievement> achievements;

    /**
     * Default constructor.
     */
    public CategoryView1(AchieveSettings settings, String category, double percentage) {
        // Create the JFrame being extended

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Achievements: " + category);
        
        //Set local settings
    	this.settings = settings;
    	this.category = category;

        // Set up the GUI widgets --------------------------------------------

        /*
         * Create widgets
         */
    	//Labels
        this.lCompleted = new JLabel("Completed: " + String.format("%.2f", 100 * percentage) + "%");
        
        //Buttons
        this.bDelete = new JButton("Delete Category");
        this.bBack = new JButton("Back");
        this.bAchievements = new TreeMap<String, JButton>();
        
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
        this.achievements = settings.storage.getAchievements(category);
        int achieveWeightX = 0, achieveWeightY = 0;
        for(Map.Entry<String, Achievement> achievementPair : achievements.entrySet()) {
        	Achievement achievement = achievementPair.getValue();
        	
        	/*
        	 * Create button and widgets to add to button
        	 */
        	JLabel lAchievement = new JLabel(achievementPair.getKey());
        	JProgressBar pAchievement = new JProgressBar(0, achievement.maxProg);
        	pAchievement.setValue(achievement.currentProg);
        	pAchievement.setStringPainted(true);

        	ImageIcon iAchievement = new ImageIcon();
        	try {
        		if (achievement.currentProg < achievement.maxProg) {
        			iAchievement = new ImageIcon(ImageIO.read(new File(settings.lockedImageAddress)).getScaledInstance(64, 64, Image.SCALE_FAST));
        		} else {
        			if(!achievement.imageURL.equals(settings.storage.MISSING_IMAGE_TEXT)) {
            			iAchievement = new ImageIcon(ImageIO.read(new URL(achievement.imageURL)).getScaledInstance(64, 64, Image.SCALE_FAST));
            		}
            		else {
            			iAchievement = new ImageIcon(ImageIO.read(new File(settings.missingImageAddress)).getScaledInstance(64, 64, Image.SCALE_FAST));
            		}
        		}
			} catch (Exception e) {
				System.err.println("[WARNING] Could not load image for achievement: " + achievementPair.getKey());
				System.err.println(e.getMessage());
			}
        	
        	/*
        	 * Create panel to add to button
        	 */
        	JButton bAchievement = new JButton();
        	JPanel interiorPanel = new JPanel(new GridBagLayout());
        	GridBagConstraints interiorConstraints = new GridBagConstraints();
        	
        	/*
        	 * Add widgets to panel to add to button
        	 */
        	interiorPanel.add(new JLabel(iAchievement), interiorConstraints);
        		interiorConstraints.gridx = 1;
        	interiorPanel.add(lAchievement, interiorConstraints);
        		interiorConstraints.gridx = 0;
        		interiorConstraints.gridy = 1;
        		interiorConstraints.gridwidth = 2;
        	interiorPanel.add(pAchievement, interiorConstraints);
        	bAchievement.add(interiorPanel);
        	
        	this.bAchievements.put(achievementPair.getKey(), bAchievement);
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
        mainPanel.add(this.bDelete, mainConstraints);
        	mainConstraints.gridx = 2;
        	mainConstraints.gridwidth = 2;
        mainPanel.add(this.lCompleted, mainConstraints);
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
        this.bDelete.addActionListener(this);
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
    public void registerObserver(CategoryController controller) {

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
        if (source == this.bDelete) {
        	this.controller.processDeleteCategoryEvent(this.settings, this.category);
        } else if (source == this.bBack){
        	this.controller.processBackEvent(this.settings);
        } else {
            for(Map.Entry<String, JButton> categoryButton : this.bAchievements.entrySet()) {
            	if(source == categoryButton.getValue()) {
            		this.controller.processAchievementEvent(this.settings, categoryButton.getKey(), (this.achievements.get(categoryButton.getKey())), this.category);
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
