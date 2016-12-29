/**
 * 
 * @author Sergio Arellano Diaz
 * @version 1.0.1
 * @since 17/12/2016
 *
 */

package it.peruvianit.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import it.peruvianit.exceptions.GeoException;

public class GeoPropertiesUtil {
private static Logger logger = Logger.getLogger(GeoPropertiesUtil.class);
	
	private String serverConfigDir;
	private String geoProperties;
	
	private Properties properties;
	
	public GeoPropertiesUtil(String serverConfigDir,String geoProperties){
		this.serverConfigDir = serverConfigDir;
		this.geoProperties = geoProperties;
	}
	
	/**
	 * Ritorna il valore da una chiave, che cerca al interno del file
	 * di properties
	 * 
	 * @param keyPropertie nome della propieta
	 * @return String restituici il valore della propieta
	 * @throws GeoException 
	 */	
	public String getProperty(String keyPropertie) throws GeoException{
		if (properties == null){
			this.init();
		}
		return properties.getProperty(keyPropertie);
	}
	
	/**
	 * Carica il file properties.
	 * 
	 * @throws GeoException 
	 */	
	public void init() throws GeoException {
		try {
			properties = new Properties();

			String configDir = this.serverConfigDir;

			if (configDir != null) {
				String pathFile = configDir + "/" + this.geoProperties;
				File file = new File(pathFile);

				try{
					properties.load(new FileInputStream(file));					
				} catch(Exception e) {
					logger.warn("file " + this.geoProperties + " non trovato in: /" + pathFile);
					throw e;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new GeoException(e.getMessage());
		}
	}
}
