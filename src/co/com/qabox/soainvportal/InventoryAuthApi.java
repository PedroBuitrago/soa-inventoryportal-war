package co.com.qabox.soainvportal;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.util.DateUtil;

import co.com.qabox.soainvportal.ejb.AuthServiceLocal;
import co.com.qabox.soainvportal.exceptions.WebAppFriendlyException;
import co.com.qabox.soainvportal.to.IdentityDTO;
import co.com.qabox.soainvportal.to.MenuDTO;
import co.com.qabox.soainvportal.to.MsgBodyDTO;
import co.com.qabox.soainvportal.to.MsgHeaderDTO;
import co.com.qabox.soainvportal.to.MsgMasterDTO;
import co.com.qabox.soainvportal.to.MsgResultDTO;
import co.com.qabox.soainvportal.to.ResultType;
import co.com.qabox.soainv.exceptions.IntegrationException;


@RequestScoped
@Path("InventoryAuthService")
//@RolesAllowed({"ADMIN", "ORG1"})
public class InventoryAuthApi {

	public static Logger LOG =  LogManager.getLogger(InventoryAuthApi.class);
	
	private AuthServiceLocal authService;
	
	/**
     * Default constructor. 
     */
    public InventoryAuthApi() throws WebAppFriendlyException{
    	try {
	        String lookupName = "java:app/SOA-Inventory-Ejb/AuthService!com.qabox.inv.services.AuthServiceLocal";
	        authService = (AuthServiceLocal) InitialContext.doLookup(lookupName);
	        
	    } catch (NamingException e) {
	    	LOG.fatal("[FATAL] Error in ServiceLocator. Services OUTLINE", e);
	    	throw new WebAppFriendlyException("[FATAL] Error in ServiceLocator. Services OUTLINE", e);
	    }
    }
    
    @Path("getMenu")
	@GET
	@Produces("application/json")
    //@RolesAllows("ADMIN")
	public MsgMasterDTO getMenu(@QueryParam("user") String clientId, @QueryParam("user") String user, @QueryParam("pass")String pass){
    	
    	LOG.info("Ingresa el usuario: " + user + " con password: " + pass);
    	
    	MsgMasterDTO master = new MsgMasterDTO();
    	Collection<MenuDTO> menu = new ArrayList<MenuDTO>();
    	MsgBodyDTO body = new MsgBodyDTO();
    	body.setContentType("Menu");
    	//MsgBodyDTO<Collection<MenuDTO>> body = (MsgBodyDTO<Collection<MenuDTO>>) new MsgBodyDTO(Collection.class);
    	MsgResultDTO result = new MsgResultDTO();
    	MsgHeaderDTO header = new MsgHeaderDTO();
    	
    	header.setClientId(clientId);
    	try {
			header.setDate(co.com.qabox.util.date.DateUtil.getFormatDate(co.com.qabox.util.date.DateUtil.dateFormatyyyyMMddhh_mm_ss));
		} catch (ParseException e1) {
			header.setDate("Undefined");
		}
    	try {
			header.setId("IDTXN_" + co.com.qabox.util.date.DateUtil.getFormatDate(co.com.qabox.util.date.DateUtil.dateFormatyyyyMMddhhmmss));
		} catch (ParseException e1) {
			header.setId("Undefined");
		}
    	
    	try {
    		
    		menu = authService.getMenu(user);
			
    		if ( menu.isEmpty() ){
    			result.setCode("QA-003");
				result.setDesc("DESC ERR_QA_003");
				result.setResultType(ResultType.ERROR);
    		}else{
    			body.setContent(menu);
    		}
    			
    	} catch (IntegrationException e) {
			LOG.error( "MOntar los mensajes de error en constantes" , e);
			
			result.setCode("QA-001");
			result.setDesc("DESC ERR_QA_001");
			result.setResultType(ResultType.ERROR);
			
		}
    	
    	master.setResult(result);
    	master.setHeader(header);
    	
    	master.setBody(body);
    	
    	return master;
    	
    	
    }
    
    @Path("checkAuth")
	@GET
	@Produces("application/json")
    //@RolesAllows("ADMIN")
	public MsgMasterDTO checkAuth(@QueryParam("user") String clientId, @QueryParam("user") String user, @QueryParam("pass")String pass){
    	
    	LOG.info("Ingresa el usuario: " + user + " con password: " + pass);
    	
    	MsgMasterDTO master = new MsgMasterDTO();
    	IdentityDTO identity = new IdentityDTO();
    	MsgBodyDTO body = new MsgBodyDTO();
    	body.setContentType("Identity");
    	
    	MsgResultDTO result = new MsgResultDTO();
    	MsgHeaderDTO header = new MsgHeaderDTO();
    	
    	header.setClientId(clientId);
    	
    	try {
			header.setDate(co.com.qabox.util.date.DateUtil.getFormatDate(co.com.qabox.util.date.DateUtil.dateFormatyyyyMMddhh_mm_ss));
		} catch (ParseException e1) {
			header.setDate("Undefined");
		}
    	try {
			header.setId("IDTXN_" + co.com.qabox.util.date.DateUtil.getFormatDate(co.com.qabox.util.date.DateUtil.dateFormatyyyyMMddhhmmss));
		} catch (ParseException e1) {
			header.setId("Undefined");
		}
    	
    	try {
    		
    		identity.setFirtName(user);
    		identity.setUser(user);
    		identity.setPass(pass);
    		
			identity = authService.checkUser(identity);
			
			if ( identity.isAuthenticated() ){
				result.setCode("QA-000");
				result.setDesc("" + ResultType.OK);
				result.setResultType(ResultType.OK);
			}else{
				result.setCode("QA-002");
				result.setDesc("DESC ERR_QA_002");
				result.setResultType(ResultType.ERROR);
			}
			
		} catch (IntegrationException e) {
			LOG.error( "MOntar los mensajes de error en constantes" , e);
			
			result.setCode("QA-001");
			result.setDesc("DESC ERR_QA_001");
			result.setResultType(ResultType.ERROR);
			
		}
    	
    	/*Cambiar el password*/
    	identity.setPass("##########");
    	master.setResult(result);
    	master.setHeader(header);
    	body.setContent(identity);
    	master.setBody(body);
    	
    	return master;
    	
    }
	
	
}
