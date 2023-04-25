package com.lrpa.springboot.backend.apirest.controllers;

import com.lrpa.springboot.backend.apirest.models.entity.Cliente;
import com.lrpa.springboot.backend.apirest.models.services.IClienteService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ClienteRestController {

    private final IClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }

    @GetMapping("/clientes/{id}")
    public Cliente show(@PathVariable Long id){
        return clienteService.findById(id);
    }

    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Cliente create(@RequestBody Cliente cliente){

        return clienteService.save(cliente);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id){
        Cliente clienteCurrent = clienteService.findById(id);
        Cliente clienteUpdate=null;

        Map<String, Object> response= new HashMap<>();
        StringBuilder sb= new StringBuilder();

        if (clienteCurrent == null) {
            response.put("message", sb.append("Error: no se puede editar, el cliente Id: ")
                    .append(id.toString()).append(" no existe en la base de datos!"));

            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        try {
            clienteCurrent.setNombre(cliente.getNombre());
            clienteCurrent.setApellido(cliente.getApellido());
            clienteCurrent.setEmail(cliente.getEmail());

            clienteUpdate= clienteService.save(clienteCurrent);

        }catch (DataAccessException e){
            response.put("message", sb.append("Error al actualizar el cliente en la base de datos ")
                    .append(clienteCurrent.getId().toString()));
            response.put("error",sb.append(e.getMessage()).append(": ")
                    .append(e.getMostSpecificCause().toString()));
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", sb.append("El cliente ha sido actualizado con exito!"));
        response.put("cliente", clienteUpdate);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
        
    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        clienteService.delete(id);
    }


}
