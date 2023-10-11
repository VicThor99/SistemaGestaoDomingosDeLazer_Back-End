package br.com.paraisopolis.SistemaGestao.service.impl;


import br.com.paraisopolis.SistemaGestao.entity.DataAula;
import br.com.paraisopolis.SistemaGestao.entity.Usuario;
import br.com.paraisopolis.SistemaGestao.exception.InvalidPasswordException;
import br.com.paraisopolis.SistemaGestao.repository.DataAulaRepository;
import br.com.paraisopolis.SistemaGestao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DataAulaServiceImpl {

    @Autowired
    private DataAulaRepository repository;

    public Optional<DataAula> getProximaAula(String domingo){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return this.repository.getProximaAula(domingo, sdf.format(new Date()));
    }

    public List<DataAula> listAll(){
        return this.repository.findAll(Sort.by("dataAula"));
    }

    public DataAula save(DataAula dataAula){
        return this.repository.save(dataAula);
    }


}
