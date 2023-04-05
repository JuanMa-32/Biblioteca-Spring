
package com.egg.Biblioteca.servicios;

import com.egg.Biblioteca.entidades.Autor;
import com.egg.Biblioteca.excepciones.MiException;
import com.egg.Biblioteca.repositorios.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepo;
    
    @Transactional()
    public void crearAutor(String nombre) throws MiException{
        if(nombre.isEmpty() || nombre==null){
            throw new MiException("nombre null");
        }
        Autor aut = new Autor();
        aut.setNombre(nombre);
        
        autorRepo.save(aut);
        
    }
    
     public List<Autor> listarAutores(){
        
        List<Autor> listAutores = new ArrayList();
        listAutores= autorRepo.findAll();
        
        return listAutores;
    }
     
     @Transactional()
     public void editAutor(String id, String nombre) throws MiException{
         
         if (id.isEmpty() || id == null) {
             throw new MiException("id null");
         }
          if(nombre.isEmpty() || nombre==null){
            throw new MiException("nombre null");
        }
         Optional<Autor> respA = autorRepo.findById(id);
         
         if(respA.isPresent()){
             Autor aut = respA.get();
             
             aut.setNombre(nombre);
             
             autorRepo.save(aut);
         }
     }
     
     public Autor getOne(String id){
         return autorRepo.getOne(id);
     }
     
     
}
