package com.lrpa.springboot.backend.apirest.controllers;

import com.lrpa.springboot.backend.apirest.models.entity.Cliente;
import com.lrpa.springboot.backend.apirest.services.IClienteService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ClienteRestController {

    private final IClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> index() {
        return clienteService.findAll();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {

        Optional<Cliente> cliente = null;
        Map<String, Object> response = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        try {
            cliente = Optional.ofNullable(clienteService.findById(id));
            if (!cliente.isPresent()) {
                response.put("message", sb.append("El cliente con ID: ")
                        .append(id.toString()).append(" no existe en la base de datos!"));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            response.put("message", sb.append("Error al realizar consulta a la base de datos"));
            response.put("error", sb.append(e.getMessage()).append(" : ").append(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<Cliente>(cliente.get(), HttpStatus.OK);
    }

    @PostMapping("/clientes")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {

        Cliente clienteNew=null;
        Map<String, Object> response = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        if (result.hasErrors()) {

            List<String> errors= result.getFieldErrors()
                    .stream()
                    .map(e-> "El campo "+ e.getField()+" "+e.getDefaultMessage())
                            .collect(Collectors.toList());

            response.put("errors",errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            clienteNew =clienteService.save(cliente);
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar insert a la base de datos");
            response.put("error", sb.append(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Message","Cliente creado con exito!!");
        response.put("customer",clienteNew);

        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result,@PathVariable Long id) {
        Cliente clienteCurrent = clienteService.findById(id);
        Cliente clienteUpdate = null;

        Map<String, Object> response = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        if (result.hasErrors()) {

            List<String> errors= result.getFieldErrors()
                    .stream()
                    .map(e-> "El campo "+ e.getField()+" "+e.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors",errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (clienteCurrent == null) {
            response.put("message", sb.append("Error: no se puede editar, el cliente Id: ")
                    .append(id.toString()).append(" no existe en la base de datos!"));

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            clienteCurrent.setNombre(cliente.getNombre());
            clienteCurrent.setApellido(cliente.getApellido());
            clienteCurrent.setEmail(cliente.getEmail());
            clienteCurrent.setCreateAt(cliente.getCreateAt());

            clienteUpdate = clienteService.save(clienteCurrent);

        } catch (DataAccessException e) {
            response.put("message", sb.append("Error al actualizar el cliente en la base de datos ")
                    .append(clienteCurrent.getId().toString()));
            response.put("error", sb.append(e.getMostSpecificCause().toString()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", sb.append("El cliente ha sido actualizado con éxito!"));
        response.put("cliente", clienteUpdate);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/clientes/{id}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        try {
            clienteService.delete(id);
        }catch (DataAccessException e) {
            response.put("message", "Error al eliminar cliente en la base de datos");
            response.put("error", sb.append(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "Cliente eliminado con éxito!!!");
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

    }


}
