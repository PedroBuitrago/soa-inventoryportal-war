package co.com.qabox.soainvportal.crud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import co.com.qabox.soainv.ejb.TechnicalServiceLocal;
import co.com.qabox.soainv.exception.SoaInventoryException;
import co.com.qabox.soainv.to.TechnicalServiceTO;
import co.com.qabox.soainvportal.util.JsfUtil;
import co.com.qabox.soainvportal.util.JsfUtil.PersistAction;

@Named("technicalServiceController")
@RequestScoped
public class TechnicalServiceController implements Serializable {

	private static final long serialVersionUID = -4536906140213308826L;
	@Inject TechnicalServiceTO techServTo;
	@Inject TechnicalServiceLocal technicalServiceRemote;
	private List<TechnicalServiceTO> items = null;
	
	private ResourceBundle BUNDLE = ResourceBundle.getBundle("co/com/qabox/soainvportal/labels");
	
    //Se utilizara el techServTo
	//private TechnicalServiceTO selected;
	
	private String nombre;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void create() {
        persist(PersistAction.CREATE, BUNDLE.getString("labels.createservice.createdOk"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, BUNDLE.getString("labels.updateservice.updatedOk"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, BUNDLE.getString("labels.removeservice.removedOk"));
        if (!JsfUtil.isValidationFailed()) {
            techServTo = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TechnicalServiceTO> getItems() {
        if (items == null) {
            try {
				//items = getFacade().findAll();
            	
            	List<TechnicalServiceTO> items = new ArrayList<>();
            	TechnicalServiceTO item = new TechnicalServiceTO();
            	item.setId(Integer.getInteger("1"));
            	item.setName("Nombre servicio 1");
            	
            	items.add(item);
            	
            	item = new TechnicalServiceTO();
            	item.setId(Integer.getInteger("2"));
            	item.setName("Nombre servicio 2");
            	
            	items.add(item);
            	
            //} catch (SoaInventoryException e) {
            } catch (Exception e) {
				JsfUtil.addErrorMessage(e, BUNDLE.getString("labels.listservice.findAllException"));
			}
        }
        return items;
    }

	private void persist(PersistAction persistAction, String successMessage) {
        if (techServTo != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(techServTo);
                } else {
                    getFacade().remove(techServTo);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (SoaInventoryException e) {
                String msg = "";
                Throwable cause = e.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(e, BUNDLE.getString("labels.createservice.persistenceException"));
                }
            } catch (Exception ex) {
                //Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, BUNDLE.getString("labels.createservice.persistenceException"));
            }
        }
    }
     
	protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }
    
    public TechnicalServiceTO prepareCreate() {
        initializeEmbeddableKey();
        return techServTo;
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

	public TechnicalServiceLocal getFacade() {
		return technicalServiceRemote;
	}
    
    
	
}
