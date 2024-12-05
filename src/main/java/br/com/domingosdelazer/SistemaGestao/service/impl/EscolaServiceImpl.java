package br.com.domingosdelazer.SistemaGestao.service.impl;


import br.com.domingosdelazer.SistemaGestao.entity.DataAula;
import br.com.domingosdelazer.SistemaGestao.entity.Escola;
import br.com.domingosdelazer.SistemaGestao.repository.DataAulaRepository;
import br.com.domingosdelazer.SistemaGestao.repository.EscolaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EscolaServiceImpl {

    @Autowired
    private EscolaRepository repository;

    public Escola save(Escola escola){
        return this.repository.save(escola);
    }

    public List<Escola> listAll(){
        return this.repository.findAll();
    }

    public Escola getEscolaById(Integer escolaId) {
        return this.repository.getEscolaById(escolaId);
    }
}
