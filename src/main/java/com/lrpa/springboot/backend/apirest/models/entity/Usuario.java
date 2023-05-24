package com.lrpa.springboot.backend.apirest.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Usuario implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "no puede estar vacio")
    @Column(unique = true, length = 20,nullable = false)
    private String username;
    @NotEmpty(message = "no puede estar vacio")
    @Column(length = 75, nullable = false)
    private String password;
    private Boolean enabled;

    private String nombre;
    private String apellido;
    @Column(unique = true)
    @NotEmpty(message = "no puede estar vacio")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "usuarios_roles",
    joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"),
    uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id ","role_id"})})
    private List<Role> roles;

    @PrePersist
    public void isEnabled(){
        this.enabled= true;
    }

}
