package med.voll.api.domain.consulta;

import java.util.List;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelamentoDeConsultas {

  @Autowired
  private ConsultaService consultaService;
  @Autowired
  private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

  @Transactional
  public DadosDetalhamentoConsulta execute(DadosCancelamentoConsulta dados) {
    var consulta = consultaService.findByIdRequired(dados.idConsulta());

    validadoresCancelamento.forEach(v -> v.validar(dados));

    consulta.cancelar(dados.motivo());

    return new DadosDetalhamentoConsulta(consulta);
  }
}
