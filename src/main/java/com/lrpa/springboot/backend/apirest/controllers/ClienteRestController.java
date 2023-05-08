package com.lrpa.springboot.backend.apirest.controllers;

import com.lrpa.springboot.backend.apirest.exceptions.FileUnknownException;
import com.lrpa.springboot.backend.apirest.models.entity.Cliente;
import com.lrpa.springboot.backend.apirest.services.IClienteService;
import com.lrpa.springboot.backend.apirest.services.IUploadFileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ClienteRestController {

    private final IClienteService clienteService;
    private final IUploadFileService uploadFileService;

    private static final String DESCRIPTION="Bad Request Exception (400)";


    @GetMapping("/clientes")
    public List<Cliente> index() {
        return clienteService.findAll();
    }

    @GetMapping("/clientes/page/{page}")
    public Page<Cliente> index(@PathVariable Integer page) {
        Page<Cliente> cli = clienteService.limitFindAll(PageRequest.of(page, 4));
        cli.getContent();
        cli.getNumber();
        return clienteService.limitFindAll(PageRequest.of(page, 4));
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

        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(e -> "El campo " + e.getField() + " " + e.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            clienteNew = clienteService.save(cliente);
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar insert a la base de datos");
            response.put("error", sb.append(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Message", "Cliente creado con exito!!");
        response.put("customer", clienteNew);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
        Cliente clienteCurrent = clienteService.findById(id);
        Cliente clienteUpdate = null;

        Map<String, Object> response = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(e -> "El campo " + e.getField() + " " + e.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
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
            Cliente cliente = clienteService.findById(id);
            String nombreFotoAnterior = cliente.getFoto();

            uploadFileService.eliminar(nombreFotoAnterior);

            clienteService.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar cliente en la base de datos");
            response.put("error", sb.append(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "Cliente eliminado con éxito!!!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

    @PostMapping("/clientes/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        Cliente cliente = clienteService.findById(id);


        if (!archivo.isEmpty()) {

            String nombreArchivo = null;

            try {
                nombreArchivo = uploadFileService.copiar(archivo);


            } catch (IOException e) {
                response.put("message", "Error al subir la imagen del cliente en la base de datos");
                response.put("error", sb.append(e.getMessage() + " ").append(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String nombreFotoAnterior = cliente.getFoto();
            uploadFileService.eliminar(nombreFotoAnterior);

            cliente.setFoto(nombreArchivo);
            clienteService.save(cliente);

            response.put("cliente", cliente);
            response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);
        } else {


            throw new FileUnknownException(DESCRIPTION + " No ha subido ninguna foto o imagen.");

        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/uploads/img/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {

        Resource recurso = null;
        try {
            recurso = uploadFileService.cargar(nombreFoto);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        //Para descargar la imagen o archivo que envias al response entity
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

        return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
    }


}
