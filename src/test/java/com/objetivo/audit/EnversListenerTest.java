package com.objetivo.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.objetivo.auth.dto.LoginDTO;
import com.objetivo.entities.Pessoa;
import com.objetivo.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(value = "test")
@Sql(scripts = {
        "/sql/pessoa.sql",
        "/sql/endereco.sql",
        "/sql/usuario.sql"
})
class EnversListenerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    PessoaRepository pessoaRepository;

    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    RevisionRepository revisionRepository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser(username = "junior")
    public void newRevision() throws Exception {
        this.executeRevision();
        assertTrue(revisionRepository.findById(1L).orElseThrow().getLogin().equals("junior"));
    }

    @Transactional
    void executeRevision() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin("junior");
        loginDTO.setSenha("123");

        MvcResult mvcResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andDo(print())
                .andReturn();

        String token = mvcResult.getResponse().getContentAsString();

        Pessoa pessoaAlterada = pessoaRepository.findById(3L).orElseThrow();
        pessoaAlterada.setNome("Nome modificado");
        pessoaAlterada.setTelefone("1234567890");

        mockMvc.perform(put("/pessoas/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaAlterada))
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

}