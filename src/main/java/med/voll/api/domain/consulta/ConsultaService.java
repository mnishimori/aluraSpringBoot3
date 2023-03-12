package med.voll.api.domain.consulta;

import java.time.LocalDateTime;
import med.voll.api.domain.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {

  @Autowired
  private ConsultaRepository repository;

  public Consulta save(Consulta consulta) {
    return repository.save(consulta);
  }

  public Consulta findByIdRequired(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new ValidacaoException("Id de consulta não existe."));
  }

  public boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long idMedico,
      LocalDateTime data) {
    return repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(idMedico, data);
  }

  public boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario,
      LocalDateTime ultimoHorario) {
    return repository.existsByPacienteIdAndDataBetween(idPaciente, primeiroHorario, ultimoHorario);
  }

}
