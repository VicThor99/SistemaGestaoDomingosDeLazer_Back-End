package br.com.domingosdelazer.SistemaGestao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "planoaulaserie")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanoAulaSerie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie serie;

    @ManyToOne
    @JoinColumn(name = "planoaula_id")
    private PlanoAula planoAula;

}
