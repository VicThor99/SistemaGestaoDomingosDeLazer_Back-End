package br.com.domingosdelazer.SistemaGestao.entity.dto.response;

import br.com.domingosdelazer.SistemaGestao.entity.enums.EnumPresencas;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegistroPresencaAlunoResponseDTO {

    private String codigo;
    private String nome;
    private EnumPresencas fevereiro;
    private EnumPresencas marco;
    private EnumPresencas abril;
    private EnumPresencas maio;
    private EnumPresencas junho;
    private EnumPresencas agosto;
    private EnumPresencas setembro;
    private EnumPresencas outubro;
    private EnumPresencas novembro;

    public RegistroPresencaAlunoResponseDTO(EnumPresencas fevereiro, EnumPresencas marco, EnumPresencas abril,
                                            EnumPresencas maio, EnumPresencas junho, EnumPresencas agosto,
                                            EnumPresencas setembro, EnumPresencas outubro, EnumPresencas novembro) {
        this.fevereiro = fevereiro;
        this.marco = marco;
        this.abril = abril;
        this.maio = maio;
        this.junho = junho;
        this.agosto = agosto;
        this.setembro = setembro;
        this.outubro = outubro;
        this.novembro = novembro;
    }
}
