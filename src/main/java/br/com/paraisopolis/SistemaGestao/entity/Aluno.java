package br.com.paraisopolis.SistemaGestao.entity;

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

    @Column(name = "sapato")
    private Integer sapato;

    @Column(name = "camisa")
    private Integer camisa;

    @Column(name = "calca")
    private Integer calca;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "nomeresponsavel")
    private String nomeResponsavel;

    @Column(name = "telefoneresponsavel")
    private String telefoneResponsavel;

    @Column(name = "emailresponsavel")
    private String emailResponsavel;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie serie;

    @ManyToOne
    @JoinColumn(name = "registro_id")
    private RegistroPresencas registroPresencas;

}
