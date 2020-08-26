package com.bolsadeideas.springboot.form.app.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.app.models.domain.Pais;

@Service
public class PaisServiceImplement implements PaisService {

	private List<Pais> lista;
	
	public PaisServiceImplement() {
		this.lista = Arrays.asList(
			new Pais(1, "ES", "España"),
			new Pais(2, "CO", "Colombia"),
			new Pais(3, "FR", "Francia"),
			new Pais(4, "CA", "Canadá")
		);
	}
	
	@Override
	public List<Pais> listar() {
		return lista;
	}

	@Override
	public Pais obtenerPorId(Integer id) {
		Pais resultado = null;
		for(Pais pais: this.lista) {
			if(id == pais.getId()) {
				resultado = pais;
				break;
			}
		}
		return resultado;
	}

}
