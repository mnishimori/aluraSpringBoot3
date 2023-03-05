package med.voll.api.domain.consulta;

import med.voll.api.domain.exception.ValidacaoException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

  @Autowired
  private ConsultaRepository consultaRepository;
  @Autowired
  private MedicoRepository medicoRepository;
  @Autowired
  private PacienteRepository pacienteRepository;

  public void agendar(DadosAgendamentoConsulta dados) {
    Medico medico = escolherMedico(dados);

    var paciente = recuperarPaciente(dados);

    var consulta = new Consulta(null, medico, paciente, dados.data());

    consultaRepository.save(consulta);
  }

  private Paciente recuperarPaciente(DadosAgendamentoConsulta dados) {
    return pacienteRepository.findById(dados.idPaciente())
        .orElseThrow(() -> new ValidacaoException("Id do paciente informado não existe!"));
  }

  private Medico escolherMedico(DadosAgendamentoConsulta dados) {
    if (dados.idMedico() != null) {
      return medicoRepository.findById(dados.idMedico())
          .orElseThrow(() -> new ValidacaoException("Id do médico informado não existe!"));
    } else {
      return definirMedicoAleatorio(dados);
    }
  }

  private Medico definirMedicoAleatorio(DadosAgendamentoConsulta dados) {
    if (dados.especialidade() == null) {
      throw new ValidacaoException("A especialidade é obrigatória quando o médico for opcional!");
    }
    return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
  }

}
