package br.com.domingosdelazer.SistemaGestao.entity.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GerarProtocolosRequestDTO {

    private Boolean ativos;
    private String serie;
    private String sala;
    private String domingo;
    private String codigo;

}