package br.com.domingosdelazer.SistemaGestao.entity.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PlanoAulaResponseDTO {

    private Integer id;
    private String mes;
    private String tema;
    private String objetivos;
    private String quebraGelo;
    private String tituloHistoria;
    private String historia;
    private String atividade;
    private String material;
    private String series;

}
