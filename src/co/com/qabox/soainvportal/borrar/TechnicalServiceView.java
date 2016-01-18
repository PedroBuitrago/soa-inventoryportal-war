package co.com.qabox.soainvportal.borrar;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import co.com.qabox.soainv.ejb.TechnicalServiceLocal;
import co.com.qabox.soainv.ejb.TechnicalServiceRemote;
import co.com.qabox.soainv.to.TechnicalServiceTO;

@ManagedBean
@RequestScoped
public class TechnicalServiceView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4536906140213308826L;
	@Inject TechnicalServiceTO techServTo;
	//@Inject TechnicalServiceRemote technicalServiceRemote;
	@Inject TechnicalServiceLocal technicalServiceRemote;
	
	private String nombre;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void saveInfo ( ActionEvent actionEvent ){
		
		System.out.println("Testing");
		
		
//		TechnicalServiceRemote techServBean = null;
//		try {
//			techServBean = getService(JNDI_TECH_SERV_BEAN);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		if ( techServBean != null ){
		if  (technicalServiceRemote != null){
			TechnicalServiceTO to = new TechnicalServiceTO();
			to.setName("Crudo");
			
			technicalServiceRemote.saveInfo( to );
			//techServTo.setName(this.nombre);
			//technicalServiceRemote.saveInfo(techServTo);
			//technicalServiceRemote.saveInfo();
			 addMessage("Almacenamiento OK ");
		}
		else{
			addMessage("Error del sistema, no se localizar el servicio de soporte para Servicios Técnicos");
		}
		
		
    }
     
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public TechnicalServiceTO getTechServTo() {
		return techServTo;
	}

	public void setTechServTo(TechnicalServiceTO techServTo) {
		this.techServTo = techServTo;
	}
    
    
	
}
