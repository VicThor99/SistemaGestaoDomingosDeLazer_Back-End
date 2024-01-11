package br.com.domingosdelazer.SistemaGestao.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "usuarioacesso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioAcesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "escola_id")
    private Escola escola;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
