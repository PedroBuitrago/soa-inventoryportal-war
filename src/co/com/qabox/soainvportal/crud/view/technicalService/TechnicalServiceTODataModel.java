package co.com.qabox.soainvportal.crud.view.technicalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import co.com.qabox.soainv.to.TechnicalServiceTO;

public class TechnicalServiceTODataModel extends LazyDataModel<TechnicalServiceTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4763954970846273981L;
	private List<TechnicalServiceTO> datasource;
    
    public TechnicalServiceTODataModel(List<TechnicalServiceTO> datasource) {
        this.datasource = datasource;
    }
    
    @Override
    public TechnicalServiceTO getRowData(String rowKey) {
        for(TechnicalServiceTO item : datasource) {
            if(item.getId().equals(rowKey))
                return item;
        }

        return null;
    }

    @Override
    public Object getRowKey(TechnicalServiceTO item) {
        return item.getId();
    }

    @Override
    public List<TechnicalServiceTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<TechnicalServiceTO> data = new ArrayList<TechnicalServiceTO>();

        //filter
        for(TechnicalServiceTO car : datasource) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(car.getClass().getField(filterProperty).get(car));

                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                    }
                    else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                    }
                }
            }

            if(match) {
                data.add(car);
            }
        }

        //sort
        if(sortField != null) {
            Collections.sort(data, new LazySorter(sortField, sortOrder));
        }

        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
	
}
