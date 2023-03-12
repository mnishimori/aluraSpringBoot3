package med.voll.api.domain.paciente;

import med.voll.api.domain.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PacienteService {

  @Autowired
  private PacienteRepository repository;

  @Transactional
  public Paciente save(Paciente paciente) {
    return repository.save(paciente);
  }

  public Paciente findByIdRequired(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new ValidacaoException("Id do paciente informado não existe."));
  }

}
