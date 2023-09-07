package br.com.paraisopolis.SistemaGestao.entity.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardResponseDTO {

    private ParametrosDomingoResponseDTO domingoa;
    private ParametrosDomingoResponseDTO domingob;
    private ParametrosDomingoResponseDTO domingos;

    private List<DadosGraficoResponseDTO> dadosGraficoA;
    private List<DadosGraficoResponseDTO> dadosGraficoB;
    private List<DadosGraficoResponseDTO> dadosGraficoTotal;

    private String proximaDataDomA;
    private String proximaDataDomB;

}
