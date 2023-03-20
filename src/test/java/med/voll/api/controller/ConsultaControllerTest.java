package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

  @Autowired
  private MockMvc mvc;
  @Autowired
  private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJacksonTester;
  @Autowired
  private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJacksonTester;
  @MockBean
  private AgendaDeConsultas agendaDeConsultas;

  @Test
  @WithMockUser
  void deveriaDevolverStatus400QuandoInformacoesEstaoInvalidas() throws Exception {
    var request = MockMvcRequestBuilders.post("/consultas");

    var response = mvc.perform(request)
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @WithMockUser
  void deveriaDevolverStatus200QuandoInformacoesEstaoValidas() throws Exception {
    var data = LocalDateTime.now().plusHours(1);
    var especialidade = Especialidade.CARDIOLOGIA;
    var dadosAgendamentoConsulta = new DadosAgendamentoConsulta(2L, 5L, data,
        especialidade);
    var json = dadosAgendamentoConsultaJacksonTester.write(dadosAgendamentoConsulta).getJson();
    var dadosDetalhamentoConsulta = new DadosDetalhamentoConsulta(null, 2l, 5l, data);
    var jsonEsperado = dadosDetalhamentoConsultaJacksonTester.write(dadosDetalhamentoConsulta)
        .getJson();

    Mockito.when(agendaDeConsultas.execute(dadosAgendamentoConsulta))
        .thenReturn(dadosDetalhamentoConsulta);

    var request = MockMvcRequestBuilders.post("/consultas")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json);
    var response = mvc.perform(request)
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
  }

}