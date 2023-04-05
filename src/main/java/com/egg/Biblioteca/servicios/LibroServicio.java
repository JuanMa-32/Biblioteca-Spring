
package com.egg.Biblioteca.servicios;

import com.egg.Biblioteca.entidades.Autor;
import com.egg.Biblioteca.entidades.Editorial;
import com.egg.Biblioteca.entidades.Libro;
import com.egg.Biblioteca.excepciones.MiException;
import com.egg.Biblioteca.repositorios.AutorRepositorio;
import com.egg.Biblioteca.repositorios.EditorialRepositorio;
import com.egg.Biblioteca.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {
    
    @Autowired // esta anotacion le indica a spring donde debe ocurrir una DEPENDECY INJECTION.
    private LibroRepositorio libroRepositorio;
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional()
    public void crearLibro(Long isbn,String titulo,Integer ejemplares,String idAutor,String idEditorial) throws MiException{
        
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        
        Libro lib = new Libro();
        Autor aut = autorRepositorio.findById(idAutor).get();//metodo para traer un autor por su id
        Editorial edi = editorialRepositorio.findById(idEditorial).get();
        
        
        lib.setIsbn(isbn);
        lib.setTitulo(titulo);
        lib.setEjemplares(ejemplares);
        lib.setAlta(new Date());
        lib.setAutor(aut);
        lib.setEditorial(edi);
        
        libroRepositorio.save(lib);//metodo para guardar un libro
    }
    
    public List<Libro> listarLibros(){
        
        List<Libro> listLibros = new ArrayList();
        listLibros= libroRepositorio.findAll();
        
        return listLibros;
    }
    
    @Transactional
    public void editLibro(Long isbn, String titulo, Integer ejemplares,String idAutor,String idEditorial) throws MiException{
        
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        
        Optional<Libro> respL = libroRepositorio.findById(isbn);
        Optional<Autor> respA = autorRepositorio.findById(idAutor);
        Optional<Editorial> respE = editorialRepositorio.findById(idEditorial);
        
        Autor aut = new Autor();
        Editorial edi= new Editorial();
        
        if(respA.isPresent()){
             aut= respA.get();
        }
        if(respE.isPresent()){
             edi=respE.get();
        }
        if(respA.isPresent()){
            Libro lib = respL.get();
            
            lib.setTitulo(titulo);
            lib.setEjemplares(ejemplares);
            lib.setAutor(aut);
            lib.setEditorial(edi);
            
            libroRepositorio.save(lib);
        }
    }
    
    private void validar(Long isbn,String titulo,Integer ejemplares,String idAutor,String idEditorial) throws MiException{
        
        if(isbn==null){
            throw new MiException("isbn null");
        }
        if(titulo.isEmpty() || titulo==null){
            throw new MiException("titulo vacio");
        }
         if(ejemplares==null){
            throw new MiException("ejemplares null");
        }
        if(idAutor.isEmpty() || idAutor==null){
            throw new MiException("id Autor vacio");
        }
           if(idEditorial.isEmpty() || idEditorial==null){
            throw new MiException("id editorial vacio");
        }
    }
    public Libro getOne(Long isbn){
        return libroRepositorio.getOne(isbn);
    }
}
