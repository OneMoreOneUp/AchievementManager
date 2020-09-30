/**
 * Settings files.
 * 
 * Stores the settings for database and account.
 * If they are missing any properties, they will automatically be added.
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 * 
 */

package _main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class AchieveSettings {
	
	private boolean isLoggedIn;
	private String accountType;
	
	private final String databaseAddress;
	private final String accountAddress;
	public final String missingImageAddress;
	public final String lockedImageAddress;
	
	private Properties databaseConfig;
	private Properties accountConfig;
	public AchieveStorage storage;
	
	
	/**
	 * Constructor to check and (if non-existent) create the property files.
	 * 
	 *  @param dbConfigAddress
	 *  	Location of the database properties file
	 *  @param accountConfigAddress
	 *  	Location of the account properties file
	 */
	public AchieveSettings (String dbConfigAddress, String accountConfigAddress, String missingImageAddress, String lockedImageAddress) {
		this.isLoggedIn = false;
		this.accountType = "artist";
		
		this.databaseAddress = dbConfigAddress;
		this.accountAddress = accountConfigAddress;
		this.missingImageAddress = missingImageAddress;
		this.lockedImageAddress = lockedImageAddress;
		this.storage = new AchieveStorage();
		
		createDbConfig(dbConfigAddress);
		createAccountConfig(accountConfigAddress);
	}
	
	
	/**
	 * Loads any existing properties from the file at the given address. If any properties are missing the are added.
	 * 
	 * Properties (name: default value):
	 * 		useLocal: true
	 * 		awsAccessKey: ""
	 * 		awsSecretKey: ""
	 * 		awsEndpoint: ""
	 * 		awsRegion: ""
	 * 
	 * @param address
	 * 	Address of the file to load
	 */
	private void createDbConfig (String address) {
		Properties dbConfig = new Properties();
		
		/*
		 * Load any previous version of the property file at the address.
		 */
		File dbConfigFile = new File(address);
		try {
			/*
			 * Creates file/directory if it could not be found
			 */
			if(dbConfigFile.getParentFile().mkdirs()) System.err.println("[WARNING] Database properties directory location was missing and has been created.");
			if(dbConfigFile.createNewFile()) System.err.println("[WARNING] Database properties file was missing and has been created.");
			
			FileReader dbConfigReader = new FileReader(dbConfigFile);
			dbConfig.load(dbConfigReader);
		} catch (IOException e) {
			System.err.println("[ERROR]: Could not initialize database properties file: " + address);
			System.err.println(e.getMessage());
		}
		
		/*
		 * Check to see if any properties are missing.
		 */
		if(!dbConfig.containsKey("useLocal")) dbConfig.put("useLocal", "true");
		
		//AWS DynamoDB settings
		if(!dbConfig.containsKey("dynamoDbAccessKey")) dbConfig.put("dynamoDbAccessKey", "");
		if(!dbConfig.containsKey("dynamoDbSecretKey")) dbConfig.put("dynamoDbSecretKey", "");
		if(!dbConfig.containsKey("dynamoDbEndpoint")) dbConfig.put("dynamoDbEndpoint", "");
		if(!dbConfig.containsKey("dynamoDbRegion")) dbConfig.put("dynamoDbRegion", "");
		
		//AWS KMS settings
		if(!dbConfig.containsKey("kmsAccessKey")) dbConfig.put("kmsAccessKey", "");
		if(!dbConfig.containsKey("kmsSecretKey")) dbConfig.put("kmsSecretKey", "");
		if(!dbConfig.containsKey("kmsEndpoint")) dbConfig.put("kmsEndpoint", "");
		if(!dbConfig.containsKey("kmsRegion")) dbConfig.put("kmsRegion", "");
		
		//Google Drive Settings
		if(!dbConfig.containsKey("driveClientId")) dbConfig.put("driveClientId", "");
		if(!dbConfig.containsKey("driveClientSecret")) dbConfig.put("driveClientSecret", "");
		

		this.databaseConfig = dbConfig;
		
		/*
		 * Save any changes
		 */
		this.saveDbConfig();
	}
	
	/**
	 * Loads any existing properties from the file at the given address. If any properties are missing the are added.
	 * 
	 * Properties (name: default value):
	 * 		username: ""
	 * 		rememberLogin: false
	 * 
	 * @param address
	 * 	Address of the file to load
	 */
	private void createAccountConfig (String address) {
		Properties accountConfig = new Properties();
		
		/*
		 * Load any previous version of the property file at the address.
		 */
		File accountConfigFile = new File(address);
		try {
			/*
			 * Creates file/directory if it could not be found
			 */
			if(accountConfigFile.getParentFile().mkdirs()) System.err.println("[WARNING] Account properties directory location was missing and has been created.");
			if(accountConfigFile.createNewFile()) System.err.println("[WARNING] Account properties file was missing and has been created.");
			
			accountConfig.load(new FileReader(accountConfigFile));
		} catch (IOException e) {
			System.err.println("[ERROR]: Could not initialize account properties file: " + address);
			System.err.println(e.getMessage());
		}
		
		/*
		 * Check to see if any properties are missing.
		 */
		if(!accountConfig.containsKey("username")) accountConfig.put("username", "");
		if(!accountConfig.containsKey("password")) accountConfig.put("password", "");
		if(!accountConfig.containsKey("rememberLogin")) accountConfig.put("rememberLogin", "false");
		
		this.accountConfig = accountConfig;
		
		/*
		 * Save any changes
		 */
		this.saveAccountConfig();
	}
	
	/**
	 * Saves any current changes to the database properties to local file
	 */
	public void saveDbConfig () {
		try {
			this.databaseConfig.store(new FileWriter(new File(this.databaseAddress)), "");
		} catch (IOException e) {
			System.err.println("[ERROR] Could not save initialization to database properties file: " + databaseAddress);
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Saves any current changes to the account properties to local file
	 */
	public void saveAccountConfig () {
		try {
			this.accountConfig.store(new FileWriter(new File(this.accountAddress)), "");
		} catch (IOException e) {
			System.err.println("[ERROR] Could not save initialization to account properties file: " + accountAddress);
			System.err.println(e.getMessage());
		}
	}
	
	//Getter operations ------------------------------------------------------------------
	
	/**Get useLocal (T/F if using file system for storage)*/
	public boolean getUseLocal() {return this.databaseConfig.getProperty("useLocal").equals("true");}
	
	/**Get dynamoDb access key*/
	public String getDynamoDbAccessKey() {return this.databaseConfig.getProperty("dynamoDbAccessKey");}
	
	/**Get dynamoDb secret key*/
	public String getDynamoDbSecretKey() {return this.databaseConfig.getProperty("dynamoDbSecretKey");}
	
	/**Get dynamoDb end-point*/
	public String getDynamoDbEndpoint() {return this.databaseConfig.getProperty("dynamoDbEndpoint");}
	
	/**Get dynamoDb region*/
	public String getDynamoDbRegion() {return this.databaseConfig.getProperty("dynamoDbRegion");}
	
	/**Get KMS access key*/
	public String getKmsAccessKey() {return this.databaseConfig.getProperty("kmsAccessKey");}
	
	/**Get KMS secret key*/
	public String getKmsSecretKey() {return this.databaseConfig.getProperty("kmsSecretKey");}
	
	/**Get KMS end-point*/
	public String getKmsEndpoint() {return this.databaseConfig.getProperty("kmsEndpoint");}
	
	/**Get KMS region*/
	public String getKmsRegion() {return this.databaseConfig.getProperty("kmsRegion");}
	
	/**Get user-name*/
	public String getUsername() {return this.accountConfig.getProperty("username");}
	
	/**Get password*/
	public String getPassword() {return this.accountConfig.getProperty("password");}
	
	/**Get rememberLogin (T/F iff we remember the login password)*/
	public boolean getRememberLogin() {return this.accountConfig.getProperty("rememberLogin").equals("true");}
	
	/**Get isLoggedIn*/
	public boolean getIsLoggedIn() {return this.isLoggedIn;}
	
	/**Get accountType*/
	public String getAccountType() {return this.accountType;}
	
	/**Get Google Drive client id*/
	public String getDriveClientId() {return this.databaseConfig.getProperty("driveClientId");}
	
	/**Get GoogleDrive client secret*/
	public String getDriveSecretId() {return this.databaseConfig.getProperty("driveClientSecret");}
	
	//Setter operations ------------------------------------------------------
	
	/**Set useLocal (T/F if using file-system for storage)*/
	public void setUseLocal(boolean useLocal) {this.databaseConfig.setProperty("useLocal", Boolean.toString(useLocal));}
	
	/**Set dynamoDb access key*/
	public void setDynamoDbAccessKey(String key) {this.databaseConfig.setProperty("dynamoDbAccessKey", key);}
	
	/**Set dynamoDb secret key*/
	public void setDynamoDbSecretKey(String key) {this.databaseConfig.setProperty("dynamoDbSecretKey", key);}
	
	/**Set dynamoDb end-point*/
	public void setDynamoDbEndpoint(String endpoint) {this.databaseConfig.setProperty("dynamoDbEndpoint", endpoint);}
	
	/**Set dynamoDb region*/
	public void setDynamoDbRegion(String region) {this.databaseConfig.setProperty("dynamoDbRegion", region);}
	
	/**Set KMS access key*/
	public void setKmsAccessKey(String key) {this.databaseConfig.setProperty("kmsAccessKey", key);}
	
	/**Set KMS secret key*/
	public void setKmsSecretKey(String key) {this.databaseConfig.setProperty("kmsSecretKey", key);}
	
	/**Set KMS end-point*/
	public void setKmsEndpoint(String endpoint) {this.databaseConfig.setProperty("kmsEndpoint", endpoint);}
	
	/**Set KMS region*/
	public void setKmsRegion(String region) {this.databaseConfig.setProperty("kmsRegion", region);}
	
	/**Set user-name*/
	public void setUsername(String username) {this.accountConfig.setProperty("username", username);}
	
	/**Set password*/
	public void setPassword(String password) {this.accountConfig.setProperty("password", password);}
	
	/**Set rememberLogin*/
	public void setRememberLogin(boolean rememberLogin) {this.accountConfig.setProperty("rememberLogin", Boolean.toString(rememberLogin));}
	
	/**Set isLoggedIn*/
	public void setIsLoggedIn(boolean isLoggedIn) {this.isLoggedIn = isLoggedIn;}
	
	/**Set accountType*/
	public void setAccountType(String accountType) {this.accountType = accountType;}
	
	/**Set Google Drive client id*/
	public void setDriveClientId(String id) {this.databaseConfig.setProperty("driveClientId", id);}
	
	/**Set Google Drive client secret*/
	public void setDriveClientSecret(String secret) {this.databaseConfig.setProperty("driveClientSecret", secret);}
}
