package com.lrpa.springboot.backend.apirest.models.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private String observacion;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;


    @JsonIgnoreProperties({"facturas","hibernateLazyInitializer","handler"})
    //@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"}, allowSetters = true )
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "factura_id")
    private List<ItemFactura> items;

    public Factura() {
        this.items=new ArrayList<>();
    }

    @PrePersist
    public void prePersist(){
        this.createAt= new Date();
    }

    public Double getTotal(){
        Double total= 0.00;
        for (ItemFactura item: items) {
            total +=item.getImporte();
        }
        return total;
    }


}
