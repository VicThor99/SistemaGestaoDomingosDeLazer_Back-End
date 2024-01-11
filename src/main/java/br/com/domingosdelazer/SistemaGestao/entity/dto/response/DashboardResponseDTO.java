package br.com.domingosdelazer.SistemaGestao.entity.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardResponseDTO {

    private ParametrosDomingoResponseDTO domingoA;
    private ParametrosDomingoResponseDTO domingoB;
    private ParametrosDomingoResponseDTO domingoC;
    private ParametrosDomingoResponseDTO domingoD;
    private ParametrosDomingoResponseDTO domingos;

    private List<DadosGraficoResponseDTO> dadosGraficoA;
    private List<DadosGraficoResponseDTO> dadosGraficoB;
    private List<DadosGraficoResponseDTO> dadosGraficoC;
    private List<DadosGraficoResponseDTO> dadosGraficoD;

    private String proximaDataDomA;
    private String proximaDataDomB;
    private String proximaDataDomC;
    private String proximaDataDomD;

}
