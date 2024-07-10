package com.objetivo.Service;

import com.objetivo.converter.PessoaDTOConverter;
import com.objetivo.dto.PessoaDTO;
import com.objetivo.entities.Pessoa;
import com.objetivo.fixtures.PessoaFixtures;
import com.objetivo.service.PessoaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Sql(scripts = {
        "/sql/pessoa.sql"
})
@Transactional
public class PessoaServiceTest {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaDTOConverter pessoaDTOConverter;

    @Test
    public void count(){
        assertEquals(3L, pessoaService.count());
    }

    @Test
    public void findByIdCpfNomeContainingApenasNome() {
        assertTrue(pessoaService.findByIdCpfNomeContaining("", "Phill Collins", Pageable.ofSize(1)).getTotalElements() == 1);
    }

    @Test
    public void findByIdCpfNomeContainingCpfNome() {
        assertTrue(pessoaService.findByIdCpfNomeContaining("53", "Lenny", Pageable.ofSize(1)).getTotalElements() == 1);
    }

    @Test
    public void findByIdCpfNomeContainingCpfNomeApenasCpf() {
        assertTrue(pessoaService.findByIdCpfNomeContaining("5", "", Pageable.ofSize(1)).getTotalElements() == 2);
    }

    @Test
    public void obterPessoaorId(){
        assertTrue(pessoaService.obterPessoaPorId(2L).getTelefone().equals("45955324578"));
        assertTrue(pessoaService.obterPessoaPorId(3L).getCpf().equals("42166575595"));
    }

    @Test
    public void obterPessoaorIdThrows(){
        assertThrows(EntityNotFoundException.class, () -> pessoaService.obterPessoaPorId(72L));
    }

    @Test
    public void createPessoa() {
        pessoaService.createPessoa(pessoaDTOConverter.to(PessoaFixtures.pessoaJamesGosling()));
        assertTrue(pessoaService.findByIdCpfNomeContaining("", "James Gosling", Pageable.ofSize(1)).getTotalElements() == 1);
    }

    @Test
    public void createPessoaThrows() {
        Pessoa pessoaThrows = PessoaFixtures.pessoaLinusTorvalds();
        pessoaThrows.setCpf("42166575595");
        assertThrows(IllegalArgumentException.class, () -> pessoaService.createPessoa(pessoaDTOConverter.to(pessoaThrows)));
    }

    @Test
    public void editPessoa() {
        Pessoa pessoaAlterada = pessoaService.obterPessoaPorId(2L);
        pessoaAlterada.setNome("Phill Collins Alterado");
        pessoaAlterada.setTelefone("123456789");
        pessoaAlterada.setEnderecos(pessoaService.obterPessoaPorId(2L).getEnderecos());
        PessoaDTO pessoaToDto = pessoaDTOConverter.to(pessoaAlterada);
        pessoaService.editPessoa(2L, pessoaToDto);
        assertTrue(pessoaService.obterPessoaPorId(2L).getNome().equals("Phill Collins Alterado"));
        assertTrue(pessoaService.obterPessoaPorId(2L).getTelefone().equals("123456789"));
    }

    @Test
    public void deleteById() {
        pessoaService.deleteById(3L);
        pessoaService.deleteById(2L);
        assertTrue(pessoaService.count() == 1);
    }

}
