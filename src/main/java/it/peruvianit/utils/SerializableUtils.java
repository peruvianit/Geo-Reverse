package it.peruvianit.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.UUID;

public class SerializableUtils {
	public static void serialize(String pathFile, String fileName, Object object) throws IOException{
		try(
			OutputStream file = new FileOutputStream(pathFile + "\\" + fileName + "-" + UUID.randomUUID().toString() + ".ser");
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
		){
			output.writeObject(object);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static Object deserialize(String pathFile) throws IOException, ClassNotFoundException{
		try(
		    InputStream file = new FileInputStream(pathFile);
		    InputStream buffer = new BufferedInputStream(file);
		    ObjectInput input = new ObjectInputStream(buffer);
		){
			return input.readObject();
		}
	}
}
