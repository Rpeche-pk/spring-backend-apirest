package com.lrpa.springboot.backend.apirest.services;

import com.lrpa.springboot.backend.apirest.models.entity.Cliente;
import com.lrpa.springboot.backend.apirest.models.entity.Factura;
import com.lrpa.springboot.backend.apirest.models.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IClienteService {

     List<Cliente> findAll();
     Page<Cliente> limitFindAll(Pageable page);
     Cliente save(Cliente cliente);
     void delete(Long id);
     Cliente findById(Long id);
     List<Region> findAllRegiones();
     Factura findFacturaById(Long id);
     Factura saveFacturaById(Factura factura);
     void deleteFacturaById(Long id);

}
