
package com.egg.Biblioteca.servicios;

import com.egg.Biblioteca.entidades.Editorial;
import com.egg.Biblioteca.excepciones.MiException;
import com.egg.Biblioteca.repositorios.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {
    
    @Autowired
    private EditorialRepositorio editorialRepo;
    
    @Transactional()
    public void crearEditorial(String nombre) throws MiException{
        
         if(nombre.isEmpty() || nombre==null){
            throw new MiException("nombre null");
        }
        Editorial edi = new Editorial();
        edi.setNombre(nombre);
        
        editorialRepo.save(edi);
    }
    
     public List<Editorial> listarEditoriales(){
        
        List<Editorial> listEdi = new ArrayList();
        listEdi= editorialRepo.findAll();
        
        return listEdi;
    }
     
     @Transactional()
     public void editEditorial(String id, String nombre) throws MiException{
         
          if (id.isEmpty() || id == null) {
             throw new MiException("id null");
         }
          if(nombre.isEmpty() || nombre==null){
            throw new MiException("nombre null");
        }
         Optional<Editorial> respE = editorialRepo.findById(id);
         
         if(respE.isPresent()){
             Editorial aut = respE.get();
             
             aut.setNombre(nombre);
             
             editorialRepo.save(aut);
         }
     }
     
     public Editorial getOne(String id){
         return editorialRepo.getOne(id);
     }
}
