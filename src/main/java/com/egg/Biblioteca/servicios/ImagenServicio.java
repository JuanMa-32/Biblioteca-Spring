
package com.egg.Biblioteca.servicios;

import com.egg.Biblioteca.entidades.Imagen;
import com.egg.Biblioteca.repositorios.ImagenRepositorio;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {
    @Autowired
    private ImagenRepositorio imagenRepo;
    
    public Imagen guardar(MultipartFile archivo)throws  IOException{
        
        if(archivo!=null){
            try {
                 Imagen img = new Imagen();
            
            img.setMime(archivo.getContentType());
            
            img.setNombre(archivo.getName());
            
            img.setContenido(archivo.getBytes());
            
           return  imagenRepo.save(img);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
           
        }
        return null;
    }
    
     public Imagen actualizar(MultipartFile archivo,String id)throws Exception{
          if(archivo!=null){
            try {
                 Imagen img = new Imagen();
            
                 if(id!=null){
                     Optional<Imagen> resp = imagenRepo.findById(id);
                     
                     if(resp.isPresent()){
                         img=resp.get();
                     }
                 }
                 
            img.setMime(archivo.getContentType());
            
            img.setNombre(archivo.getName());
            
            img.setContenido(archivo.getBytes());
            
           return  imagenRepo.save(img);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
           
        }
        return null;
     }
     
     
}
