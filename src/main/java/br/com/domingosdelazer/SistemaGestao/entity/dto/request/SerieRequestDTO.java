package br.com.domingosdelazer.SistemaGestao.entity.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SerieRequestDTO {

    private String serie;
    private String sala;

}