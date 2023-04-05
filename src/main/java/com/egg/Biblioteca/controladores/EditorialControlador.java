
package com.egg.Biblioteca.controladores;

import com.egg.Biblioteca.entidades.Editorial;
import com.egg.Biblioteca.excepciones.MiException;
import com.egg.Biblioteca.servicios.EditorialServicio;
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
@RequestMapping("/Editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio editorialServ;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "editorial_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,ModelMap model){
        try {
            editorialServ.crearEditorial(nombre);
            model.put("exito", "editorial cargada correctamente");
            return "index.html";
        } catch (MiException e) {
            model.put("error", e.getMessage());
            return "editorial_form.html";
        }
    }
    
    @GetMapping("/listar")
    public String listar(ModelMap model){
        List<Editorial> editoriales=editorialServ.listarEditoriales();
        model.addAttribute("editoriales", editoriales);
        return "editorial_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable("id") String id,ModelMap model){
        model.put("editorial", editorialServ.getOne(id));
        
        return "editorial_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable("id") String id,String nombre){
        
        try {
            editorialServ.editEditorial(id, nombre);
            return "redirect:../listar";
        } catch (MiException e) {
            return "editorial_modificar.html";
        }
    }
}
