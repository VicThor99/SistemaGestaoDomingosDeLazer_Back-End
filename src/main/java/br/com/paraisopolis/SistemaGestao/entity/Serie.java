package br.com.paraisopolis.SistemaGestao.entity;

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

    @Column(name = "sala")
    private String sala;

}
