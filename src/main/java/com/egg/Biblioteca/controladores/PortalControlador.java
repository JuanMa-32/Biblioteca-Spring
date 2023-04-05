
package com.egg.Biblioteca.controladores;

import com.egg.Biblioteca.entidades.Usuario;
import com.egg.Biblioteca.excepciones.MiException;
import com.egg.Biblioteca.servicios.UsuarioServicio;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    private UsuarioServicio usuarioSer;
    
   
    
    @GetMapping("/")
    public String index(){
        return "index.html";
    }

    
    @GetMapping("/registrar")
    public String registro(){
        return "registro.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,@RequestParam String email,@RequestParam String password, String password2,ModelMap model,MultipartFile archivo) throws IOException {
       
        try {
              usuarioSer.crearUsario(archivo,nombre, email, password, password2);
              model.addAttribute("exito", "Cuenta creada ");
              return "index.html";
        } catch (MiException e) {
             model.addAttribute("error", e.getMessage());
             return "registro.html";
        }
    }
    
    
    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
    
    
   
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }

        return "inicio.html";
    }
    
    
      @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo,HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
         modelo.put("usuario", usuario);
        return "usuario_modificar.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/perfil/{id}")
    public String actualizar(MultipartFile archivo,@PathVariable String id, @RequestParam String nombre,@RequestParam String email, 
            @RequestParam String password,@RequestParam String password2, ModelMap modelo) throws Exception {

        try {
            usuarioSer.actualizar(archivo, id, nombre, email, password, password2);

            modelo.put("exito", "Usuario actualizado correctamente!");

            return "inicio.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "usuario_modificar.html";
        }

    }

}
