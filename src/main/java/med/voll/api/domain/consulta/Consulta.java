package med.voll.api.domain.consulta;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

@Entity
@Table(name = "Consultas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "medico_id")
  private Medico medico;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "paciente_id")
  private Paciente paciente;

  private LocalDateTime data;

  private MotivoCancelamento motivoCancelamento;

  public void cancelar(MotivoCancelamento motivo) {
    this.motivoCancelamento = motivo;
  }
}
