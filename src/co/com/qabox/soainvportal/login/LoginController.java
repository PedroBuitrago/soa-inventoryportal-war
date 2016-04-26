package co.com.qabox.soainvportal.login;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import co.com.qabox.soainv.ejb.identity.IdentityServiceLocal;
import co.com.qabox.soainv.exception.IntegrationException;
import co.com.qabox.soainv.to.identity.IdentityDTO;
import co.com.qabox.soainv.to.identity.RoleDTO;

//@ManagedBean(name="loginController")
//@SessionScoped
@Named("loginController")
@javax.enterprise.context.SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = -8167728447743856025L;

	private IdentityDTO identity = new IdentityDTO(new RoleDTO());
	@Inject
	IdentityServiceLocal identityService;

	private boolean logged = false;
	
	@PostConstruct
	public void init(){
		setIdentity(new IdentityDTO(new RoleDTO()));
		setLogged(false);
	}

	public void login(ActionEvent actionEvent) {

		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage msg = null;
		
		System.out.println(identity);
		
		
		if ( IdentityDTO.basicValidation(identity)  ) {
			
			try {
				identity = identityService.checkUser(identity);
				
				if ( identity != null ){
					System.out.println("Identity es diferente de null");
					logged = true;
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@",
							identity.getUser());
				}
				else{
					System.out.println("Identity es null");
					init();
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
							"Credenciales inválidas");
				}
			} catch (IntegrationException e) {
				logged = false;
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
						"No es posible autenticar el usaurio consulta con el administrador " );
			}
			
		} else {
			logged = false;
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
					"Ingrese todos los datos requeridos");
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("islogged", logged);
		if (logged)
			context.addCallbackParam("view",
					"inv/crud/listservice.xhtml");
		

	}

	public void logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.invalidate();
		logged = false;
	}

	public IdentityDTO getIdentity() {
		return identity;
	}

	public void setIdentity(IdentityDTO identity) {
		this.identity = identity;
	}

	public IdentityServiceLocal getIdentityService() {
		return identityService;
	}

	public void setIdentityService(IdentityServiceLocal identityService) {
		this.identityService = identityService;
	}

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	
}
