package br.com.domingosdelazer.SistemaGestao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dataaula")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dataaula")
    private LocalDate dataAula;

    @Column(name = "domingo")
    private String domingo;

    @ManyToOne
    @JoinColumn(name = "escola_id")
    private Escola escola;

}
