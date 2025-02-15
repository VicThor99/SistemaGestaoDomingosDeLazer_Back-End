package br.com.domingosdelazer.SistemaGestao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "planoaula")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanoAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mes")
    private LocalDate mes;

    @Column(name = "tema")
    private String tema;

    @Column(name = "quebragelo")
    private String quebraGelo;

    @Column(name = "titulohistoria")
    private String tituloHistoria;

    @Column(name = "historia")
    private String historia;

    @Column(name = "atividade")
    private String atividade;

    @Column(name = "material")
    private String material;

    @Column(name = "objetivos")
    private String objetivos;

    @ManyToOne
    @JoinColumn(name = "escola_id")
    private Escola escola;

}
