package com.lrpa.springboot.backend.apirest.controllers;

import com.lrpa.springboot.backend.apirest.models.entity.Factura;
import com.lrpa.springboot.backend.apirest.services.IClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api")
public class FacturaRestController {
    private IClienteService clienteService;


    @GetMapping("/facturas/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Factura show(@PathVariable Long id){
        return clienteService.findFacturaById(id);
    }

    @DeleteMapping("/facturas/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        clienteService.deleteFacturaById(id);
    }
}
