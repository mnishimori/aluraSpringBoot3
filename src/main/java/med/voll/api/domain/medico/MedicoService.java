package med.voll.api.domain.medico;

import java.time.LocalDateTime;
import med.voll.api.domain.especialidade.Especialidade;
import med.voll.api.domain.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MedicoService {

  @Autowired
  private MedicoRepository repository;

  @Transactional
  public Medico save(Medico medico) {
    return repository.save(medico);
  }

  public Medico findByIdRequired(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new ValidacaoException("Id de médico informado não existe."));
  }

  public Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade,
      LocalDateTime data) {
    validarEscolhaDeMedicoAleatorio(especialidade, data);
    return repository.escolherMedicoAleatorioLivreNaData(especialidade, data)
        .orElseThrow(() -> new ValidacaoException("Não há médico disponível na data."));
  }

  private void validarEscolhaDeMedicoAleatorio(Especialidade especialidade, LocalDateTime data) {
    if (especialidade == null) {
      throw new ValidacaoException("A especialidade é obrigatória quando o médico for opcional.");
    }
    if (data == null) {
      throw new ValidacaoException("É necessário informar a data da consulta.");
    }
  }

  public Page<Medico> findAllByAtivoTrue(Pageable pageable) {
    return repository.findAllByAtivoTrue(pageable);
  }
}
