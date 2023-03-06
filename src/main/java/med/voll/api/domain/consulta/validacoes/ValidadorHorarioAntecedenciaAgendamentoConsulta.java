package med.voll.api.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.exception.ValidacaoException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorHorarioAntecedenciaAgendamentoConsulta implements ValidadorAgendamentoDeConsulta{

  public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta){
    var dataConsulta = dadosAgendamentoConsulta.data();
    var diferencaEmMinutos = Duration.between(LocalDateTime.now(), dataConsulta).toMinutes();

    if (diferencaEmMinutos < 30){
      throw new ValidacaoException("A consulta deve ser agendada com antecedência mínima de 30 minutos");
    }
  }
}
