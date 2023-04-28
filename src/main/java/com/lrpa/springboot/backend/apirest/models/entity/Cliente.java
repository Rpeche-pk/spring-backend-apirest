package com.lrpa.springboot.backend.apirest.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="clientes")
public class Cliente implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty
    @Size(min = 4 , max=12)
    private String nombre;
    @NotEmpty
    private String apellido;
    @Column(nullable = false, unique = true)
    @NotEmpty
    @Email
    private String email;
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createAt;

    @PrePersist
    public void prePersist(){
        this.createAt= new Date();
    }


}