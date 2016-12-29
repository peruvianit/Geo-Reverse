/**
 * Utilizzata per ogni volta che viene eseguita l'applicazione, sono letti tutti file processati
 * e aggiunti tutti codici dei indirizzi al interno della Mappa sortedSetCodeAddress, per evitare una doppia 
 * chiamata a Google Maps.
 * 
 * @author Sergio Arellano Diaz
 * @version 1.0.1
 * @since 17/12/2016
 *
 */
package it.peruvianit.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import it.peruvianit.exceptions.GeoException;

public class MapCodeAddress {
	private static MapCodeAddress instance = null;
	
	private SortedSet<String> sortedSetCodeAddress = new TreeSet<String>();

	protected MapCodeAddress() {}
	   
	public static MapCodeAddress getInstance(){
		if(instance == null) {
			synchronized (MapCodeAddress.class) {
				if(instance == null) {
					instance = new MapCodeAddress();
				}
			}
	    }
	    return instance;
	}
	
	public void addCodeAddress(String codeAddress){
		synchronized(sortedSetCodeAddress){
			sortedSetCodeAddress.add(codeAddress);
		}
	}
	
	public boolean findCodeAddress(String codeAddress){
		return sortedSetCodeAddress.contains(codeAddress);
	}
	
	public int size(){
		return sortedSetCodeAddress.size();
	}
	
	/**
	 * Metodo per il caricamento della Mappa
	 * 
	 * @param pathFolder path dove saranno letti tutti file OK e KO
	 * @throws GeoException 
	 */	
	public void loadMap(String pathFolder) throws GeoException{
		File mainFolder = new File(pathFolder);
		
		File files[];
		files = mainFolder.listFiles();
		
		if (files != null){
			for (int i = 0; i < files.length; i++) {
				String pathFileName = files[i].getPath();
				if(pathFileName.endsWith("OK") || pathFileName.endsWith("KO")){
					FileReader fr = null;
					BufferedReader br = null;
					try {
						fr = new FileReader(pathFileName);
						br = new BufferedReader(fr);
						
						String sCurrentLine;
						
						while ((sCurrentLine = br.readLine()) != null) {
							String addressArray[] = sCurrentLine.split("\\|\\|");
							
							if (addressArray.length >= 2){
								sortedSetCodeAddress.add(addressArray[0]);
							}
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						throw new GeoException(e.getMessage());
					}
					
				}
	        }
		}
	}
}
