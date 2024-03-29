package br.com.domingosdelazer.SistemaGestao.service.impl;

import br.com.domingosdelazer.SistemaGestao.entity.Escola;
import br.com.domingosdelazer.SistemaGestao.repository.EscolaRepository;
import br.com.domingosdelazer.SistemaGestao.repository.SerieRepository;
import br.com.domingosdelazer.SistemaGestao.entity.Serie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieServiceImpl {

    @Autowired
    private SerieRepository repository;
    @Autowired
    private EscolaRepository escolaRepository;

    public Serie verificarOuSalvar(String serieStr, String sala, String domingo, Integer escolaId){
        Serie serie = this.repository.verificarSeries(serieStr, escolaId);
        if(serie != null){
            return serie;
        }else{
            Escola escola = this.escolaRepository.getEscolaById(escolaId);
            return repository.save(Serie.builder()
                    .id(0)
                    .serie(serieStr)
                    .sala(sala)
                    .domingo(domingo)
                            .escola(escola)
                    .build());
        }
    }

    public Serie verificarSerie(String serieStr, Integer escolaId) {
        return this.repository.verificarSeries(serieStr, escolaId);
    }

    public List<Serie> listAll(Integer escolaId){
        return this.repository.findAllEscola(escolaId);
    }

    public Serie save(Serie serie){
        return this.repository.save(serie);
    }

    public List<String> listSeriesString(Integer escolaId) {
        return this.repository.listSeriesString(escolaId);
    }

    public List<String> listSalasString(Integer escolaId) {
        return this.repository.listSalasString(escolaId);
    }
}
