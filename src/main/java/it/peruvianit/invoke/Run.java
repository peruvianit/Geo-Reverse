/**
 * Boot dell'applicazione, carica e configura tutti parametri che 
 * servono all'applicazione. 
 * Crea dei Thread per ogni file dei inidirizzi.
 * 
 * @author Sergio Arellano Diaz
 * @version 1.0.1
 * @since 17/12/2016
 *
 */
package it.peruvianit.invoke;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import it.peruvianit.exceptions.GeoException;
import it.peruvianit.repository.MapCodeAddress;
import it.peruvianit.utils.FileUtil;
import it.peruvianit.utils.GeoPropertiesUtil;

public class Run {
	public final static String  GEO_FILE_PROPERTIES = "geo.properties";
	public final static String  GEO_PATH_DIRECTORY_IN = "geo.path.directory.in";
	public final static String  GEO_PATH_DIRECTORY_PROCESS = "geo.path.directory.process";
	
	public final static Logger logger = Logger.getLogger(Run.class);
	
	public static void main(String[] args) throws GeoException, UnsupportedEncodingException {
		logger.info("Start program.");
		
		GeoPropertiesUtil geoPropertiesUtil = loadGeoPropertiesUtil();
		String pathDirectoryIn = geoPropertiesUtil.getProperty(GEO_PATH_DIRECTORY_IN);
		String pathDirectoryProcess = geoPropertiesUtil.getProperty(GEO_PATH_DIRECTORY_PROCESS);
		
		FileUtil.checkDirecctory(pathDirectoryIn,pathDirectoryProcess);
		
		File mainFolder = new File(pathDirectoryIn);
		File files[];
		files = mainFolder.listFiles();
		
		if (files != null){
			logger.debug("Caricando mapa dei inidirizzi processati in pasato");
			MapCodeAddress.getInstance().loadMap(pathDirectoryProcess);
			logger.debug("Total inidirizzi caricati : " + MapCodeAddress.getInstance().size());
			
			logger.info("[" + files.length + "] Files trovati da processare");
			int i;
			for (i = 0; i < files.length; i++) {
				String fileName = files[i].getPath();
				Thread t = new Thread(new Interpreter(fileName,pathDirectoryProcess));
		        t.start();
				logger.debug("Thread [" + (i + 1)  + "] : [" + fileName + "]");
	        }
			logger.info("Numero di Threads caricati : " + i);
		}
	}
	
	/**
	 * Ritorna tutti le propietati 
	 * 
	 * @return GeoPropertiesUtil
	 * @throws GeoException 
	 */	
	public static GeoPropertiesUtil loadGeoPropertiesUtil() throws GeoException{
		String pathName = FileUtil.getPathClass(FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String pathNameConf = pathName + "/conf";
		
		FileUtil.checkDirecctory(pathNameConf);
		
		GeoPropertiesUtil geoPropertiesUtil = new GeoPropertiesUtil(pathNameConf, GEO_FILE_PROPERTIES);
		geoPropertiesUtil.init();
		
		return geoPropertiesUtil;
	}
}
