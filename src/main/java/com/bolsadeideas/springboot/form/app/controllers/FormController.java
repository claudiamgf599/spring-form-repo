package com.bolsadeideas.springboot.form.app.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bolsadeideas.springboot.form.app.models.domain.Usuario;

@Controller
public class FormController {
	
	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario(); 
		model.addAttribute("titulo", "Formulario usuarios");
		model.addAttribute("user", usuario);
		return "form";
	}
	
	@PostMapping("/form")
	//Si los parámetros se llaman igual no hay necesidad de usar el atributo name en el RequestParam
	/*
	public String procesarFormulario(Model model,
			@RequestParam String username,
			@RequestParam(name="password") String password,
			@RequestParam String email) {
	*/		
	
	//El formulario se puede mapear directamente a una clase POJO, siempre y cuando los atributos de dicha clase sean los mismos del formulario
	//BindingResult es propio de Spring y sirve para retornar los resultados de la validación, siempre debe estar justo después del objeto a validar
	//@ModelAttribute sirve para definir un nombre distinto al nombre de la clase para un parámetro
	public String procesarFormulario(@Valid @ModelAttribute("user") Usuario usuario, BindingResult result, Model model) {
		
		/* Una forma más limpia de pasar los datos a través de un POJO
		Usuario usuario = new Usuario();
		usuario.setUsername(username);
		usuario.setPassword(password);
		usuario.setEmail(email);
		*/
		
		/* Forma de mandar los atributos si no tenemos un POJO 
		model.addAttribute("username", username);
		model.addAttribute("password", password);
		model.addAttribute("email", email);
		*/
		
		model.addAttribute("titulo", "Resultado del formulario");
		
		if(result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			model.addAttribute("error", errores);
			return "form";
		}

		model.addAttribute("usuario", usuario);
		return "resultado";
	}

}
