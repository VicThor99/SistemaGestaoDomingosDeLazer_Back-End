package br.com.domingosdelazer.SistemaGestao.service.impl;

import br.com.domingosdelazer.SistemaGestao.entity.Escola;
import br.com.domingosdelazer.SistemaGestao.entity.Sala;
import br.com.domingosdelazer.SistemaGestao.entity.Serie;
import br.com.domingosdelazer.SistemaGestao.repository.EscolaRepository;
import br.com.domingosdelazer.SistemaGestao.repository.SalaRepository;
import br.com.domingosdelazer.SistemaGestao.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaServiceImpl {

    @Autowired
    private SalaRepository repository;

    public Sala verificarOuSalvar(String nomeSala){
        Sala sala = this.getSalaPorNomeSala(nomeSala);
        if(sala != null){
            return sala;
        } else {
            return this.save(Sala.builder()
                    .sala(nomeSala)
                    .build());
        }
    }

    public Sala save(Sala sala){
        return this.repository.save(sala);
    }

    public List<String> listSalasString(Integer escolaId) {
        return this.repository.listSalasString(escolaId);
    }

    public Sala getSalaPorNomeSala(String sala) {
        return this.repository.getSalaPorNomeSala(sala);
    }
}
