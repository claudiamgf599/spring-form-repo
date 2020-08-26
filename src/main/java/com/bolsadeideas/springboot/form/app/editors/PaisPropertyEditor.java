package com.bolsadeideas.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.form.app.services.PaisService;

@Component
public class PaisPropertyEditor extends PropertyEditorSupport {

	@Autowired
	private PaisService service;
	
	@Override
	public void setAsText(String idString) throws IllegalArgumentException {
		if(idString != null && idString.length() > 0) {
			try {
				Integer id = Integer.parseInt(idString);
				//System.out.println("----- id: " + id);
				//System.out.println("----- id: " + service.obtenerPorId(id));
				this.setValue(service.obtenerPorId(id));
			}catch(NumberFormatException ne) {
				setValue(null);
			}
		}else {
			setValue(null);
		}
	}

}
