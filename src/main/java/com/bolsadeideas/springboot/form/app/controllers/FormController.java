package com.bolsadeideas.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.springboot.form.app.editors.PaisPropertyEditor;
import com.bolsadeideas.springboot.form.app.editors.RolesEditor;
import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import com.bolsadeideas.springboot.form.app.models.domain.Rol;
import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.services.PaisService;
import com.bolsadeideas.springboot.form.app.services.RoleService;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;

@Controller
@SessionAttributes("user") //Esta anotación hace que el objeto identificado con user se mantenga en la sesión con todos sus datos, no sólo los que están en formularios
public class FormController {
	
	@Autowired
	private UsuarioValidador validador;
	
	@Autowired
	private PaisService paisService;
	
	//Esto es para poder obtener el objeto país completo cuando desde la vista sólo se recupera el id
	@Autowired
	private PaisPropertyEditor paisEditor;
	
	@Autowired
	private RolesEditor rolEditor;
	
	@Autowired
	private RoleService rolService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validador);
		
		//Esto es como un filtro para interceptar un valor que nos envían y darle un formato
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	
		//Para usar un CustomEditor creado por nosotros mismos
		binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditor());
		binder.registerCustomEditor(String.class, "apellido", new NombreMayusculaEditor());
		binder.registerCustomEditor(Pais.class, "pais", paisEditor);
		binder.registerCustomEditor(Rol.class, "roles", rolEditor);
	}
	
	@ModelAttribute("genero")
	public List<String> genero(){
		return Arrays.asList("Hombre", "Mujer");
	}
	
	@ModelAttribute("listaRoles")
	public List<Rol> listaRoles(){
		return this.rolService.listar();
	}
	
	@ModelAttribute("listaRolesString")
	public List<String> listaRolesString(){
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
	
	@ModelAttribute("listaPaises") 
	public List<Pais> listaPaises() {
		return paisService.listar();
	}
	
	@ModelAttribute("paises") //Con esta anotación lo que retorna el método se pasa a la vista
	public List<String> paises() {
		return Arrays.asList("España", "Colombia", "Francia", "Canadá");
	}
	
	@ModelAttribute("paisesMap") 
	public Map<String, String> paisesMap() {
		Map<String, String> paises = new HashMap<String, String>();
		paises.put("ES", "España");
		paises.put("ES", "Colombia");
		paises.put("FR", "Francia");
		paises.put("CA", "Canadá");
		return paises;
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario(); 
		usuario.setNombre("Claudia");
		usuario.setApellido("Gomez");
		usuario.setIdentificador("43.259.856-K");
		usuario.setHabilitar(true);
		usuario.setValorSecreto("Un valor ultra secreto");
		//Para que se seleccione un país por defecto, pero en la clase Pais debe sobreescribir el método toString()
		usuario.setPais(new Pais(2, "CO", "Colombia"));
		//Para que se seleccione una opción por defecto, pero en el form debe también hacer un cambio e implementar el método Equals en la clase Rol
		usuario.setRoles(Arrays.asList(new Rol(2, "Usuario", "ROLE_USER")));
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
		
		//validador.validate(usuario, result);
		//Otra forma de validar es con anotaciones, registrando en el InitBinder, no requiere líneas de código aquí
		
		if(result.hasErrors()) {
			/*
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			model.addAttribute("error", errores);
			*/
			
			model.addAttribute("titulo", "Resultado del formulario");
			//Se puede usar sin la implementación anterior para usar mejor las propiedades de thymeleaf en el manejo de errores, ya los mensajes de error serían genéricos
			return "form";
		}

		
		return "redirect:/ver";  //Para que haga un nuevo request en lugar de estar lanzando las mismas peticiones
	}
	
	@GetMapping("/ver")
	public String ver(@SessionAttribute(name="user", required=false) Usuario usuario, Model model, SessionStatus status) {
		
		if(usuario == null) {
			return "redirect:/form";
		}
		
		model.addAttribute("titulo", "Resultado del formulario (ver)");
		model.addAttribute("usuario", usuario);
		status.setComplete(); //Elimina el objeto de la sesión, porque ya ha finalizado
		
		return "resultado";
	}

}
