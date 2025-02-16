package br.com.domingosdelazer.SistemaGestao.service.impl;

import br.com.domingosdelazer.SistemaGestao.entity.*;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.PlanoAulaRequestDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.ListPlanoAulaResponseDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.PlanoAulaResponseDTO;
import br.com.domingosdelazer.SistemaGestao.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
public class PlanoAulaServiceImpl {

    @Autowired
    private PlanoAulaRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private PlanoAulaSerieRepository planoAulaSerieRepository;

    @Autowired
    private SerieRepository serieRepository;

    public void verificarOuSalvar(PlanoAulaRequestDTO request, Integer escolaId) {
        if(request.getId() == null || request.getId() == 0) {
            PlanoAula planoAula = this.save(PlanoAula.builder()
                    .mes(request.getMes())
                    .tema(request.getTema())
                    .objetivos(request.getObjetivos())
                    .quebraGelo(request.getQuebraGelo())
                    .tituloHistoria(request.getTituloHistoria())
                    .historia(request.getHistoria())
                    .atividade(request.getAtividade())
                    .material(request.getMaterial())
                    .escola(this.escolaRepository.getEscolaById(escolaId))
                    .build());
            cadastrarPlanoAulaSeries(request.getSeries(), planoAula, escolaId);
        } else {
            this.repository.findById(request.getId()).ifPresent(planoAula -> cadastrarPlanoAulaSeries(request.getSeries(), planoAula, escolaId));
        }
    }

    private void cadastrarPlanoAulaSeries(List<String> series, PlanoAula planoAula, Integer escolaId) {
        for(String serie : series){
            if(!this.repository.verificaLigacaoPlanoAulaSerie(serie, planoAula.getId())){
                this.planoAulaSerieRepository.save(PlanoAulaSerie
                        .builder()
                        .serie(this.serieRepository.verificarSeries(serie, escolaId))
                        .planoAula(planoAula)
                        .build());
            }
        }
    }

    public PlanoAula save(PlanoAula planoAula){
        return this.repository.save(planoAula);
    }

    public List<ListPlanoAulaResponseDTO> listAll(Integer escolaId) {
        List<ListPlanoAulaResponseDTO> responses = new ArrayList<>();
        this.repository.findAll(escolaId).forEach(planoAula -> {
            responses.add(ListPlanoAulaResponseDTO
                    .builder()
                    .id(planoAula.getId())
                    .atividade(planoAula.getAtividade())
                    .mes(planoAula.getMes().getMonth().getDisplayName(TextStyle.FULL, new Locale("pt","BR")).toUpperCase())
                    .objetivos(planoAula.getObjetivos())
                    .material(planoAula.getMaterial())
                    .tema(planoAula.getTema())
                    .historia(planoAula.getHistoria())
                    .tituloHistoria(planoAula.getTituloHistoria())
                    .quebraGelo(planoAula.getQuebraGelo())
                    .series(traduzirSeries(this.repository.getLigacoesPlanoAulaSerie(planoAula.getId())))
                    .build());
        });
        return responses;
    }

    private String traduzirSeries(List<String> ligacoesPlanoAulaSerie) {
        StringBuilder sb = new StringBuilder();
        ligacoesPlanoAulaSerie.forEach(ligacao -> {
            sb.append(ligacao).append(",");
        });
        return sb.length() > 0 ? sb.deleteCharAt(sb.length() - 1).toString() : "";
    }

    public PlanoAulaResponseDTO getBySala(String username, Integer escolaId) {
        PlanoAula planoAula = this.repository.getPlanoPorNomeSala(username, escolaId);
        if(planoAula != null) {
            return PlanoAulaResponseDTO
                    .builder()
                    .id(planoAula.getId())
                    .atividade(planoAula.getAtividade())
                    .mes(planoAula.getMes().getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")) + " de " + planoAula.getMes().getYear())
                    .objetivos(planoAula.getObjetivos())
                    .material(planoAula.getMaterial())
                    .tema(planoAula.getTema())
                    .historia(planoAula.getHistoria())
                    .tituloHistoria(planoAula.getTituloHistoria())
                    .quebraGelo(planoAula.getQuebraGelo())
                    .build();
        } else {
            return null;
        }
    }

    public PlanoAulaResponseDTO getById(Integer planoId, Integer escolaId) {
        PlanoAula planoAula = this.repository.findById(planoId, escolaId);
        if(planoAula != null) {
            return PlanoAulaResponseDTO
                    .builder()
                    .id(planoAula.getId())
                    .atividade(planoAula.getAtividade())
                    .mes(planoAula.getMes().getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")) + " de " + planoAula.getMes().getYear())
                    .objetivos(planoAula.getObjetivos())
                    .material(planoAula.getMaterial())
                    .tema(planoAula.getTema())
                    .historia(planoAula.getHistoria())
                    .tituloHistoria(planoAula.getTituloHistoria())
                    .quebraGelo(planoAula.getQuebraGelo())
                    .series(this.repository.getLigacoesPlanoAulaSerie(planoAula.getId()))
                    .build();
        } else {
            return null;
        }
    }
}
