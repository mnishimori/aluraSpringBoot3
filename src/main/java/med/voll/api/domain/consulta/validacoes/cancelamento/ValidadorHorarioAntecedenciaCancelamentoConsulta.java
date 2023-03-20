package med.voll.api.domain.consulta.validacoes.cancelamento;

import java.time.Duration;
import java.time.LocalDateTime;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import med.voll.api.domain.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorHorarioAntecedenciaCancelamentoConsulta implements
    ValidadorCancelamentoDeConsulta {

  @Autowired
  private ConsultaRepository repository;

  @Override
  public void validar(DadosCancelamentoConsulta dados) {
    var consulta = repository.getReferenceById(dados.idConsulta());
    var agora = LocalDateTime.now();
    var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

    if (diferencaEmHoras < 24) {
      throw new ValidacaoException(
          "Consulta somente pode ser cancelada com antecedência mínima de 24h!");
    }
  }
}