package co.com.qabox.soainvportal.bean;

import javax.ejb.EJB;
import javax.enterprise.inject.Produces;

import co.com.qabox.soainv.ejb.TechnicalServiceLocal;
import co.com.qabox.soainv.ejb.identity.IdentityServiceLocal;
import co.com.qabox.soainv.to.TechnicalServiceTO;

public class Resources {
    
	public static final String JNDI_TECH_SERV_BEAN = 
			//"java:global/soa-inventory-ear/soa-inventory-ejb/TechnicalServiceBean!co.com.qabox.soainv.ejb.TechnicalServiceRemote";
			"java:global/soa-inventory-ear/soa-inventory-ejb/TechnicalServiceBean!co.com.qabox.soainv.ejb.TechnicalServiceLocal";
	
	public static final String JNDI_IDENTITY_BEAN = 
			//"java:global/soa-inventory-ear/soa-inventory-ejb/TechnicalServiceBean!co.com.qabox.soainv.ejb.TechnicalServiceRemote";
			"java:global/soa-inventory-ear/soa-inventory-ejb/IdentityServiceBean";
	
	@Produces
    @EJB(lookup = JNDI_TECH_SERV_BEAN)
    //private TechnicalServiceRemote technicalServiceRemote;
	private TechnicalServiceLocal technicalServiceLocal;
	
	@Produces
    @EJB(lookup = JNDI_IDENTITY_BEAN)
    //private TechnicalServiceRemote technicalServiceRemote;
	private IdentityServiceLocal identityService;
    
    @Produces public TechnicalServiceTO createTechnicalServiceTO() {
        //System.out.println("TechnicalServiceTO Creado con un productor");
        return new TechnicalServiceTO();
    }
}