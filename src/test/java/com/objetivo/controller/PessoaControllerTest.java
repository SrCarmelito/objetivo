package com.objetivo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.objetivo.entities.Pessoa;
import com.objetivo.fixtures.PessoaFixtures;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
@Sql(scripts = {
        "/sql/pessoa.sql"
})
public class PessoaControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Transactional
    public void findAllPaginada() throws Exception {
        mockMvc.perform(get("/pessoas")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pageable", notNullValue()))
            .andExpect(jsonPath("$.content", hasSize(3)));
    }

    @Test
    @Transactional
    public void quantidadePessoas() throws Exception {
       mockMvc.perform(get("/pessoas/count")
           .contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("@", equalTo(3)));
    }

    @Test
    @Transactional
    public void excluirPessoa() throws Exception {
        mockMvc.perform(delete("/pessoas/{id}", 2)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent());

        mockMvc.perform(get("/pessoas/count")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("@", equalTo(2)));
    }

    @Test
    @Transactional
    public void obterPessoaPorId() throws Exception {
        mockMvc.perform(get("/pessoas/{id}", 3)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome", equalTo("Phill Collins")))
            .andExpect(jsonPath("$.cpf", equalTo("42166575595")))
            .andExpect(jsonPath("$.dataNascimento", equalTo("1960-11-28")))
            .andExpect(jsonPath("$.telefone", equalTo("43965214578")));
    }

    @Test
    @Transactional
    public void novaPessoa() throws Exception {
        mockMvc.perform(post("/pessoas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(PessoaFixtures.pessoaJamesGosling())))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()));

        mockMvc.perform(get("/pessoas/{id}", 4)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome", equalTo("James Gosling")))
            .andExpect(jsonPath("$.cpf", equalTo("62510728490")));
    }

    @Test
    @Transactional
    public void alterarPessoa() throws Exception {
        Pessoa pessoa = new Pessoa();

        MvcResult result = mockMvc.perform(get("/pessoas/{id}", 3)).andReturn();

        String resultAsString = result.getResponse().getContentAsString();
        pessoa = objectMapper.readValue(resultAsString, Pessoa.class);

        pessoa.setCpf("62756443204");
        pessoa.setNome("Kobe Bryant");

        mockMvc.perform(put("/pessoas/{id}", 3)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(pessoa)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()));

        mockMvc.perform(get("/pessoas/{id}", 3)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome", equalTo("Kobe Bryant")))
            .andExpect(jsonPath("$.cpf", equalTo("62756443204")));
    }
}
