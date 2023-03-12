package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.CancelamentoDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

  @Autowired
  private AgendaDeConsultas agenda;
  @Autowired
  private CancelamentoDeConsultas cancelamentoDeConsultas;

  @PostMapping
  public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
    var dto = agenda.execute(dados);
    return ResponseEntity.ok(dto);
  }

  @PutMapping("cancelamento")
  public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
    var dadosCancelamentoConsulta = cancelamentoDeConsultas.execute(dados);
    return ResponseEntity.ok(dadosCancelamentoConsulta);
  }

}
