package com.objetivo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.objetivo.auth.LoginDTO;
import com.objetivo.entities.Endereco;
import com.objetivo.fixtures.EnderecoFixtures;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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
        "/sql/pessoa.sql",
        "/sql/endereco.sql"
})
public class EnderecoControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private static final String SITE = "/enderecos";

    @Test
    @Transactional
    public void obterEnderecoPorId() throws Exception {
        mockMvc.perform(get(SITE + "/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep", equalTo("65945000")))
                .andExpect(jsonPath("$.logradouro", equalTo("Av. Santos Dumont")))
                .andExpect(jsonPath("$.uf", equalTo("PR")))
                .andExpect(jsonPath("$.bairro", equalTo("Industrial")));
    }

    @Test
    @Transactional
    public void pessoaPorEnderecoId() throws Exception {
        mockMvc.perform(get(SITE + "/end/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("@", equalTo(1)));

        mockMvc.perform(get(SITE + "/end/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("@", equalTo(2)));
    }

    @Test
    @Transactional
    public void alteraEndereco() throws Exception {

        MvcResult result = mockMvc.perform(get(SITE + "/{id}", 2)).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        Endereco enderecoToUpdate = objectMapper.readValue(resultAsString, Endereco.class);

        enderecoToUpdate.setCep("315588456");
        enderecoToUpdate.setUf("RJ");
        enderecoToUpdate.setCidade("Gramado");

        mockMvc.perform(put(SITE + "/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enderecoToUpdate)))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get(SITE + "/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep", equalTo("315588456")))
                .andExpect(jsonPath("$.uf", equalTo("RJ")))
                .andExpect(jsonPath("$.cidade", equalTo("Gramado")));
    }

    @Test
    @Transactional
    public void novoEndereco() throws Exception {
        mockMvc.perform(post(SITE + "/{pessoa}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EnderecoFixtures.endereco())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));

        mockMvc.perform(get(SITE + "/{id}", 4)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep", equalTo("75830112")))
                .andExpect(jsonPath("$.bairro", equalTo("Centro")))
                .andExpect(jsonPath("$.uf", equalTo("SP")))
                .andExpect(jsonPath("$.cidade", equalTo("HollyWood")));
    }

    @Test
    @Transactional
    public void buscaporCep() throws Exception {
        mockMvc.perform(get(SITE + "/cep/{cep}", "95670024")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.logradouro", equalTo("Rua Fredolino Birck")))
                .andExpect(jsonPath("$.bairro", equalTo("Minuano")))
                .andExpect(jsonPath("$.localidade", equalTo("Gramado")))
                .andExpect(jsonPath("$.uf", equalTo("RS")));
    }

    @Test
    @Transactional
    public void deleteEndereco() throws Exception {
        mockMvc.perform(delete(SITE + "/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(delete(SITE + "/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

}