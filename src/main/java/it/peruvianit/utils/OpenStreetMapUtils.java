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

package it.peruvianit.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import it.peruvianit.exceptions.GeoException;

public class OpenStreetMapUtils{

    public final static Logger logger = Logger.getLogger(OpenStreetMapUtils.class);

    private static OpenStreetMapUtils instance = null;
    @SuppressWarnings("unused")
	private JSONParser jsonParser;

    public OpenStreetMapUtils() {
        jsonParser = new JSONParser();
    }

    public static OpenStreetMapUtils getInstance() {
        if (instance == null) {
            instance = new OpenStreetMapUtils();
        }
        return instance;
    }

    
    /**
	 * Chiamata al servizio Google Maps Api
	 * 
	 * @param url https://maps.googleapis.com/maps/api/geocode/json?address=
	 * @throws Exception 
	 */	
    private String getRequest(String url) throws Exception {

        final URL obj = new URL(url);
        final HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        if (con.getResponseCode() != 200) {
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    /**
	 * Richieste delle coordinate da un indirizzo
	 * 
	 * @param address indirizzo che sara utilizato come parametro al servizio
	 * Google Maps Api
	 * @return Map<String, Double> una mappa per la LAT e LON
	 * @throws GeoException 
	 */	
    public Map<String, Double> getCoordinates(String address, String googleKeyApi) throws GeoException {
        Map<String, Double> res;
        StringBuffer query;
        String[] split = address.split(" ");
        String queryResult = null;

        query = new StringBuffer();
        res = new HashMap<String, Double>();

        query.append("https://maps.googleapis.com/maps/api/geocode/json?address=");
       
        if (split.length == 0) {
            return null;
        }

        for (int i = 0; i < split.length; i++) {
            query.append(split[i]);
            if (i < (split.length - 1)) {
                query.append("+");
            }
        }
        query.append("&key=" + googleKeyApi); // CHIAVE DA GOOGLE ACCOUNT

        try {
            queryResult = getRequest(query.toString());
        } catch (UnknownHostException uhEx) {
			logger.error("Problemi con la conessione al server: " + uhEx.getMessage());
			throw new GeoException(uhEx);
        } catch (Exception ex) {
            logger.error("Errore quando si cerca di ottenere i dati con la seguente Query: " + query);
            throw new GeoException(ex);
        }

        if (queryResult == null) {
            return null;
        }

        Object obj = JSONValue.parse(queryResult);

        if (obj instanceof JSONObject) {
        		try{
	                JSONObject jsonObject2 = (JSONObject)obj;
	                
	                if (jsonObject2.get("status").equals("OK") || jsonObject2.get("status").equals("ZERO_RESULTS")){
	                    JSONObject jsonObject = (JSONObject)((JSONObject)((JSONObject)((JSONArray)jsonObject2.get("results")).get(0)).get("geometry")).get("location");
		
		                String lon = String.valueOf(jsonObject.get("lng"));
		                String lat = String.valueOf(jsonObject.get("lat"));
		                res.put("lon", Double.parseDouble(lon));
		                res.put("lat", Double.parseDouble(lat));
	                }else{
	                	// "status":"OVER_QUERY_LIMIT" E ALTRI
	                	throw new GeoException("Problemi con la risposta : " + obj.toString());
	                }
        		}catch (GeoException gEx) {
        			throw gEx;
        		}
        		catch (Exception ex) {	
        			logger.warn("Controllare : " + query);
				}
        }

        return res;
    }
}

