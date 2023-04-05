
package com.egg.Biblioteca.controladores;


import com.egg.Biblioteca.entidades.Usuario;
import com.egg.Biblioteca.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    @Autowired
    private UsuarioServicio usuarioServ;
    
    @GetMapping("/dashboard")
    public String panelAdmin(){
        return "panel.html";
    }
    
    @GetMapping("/listar")
    public String lista(ModelMap model){
        List<Usuario> usuarios= usuarioServ.listUsuario();
        
        model.put("usuarios", usuarios);
        
        return "usuario_list.html";
    }
    
    @GetMapping("/modificarRol/{id}")
    public String modificarRol(@PathVariable String id){
        usuarioServ.cambiarRol(id);
        return "redirect:/admin/listar";
    }
}
