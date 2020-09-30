/**
 * View for the categories window. This is where all of the different categories that contain achievements can be found.
 * From here a new achievement can be created or we can open a category.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */

package categories;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import _main.AchieveSettings;

public final class CategoriesView1 extends JFrame implements CategoriesView {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Local stored settings
	 */
	private AchieveSettings settings;
	private Map<String, Double> categories;

	/**
     * Controller object registered with this view to observe user-interaction
     * events.
     */
    private CategoriesController controller;

    /**
     * JComponents
     */
    private final JButton bBack, bNewAchieve;
    private Map<String, JButton> bCategories;

    /**
     * Default constructor.
     */
    public CategoriesView1(AchieveSettings settings) {
        // Create the JFrame being extended

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Categories");
        
        //Set local settings
    	this.settings = settings;

        // Set up the GUI widgets --------------------------------------------

        /*
         * Create widgets
         */
        //Buttons
        this.bNewAchieve = new JButton("New Achievement");
        this.bBack = new JButton("Back");
        this.bCategories = new TreeMap<String, JButton>();
        

        // Set up the GUI widgets --------------------------------------------
        
        /*
         * Create achievePanel
         */
        JPanel achievePanel = new JPanel(new GridBagLayout());
        GridBagConstraints achieveConstraints = new GridBagConstraints();
        	achieveConstraints.fill = GridBagConstraints.BOTH;
        	achieveConstraints.weighty = 1;
        	achieveConstraints.weightx = 1;
            achieveConstraints.insets = new Insets(3,3,3,3);
        
        //Add widgets to achievePanel
        this.categories = this.settings.storage.getCategories();
        int achieveWeightX = 0, achieveWeightY = 0;
        for(Map.Entry<String, Double> category: categories.entrySet()) {
        	JButton bCategory = new JButton(category.getKey() + " [" + String.format("%.2f", 100 * category.getValue()) + "%]");
        	bCategories.put(category.getKey(), bCategory);
        	achieveConstraints.gridx = achieveWeightX;
        	achieveConstraints.gridy = achieveWeightY;
        	achievePanel.add(bCategory, achieveConstraints);
        	if(achieveWeightX >= 2) {
        		achieveWeightX = 0;
        		achieveWeightY++;
        	} else {
        		achieveWeightX++;
        	}
        }
        
        //Make achievePanel into a scroll pane
        JScrollPane scrollAchievePane = new JScrollPane(achievePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        /*
         * Create main panel
         */
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints mainConstraints = new GridBagConstraints();
        	mainConstraints.weightx = 1;
        	mainConstraints.gridwidth = 2;
        	mainConstraints.insets = new Insets(3,3,3,3);
        	mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        
        
        /*
         * Add widgets to main panel
         */
        mainPanel.add(this.bBack, mainConstraints);
        	mainConstraints.gridx = 2;
        mainPanel.add(this.bNewAchieve, mainConstraints);
        	mainConstraints.fill = GridBagConstraints.BOTH;
        	mainConstraints.weighty = 1;
        	mainConstraints.gridx = 0;
        	mainConstraints.gridy = 1;
        	mainConstraints.gridwidth = 4;
        mainPanel.add(scrollAchievePane, mainConstraints);
       

        /*
         * Add panel to this
         */
        this.add(mainPanel);
        
        // Set up the observers ----------------------------------------------

        /*
         * Register this object as the observer for all GUI events
         */
        this.bBack.addActionListener(this);
        this.bNewAchieve.addActionListener(this);
        for(Map.Entry<String, JButton> category : bCategories.entrySet()) {
        	category.getValue().addActionListener(this);
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
    public void registerObserver(CategoriesController controller) {
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
        if (source == this.bNewAchieve) {
        	this.controller.processNewAchieveEvent(this.settings);
        } else if (source == this.bBack){
        	this.controller.processBackEvent(this.settings);
        } else {
            for(Map.Entry<String, JButton> categoryButton : this.bCategories.entrySet()) {
            	if(source == categoryButton.getValue()) {
            		this.controller.processCategoryEvent(this.settings, categoryButton.getKey(), this.categories.get(categoryButton.getKey()));
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
