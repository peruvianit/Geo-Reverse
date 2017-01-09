/**
 * Thread creato per ogni file.
 * Per ogni file elaborato viene creato un file con il formato :
 *  - UUID_OK
 *  - UUID_KO
 * Dove saranno aggiunti le richieste al servizio Google Maps Api
 * 
 * @author Sergio Arellano Diaz
 * @version 1.0.1
 * @since 17/12/2016
 *
 */

package it.peruvianit.invoke;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import it.peruvianit.bean.GeoConfig;
import it.peruvianit.exceptions.GeoException;
import it.peruvianit.repository.MapCodeAddress;
import it.peruvianit.utils.OpenStreetMapUtils;

public class Interpreter implements Runnable {
	public final static Logger logger = Logger.getLogger(Interpreter.class);
	
	private final Integer csvColumnCode;
	private final Integer csvColumnAddress;
	private final String pathFileName;
	private final String pathDirectoryProcess;
	
    public Interpreter(String pathFileName, GeoConfig geoConfig) {
		super();
		this.pathFileName = pathFileName;
		this.csvColumnCode = Integer.parseInt(geoConfig.getCsvColumnCode());
		this.csvColumnAddress = Integer.parseInt(geoConfig.getCsvColumnAddress());
		this.pathDirectoryProcess = geoConfig.getPathDirectoryProcess();
	}

	@SuppressWarnings("resource")
	public void run() {
		BufferedReader br = null;
		FileReader fr = null;

		BufferedWriter bw_OK = null;
		BufferedWriter bw_KO = null;
		
		try {
			fr = new FileReader(this.pathFileName);
			br = new BufferedReader(fr);

			String fileName = FilenameUtils.getName(this.pathFileName);
			if (fileName.lastIndexOf(".")>=0){
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
			}
			
			String UUID_code = UUID.randomUUID().toString();
			
			String fileWorking = this.pathDirectoryProcess + "\\" + fileName + "-" + UUID_code;
			bw_OK = new BufferedWriter(new FileWriter(fileWorking + ".OK"));
			bw_KO = new BufferedWriter(new FileWriter(fileWorking + ".KO"));
			
			String sCurrentLine;
			int riga = 1;
			while ((sCurrentLine = br.readLine()) != null) {
				String addressArray[] = sCurrentLine.split("\\|\\|");
				if (addressArray.length >= 2){
					Map<String, Double> coords = null;
					String idAddress = addressArray[csvColumnCode-1];
	        		String address = addressArray[csvColumnAddress-1];
	        		
	        		if(!MapCodeAddress.getInstance().findCodeAddress(idAddress)){
		                try {
							coords = OpenStreetMapUtils.getInstance().getCoordinates(address);
						} catch (GeoException gEx) {							
							throw gEx;
						}
		                
		                String status;
		                if(coords != null && coords.size()>0){
		                	bw_OK.write(sCurrentLine + "||" + coords.get("lat") + "||" + coords.get("lon"));
		                	bw_OK.newLine();
		                	bw_OK.flush();
		                	status = "OK";
		                }else{
		                	bw_KO.write(idAddress + "||" + address);
		                	bw_KO.newLine();
		                	bw_KO.flush();
		                	status = "KO";
		                	logger.warn(idAddress + "||" + address);
		                }
		                logger.debug("Riga elaborata [" + idAddress + "][" + status + "]: " + riga++);
	        		}
				}else{
					logger.warn("Riga Corrotta : " + sCurrentLine);
				}
			}
			logger.info("Fine elaborazione per : " + pathFileName + ", numero di righe processati : " + riga);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}catch (GeoException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
				if (bw_OK != null)
					bw_OK.close();
				if (bw_KO != null)
					bw_KO.close();
			} catch (IOException ex) {
				logger.error(ex.getMessage());
				ex.printStackTrace();
			}
		}
    } 
}
