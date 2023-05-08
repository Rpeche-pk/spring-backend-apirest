package com.lrpa.springboot.backend.apirest.services.impl;

import com.lrpa.springboot.backend.apirest.services.IUploadFileService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

@Service("testeo")
//@Qualifier("")
public class Testeo implements IUploadFileService {
    @Override
    public Resource cargar(String nombreFoto) throws MalformedURLException {
        return null;
    }

    @Override
    public String copiar(MultipartFile archivo) throws IOException {
        return null;
    }

    @Override
    public boolean eliminar(String nombreFoto) {
        System.out.println("SERVICOO TESTEO");
        return false;
    }

    @Override
    public Path getPath(String nombreArchivo) {
        return null;
    }
}
