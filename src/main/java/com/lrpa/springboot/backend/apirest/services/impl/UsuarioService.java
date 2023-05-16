package com.lrpa.springboot.backend.apirest.services.impl;

import com.lrpa.springboot.backend.apirest.models.dao.IUsuarioDao;
import com.lrpa.springboot.backend.apirest.models.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UsuarioService implements UserDetailsService {

    private IUsuarioDao usuarioDao;
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<Usuario> usuario = Optional
                .ofNullable(usuarioDao.findByUsername(username));

        if (!usuario.isPresent()) {
            log.error("Error en el login: no existe el usuario en el sistema ->" + username);
            usuario.orElseThrow(() ->new UsernameNotFoundException(String.format("Error en el login: no existe el usuario '%s' en el sistema", username)));
        }
        System.out.println(usuario.get().toString());

        List<GrantedAuthority> authorities=usuario.get().getRoles().stream()
                .map(role-> new SimpleGrantedAuthority(role.getNombre()))
                .peek((authority)->log.info("ROLE: "+ authority.getAuthority()))
                .collect(Collectors.toList());

        User user = new User(usuario.get().getUsername(),usuario.get().getPassword(),usuario.get().getEnabled(),true,true,true,authorities);
        System.out.println("usuario "+user.toString());
        UserDetails userDetails = User.builder()
                .username(usuario.get().getUsername())
                .password(usuario.get().getPassword())
                .disabled(!usuario.get().getEnabled())
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .authorities(authorities)
                .build();
        System.out.println(userDetails);
        return userDetails;
    }
}
