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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@DataJpaTest
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes=ObjetivoApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class RepositoryTest {

    @Autowired
    public PessoaRepository repository;

    @Test
    public void findByCpfContaining() {
        assertEquals(1, repository.findByCpfContaining("679").size());
    }

    @Test
    public void findByIdCpfNomeContaining() {
        assertEquals(1, repository.ops("11", null, null).size());
    }

}
