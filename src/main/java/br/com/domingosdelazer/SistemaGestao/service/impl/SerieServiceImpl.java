package br.com.domingosdelazer.SistemaGestao.service.impl;

import br.com.domingosdelazer.SistemaGestao.entity.Escola;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.SerieRequestDTO;
import br.com.domingosdelazer.SistemaGestao.repository.EscolaRepository;
import br.com.domingosdelazer.SistemaGestao.repository.SalaRepository;
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

    @Autowired
    private SalaRepository salaRepository;

    public Serie verificarOuSalvar(String serieStr, String sala, String domingo, Integer escolaId){
        Serie serie = this.repository.verificarSeries(serieStr, escolaId);
        if(serie != null){
            return serie;
        }else{
            Escola escola = this.escolaRepository.getEscolaById(escolaId);
            return repository.save(Serie.builder()
                    .id(0)
                    .serie(serieStr)
                    .sala(this.salaRepository.getSalaPorNomeSala(sala))
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

    public Serie save(SerieRequestDTO serie, Integer escolaId){
        return this.repository.save(Serie.builder()
                .id(serie.getId())
                .domingo(serie.getDomingo())
                .serie(serie.getSerie())
                .sala(this.salaRepository.getSalaPorNomeSala(serie.getSala()))
                .escola(this.escolaRepository.getEscolaById(escolaId))
                .build());
    }

    public List<String> listSeriesString(Integer escolaId) {
        return this.repository.listSeriesString(escolaId);
    }

}
