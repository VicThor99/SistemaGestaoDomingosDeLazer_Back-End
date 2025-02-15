package br.com.domingosdelazer.SistemaGestao.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "serie")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "serie")
    private String serie;

    @Column(name = "domingo")
    private String domingo;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "escola_id")
    private Escola escola;

}
