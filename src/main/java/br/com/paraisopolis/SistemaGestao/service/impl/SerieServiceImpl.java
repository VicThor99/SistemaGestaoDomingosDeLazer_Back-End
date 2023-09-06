package br.com.paraisopolis.SistemaGestao.service.impl;

import br.com.paraisopolis.SistemaGestao.entity.Aluno;
import br.com.paraisopolis.SistemaGestao.entity.Serie;
import br.com.paraisopolis.SistemaGestao.repository.AlunoRepository;
import br.com.paraisopolis.SistemaGestao.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieServiceImpl {

    @Autowired
    private SerieRepository repository;

    public Serie verificarOuSalvar(String serieStr, String sala, String domingo){
        Serie serie = this.repository.verificarSeries(serieStr);
        if(serie != null){
            return serie;
        }else{
            return repository.save(Serie.builder()
                    .id(0)
                    .serie(serieStr)
                    .sala(sala)
                    .domingo(domingo)
                    .build());
        }
    }

}
