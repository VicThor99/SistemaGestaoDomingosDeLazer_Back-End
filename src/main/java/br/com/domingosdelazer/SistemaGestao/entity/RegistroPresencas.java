package br.com.domingosdelazer.SistemaGestao.entity;

import br.com.domingosdelazer.SistemaGestao.entity.enums.EnumPresencas;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "registropresencas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroPresencas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "fevereiro")
    private EnumPresencas fevereiro;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "marco")
    private EnumPresencas marco;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "abril")
    private EnumPresencas abril;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "maio")
    private EnumPresencas maio;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "junho")
    private EnumPresencas junho;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "agosto")
    private EnumPresencas agosto;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "setembro")
    private EnumPresencas setembro;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "outubro")
    private EnumPresencas outubro;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "novembro")
    private EnumPresencas novembro;

}
