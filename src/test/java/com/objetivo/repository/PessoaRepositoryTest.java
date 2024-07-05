package com.objetivo.repository;

import com.objetivo.entities.Pessoa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = {
        "/sql/pessoa.sql"
})
public class PessoaRepositoryTest {

    @Autowired
    public PessoaRepository repository;

    @Test
    void findByCpfContainingAndNomeIgnoreCaseContaining() {
        Page<Pessoa> pessoaFiltraSemFiltros = repository.findByCpfContainingAndNomeIgnoreCaseContaining("", "", Pageable.ofSize(1));
        assertEquals(3, pessoaFiltraSemFiltros.getTotalElements());

        Page<Pessoa> pessoaFiltradaApenasPorCpf = repository.findByCpfContainingAndNomeIgnoreCaseContaining("5", "", Pageable.ofSize(1));
        assertEquals(2, pessoaFiltradaApenasPorCpf.getTotalElements());

        Page<Pessoa> pessoaFiltradaPorCpfEPorParteDoNomeMinusculo = repository.findByCpfContainingAndNomeIgnoreCaseContaining("5", "lenny", Pageable.ofSize(1));
        assertEquals(1, pessoaFiltradaPorCpfEPorParteDoNomeMinusculo.getTotalElements());

        Page<Pessoa> pessoaFiltradaPorCpfEParteDoNomeMinusculo = repository.findByCpfContainingAndNomeIgnoreCaseContaining("53", "Lenny", Pageable.ofSize(1));
        assertEquals(1, pessoaFiltradaPorCpfEParteDoNomeMinusculo.getTotalElements());

        Page<Pessoa> pessoaFiltradaPorParteDoNomeMinusculo = repository.findByCpfContainingAndNomeIgnoreCaseContaining("", "collins", Pageable.ofSize(1));
        assertEquals(1, pessoaFiltradaPorParteDoNomeMinusculo .getTotalElements());

        Page<Pessoa> pessoaFiltradaPorParteDoNomeMaiusculo = repository.findByCpfContainingAndNomeIgnoreCaseContaining("", "Phill Collins", Pageable.ofSize(1));
        assertEquals(1, pessoaFiltradaPorParteDoNomeMaiusculo .getTotalElements());

    }

}