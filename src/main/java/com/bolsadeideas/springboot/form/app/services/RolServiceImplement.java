package com.bolsadeideas.springboot.form.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.app.models.domain.Rol;

@Service
public class RolServiceImplement implements RoleService {
	
	private List<Rol> roles;
	
	public RolServiceImplement() {
		this.roles = new ArrayList<Rol>();
		this.roles.add(new Rol(1, "Administrador", "ROLE_ADMIN"));
		this.roles.add(new Rol(2, "Usuario", "ROLE_USER"));
		this.roles.add(new Rol(3, "Moderador", "ROLE_MODERATOR"));
	}

	@Override
	public List<Rol> listar() {
		return roles;
	}

	@Override
	public Rol obtenerPorId(Integer id) {
		Rol resultado = null;
		for(Rol rol: roles) {
			if(id == rol.getId()) {
				resultado = rol; 
				break;
			}
		}
		return resultado;
	}

}
