package com.bolsadeideas.springboot.form.app.models.domain;

public class Rol {

	private Integer id;
	private String nombre;
	private String rol;
	
	public Rol() {

	}
	
	public Rol(Integer id, String nombre, String rol) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.rol = rol;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof Rol)) {
			return false;
		}
		
		Rol rol = (Rol)obj;
		return this.id != null && this.id.equals(rol.getId());
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	} 
	
}
