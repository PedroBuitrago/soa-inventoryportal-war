package co.com.qabox.soainvportal.crud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import co.com.qabox.soainv.ejb.TechnicalServiceLocal;
import co.com.qabox.soainv.to.TechnicalServiceTO;
import co.com.qabox.soainvportal.util.JsfUtil;

@Named("technicalServiceController")
@RequestScoped
public class TechnicalServiceController implements Serializable {

	private static final long serialVersionUID = -4536906140213308826L;
	
	//@Inject TechnicalServiceTO techServTo;
	@Inject TechnicalServiceLocal technicalServiceRemote;
	private List<TechnicalServiceTO> items = new ArrayList<TechnicalServiceTO>();
	//private List<TechnicalServiceTO> selectedItems = null;
	private TechnicalServiceTO selectedItem = null;
	
	private ResourceBundle BUNDLE = ResourceBundle.getBundle("co/com/qabox/soainvportal/labels");
	
	private String nombre;
	
		public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@PostConstruct
	public void init() {
		if ( items == null || items.size() == 0 ){
			try {
				//items = getFacade().findAll();
	        	
	        	TechnicalServiceTO item = null;
	        	
				for ( int i = 1; i < 50; i++ ){
					item = new TechnicalServiceTO();
		        	item.setId(Integer.valueOf(i));
		        	item.setName("Servicio Numero " + i);
		        	
		        	this.items.add(item);
				}
				
				//System.out.println("El numero de items es " + items.size());
			      
	        	
	        //} catch (SoaInventoryException e) {
	        } catch (Exception e) {
				JsfUtil.addErrorMessage(e, BUNDLE.getString("labels.listservice.findAllException"));
				
			}
		}
    }
	
//	public void create() {
//        persist(PersistAction.CREATE, BUNDLE.getString("labels.createservice.createdOk"));
//        if (!JsfUtil.isValidationFailed()) {
//            items = null;    // Invalidate list of items to trigger re-query.
//        }
//    }
//
//    public void update() {
//        persist(PersistAction.UPDATE, BUNDLE.getString("labels.updateservice.updatedOk"));
//    }

//    public void destroy() {
//        persist(PersistAction.DELETE, BUNDLE.getString("labels.removeservice.removedOk"));
//        if (!JsfUtil.isValidationFailed()) {
//            techServTo = null; // Remove selection
//            items = null;    // Invalidate list of items to trigger re-query.
//        }
//    }

    
   
    
    public List<TechnicalServiceTO> getItems() {
        if (items == null || items.size() == 0) {
            init();
        }
        
        return items;
    }

    
//    public TechnicalServiceTO getTechnigcalService(java.lang.Integer id) {
//    	TechnicalServiceTO item = new TechnicalServiceTO();
//    	item.setId(Integer.getInteger("1"));
//    	item.setName("Nombre servicio 1");
//    	return item;
//    }
    
//	private void persist(PersistAction persistAction, String successMessage) {
//       
//		if (techServTo != null) {
//            
//            try {
//                if (persistAction != PersistAction.DELETE) {
//                    getFacade().edit(techServTo);
//                } else {
//                    getFacade().remove(techServTo);
//                }
//                JsfUtil.addSuccessMessage(successMessage);
//            } catch (SoaInventoryException e) {
//                String msg = "";
//                Throwable cause = e.getCause();
//                if (cause != null) {
//                    msg = cause.getLocalizedMessage();
//                }
//                if (msg.length() > 0) {
//                    JsfUtil.addErrorMessage(msg);
//                } else {
//                    JsfUtil.addErrorMessage(e, BUNDLE.getString("labels.createservice.persistenceException"));
//                }
//            } catch (Exception ex) {
//                //Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//                JsfUtil.addErrorMessage(ex, BUNDLE.getString("labels.createservice.persistenceException"));
//            }
//        }
//    }

	public void onRowSelect(SelectEvent event) {
		

		System.out.println(getSelectedItem());
		//System.out.println("Evento de rowselect para el ts: " +((TechnicalServiceTO) event.getObject()).getId() );
		
        //FacesMessage msg = new FacesMessage("Servicio Seleccionado", "" + ((TechnicalServiceTO) event.getObject()).getId());
        
        //System.out.println("Se configura el facesmessga " + msg.getSummary() + " - " + msg);
        
        //FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Servicio Deseleccionado", "" +  ((TechnicalServiceTO) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
//	public TechnicalServiceTO prepareCreate() {
//        return techServTo;
//    }
	
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
//    public TechnicalServiceTO getTechServTo() {
//		return techServTo;
//	}
//
//	public void setTechServTo(TechnicalServiceTO techServTo) {
//		this.techServTo = techServTo;
//	}

	public TechnicalServiceLocal getFacade() {
		return technicalServiceRemote;
	}

//	public List<TechnicalServiceTO> getSelectedItems() {
//		return selectedItems;
//	}
//
//	public void setSelectedItems(List<TechnicalServiceTO> selectedItems) {
//		this.selectedItems = selectedItems;
//	}

	public TechnicalServiceTO getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(TechnicalServiceTO selectedItem) {
		this.selectedItem = selectedItem;
	}

	
}
