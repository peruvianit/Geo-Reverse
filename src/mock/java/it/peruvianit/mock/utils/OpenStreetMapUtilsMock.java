/**
 * 
 * Chiamate Http al servizio Google Maps Api e parser della risposta in 
 * formato JSon
 * 
 * @author Sergio Arellano Diaz
 * @version 1.0.1
 * @since 17/12/2016
 *
 */

package it.peruvianit.mock.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import it.peruvianit.exceptions.GeoException;

public class OpenStreetMapUtilsMock {

    public final static Logger logger = Logger.getLogger(OpenStreetMapUtilsMock.class);

    private static OpenStreetMapUtilsMock instance = null;

    public OpenStreetMapUtilsMock() {}

    public static OpenStreetMapUtilsMock getInstance() {
        if (instance == null) {
            instance = new OpenStreetMapUtilsMock();
        }
        return instance;
    }
    
    /**
	 * Richieste delle coordinate da un indirizzo
	 * 
	 * @param address indirizzo che sara utilizato come parametro al servizio
	 * Google Maps Api
	 * @return Map<String, Double> una mappa per la LAT e LON
	 * @throws GeoException 
	 */	
    public Map<String, Double> getCoordinates(String address) throws GeoException {
        Map<String, Double> res = new HashMap<>();
    
        res.put("lon", 43.5152D);
        res.put("lat", 17.2323D);
        return res;
    }
}

