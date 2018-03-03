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

import it.peruvianit.bean.GeoConfigBean;
import it.peruvianit.bean.LoadInfo;
import it.peruvianit.bean.ProcessBean;
import it.peruvianit.bean.SummaryGeo;
import it.peruvianit.exceptions.GeoException;
import it.peruvianit.repository.MapCodeAddress;
import it.peruvianit.utils.DateUtils;
import it.peruvianit.utils.OpenStreetMapUtils;
import it.peruvianit.utils.SerializableUtils;
import it.peruvianit.utils.StringUtils;

public class Interpreter implements Runnable {
	public final static Logger logger = Logger.getLogger(Interpreter.class);
	
	private final Integer csvColumnCode;
	private final Integer csvColumnAddress;
	private final String pathFileName;
	private final String pathDirectoryProcess;
	private final String pathDirectoryMetrics;
	private final String googleKeyApi;
		
	private final LoadInfo loadInfo;
	
    public Interpreter(String pathFileName, GeoConfigBean geoConfig, LoadInfo loadInfo) {
		super();
		this.pathFileName = pathFileName;
		this.csvColumnCode = Integer.parseInt(geoConfig.getCsvColumnCode());
		this.csvColumnAddress = Integer.parseInt(geoConfig.getCsvColumnAddress());
		this.pathDirectoryProcess = geoConfig.getPathDirectoryProcess();
		this.pathDirectoryMetrics = geoConfig.getPathDirectoryMetrics();
		this.googleKeyApi = geoConfig.getGoogleKeyApi();
		this.loadInfo = loadInfo;
	}

	@SuppressWarnings("resource")
	public void run() {
		SummaryGeo summaryGeo = new SummaryGeo();
		ProcessBean processBean = new ProcessBean();
		processBean.setStartProcess(DateUtils.getCurrentTimeUTC());
		BufferedReader br = null;
		FileReader fr = null;

		BufferedWriter bw_OK = null;
		BufferedWriter bw_KO = null;

		int riga = 1;
		Integer rowSuccess = 0, rowFails = 0;
		
		try {
			summaryGeo.setRowLastWorking(loadInfo.getRowLastWorking());
			
			fr = new FileReader(this.pathFileName);
			br = new BufferedReader(fr);

			String fileName = FilenameUtils.getName(this.pathFileName);
			
			if (fileName.lastIndexOf(".")>=0){
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
			}
			
			String UUID_code = UUID.randomUUID().toString();
			
			processBean.setNameProcess(fileName + "-" + UUID_code);
			
			String fileWorking = StringUtils.createStringBuilder(this.pathDirectoryProcess, "\\", fileName, "-", UUID_code.toString()).toString();
			bw_OK = new BufferedWriter(new FileWriter(fileWorking + ".OK"));
			bw_KO = new BufferedWriter(new FileWriter(fileWorking + ".KO"));
			
			String sCurrentLine;
			
			while ((sCurrentLine = br.readLine()) != null) {
				String addressArray[] = sCurrentLine.split("\\|\\|");
				if (addressArray.length >= 2){
					Map<String, Double> coords = null;
					String idAddress = addressArray[csvColumnCode-1];
	        		String address = addressArray[csvColumnAddress-1];
	        		
	        		if(!MapCodeAddress.getInstance().findCodeAddress(idAddress)){
		                try {
							coords = OpenStreetMapUtils.getInstance().getCoordinates(address, googleKeyApi);
						} catch (GeoException gEx) {							
							throw gEx;
						}
		                
		                String status;
		                if(coords != null && coords.size()>0){
		                	bw_OK.write(StringUtils.createStringBuilder(sCurrentLine , "||" , coords.get("lat").toString() , "||" , coords.get("lon").toString()).toString());
		                	bw_OK.newLine();
		                	bw_OK.flush();
		                	status = "OK";
		                	rowSuccess++;
		                }else{
		                	bw_KO.write(StringUtils.createStringBuilder(idAddress, "||", address).toString());
		                	bw_KO.newLine();
		                	bw_KO.flush();
		                	status = "KO";
		                	rowFails++;
		                	logger.warn(StringUtils.createStringBuilder(idAddress, "||", address).toString());
		                }
		                logger.debug(StringUtils.createStringBuilder("Riga elaborata [", idAddress, "][", status, "]: ", String.valueOf(riga++)).toString());
	        		}
				}else{
					logger.warn(StringUtils.createStringBuilder("Riga Corrotta : ", sCurrentLine).toString());
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}catch (GeoException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}catch(Exception ex){
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}finally {
			processBean.setEndProcess(DateUtils.getCurrentTimeUTC());
			summaryGeo.setProcessBean(processBean);

			summaryGeo.setRowFail(rowFails);
			summaryGeo.setRowSuccess(rowSuccess);
			summaryGeo.setRowWorking(rowFails + rowSuccess);
			summaryGeo.setRowTotal(loadInfo.getRowTotal());
			
			logger.info("Fine elaborazione per : " + pathFileName + ", numero di righe processati : " + riga);
			logger.info("###################### Summary ######################");
			logger.info("Process Name: " + summaryGeo.getProcessBean().getNameProcess());
			logger.info("End process : " + summaryGeo.getProcessBean().getEndProcess());
			logger.info("Elapsed time : " + summaryGeo.getProcessBean().getElapsedTime());
			logger.info("Row total : " + summaryGeo.getRowTotal());
			logger.info("Last row Working : " + summaryGeo.getRowLastWorking());
			logger.info("Last row Success : " + loadInfo.getRowLastSuccess());
			logger.info("Last row Fail : " + loadInfo.getRowLastFails());
			logger.info("Row working current process : " + summaryGeo.getRowWorking());
			logger.info("Row success current process : " + summaryGeo.getRowSuccess());
			logger.info("Row fails current process: " + summaryGeo.getRowFail());
			logger.info("current lost address : " +  summaryGeo.getPercentualLost());
			logger.info("Percentual progress : " + summaryGeo.getPercentualProgress());
			logger.info("Start process : " + summaryGeo.getProcessBean().getStartProcess());
			
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
				if (bw_OK != null)
					bw_OK.close();
				if (bw_KO != null)
					bw_KO.close();
				
				SerializableUtils.serialize(this.pathDirectoryMetrics, "Summary", summaryGeo);
			} catch (IOException ex) {
				logger.error(ex.getMessage());
				ex.printStackTrace();
			}
		}
    } 
}
