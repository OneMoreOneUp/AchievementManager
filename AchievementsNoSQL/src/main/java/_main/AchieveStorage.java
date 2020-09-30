/**
 *  Manages storing achievement program data (user accounts, achievements, images) (does not include settings).
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
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.CreateAliasRequest;
import com.amazonaws.services.kms.model.CreateKeyRequest;
import com.amazonaws.services.kms.model.CreateKeyResult;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.services.kms.model.EncryptResult;
import com.amazonaws.util.BinaryUtils;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.Permission;


public class AchieveStorage {
	
	public class Achievement {
		public String description = null;
		public int maxProg = 0;
		public int currentProg = 0;
		public String imageURL = null;
	}
	
	public class AchievementPair {
		public String title = null;
		public String category = null;
	}
	
	private DynamoDB awsDb;
	private AmazonDynamoDB awsDbClient;
	private AWSKMS awsKeyClient;
	private Drive drive;
	
	private static final String KEY_NAME = "alias/Achieve_Test2", KEY_DESC = "Key for protecting DynamoDB Acheive_Account passwords."; 	//Encryption key name and description
	private static final String ACCOUNT_TABLE_NAME = "Achieve_Account", ACHIEVE_TABLE_NAME = "Achieve_Achievements"; //Database table names
	private static final String APPLICATION_NAME = "Achievement Manager";
	public final String MISSING_IMAGE_TEXT = "NO_IMAGE";

	
	
	/**
	 * Constructor to check and (if non-existent) create the property files.
	 * 
	 *  @param useLocal
	 *  	iff the files should be stored locally (false will use AWS NoSQL)
	 */
	public AchieveStorage () {
		this.awsDb = null;
		this.awsDbClient = null;
		this.awsKeyClient = null;
		this.drive = null;
	}
	
	/**
	 * Attempts to establish a connection to the AWS NoSQL database using those stored in the local properties
	 * This will be stored for future uses of this class.
	 * 
	 * @param settings
	 * 	Local properties
	 */
	public void connectDatabase(AchieveSettings settings) {
		try {
    		this.awsDbClient = AmazonDynamoDBClientBuilder.standard()
        			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(settings.getDynamoDbEndpoint(), settings.getDynamoDbRegion()))
        			.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(settings.getDynamoDbAccessKey(), settings.getDynamoDbSecretKey())))
        			.build();
    		
    		this.awsDb= new DynamoDB(this.awsDbClient);
    		
    		System.err.println("[SUCCESS] Connected to DynamoDB.");
    	} catch (Exception e) {
    		System.err.println("[WARNING] Could not connect to DynamoDB.");
    		System.err.println(e.getMessage());
    	}
	}
	
	/**
	 * Attempts to establish a connection to AWS KMS using those stored in the local properties
	 * This will be stored for future uses of this class.
	 * 
	 * @param settings
	 * 	Local properties
	 */
	public void connectKMS(AchieveSettings settings) {
    	try {
    		this.awsKeyClient = AWSKMSClientBuilder.standard()
        			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(settings.getKmsEndpoint(), settings.getKmsRegion()))
        			.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(settings.getKmsAccessKey(), settings.getKmsSecretKey())))
        			.build();
    		System.err.println("[SUCCESS] Connected to KMS.");
    	} catch (Exception e) {
    		System.err.println("[WARNING] Could not connect to KMS.");
    		System.err.println(e.getMessage());
    	}
	}
	
	/**
	 * Attempts to establish a connection to Google Drive using those stored in the local properties
	 * This will be stored for future uses of this class.
	 * 
	 * @param settings
	 * 	Local properties
	 */
	public void connectDrive(AchieveSettings settings) {
		try {
			final NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
			final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
			final List<String> scopes = Collections.singletonList(DriveScopes.DRIVE_FILE);
			
			//Create flow and trigger user authorization request
			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	                transport, jsonFactory, settings.getDriveClientId(), settings.getDriveSecretId(), scopes)
	                .setDataStoreFactory(new FileDataStoreFactory(new File("tokens")))
	                .setAccessType("offline")
	                .build();
	        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
	        Credential httpRequestInitializer = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
			
			//Create new Google Drive client
			this.drive = new Drive.Builder(transport, jsonFactory, httpRequestInitializer)
					.setApplicationName(APPLICATION_NAME)
					.build();
			System.err.println("[SUCCESS] Connected to Google Drive.");
		} catch (Exception e) {
			System.err.println("[WARNING] Could not connect to Google Drive.");
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Attempts to create a new KMS encryption key
	 */
	public void createKey () {
		try {
			/*
			 * Create the KMS key
			 */
			CreateKeyRequest createKeyRequest = new CreateKeyRequest();
			createKeyRequest.setDescription(KEY_DESC);
			CreateKeyResult result = this.awsKeyClient.createKey(createKeyRequest);
			
			System.err.println("[SUCCESS] Created new KMS key.");
			
			/*
			 * Give the key an alias
			 */
			try {
				CreateAliasRequest aliasRequest = new CreateAliasRequest();
				aliasRequest.setAliasName(KEY_NAME);
				aliasRequest.withTargetKeyId(result.getKeyMetadata().getKeyId());
				this.awsKeyClient.createAlias(aliasRequest);
				
				System.err.println("[SUCCESS] New KMS key was given an alias.");
			} catch (Exception e2) {
				System.err.println("[WARNING] Could not give key an alias.");
				System.err.println(e2.getMessage());
			}
		} catch (Exception e1) {
			System.err.println("[ERROR] Could not create KMS key.");
			System.err.println(e1.getMessage());
		}
	}
	
    /**
     * Attempts to create two new tables for the database using properties stored in $settings
     * 
     * The following tables will attempt to be created:
     * 		Achieve_Accounts(username, password, type)
     * 		Achieve_Achievements(title, description, curr_prog, max_prog, image_url, category)
     */
	public void createDatabase() {
		/*
		 * Create account table
		 */
		try {
    		Table accountTable = this.awsDb.createTable(ACCOUNT_TABLE_NAME,
                    Arrays.asList(new KeySchemaElement("username", KeyType.HASH)), // Partition key
                    Arrays.asList(new AttributeDefinition("username", ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L, 10L));
                accountTable.waitForActive();
                System.err.println("[SUCCESS] Account table status: " + accountTable.getDescription().getTableStatus());
		} catch (Exception e) {
			System.err.println("[ERROR] Could not create account table.");
    		System.err.println(e.getMessage());
		}
		
		/*
		 * Create achievement table
		 */
		try {
			Table achieveTable = this.awsDb.createTable(ACHIEVE_TABLE_NAME,
	                Arrays.asList(new KeySchemaElement("title", KeyType.HASH), // Partition key
	                    new KeySchemaElement("category", KeyType.RANGE)), // Sort key
	                Arrays.asList(new AttributeDefinition("title", ScalarAttributeType.S),
	                    new AttributeDefinition("category", ScalarAttributeType.S)),
	                new ProvisionedThroughput(10L, 10L));
	            achieveTable.waitForActive();
	            System.err.println("[SUCCESS] Achievement table status: " + achieveTable.getDescription().getTableStatus());
		} catch (Exception e) {
			System.err.println("[ERROR] Could not create achievement table.");
    		System.err.println(e.getMessage());
		}
	}
	
	public boolean createAccount(String username, String email, String password, AchieveSettings settings) {
		
		boolean success = false;
		
		try {
    		Table accountTable = this.awsDb.getTable("Achieve_Account");
    		
    		/*
    		 * Check to make sure username is unique
    		 */
    		if(accountTable.getItem("username", username) == null) {
    			/*
    			 * If unique add username and (encrypted) password to the database with the type artist
    			 */
    			accountTable.putItem(new Item().withPrimaryKey("username", username).withString("email", email).withString("password", this.encryptPassword(password)).withString("type", "artist"));
    			System.err.println("[SUCCESS] Account has been created.");
    			success = true;
    		} else {
    			System.err.println("[ERROR] Username has been taken.");
    		}
    		
		} catch (Exception e) {
			System.err.println("[ERROR] Could not create account.");
			System.err.println(e.getMessage());
		}
		
		return success;
	}
	
	public boolean login(String username, String password, AchieveSettings settings) {
		try {
			Table accountTable = this.awsDb.getTable("Achieve_Account");
			Item account = accountTable.getItem("username", username);
			
			/*
			 * If there is no account with that username print an error
			 */
			if(account != null) {
				String encPassword = account.getString("password");
				/*
				 * If the decrypted password from the database and the supplied password don't match print an error
				 */
				if(password.equals(this.decryptPassword(encPassword))) {
					/*
					 * Set saved variables of logged in and account type to true and whatever was stored in the database
					 */
					settings.setAccountType(account.getString("type"));
					settings.setIsLoggedIn(true);
					System.err.println("[SUCCESS] Successfully logged in.");
				} else {
					System.err.println("[ERROR] Incorrect password.");
				}
			} else {
				System.err.println("[ERROR] No account with that username could be found.");
			}
		} catch (Exception e) {
			System.err.println("[ERROR] Could not log in.");
			System.err.println(e.getMessage());
		}
		
		return false;
	}
	
	public String encryptPassword(String password) {
		String encPassword = null;
		
		try {
			byte[] encryptedBytes = password.getBytes("UTF-8");
	        ByteBuffer encryptedBuffer = ByteBuffer.wrap(encryptedBytes);
	        EncryptRequest request = new EncryptRequest().withPlaintext(encryptedBuffer).withKeyId("alias/Achieve_Test2");
	        EncryptResult response = this.awsKeyClient.encrypt(request);
	        byte[] plaintextBytes = BinaryUtils.copyAllBytesFrom(response.getCiphertextBlob());
	        encPassword = BinaryUtils.toBase64(plaintextBytes);
		} catch (Exception e) {
			System.err.println("[ERROR] Could not encrypt password.");
			System.err.println(e.getMessage());
		}
		
		return encPassword;
	}
	
	public String decryptPassword(String encPassword) {
		String password = null;
		
		try {
	        byte[] encryptedBytes = BinaryUtils.fromBase64(encPassword);
	        ByteBuffer encryptedBuffer = ByteBuffer.wrap(encryptedBytes);
	        DecryptRequest request = new DecryptRequest().withCiphertextBlob(encryptedBuffer);
	        DecryptResult response = this.awsKeyClient.decrypt(request);
	        byte[] plaintextBytes = BinaryUtils.copyAllBytesFrom(response.getPlaintext());
	        password = new String(plaintextBytes, "UTF-8");
		} catch (Exception e) {
			System.err.println("[ERROR] Could not decrypt password.");
			System.err.println(e.getMessage());
		}
		
		return password;
	}

	/**
	 * Gets a list of all categories (and their progress) from the AWS DynamoDB database
	 * @return 
	 * 		Map of the category names to their percentage complete
	 */
	public Map<String, Double> getCategories() {
		Map<String, Double> categories = new TreeMap<String, Double>();
		try {
			Map<String, Integer> currentProgMap = new TreeMap<String, Integer>();
			Map<String, Integer> maxProgMap = new TreeMap<String, Integer>();
			ScanRequest scanRequest = new ScanRequest()
					.withTableName(ACHIEVE_TABLE_NAME)
					.withProjectionExpression("category, currentProg, maxProg");
			
			ScanResult result = this.awsDbClient.scan(scanRequest);
			for(Map<String, AttributeValue> item : result.getItems()) {
				String category = null;
				int currentProg = 0;
				int maxProg = 0;
				for(Map.Entry<String, AttributeValue> attribute : item.entrySet()) {
					String attributeName = attribute.getKey();
					switch (attributeName){
						case "category" :
							category = attribute.getValue().getS();
							break;
						case "currentProg":
							currentProg = Integer.parseInt(attribute.getValue().getN());
							break;
						case "maxProg":
							maxProg = Integer.parseInt(attribute.getValue().getN());
							break;
						default:
							System.err.println("[WARNING] Somehow managed to retrieve the attribute: " + attributeName + " when it wasn't being looked for.");
					}
				}
				Integer oldCurrentProg = currentProgMap.putIfAbsent(category, currentProg);
				if(oldCurrentProg != null) currentProgMap.put(category, currentProg + oldCurrentProg);
				Integer oldMaxProg = maxProgMap.putIfAbsent(category, maxProg);
				if(oldMaxProg != null) maxProgMap.put(category, maxProg + oldMaxProg);
			}
			
			//Calculate progress of each category
			for(Map.Entry<String, Integer> pair : currentProgMap.entrySet()) {
				double percentage = pair.getValue() / (1.0 * maxProgMap.get(pair.getKey()));
				categories.put(pair.getKey(), percentage);
			}
		} catch (Exception e) {
			System.err.println("[ERROR] Could not get categories from database.");
			System.err.println(e.getMessage());
		}
		
		return categories;
 		
	}
	
	/**
	 * Gets all of the achievements from the AWS DynamoDB for a given category within the AWS DynamoDB
	 * @param category
	 * 	Category to get the achievements from
	 * @return
	 * 	Map of achievement names to the achievement data
	 */	
	public Map<String, Achievement> getAchievements(String category) {
		Map<String, Achievement> achievements = new TreeMap<String, Achievement>();
		try {
			Map<String, AttributeValue> expressionAttributeValues = new TreeMap<String, AttributeValue>();
			expressionAttributeValues.put(":category", new AttributeValue().withS(category));
			
			ScanRequest scanRequest = new ScanRequest()
					.withTableName(ACHIEVE_TABLE_NAME)
					.withFilterExpression("category = :category")
					.withProjectionExpression("title, description, currentProg, maxProg, imageURL")
					.withExpressionAttributeValues(expressionAttributeValues);
			
			ScanResult result = this.awsDbClient.scan(scanRequest);
			for(Map<String, AttributeValue> item : result.getItems()) {
				Achievement achievement = new Achievement();
				String title = null;
				for(Map.Entry<String, AttributeValue> attribute : item.entrySet()) {
					String attributeName = attribute.getKey();
					switch (attributeName){
						case "title" :
							title = attribute.getValue().getS();
							break;
						case "description" :
							achievement.description = attribute.getValue().getS();
							break;
						case "currentProg":
							achievement.currentProg = Integer.parseInt(attribute.getValue().getN());
							break;
						case "maxProg":
							achievement.maxProg = Integer.parseInt(attribute.getValue().getN());
							break;
						case "imageURL":
							achievement.imageURL = attribute.getValue().getS();
							break;
						default:
							System.err.println("[WARNING] Somehow managed to retrieve the attribute: " + attributeName + " when it wasn't being looked for.");
					}
				}
				achievements.put(title, achievement);
			}
		} catch (Exception e) {
			System.err.println("[ERROR] Could not get achievements from database.");
			System.err.println(e.getMessage());
		}
		
		return achievements;
	}
	
	/**
	 * Gets all achievements without an image within the AWS DynamoDB
	 * @return
	 * 	A set of achievement pairs (title of achievement, and title of category)
	 */
	public Set<AchievementPair> getNoImageAchievements() {
		Set<AchievementPair> achievements = new HashSet<AchievementPair>();
		try {
			Map<String, AttributeValue> expressionAttributeValues = new TreeMap<String, AttributeValue>();
			expressionAttributeValues.put(":imageURL", new AttributeValue().withS(MISSING_IMAGE_TEXT));
			
			ScanRequest scanRequest = new ScanRequest()
					.withTableName(ACHIEVE_TABLE_NAME)
					.withFilterExpression("imageURL = :imageURL")
					.withProjectionExpression("title, category")
					.withExpressionAttributeValues(expressionAttributeValues);
			
			ScanResult result = this.awsDbClient.scan(scanRequest);
			for(Map<String, AttributeValue> item : result.getItems()) {
				AchievementPair achievement = new AchievementPair();
				for(Map.Entry<String, AttributeValue> attribute : item.entrySet()) {
					String attributeName = attribute.getKey();
					switch (attributeName) {
						case "title" :
							achievement.title = attribute.getValue().getS();
							break;
						case "category" :
							achievement.category = attribute.getValue().getS();
							break;
						default:
							System.err.println("[WARNING] Somehow managed to retrieve the attribute: " + attributeName + " when it wasn't being looked for.");
					}
				}
				achievements.add(achievement);
			}
		} catch (Exception e) {
			System.err.println("[ERROR] Could not get achievements from database.");
			System.err.println(e.getMessage());
		}
		
		return achievements;
	}

	/**
	 * Removes an $achievement from a given $category within the AWS DynamoDB
	 * @param achievement
	 * 	The name of the achievement to remove
	 * @param category
	 * 	The name of the category to remove the achievement from
	 */
	public void deleteAchievement(String achievement, String category) {
		Table achieveTable = this.awsDb.getTable(ACHIEVE_TABLE_NAME);
		DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
				.withPrimaryKey(new PrimaryKey("title", achievement, "category", category));
		
		try {
			achieveTable.deleteItem(deleteItemSpec);
			System.err.println("[SUCCESS] Successfully deleted achievement: " + achievement);
		} catch (Exception e) {
			System.err.println("[ERROR] Could not delete achievement: " + achievement);
			System.err.println(e.getMessage());
		}
		
	}
	
	/**
	 * Removes all achievements in a given $category within the AWS DynamoDB
	 * @param category
	 * 	The name of the category to remove all achievements from
	 */
	public void deleteCategory(String category) {
		Table achieveTable = this.awsDb.getTable(ACHIEVE_TABLE_NAME);

		Map<String, AttributeValue> expressionAttributeValues = new TreeMap<String, AttributeValue>();
		expressionAttributeValues.put(":category", new AttributeValue().withS(category));
		
		ScanRequest scanRequest = new ScanRequest()
				.withTableName(ACHIEVE_TABLE_NAME)
				.withFilterExpression("category = :category")
				.withProjectionExpression("title")
				.withExpressionAttributeValues(expressionAttributeValues);
		
		try {
			ScanResult result = this.awsDbClient.scan(scanRequest);
			for(Map<String, AttributeValue> item : result.getItems()) {
				for(Map.Entry<String, AttributeValue> attribute : item.entrySet()) {
					if(attribute.getKey().equals("title")) {
						try {
							achieveTable.deleteItem("title", attribute.getValue().getS(), "category", category);
						} catch (Exception e1) {
							System.err.println("[ERROR] Could not delete achievement: " + attribute.getValue().getS() + " within the category: " + category);
							System.err.println(e1.getMessage());
						}
					} else {
						System.err.println("[WARNING] Somehow managed to retrieve the attribute: " + attribute.getKey() + " when it wasn't being looked for.");
					}
				}
			}
			System.err.println("[SUCCESS] Successfully deleted category: " + category);
		} catch (Exception e2) {
			System.err.println("[ERROR] Could not delete category: " + category);
			System.err.println(e2.getMessage());
		}
		
	}
	
	/**
	 * Uploads to Google Drive and create shareable link to image. The image will be named $category_$achieveTitle
	 * @param achieveTitle
	 * 	The name of the achievement linked with this image
	 * @param category
	 * 	The name of the category linked with this image
	 * @param image
	 * 	The image to upload
	 * @return
	 */
	public String createImageURL(String achieveTitle, String category, File image) {
		
		String imageURL = null;
		
		try {
			/*
			 * Upload image to Google Drive
			 */
			com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
			fileMetadata.setName(category + "_" + achieveTitle);
			FileContent mediaContent = new FileContent("image/jpeg", image);
			com.google.api.services.drive.model.File file = this.drive.files().create(fileMetadata, mediaContent)
			    .setFields("id, webContentLink")
			    .execute();
			
			/*
			 * Get share-able link
			 */
			Permission linkPermission = new Permission()
					.setType("anyone")
					.setRole("reader");
			this.drive.permissions().create(file.getId(), linkPermission).execute();
			imageURL = file.getWebContentLink();
		} catch (Exception e) {
			System.err.println("[ERROR] Failed to upload image to Google Drive.");
			System.err.println(e.getMessage());
		}
		
		return imageURL;
	}
	
	/**
	 * Checks if a given achievement title, category is unique within the AWS DynamoDB
	 * @param title
	 * 	The title of the achievement
	 * @param category
	 * 	The title of the category
	 * @return
	 * 	True iff there is no achievement with the title and category given within the AWS DynamoDB
	 */
	public boolean isUnique(String title, String category) {
		boolean isUnique = false;
		
		try {
			Table table = this.awsDb.getTable(ACHIEVE_TABLE_NAME);
			Item item = table.getItem("title", title, "category", category);
			
			if(item == null) isUnique = true;
		} catch (Exception e) {
			System.err.println("[ERROR] Could not check uniqueness from database.");
			System.err.println(e.getMessage());
		}
		
		return isUnique;
	}

	/**
	 * Creates an achievement within the AWS DynamoDB with $title, $category, $description, $maxProg, $imageURL, and a currentProg of 0
	 * @requires
	 * 	$title and $category is unique to database.
	 * 	$title and $category cannot be null.
	 * @param title
	 * 	Title to give achievement
	 * @param category
	 * 	Category to give achievement
	 * @param description
	 * 	Description of achievement
	 * @param maxProg
	 * 	Max progress of achievement
	 * @param imageURL
	 * 	Image URL of achievement
	 */
	public void createAchievement(String title, String category, String description, int maxProg, String imageURL) {
		if(imageURL == null) imageURL = MISSING_IMAGE_TEXT;			//iff imageURL is missing, it is replaced with the missing image text
		
		try {
			Table table = this.awsDb.getTable(ACHIEVE_TABLE_NAME);
			table.putItem(new Item().withPrimaryKey("title", title, "category", category).with("description", description).with("maxProg", maxProg).with("currentProg", 0).with("imageURL", imageURL));
			System.err.println("[SUCCESS] Successfully added achievement to database.");
		} catch (Exception e) {
			System.err.println("[ERROR] Failed to put achievement data into database.");
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Sets the imageURL of the achievement with $title, $category to $imageURL within the AWS DynamoDB
	 * @param title
	 * 	Title of the achievement to change the imageURL of
	 * @param category
	 * 	Category of the achievement to change the imageURL of
	 * @param imageURL
	 * 	The new image URL to give to the achievement
	 */
	public void changeAchievementImage(String title, String category, String imageURL) {
		try {
			Table table = this.awsDb.getTable(ACHIEVE_TABLE_NAME);
			
	        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("title", title, "category", category)
	                .withUpdateExpression("set imageURL = :i")
	                .withValueMap(new ValueMap().withString(":i", imageURL))
	                .withReturnValues(ReturnValue.UPDATED_NEW);
	        
	        table.updateItem(updateItemSpec);
	        System.err.println("[SUCCESS] Successfully changed " + category + ": " + title + "'s image.");
		} catch (Exception e) {
			System.err.println("[ERROR] Could not update achievement image.");
			System.err.println(e.getMessage());
		}
		
	}
	

}
