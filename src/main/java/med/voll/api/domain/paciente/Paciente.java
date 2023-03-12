package med.voll.api.domain.paciente;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Entity
@Table(name = "pacientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paciente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;

  @Column(unique = true)
  private String email;

  @Column(unique = true)
  private String cpf;

  @Embedded
  private Endereco endereco;

  private String telefone;

  private boolean ativo = true;

}
