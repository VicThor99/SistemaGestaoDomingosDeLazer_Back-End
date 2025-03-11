package br.com.domingosdelazer.SistemaGestao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sala")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sala")
    private String sala;

    @ManyToOne
    @JoinColumn(name = "escola_id")
    private Escola escola;

}
