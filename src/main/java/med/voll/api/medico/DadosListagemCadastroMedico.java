package med.voll.api.medico;

public record DadosListagemCadastroMedico(
        Long id,
        String nome,
        String email,
        String telefone,
        Especialidade especialidade
    ){
    public DadosListagemCadastroMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getEspecialidade());
    }
}
