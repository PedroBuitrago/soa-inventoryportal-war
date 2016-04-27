package co.com.qabox.soainvportal.login;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.util.SecurityUtils;

import co.com.qabox.soainv.ejb.identity.IdentityServiceLocal;
import co.com.qabox.soainv.exception.IntegrationException;
import co.com.qabox.soainv.to.identity.IdentityDTO;
import co.com.qabox.soainv.to.identity.RoleDTO;

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
		
		init();
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.invalidate();
		
		FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("You have been logged out successfully"));
        ExternalContext externalContext = context.getExternalContext();
        externalContext.getFlash().setKeepMessages(true);
        externalContext.invalidateSession();
        try {
			//externalContext.redirect("login.xhtml?msg=You have been logged out sucessfully");
			externalContext.redirect("login.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
	
	public boolean checkProfile(String codeRef){
		
		System.out.println("Checking Profile for: " + identity.getRole().getCode() + " and code = " + codeRef);
		
		int codeRefInt = -1;
		
		try{
			 codeRefInt = Integer.valueOf(codeRef);
			System.out.println(codeRefInt + " for ->  " + codeRef);
		}catch(NumberFormatException e){
			System.out.println("Error de formato de " + codeRef + " no es un entero");
			return false;
		}
		
		if ( IdentityDTO.basicValidation(identity) ){
			
			int identityCodeRef = -1;
			try{
				identityCodeRef = Integer.valueOf(identity.getRole().getCode());
				System.out.println("El codigo de role en el Identity es: " + identityCodeRef);
			}catch(NumberFormatException e){
				System.out.println("Error de formato de " + identity.getRole().getCode() + " no es un entero");
				return false;
			}
			
			if ( codeRefInt == identityCodeRef )
				return true;
			
		}
		
		return false;
		
	}
	
	
}
