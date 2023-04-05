
package com.egg.Biblioteca.controladores;

import com.egg.Biblioteca.entidades.Autor;
import com.egg.Biblioteca.excepciones.MiException;
import com.egg.Biblioteca.servicios.AutorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/Autor")
public class AutorControlador {
    
    @Autowired
    private AutorServicio autorServi;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "autor_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap model) {
       
        try {
            autorServi.crearAutor(nombre);
            model.put("exito", "se cargo el autor correctamente");
            return "index.html";
        } catch (MiException e) {
            model.put("error", e.getMessage());
            return "autor_form.html";
        }
        
    }
    @GetMapping("/listar")
    public String listar(ModelMap model){
        List<Autor> autores= autorServi.listarAutores();
        model.addAttribute("autores", autores);
        return "autor_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable("id") String id,ModelMap model){
        
        model.put("autor", autorServi.getOne(id));
        
        return"autor_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable("id") String id,String nombre){
        
        try {
            autorServi.editAutor(id, nombre);
            return "redirect:../listar";
        } catch (MiException e) {
            return "autor_modificar.html";
        }
        
    }
}
