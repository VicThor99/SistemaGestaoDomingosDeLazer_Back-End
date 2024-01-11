package br.com.domingosdelazer.SistemaGestao.service.impl;


import br.com.domingosdelazer.SistemaGestao.entity.ArquivosAluno;
import br.com.domingosdelazer.SistemaGestao.entity.Escola;
import br.com.domingosdelazer.SistemaGestao.repository.ArquivosRepository;
import br.com.domingosdelazer.SistemaGestao.repository.EscolaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArquivosServiceImpl {

    @Autowired
    private ArquivosRepository repository;

    public ArquivosAluno getArquivosById(Integer id){
        return this.repository.findById(id).orElseGet(null);
    }

    public ArquivosAluno save(ArquivosAluno arquivo) {
        return this.repository.save(arquivo);
    }

    public ArquivosAluno getArquivosByIdAluno(Integer idAluno) {
        return this.repository.getArquivosByIdAluno(idAluno);
    }
}
