
package com.egg.Biblioteca.servicios;


import com.egg.Biblioteca.entidades.Imagen;
import com.egg.Biblioteca.entidades.Usuario;
import com.egg.Biblioteca.enumeraciones.Roles;
import com.egg.Biblioteca.excepciones.MiException;
import com.egg.Biblioteca.repositorios.UsuarioRepositorio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService{
    
    @Autowired
    private UsuarioRepositorio usuarioRepo;
    
    @Autowired
    private ImagenServicio imagenServ;
    
    @Transactional()
    public void crearUsario(MultipartFile archivo,String nombre,String email,String password,String password2 ) throws MiException, IOException{
       
        
            validar(nombre, email, password, password2);
            Usuario us  = new Usuario();
            us.setNombre(nombre);
            us.setEmail(email);
            us.setPassword(new BCryptPasswordEncoder().encode(password));
            us.setRol(Roles.USER);
            
            Imagen img = imagenServ.guardar(archivo);
            
            us.setImagen(img);
            
            usuarioRepo.save(us);
            
       
    }
    
    @Transactional
    public void actualizar(MultipartFile archivo,String idUsuario,String nombre,String email,String password,String password2) throws MiException, Exception{
        validar(nombre, email, password, password2);
        
        Optional<Usuario> respuesta = usuarioRepo.findById(idUsuario);
        if(respuesta.isPresent()){
            Usuario u  =respuesta.get();
            u.setEmail(email);
            u.setNombre(nombre);
            u.setPassword(new BCryptPasswordEncoder().encode(password));
            u.setRol(Roles.USER);
           
            String idImagen =null;
            if(u.getImagen()!=null){
                idImagen = u.getImagen().getId();
            }
            Imagen imagen = imagenServ.actualizar(archivo, idImagen);
            u.setImagen(imagen);
            
            usuarioRepo.save(u);
        }
            
        
    }
    
      private void validar(String nombre,String email,String password,String password2) throws MiException{
        
        
          if (nombre.isEmpty() || nombre == null) {
              throw new MiException("Nombre vacio");
          }

          if (email.isEmpty() || email == null) {
              throw new MiException("email vacio");
          }
          if (password.isEmpty() || password == null || password.length()<5) {
              throw new MiException("password vacio");
          }
          if (!password2.equals(password)) {
              throw new MiException("las contraseñas no son iguales");
          }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Usuario us = usuarioRepo.buscarPorEmail(email);
        
        if(us!=null){
            
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+us.getRol().toString());
            permisos.add(p);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", us);
                
            
            return new User(us.getEmail(), us.getPassword(), permisos);
        }else{
            return null;
        }
    }
    public Usuario getOne(String id){
        return usuarioRepo.findById(id).get();
    }
    
    public List<Usuario> listUsuario(){
        List<Usuario> listUsuarios = new ArrayList();
        listUsuarios=usuarioRepo.findAll();
        return listUsuarios;
    }
      @Transactional
    public void cambiarRol(String id){
        Optional<Usuario> respuesta = usuarioRepo.findById(id);
    	
    	if(respuesta.isPresent()) {
    		
    		Usuario usuario = respuesta.get();
    		
    		if(usuario.getRol().equals(Roles.USER)) {
    			
    		usuario.setRol(Roles.ADMIN);
    		
    		}else if(usuario.getRol().equals(Roles.ADMIN)) {
    			usuario.setRol(Roles.USER);
    		}
    	}
    }
}
