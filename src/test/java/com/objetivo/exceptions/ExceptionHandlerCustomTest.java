package com.objetivo.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.objetivo.entities.Pessoa;
import com.objetivo.fixtures.PessoaFixtures;
import com.objetivo.service.EnderecoService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
public class ExceptionHandlerCustomTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private EnderecoService enderecoService;

    @Test
    public void general() {  //TODO
    }

    @Test
    public void dataIntegrity() {  //TODO
    }

    @Test
    public void argumentNotValid() throws Exception {
        Pessoa pessoaComErro =  PessoaFixtures.pessoaJamesGosling();
        pessoaComErro.setNome(null);
        pessoaComErro.setTelefone(null);

        mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaComErro)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", notNullValue()))
                .andExpect(jsonPath("$.errors", Matchers.hasItem("Campo telefone É Necessário Informar o Telefone! ")))
                .andExpect(jsonPath("$.errors", Matchers.hasItem("Campo nome É Necessário informar o Nome! ")));
    }

    @Test
    public void entityNotFound() {  //TODO
    }

    @Test
    public void constraintValidation() {  //TODO
    }

    @Test
    public void testArgumentNotValid() {  //TODO
    }

    @Test
    public void httpClientError() throws Exception {
        mockMvc.perform(get("/enderecos/cep/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", notNullValue()))
                .andExpect(jsonPath("$.message", Matchers.containsStringIgnoringCase("Não encontramos o que você precisava, tente novamente")));
    }

    @Test
    public void noResourceFound() throws Exception {
        mockMvc.perform(get("/pessoa")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors", Matchers.contains("pessoa Não corresponde a nenhum End Point!!!")));

        mockMvc.perform(get("/end")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors", Matchers.contains("end Não corresponde a nenhum End Point!!!")));
    }

    @Test
    public void illegalArgument() {
        //TODO

    }
}