package br.com.paraisopolis.SistemaGestao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "responsavel")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Responsavel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

}
