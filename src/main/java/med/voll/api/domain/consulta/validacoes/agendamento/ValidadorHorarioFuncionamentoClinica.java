package med.voll.api.domain.consulta.validacoes.agendamento;

import java.time.DayOfWeek;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.exception.ValidacaoException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

  public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
    var dataConsulta = dadosAgendamentoConsulta.data();
    var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    var sabado = dataConsulta.getDayOfWeek().equals(DayOfWeek.SATURDAY);
    var antesDaAberturaClinica = dataConsulta.getHour() < 7;
    var depoisDoEncerramentoFuncionamentoClinica = dataConsulta.getHour() > 18;

    if (domingo || sabado || antesDaAberturaClinica || depoisDoEncerramentoFuncionamentoClinica) {
      throw new ValidacaoException("Consulta fora do dia/horário de funcionamento da clínica");
    }
  }
}
