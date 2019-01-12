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
package org.Seguridades.Controller.util;




import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.Seguridades.Controller.SegListaPerfilController;
import org.Seguridades.Entities.SegPerfil;



 public class SegListaPerfilControllerConverter implements Converter {

    /**
     *
     * @param facesContext
     * @param component
     * @param value
     * @return
     */
    @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SegListaPerfilController controller = (SegListaPerfilController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "segListaPerfilController");
            return controller.getSegPerfil(getKey(value));
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
            if (object instanceof SegPerfil) {
                SegPerfil o = (SegPerfil) object;
                return getStringKey(o.getIdPerfil());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SegPerfil.class.getName());
            }
        }

    }

