package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.DadosListagemCadastroMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
public class MedicoController {

  @Autowired
  private MedicoRepository medicoRepository;

  @PostMapping
  @Transactional
  public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados,
      UriComponentsBuilder uriComponentsBuilder) {
    var endereco = getEndereco(dados);
    var medico = getMedico(dados, endereco);

    medicoRepository.save(medico);

    var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
    return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
  }

  @GetMapping
  public ResponseEntity<Page<DadosListagemCadastroMedico>> listar(
      @PageableDefault(size = 10, page = 0, sort = {"nome"})
      Pageable pageable) {
    var medicos = medicoRepository.findAllByAtivoTrue(pageable);
    var medicosCollection = medicos.map(DadosListagemCadastroMedico::new);
    return ResponseEntity.ok(medicosCollection);
  }

  @GetMapping("/{id}")
  public ResponseEntity getMedicoById(@PathVariable Long id) {
    var medico = medicoRepository.getReferenceById(id);
    return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
  }

  @PutMapping
  @Transactional
  public ResponseEntity atualizar(
      @RequestBody @Valid DadosAtualizacaoMedico dadosAtualizacaoMedico) {
    var medico = medicoRepository.findById(dadosAtualizacaoMedico.id()).get();
    medico.atualizarInformacoes(dadosAtualizacaoMedico);
    return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity deletar(@PathVariable Long id) {
    var medico = medicoRepository.getReferenceById(id);
    medico.excluir();
    return ResponseEntity.noContent().build();
  }

  private Endereco getEndereco(DadosCadastroMedico dados) {
    var endereco = Endereco.builder()
        .logradouro(dados.endereco().logradouro())
        .bairro(dados.endereco().bairro())
        .cep(dados.endereco().cep())
        .numero(dados.endereco().numero())
        .complemento(dados.endereco().complemento())
        .cidade(dados.endereco().cidade())
        .uf(dados.endereco().uf())
        .build();
    return endereco;
  }

  private Medico getMedico(DadosCadastroMedico dados, Endereco endereco) {
    var medico = Medico.builder()
        .nome(dados.nome())
        .email(dados.email())
        .telefone(dados.telefone())
        .crm(dados.crm())
        .endereco(endereco)
        .especialidade(dados.especialidade())
        .build();
    return medico;
  }
}
