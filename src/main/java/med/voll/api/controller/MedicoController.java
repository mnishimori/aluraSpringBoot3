package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.endereco.Endereco;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosListagemCadastroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){

        Endereco endereco = getEndereco(dados);

        Medico medico = getMedico(dados, endereco);

        medicoRepository.save(medico);
    }

    @GetMapping
    public Page<DadosListagemCadastroMedico> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"})
                                                                Pageable pageable){
        Page<Medico> medicos = medicoRepository.findAll(pageable);

        return medicos.map(DadosListagemCadastroMedico::new);
    }


    private Endereco getEndereco(DadosCadastroMedico dados) {
        Endereco endereco = Endereco.builder()
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
        Medico medico = Medico.builder()
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
