package com.bolsadeideas.springboot.form.app.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.models.domain.Pais;
@Service
public class PaisServiceImpl implements PaisService {
	private List<Pais> lista;

	public PaisServiceImpl() {
		// TODO Auto-generated constructor stub
		 this.lista =  Arrays.asList(
					new Pais(1, "ES", "Espana"),
					new Pais(2, "CO", "Colombia"),
					new Pais(3, "MX", "Mexico"),
					new Pais(4, "EC", "Ecuador"),
					new Pais(5, "VE", "Venezuela"));
	}

	@Override
	public List<Pais> listar() {
		// TODO Auto-generated method stub
		return lista;
	}

	@Override
	public Pais obtenerPorId(Integer id) {
		for (Pais pais : lista) {
			if(pais.getId() == id) {
				return pais;
				
			}
		}	
		return null;
	}

}
