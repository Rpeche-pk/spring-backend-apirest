package com.lrpa.springboot.backend.apirest.auth;

import com.lrpa.springboot.backend.apirest.models.entity.Usuario;
import com.lrpa.springboot.backend.apirest.services.IUsuarioService;

import org.springframework.security.authentication.AnonymousAuthenticationToken;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenAdditionalInf implements TokenEnhancer {

    private final IUsuarioService usuarioService;

    public TokenAdditionalInf(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Usuario usuario =usuarioService.findByUsername(authentication.getName());
        Map<String,Object> info= new HashMap<>();

        info.put("info_adicional","Hola que tal!: ".concat(authentication.getName()));
        info.put("nombre", usuario.getNombre());
        info.put("apellido", usuario.getApellido());
        info.put("email", usuario.getEmail());


        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
