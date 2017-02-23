package it.peruvianit.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtil {
	private static FirebaseUtil instance = null;
	private static FirebaseDatabase database;
	
	protected FirebaseUtil() {
	}
 
	public static FirebaseUtil getInstance(String url,String pathKeyFile) throws FileNotFoundException {
		if (instance == null) {
			if (instance == null) {
				instance = new FirebaseUtil();
				
				FirebaseOptions options = new FirebaseOptions.Builder()
		        .setServiceAccount(new FileInputStream(pathKeyFile))
		        .setDatabaseUrl(url)
		        .build();
		        FirebaseApp.initializeApp(options);
		         
		        database = FirebaseDatabase.getInstance();
			}
		}
		return instance;
	}
	private String getUID(){
		return UUID.randomUUID().toString();
	}
	
	public <T> void addRow(String reference, T object) throws FileNotFoundException {
         DatabaseReference myRef = database.getReference(reference + "/" + this.getUID());
         myRef.setValue(object);
	}
}