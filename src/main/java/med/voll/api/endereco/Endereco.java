package med.voll.api.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;

    public void atualizarInformacoes(DadosEndereco dadosEndereco) {
        if (dadosEndereco.logradouro() != null) this.logradouro = dadosEndereco.logradouro();
        if (dadosEndereco.bairro() != null) this.bairro = dadosEndereco.bairro();
        if (dadosEndereco.cep() != null) this.cep = dadosEndereco.cep();
        if (dadosEndereco.numero() != null) this.numero = dadosEndereco.numero();
        if (dadosEndereco.complemento() != null) this.complemento = dadosEndereco.complemento();
        if (dadosEndereco.cidade() != null) this.cidade = dadosEndereco.cidade();
        if (dadosEndereco.uf() != null) this.uf = dadosEndereco.uf();
    }
}
