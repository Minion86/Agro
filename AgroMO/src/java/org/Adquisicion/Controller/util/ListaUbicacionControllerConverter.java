/*
 * Copyright 2009 Prime Technology.
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
package org.Adquisicion.Controller.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.Adquisicion.Controller.ListaUbicacionController;
import org.Adquisicion.Entities.Ubicacion;

public class ListaUbicacionControllerConverter implements Converter {

    /**
     *
     * @param facesContext
     * @param component
     * @param value
     * @return
     */
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || "TODOS".equals(value) || "Por favor seleccione un valor".equals(value)) {
            return null;
        }
        ListaUbicacionController controller = (ListaUbicacionController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "listaUbicacionController");
        return controller.getUbicacion(getKey(value));
    }

    java.lang.Integer getKey(String value) {
        java.lang.Integer key;
        key = Integer.valueOf(value);
        return key;
    }

    String getStringKey(java.lang.Integer value) {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        return sb.toString();
    }

    /**
     *
     * @param facesContext
     * @param component
     * @param object
     * @return
     */
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Ubicacion) {
            Ubicacion o = (Ubicacion) object;
            return getStringKey(o.getIdUbicacion());
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Ubicacion.class.getName());
        }
    }

}
