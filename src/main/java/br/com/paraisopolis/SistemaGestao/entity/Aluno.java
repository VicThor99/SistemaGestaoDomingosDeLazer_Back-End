package br.com.paraisopolis.SistemaGestao.entity;

import br.com.paraisopolis.SistemaGestao.entity.enums.Serie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "aluno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nascimento")
    private Date nascimento;

    @Enumerated(EnumType.ORDINAL)
    @Column(name= "serie")
    private Serie serie;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Responsavel responsavel;

    @ManyToOne
    @JoinColumn(name = "numeracao_id")
    private Numeracao numeracao;

}
