package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.DadosDetalhamentoPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

  @Autowired
  private PacienteService pacienteService;

  @PostMapping
  public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dados,
      UriComponentsBuilder uriComponentsBuilder) {
    var endereco = getEndereco(dados);
    var paciente = getPaciente(dados, endereco);

    pacienteService.save(paciente);

    var uri = uriComponentsBuilder.path("/pacientes/{id}")
        .buildAndExpand(paciente.getId())
        .toUri();
    return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
  }

  private Endereco getEndereco(DadosCadastroPaciente dados) {
    var endereco = Endereco.builder().logradouro(dados.endereco().logradouro())
        .numero(dados.endereco().numero())
        .bairro(dados.endereco().bairro())
        .cidade(dados.endereco().cidade())
        .uf(dados.endereco().uf())
        .complemento(dados.endereco().complemento())
        .cep(dados.endereco().cep())
        .build();
    return endereco;
  }

  private Paciente getPaciente(DadosCadastroPaciente dados, Endereco endereco) {
    var paciente = Paciente.builder()
        .ativo(true)
        .nome(dados.nome())
        .cpf(dados.cpf())
        .email(dados.email())
        .telefone(dados.telefone())
        .endereco(endereco)
        .build();
    return paciente;
  }

}
