package com.bolsadeideas.springboot.form.app.models.domain;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.bolsadeideas.springboot.form.app.validation.IdentificadorRegex;
import com.bolsadeideas.springboot.form.app.validation.Requerido;

/**Clase para mapear el formulario de registro de usuario */
public class Usuario {
	
	
	//@NotEmpty(message = "El nombre de usuario no puede estar vacío")
	//@Size(min  = 3, max = 8)
	private String username;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotBlank
	@Size(min  = 3, max = 8)
	private String nombre;
	
	//@NotEmpty
	@Requerido
	private String apellido;
	
	//@Pattern(regexp = "[0-9]{2}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}")
	@IdentificadorRegex
	private String identificador;  //Como no está en el formulario no se valida
	
	@Min(1)
	@Max(5000)
	@NotNull
	private Integer cuenta;
	
	@NotNull
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past  //Valida que sea fecha pasada respecto a la actual
	//@Future
	private Date fechaNacimiento;
	
	//@NotEmpty - no se usa para objetos
	
	//@Valid -- Se usa para que valide el objeto completo
	@NotNull
	private Pais pais;
	
	@NotEmpty
	private List<Rol> roles;
	
	private Boolean habilitar;
	
	private String valorSecreto;
	
	@NotEmpty
	private String genero;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public Integer getCuenta() {
		return cuenta;
	}
	public void setCuenta(Integer cuenta) {
		this.cuenta = cuenta;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	public List<Rol> getRoles() {
		return roles;
	}
	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	public Boolean getHabilitar() {
		return habilitar;
	}
	public void setHabilitar(Boolean habilitar) {
		this.habilitar = habilitar;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getValorSecreto() {
		return valorSecreto;
	}
	public void setValorSecreto(String valorSecreto) {
		this.valorSecreto = valorSecreto;
	}
	
	
}
