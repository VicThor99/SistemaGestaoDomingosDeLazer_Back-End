package br.com.domingosdelazer.SistemaGestao.entity.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SeriesEmMassaRequestDTO {

    private List<SerieRequestDTO> seriesDomA;
    private List<SerieRequestDTO> seriesDomB;
    private List<SerieRequestDTO> seriesDomC;
    private List<SerieRequestDTO> seriesDomD;

}
