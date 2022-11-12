package med.voll.api.medico;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;
import med.voll.api.endereco.Endereco;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "medicos")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    @PostConstruct
    public void init(){
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizacaoMedico dadosAtualizacaoMedico) {
        if (dadosAtualizacaoMedico.nome() != null) this.nome = dadosAtualizacaoMedico.nome();
        if (dadosAtualizacaoMedico.telefone() != null) this.telefone = dadosAtualizacaoMedico.telefone();
        if (dadosAtualizacaoMedico.dadosEndereco() != null)
            this.endereco.atualizarInformacoes(dadosAtualizacaoMedico.dadosEndereco());
    }

    public void excluir(){
        this.ativo = false;
    }
}
