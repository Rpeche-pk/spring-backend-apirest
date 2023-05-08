package com.lrpa.springboot.backend.apirest.services.impl;

import com.lrpa.springboot.backend.apirest.services.IUploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service("testeo1")
//@Qualifier("")
public class UploadFileServiceImpl implements IUploadFileService {

    private final static String DIRECTORIO_UPLOAD = "uploads";

    @Override
    public Resource cargar(String nombreFoto) throws MalformedURLException {
        Path rutaArchivo = getPath(nombreFoto);
        //convirtiendo un path a recurso
        Resource recurso = null;
        log.info(rutaArchivo.toString());
        recurso = new UrlResource(rutaArchivo.toUri());

        if (!recurso.exists() && !recurso.isReadable()) {
            rutaArchivo = Paths.get("src/main/resources/static/images").resolve("user-default.png").toAbsolutePath();

            recurso = new UrlResource(rutaArchivo.toUri());

            log.error("Error no se pudo cargar la imagen: " + nombreFoto);
        }
        return recurso;
    }

    @Override
    public String copiar(MultipartFile archivo) throws IOException {


        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", ""); //nombre del archivo original con su extension

        Path rutaArchivo = getPath(nombreArchivo);
        log.info(rutaArchivo.toString());

        var archivoGetInput = archivo.getInputStream();
        Files.copy(archivoGetInput, rutaArchivo);
        archivoGetInput.close();

        return nombreArchivo;
    }

    @Override
    public boolean eliminar(String nombreFoto) {

        if (nombreFoto != null && nombreFoto.length()>0) {
            Path rutaFotoAnterior= Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
            File archivoFotoAnterior = rutaFotoAnterior.toFile();

            if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
                archivoFotoAnterior.delete();
                return true;
            }
        }
        return false;
    }

    @Override
    public Path getPath(String nombreArchivo) {
        //Este path Paths.get -> se utiliza para crear un objeto Path a partir de una cadena que representa una ruta en el sistema de archivos.
        // + RESOLVE ->combina dos rutas para crear una nueva ruta
        //  .toAbsolutepath convierte en un path comppelto
        return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreArchivo).toAbsolutePath();
    }
}
