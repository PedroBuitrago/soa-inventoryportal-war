package co.com.qabox.soainvportal.crud.view.technicalService;

/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.Comparator;

import org.primefaces.model.SortOrder;

import co.com.qabox.soainv.to.TechnicalServiceTO;

public class LazySorter implements Comparator<TechnicalServiceTO> {

    private String sortField;
    
    private SortOrder sortOrder;
    
    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(TechnicalServiceTO item1, TechnicalServiceTO item2) {
        try {
            Object value1 = TechnicalServiceTO.class.getField(this.sortField).get(item1);
            Object value2 = TechnicalServiceTO.class.getField(this.sortField).get(item2);

            int value = ((Comparable)value1).compareTo(value2);
            
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
