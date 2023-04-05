
package com.egg.Biblioteca.controladores;

import com.egg.Biblioteca.entidades.Autor;
import com.egg.Biblioteca.entidades.Editorial;
import com.egg.Biblioteca.entidades.Libro;
import com.egg.Biblioteca.excepciones.MiException;
import com.egg.Biblioteca.servicios.AutorServicio;
import com.egg.Biblioteca.servicios.EditorialServicio;
import com.egg.Biblioteca.servicios.LibroServicio;
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
@RequestMapping("/Libro")
public class LibroControlador {
    
    @Autowired
    private LibroServicio libroServ;
    
    @Autowired
    private EditorialServicio editorialServ;
    
    @Autowired
    private AutorServicio autorServ;
    
    @GetMapping("/registrar")
    public String registrar(ModelMap model){
        List<Autor> autores = autorServ.listarAutores();
        List<Editorial> editoriales = editorialServ.listarEditoriales();
        
        model.addAttribute("autores", autores);
        model.addAttribute("editoriales", editoriales);
        
        
        return "libro_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam Long isbn,@RequestParam String titulo,@RequestParam Integer ejemplares,@RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap model){
        try {
            libroServ.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            model.put("exito","El libro se cargo correctamente");
            return "index.html";
            
        } catch (MiException e) {
             List<Autor> autores = autorServ.listarAutores();
             List<Editorial> editoriales = editorialServ.listarEditoriales();
        
        model.addAttribute("autores", autores);
        model.addAttribute("editoriales", editoriales);
        
            model.put("error", e.getMessage());
            return "libro_form.html";
        }
    }
    
    @GetMapping("/listar")
    public String listar(ModelMap mode){
        List<Libro> libros = libroServ.listarLibros();
        mode.addAttribute("libros", libros);
       return "libro_list.html";
    }
    
    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn,ModelMap model){
         List<Autor> autores = autorServ.listarAutores();
        List<Editorial> editoriales = editorialServ.listarEditoriales();
        
        model.put("autores", autores);
        model.put("editoriales", editoriales);
        
        model.put("libro", libroServ.getOne(isbn));
       
        return "libro_modificar.html";
    }
    
    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable("isbn") Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial,ModelMap model){
        
        try {
             libroServ.editLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
              model.put("exito", "Libro editado exitosamente");
             return "redirect:../listar";
           
             
        } catch (MiException e) {
             List<Autor> autores = autorServ.listarAutores();
        List<Editorial> editoriales = editorialServ.listarEditoriales();
        
        model.put("autores", autores);
        model.put("editoriales", editoriales);
        
        model.put("libro", libroServ.getOne(isbn));
        
             model.put("error", e.getMessage());
             return "libro_modificar.html";
        }
    }
    
}
