package com.objetivo.RepositoryTest;

import com.objetivo.ObjetivoApplication;
import com.objetivo.entities.Pessoa;
import com.objetivo.repository.PessoaRepository;
import jakarta.annotation.security.RunAs;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@DataJpaTest
@ActiveProfiles("test")
public class RepositoryTest {

    //@MockBean
    @Autowired
    public PessoaRepository repository;

//    @Test
//    public void findByCpfContaining() {
//
//        Pessoa pessoa = new Pessoa(1L, "Tigas e os 300", LocalDate.now(), "06455083903",
//                "4431233830", null, null);
//
//        when(this.repository.findByCpfContaining("064")).thenReturn(List.of(pessoa));
//
//        assertEquals(1, repository.findByCpfContaining("064").size());
//    }
//
//    @Test
//    public void findByIdCpfNomeContaining() {
//
//        Pessoa pessoa = new Pessoa(19676L, "Tigas e os 300", LocalDate.now(), "06455083903",
//                "4431233830", null, null);
//
//        List<Pessoa> pessoas = new ArrayList<>();
//        pessoas.add(pessoa);
//        Page<Pessoa> page = new PageImpl<>(pessoas);
//
//        when(this.repository.findByIdCpfNomeContaining("96", "839", "300", null)).thenReturn(page);
//        assertEquals(1, repository.findByIdCpfNomeContaining("96", "839", "300", null).getSize());
//
//        Page<Pessoa> pessoaEncontrada = this.repository.findByIdCpfNomeContaining("96", "839", "300", null);
//
//        assertTrue(pessoaEncontrada.stream().findFirst().get().getNome().equals("Tigas e os 300"));
//    }

    @Test
    void teste() {
        assertFalse(this.repository.findByIdOrCpfOrNome(36L, "1", "", Pageable.ofSize(1)).isEmpty());
    }

}
