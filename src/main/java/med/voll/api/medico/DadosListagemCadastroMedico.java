package med.voll.api.medico;

public record DadosListagemCadastroMedico(
        String nome,
        String email,
        String telefone,
        Especialidade especialidade
    ){
    public DadosListagemCadastroMedico(Medico medico) {
        this(medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
