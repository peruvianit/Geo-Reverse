/**
 * 
 * @author Sergio Arellano Diaz
 * @version 1.0.1
 * @since 17/12/2016
 *
 */

package it.peruvianit.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import it.peruvianit.exceptions.GeoException;

public class FileUtils {
	public static String getPathClass(String pathName) throws GeoException{
		try {
			pathName = URLDecoder.decode(pathName,"utf-8"); // convert the path format from HTML to UTF
			pathName = pathName.substring(1,pathName.lastIndexOf("/") );
			
			FileUtils.checkDirecctory(pathName);
			
		} catch (UnsupportedEncodingException uEx) {
			throw new GeoException(uEx);
		} 

		return pathName;
	}
	
	public static void checkDirecctory(String ... pathDirectory) throws GeoException{
		for (String path : pathDirectory) {
			File file = new File(path);
			
			if (file.exists()) {
			    if (!file.isDirectory()) {
			        throw new GeoException("Directory non esistente : " + pathDirectory);
			    }
			}
		}
	}
}
