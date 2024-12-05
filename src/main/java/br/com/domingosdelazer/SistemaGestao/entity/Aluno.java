package br.com.domingosdelazer.SistemaGestao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "nascimento")
    private LocalDate nascimento;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "sapato")
    private Integer sapato;

    @Column(name = "calca")
    private Integer calca;

    @Column(name = "camisa")
    private Integer camisa;

    @Column(name = "nomeresponsavel")
    private String nomeResponsavel;

    @Column(name = "telefoneresponsavel")
    private String telefoneResponsavel;

    @Column(name = "emailresponsavel")
    private String emailResponsavel;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "sairsozinho")
    private Boolean sairSozinho;

    @Column(name = "numerosacolinha")
    private String numeroSacolinha;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie serie;

    @ManyToOne
    @JoinColumn(name = "registro_id")
    private RegistroPresencas registroPresencas;

    @ManyToOne
    @JoinColumn(name = "arquivo_id")
    private ArquivosAluno arquivos;

    @ManyToOne
    @JoinColumn(name = "escola_id")
    private Escola escola;

}
