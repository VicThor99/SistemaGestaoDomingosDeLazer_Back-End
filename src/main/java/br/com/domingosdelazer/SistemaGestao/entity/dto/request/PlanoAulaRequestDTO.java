package br.com.domingosdelazer.SistemaGestao.entity.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class PlanoAulaRequestDTO {

    private Integer id;
    private LocalDate mes;
    private String tema;
    private String objetivos;
    private String quebraGelo;
    private String tituloHistoria;
    private String historia;
    private String atividade;
    private String material;
    private List<String> series;

}
