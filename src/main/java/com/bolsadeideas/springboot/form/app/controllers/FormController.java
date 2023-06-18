package com.bolsadeideas.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.validation.BindingResult;

import com.bolsadeideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.springboot.form.app.editors.PaisPropertyEditor;
import com.bolsadeideas.springboot.form.app.editors.RolesEditor;
import com.bolsadeideas.springboot.form.app.services.PaisService;
import com.bolsadeideas.springboot.form.app.services.RoleService;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidation;
import com.bolsadeideas.springboot.form.models.domain.Pais;
import com.bolsadeideas.springboot.form.models.domain.Role;
import com.bolsadeideas.springboot.form.models.domain.Usuario;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("usuario")
public class FormController {
	@Autowired
	private UsuarioValidation validador;
	
	@Autowired
	private PaisService paisService; 
	
	@Autowired
	private PaisPropertyEditor paisEditor;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RolesEditor roleEditor;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validador);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);// Estricto o tolerante (false=estricto)
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditor());
		binder.registerCustomEditor(String.class, "apellido", new NombreMayusculaEditor());
		binder.registerCustomEditor(Pais.class,"pais",paisEditor);
		binder.registerCustomEditor(Role.class,"role",roleEditor );

	}
	
	@ModelAttribute("genero")
	public List<String>genero(){
		return Arrays.asList("Hombre","Mujer");
	}
	
	
	@ModelAttribute("listaRoles")
	public List<Role> listaRoles(){
		return this.roleService.listar();
	}
	
	@ModelAttribute("listaRolesString")
	public List<String>listaRolesString(){
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_USER");
		roles.add("ROLE_MODERATOR");
		return roles;
	}
	@ModelAttribute("listaRolesMap")
	public Map<String, String> listaRolesMap() {
		Map<String, String> roles = new HashMap<String, String>();
		roles.put("ROLE_ADMIN", "Administrador");
		roles.put("ROLE_USER", "Usuario");
		roles.put("ROLE_MODERATOR", "Moderador");
		
		return roles;
	}
	
	@ModelAttribute("listaPaises") // Para pasar a la vista
	public List<Pais> listaPaises() {
		return paisService.listar();
	}

	@ModelAttribute("paises") // Para pasar a la vista
	public List<String> paises() {
		return Arrays.asList("Espana", "Colombia", "Mexico", "Ecuador", "Venezuela");
	}

	@ModelAttribute("paisesMap")
	public Map<String, String> paisesMap() {
		Map<String, String> paises = new HashMap<String, String>();
		paises.put("ES", "Espana");
		paises.put("MX", "Mexico");
		paises.put("CO", "Colombia");
		paises.put("EC", "Ecuador");
		paises.put("VE", "Venezuela");
		return paises;
	}

	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario();
		usuario.setNombre("Santiago");
		usuario.setApellido("Moreno");
		usuario.setIdentificador("12.123.123.12-K");
		usuario.setHabilitar(true);
		usuario.setValorSecreto("Algun valor Secreto ****");
//		usuario.setPais(new Pais(2, "CO", "Colombia"));
		usuario.setRoles(Arrays.asList(new Role(2,"Usuario","ROLE_USER")));
		model.addAttribute("titulo", "Formulario Usuarios");
		model.addAttribute("usuario", usuario);
		return "form";
	}

	@PostMapping("/form")
	public String procesar(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status) {

		// validador.validate(usuario, result);

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Resultado del Formulario");
//			Map<String,String> errores = new HashMap<>();
//			result.getFieldErrors().forEach( err ->{
//				errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
//			});
//			model.addAttribute("error",errores);
			System.out.println(result);
			return "form";
		}
		model.addAttribute("usuario", usuario);
		status.setComplete();
		return "redirect:/ver";
	}
	@GetMapping("/ver")
	public String ver(@SessionAttribute(name="usuario",required =false)Usuario usuario,Model model,SessionStatus status) {
		if(usuario==null) {
			return "redirect:/form";
		}
		model.addAttribute("titulo","Resultado Form");
		status.setComplete();
		return "resultado";
	}

}
