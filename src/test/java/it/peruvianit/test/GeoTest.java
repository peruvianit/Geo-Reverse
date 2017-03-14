package it.peruvianit.test;

import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Test;

import it.peruvianit.exceptions.GeoException;
import it.peruvianit.utils.OpenStreetMapUtils;

public class GeoTest {

	@Test
	public void test() throws GeoException {
		Map<String, Double> coords;
		
		String address = "via tritone anzio";
		
        coords = OpenStreetMapUtils.getInstance().getCoordinates(address);
        
        if (coords == null){
        	fail("Not yet implemented");
        }
        System.out.println("latitude :" + coords.get("lat"));
        System.out.println("longitude:" + coords.get("lon"));
        
		
	}

}
