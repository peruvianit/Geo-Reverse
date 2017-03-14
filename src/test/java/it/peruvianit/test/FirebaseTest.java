package it.peruvianit.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import it.peruvianit.bean.ProcessBean;
import it.peruvianit.bean.SummaryGeo;
import it.peruvianit.utils.FirebaseUtil;

public class FirebaseTest {

	@Test
	public void test() {
		FirebaseUtil firebaseUtil = null;
		try {
			firebaseUtil = FirebaseUtil.getInstance("https://geometric-e91b0.firebaseio.com/",
													"D:\\Progetti\\Geo\\App\\security\\GeoMetric-93b5f0e216a5.json");
			
			SummaryGeo summaryGeo = new SummaryGeo();
			summaryGeo.setRowTotal(200);
			summaryGeo.setRowLastSuccess(2);
			summaryGeo.setRowLastFail(2);
			summaryGeo.setRowLastWorking(2);
			summaryGeo.setRowSuccess(2);
			summaryGeo.setRowFail(2);
			
			ProcessBean processBean = new ProcessBean();
			processBean.setNameProcess("Prova Process Bean");
			processBean.setStartProcess(1000L);
			processBean.setEndProcess(5000L);
			processBean.setElapsedTime(4000L);
			summaryGeo.setProcessBean(processBean);
			
			firebaseUtil.addRow("elapsedTimeRequest", summaryGeo);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Errore con la comunicazione con Firebase");
		}
	}

}
