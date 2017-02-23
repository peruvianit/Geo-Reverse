package it.peruvianit.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import it.peruvianit.bean.ProcessBean;
import it.peruvianit.bean.SummaryGeo;
import it.peruvianit.utils.SerializableUtils;

public class SerializeTest {

	@Test
	public void serializeTest() {
		SummaryGeo summaryGeo = new SummaryGeo();
		summaryGeo.setRowTotal(1000);
		summaryGeo.setRowLastSuccess(500);
		summaryGeo.setRowLastFail(300);
		summaryGeo.setRowLastWorking(800);
		summaryGeo.setRowSuccess(150);
		summaryGeo.setRowFail(230);
		
		ProcessBean processBean = new ProcessBean();
		processBean.setNameProcess("Prova Process Bean");
		processBean.setStartProcess(1000L);
		processBean.setEndProcess(5000L);
		processBean.setElapsedTime(4000L);
		summaryGeo.setProcessBean(processBean);
		
		try {
			SerializableUtils.serialize("c:\\temp", "summaryGeo", summaryGeo);
		} catch (IOException e) {
			fail("Problemi per la serializazione");
			e.printStackTrace();
		}
	}
	
	@Test
	public void deserializeTest() {
		try {
			SummaryGeo summaryGeo =  (SummaryGeo)SerializableUtils.deserialize("c:\\temp\\summaryGeo-e9d09708-3405-47cc-90e3-439f205c7dfd.ser");
			System.out.println(ReflectionToStringBuilder.toString(summaryGeo, ToStringStyle.MULTI_LINE_STYLE));
		} catch (IOException e) {
			fail("Problemi per la serializazione");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}