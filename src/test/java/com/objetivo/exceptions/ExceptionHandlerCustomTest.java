package com.objetivo.exceptions;

import com.objetivo.DefaultTest;
import com.objetivo.dto.PessoaDTO;
import com.objetivo.entities.Endereco;
import com.objetivo.entities.Pessoa;
import com.objetivo.fixtures.PessoaDTOFixtures;
import com.objetivo.fixtures.PessoaFixtures;
import com.objetivo.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDate;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExceptionHandlerCustomTest extends DefaultTest {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Test
    public void methodArgumentNotValid() throws Exception {
        Pessoa pessoaComErro =  PessoaFixtures.pessoaJamesGosling();
        pessoaComErro.setNome(null);
        pessoaComErro.setTelefone(null);

        mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaComErro)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()))
                .andExpect(jsonPath("$.errors", notNullValue()))
                .andExpect(jsonPath("$.errors", Matchers.hasItem("Campo telefone É Necessário Informar o Telefone! ")))
                .andExpect(jsonPath("$.errors", Matchers.hasItem("Campo nome É Necessário informar o Nome! ")));
    }

    @Test
    public void entityNotFound() throws Exception {
        mockMvc.perform(get("/pessoas/88")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(EntityNotFoundException.class, result.getResolvedException()))
                .andExpect(jsonPath("$.errors", Matchers.contains("Id Informado não existe na base de dados!")));

        mockMvc.perform(get("/enderecos/70")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(EntityNotFoundException.class, result.getResolvedException()))
                .andExpect(jsonPath("$.errors", Matchers.contains("Endereco não Encontrado")));
    }

    @Test
    @Transactional
    public void constraintValidation() throws Exception {
        Endereco endereco = enderecoRepository.findById(3L).orElseThrow();
        endereco.setPessoa(null);
        enderecoRepository.save(endereco);
    }

    @Test
    public void testMethodArgumentNotValidException() throws Exception{
        PessoaDTO pessoaDTO = PessoaDTOFixtures.pessoaJimiHendrix();
        pessoaDTO.setCpf("123456");
        pessoaDTO.setTelefone("1234");

        mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaDTO)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()))
                .andExpect(jsonPath("$.errors", Matchers.hasItem("Campo telefone Deve ser entre 10 e 11 caracteres com DDD ")))
                .andExpect(jsonPath("$.errors", Matchers.hasItem("Campo cpf CPF Inválido! Verifique! ")));
    }

    @Test
    public void httpClientError() throws Exception {
        mockMvc.perform(get("/enderecos/cep/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(HttpClientErrorException.class, result.getResolvedException()))
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
                .andExpect(result -> assertInstanceOf(NoResourceFoundException.class, result.getResolvedException()))
                .andExpect(jsonPath("$.errors", Matchers.contains("pessoa Não corresponde a nenhum End Point!!!")));

        mockMvc.perform(get("/end")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(NoResourceFoundException.class, result.getResolvedException()))
                .andExpect(jsonPath("$.errors", Matchers.contains("end Não corresponde a nenhum End Point!!!")));
    }

    @Test
    public void illegalArgument() throws Exception {
        PessoaDTO pessoaDTO = new PessoaDTO().builder()
                .id(15L)
                .nome("Cpf Repetido")
                .cpf("45317125871")
                .telefone("1234567890")
                .dataNascimento(LocalDate.of(2000, 11, 22)).build();

        mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaDTO)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertInstanceOf(IllegalArgumentException.class, result.getResolvedException()))
                .andExpect(jsonPath("$.errors", Matchers.contains("CPF informado já existe no cadastro")));
    }
}