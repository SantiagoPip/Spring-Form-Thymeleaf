package com.bolsadeideas.springboot.form.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.models.domain.Role;
@Service
public class RoleServiceImpl implements RoleService {

	private List<Role> roles;
	public RoleServiceImpl() {
		this.roles = new ArrayList<>();
		this.roles.add(new Role(1,"Administrador","ROLE_ADMIN"));
		this.roles.add(new Role(2,"Usuario","ROLE_USER"));
		this.roles.add(new Role(3,"Moderador","ROLE_MODERATOR"));

	}
	
	@Override
	public List<Role> listar() {
		// TODO Auto-generated method stub
		return roles;
	}

	@Override
	public Role obtenerPorId(Integer id) {
		// TODO Auto-generated method stub
		for (Role role : roles) {
			if(role.getId() == id) {
				return role;
			
			}
		}
		return null;
	}

}
