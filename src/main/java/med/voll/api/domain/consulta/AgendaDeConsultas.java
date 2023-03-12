package med.voll.api.domain.consulta;

import java.util.List;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoService;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgendaDeConsultas {

  @Autowired
  private ConsultaService consultaService;
  @Autowired
  private MedicoService medicoService;
  @Autowired
  private PacienteService pacienteService;
  @Autowired
  private List<ValidadorAgendamentoDeConsulta> validadorAgendamentoDeConsultas;

  @Transactional
  public DadosDetalhamentoConsulta execute(DadosAgendamentoConsulta dados) {
    validar(dados);

    var medico = escolherMedico(dados);
    var paciente = recuperarPaciente(dados);
    var consulta = new Consulta(null, medico, paciente, dados.data(), null);

    consulta = consultaService.save(consulta);
    return new DadosDetalhamentoConsulta(consulta);
  }

  private void validar(DadosAgendamentoConsulta dados) {
    pacienteService.findByIdRequired(dados.idPaciente());
    medicoService.findByIdRequired(dados.idMedico());
    validadorAgendamentoDeConsultas.forEach(validador -> validador.validar(dados));
  }

  private Paciente recuperarPaciente(DadosAgendamentoConsulta dados) {
    return pacienteService.findByIdRequired(dados.idPaciente());
  }

  private Medico escolherMedico(DadosAgendamentoConsulta dados) {
    if (dados.idMedico() != null) {
      return medicoService.findByIdRequired(dados.idMedico());
    } else {
      return definirMedicoAleatorio(dados);
    }
  }

  private Medico definirMedicoAleatorio(DadosAgendamentoConsulta dados) {
    return medicoService.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
  }
}
