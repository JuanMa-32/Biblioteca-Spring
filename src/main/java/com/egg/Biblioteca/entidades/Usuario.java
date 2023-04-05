
package com.egg.Biblioteca.entidades;

import com.egg.Biblioteca.enumeraciones.Roles;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter @Setter 
public class Usuario {
 
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
    private String id;
    
    private String nombre;
    private String email;
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Roles rol;
    
    @OneToOne
    private Imagen imagen;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String email, String password, Roles rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }
    
    
}
