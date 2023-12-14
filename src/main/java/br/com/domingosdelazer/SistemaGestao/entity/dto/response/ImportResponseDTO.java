package br.com.domingosdelazer.SistemaGestao.entity.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ImportResponseDTO {

    private Integer total;
    private List<ClasseResponseDTO> series;

}
