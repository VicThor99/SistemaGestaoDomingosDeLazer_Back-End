package br.com.domingosdelazer.SistemaGestao.entity.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SeriesEmMassaRequestDTO {

    private List<SerieListagemRequestDTO> seriesDomA;
    private List<SerieListagemRequestDTO> seriesDomB;
    private List<SerieListagemRequestDTO> seriesDomC;
    private List<SerieListagemRequestDTO> seriesDomD;

}
