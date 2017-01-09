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

import it.peruvianit.bean.GeoConfig;
import it.peruvianit.exceptions.GeoException;
import it.peruvianit.repository.MapCodeAddress;
import it.peruvianit.utils.FileUtil;
import it.peruvianit.utils.GeoPropertiesUtil;
import static it.peruvianit.constant.GeoConstant.*;

public class Run {
	public final static Logger logger = Logger.getLogger(Run.class);
	
	public static void main(String[] args) throws GeoException, UnsupportedEncodingException {
		logger.info("Start program.");
		
		GeoConfig geoConfig = loadGeoPropertiesUtil();
		
		String pathDirectoryIn = geoConfig.getPathDirectoryIn();
		String pathDirectoryProcess = geoConfig.getPathDirectoryProcess();
		
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
				Thread t = new Thread(new Interpreter(fileName,geoConfig));
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
	public static GeoConfig loadGeoPropertiesUtil() throws GeoException{
		GeoConfig geoConfig = new GeoConfig();
		
		String pathName = FileUtil.getPathClass(FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String pathNameConf = pathName + "/conf";
		
		FileUtil.checkDirecctory(pathNameConf);
		
		GeoPropertiesUtil geoPropertiesUtil = new GeoPropertiesUtil(pathNameConf, GEO_FILE_PROPERTIES);
		geoPropertiesUtil.init();
		
		geoConfig.setCsvColumnCode(geoPropertiesUtil.getProperty(GEO_POSTITION_CSV_COLUMN_CODE));
		geoConfig.setCsvColumnAddress(geoPropertiesUtil.getProperty(GEO_POSTITION_CSV_COLUMN_ADDRESS));
		geoConfig.setPathDirectoryIn(geoPropertiesUtil.getProperty(GEO_PATH_DIRECTORY_IN));
		geoConfig.setPathDirectoryProcess(geoPropertiesUtil.getProperty(GEO_PATH_DIRECTORY_PROCESS));
		
		return geoConfig;
	}
}
