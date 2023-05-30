package com.lrpa.springboot.backend.apirest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @NotEmpty(message = "no puede estar vacio el nombre")
    @Size(min = 4 , max=12)
    private String nombre;
    @NotEmpty(message = "no puede estar vacio el apellido")
    private String apellido;
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "no puede estar vacio")
    @Email(message = "no es una direccion de correo bien formada")
    private String email;
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "no puede estar vacio")
    private Date createAt;
    private String foto;

    @NotNull(message = "la región no puede ser vacia, no seas tarado")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Region region;

    @JsonIgnoreProperties(value = {"cliente","hibernateLazyInitializer","handler"}, allowSetters = true)
    //@JsonManagedReference
    //@JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Factura> facturas;

    public Cliente(){
        this.facturas= new ArrayList<>();
    }

    //crea la instancia de fecha antes de persistir en la bbdd, no lo necesitamos ya que lo vamos a manejar
    //en el frontend con un dataPicker
    /*@PrePersist
    public void prePersist(){
        this.createAt= new Date();
    }*/


}