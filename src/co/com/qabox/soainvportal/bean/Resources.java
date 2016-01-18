package co.com.qabox.soainvportal.bean;

import javax.ejb.EJB;
import javax.enterprise.inject.Produces;

import co.com.qabox.soainv.ejb.TechnicalServiceLocal;
import co.com.qabox.soainv.ejb.TechnicalServiceRemote;
import co.com.qabox.soainv.to.TechnicalServiceTO;

public class Resources {
    
	public static final String JNDI_TECH_SERV_BEAN = 
			//"java:global/soa-inventory-ear/soa-inventory-ejb/TechnicalServiceBean!co.com.qabox.soainv.ejb.TechnicalServiceRemote";
			"java:global/soa-inventory-ear/soa-inventory-ejb/TechnicalServiceBean!co.com.qabox.soainv.ejb.TechnicalServiceLocal";
	
	@Produces
    @EJB(lookup = JNDI_TECH_SERV_BEAN)
    //private TechnicalServiceRemote technicalServiceRemote;
	private TechnicalServiceLocal technicalServiceRemote;
    
    
    @Produces
    private TechnicalServiceTO techServTo;
}