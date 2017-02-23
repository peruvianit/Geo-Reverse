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

import it.peruvianit.bean.GeoConfigBean;
import it.peruvianit.bean.LoadInfo;
import it.peruvianit.exceptions.GeoException;
import it.peruvianit.repository.MapCodeAddress;
import it.peruvianit.utils.FileUtils;
import it.peruvianit.utils.GeoPropertiesUtil;
import static it.peruvianit.constant.GeoConstant.*;

public class Run {
	public final static Logger logger = Logger.getLogger(Run.class);
	
	public static void main(String[] args) throws GeoException, UnsupportedEncodingException {
		logger.info("Start program.");
		
		GeoConfigBean geoConfig = loadGeoPropertiesUtil();
		
		String pathDirectoryIn = geoConfig.getPathDirectoryIn();
		String pathDirectoryProcess = geoConfig.getPathDirectoryProcess();
		
		FileUtils.checkDirecctory(pathDirectoryIn,pathDirectoryProcess);
		
		File mainFolder = new File(pathDirectoryIn);
		File files[];
		files = mainFolder.listFiles();
		
		if (files != null){
			logger.debug("Caricando mapa dei inidirizzi processati in pasato");
			LoadInfo loadInfo = MapCodeAddress.getInstance().loadMap(pathDirectoryProcess);
			
			Integer rowTotal = MapCodeAddress.getInstance().countRows(pathDirectoryIn);			
			Integer rowLastWorking = MapCodeAddress.getInstance().size();
			
			loadInfo.setRowLastWorking(rowLastWorking);
			loadInfo.setRowTotal(rowTotal);
			
			logger.debug("Total inidirizzi da caricare : " + rowTotal);
			logger.debug("Total inidirizzi caricati : " + rowLastWorking);
			
			logger.info("[" + files.length + "] Files trovati da processare");
			int i;
			for (i = 0; i < files.length; i++) {
				String fileName = files[i].getPath();
				Thread t = new Thread(new Interpreter(fileName, geoConfig, loadInfo));
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
	public static GeoConfigBean loadGeoPropertiesUtil() throws GeoException{
		GeoConfigBean geoConfig = new GeoConfigBean();
		
		String pathName = FileUtils.getPathClass(FileUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String pathNameConf = pathName + "/conf";
		
		FileUtils.checkDirecctory(pathNameConf);
		
		GeoPropertiesUtil geoPropertiesUtil = new GeoPropertiesUtil(pathNameConf, GEO_FILE_PROPERTIES);
		geoPropertiesUtil.init();
		
		geoConfig.setCsvColumnCode(geoPropertiesUtil.getProperty(GEO_POSTITION_CSV_COLUMN_CODE));
		geoConfig.setCsvColumnAddress(geoPropertiesUtil.getProperty(GEO_POSTITION_CSV_COLUMN_ADDRESS));
		geoConfig.setPathDirectoryIn(geoPropertiesUtil.getProperty(GEO_PATH_DIRECTORY_IN));
		geoConfig.setPathDirectoryProcess(geoPropertiesUtil.getProperty(GEO_PATH_DIRECTORY_PROCESS));
		geoConfig.setPathDirectoryMetrics(geoPropertiesUtil.getProperty(GEO_PATH_DIRECTORY_METRICS));
		
		return geoConfig;
	}
}
