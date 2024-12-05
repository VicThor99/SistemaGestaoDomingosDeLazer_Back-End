package br.com.domingosdelazer.SistemaGestao.entity.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class RegistroRequestDTO {

    private List<String> codigos;
    private LocalDate data;

}
