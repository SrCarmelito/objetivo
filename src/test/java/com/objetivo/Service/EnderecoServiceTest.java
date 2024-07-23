package com.objetivo.Service;

import com.objetivo.converter.EnderecoDTOConverter;
import com.objetivo.dto.EnderecoDTO;
import com.objetivo.entities.Endereco;
import com.objetivo.fixtures.EnderecoDTOFixtures;
import com.objetivo.service.EnderecoService;
import com.objetivo.service.PessoaService;
import com.objetivo.utils.pesquisaporcep.EnderecoJson;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EnderecoServiceTest  {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private EnderecoDTOConverter enderecoDTOConverter;

    @Autowired
    private PessoaService pessoaService;

    @Test
    public void buscaCepRest(){
        EnderecoJson enderecoJson = enderecoService.buscaCepRest("13503260");
        assertTrue(enderecoJson.getLocalidade().equalsIgnoreCase("Rio Claro"));
        assertTrue(enderecoJson.getBairro().equalsIgnoreCase("Consolação"));
    }

    @Test
    public void findById() {
        Endereco endereco = enderecoService.findById(3L);
        assertTrue(endereco.getUf().equalsIgnoreCase("PR"));
        assertTrue(endereco.getLogradouro().equalsIgnoreCase("Av. Santos Dumont"));
        assertTrue(endereco.getPessoa().getNome().equalsIgnoreCase("Jon Yang"));
    }

    @Test
    public void findPessoaByEndereco(){
        assertTrue(enderecoService.findPessoaByEndereco(1L).equals(2L));
        assertTrue(enderecoService.findPessoaByEndereco(3L).equals(1L));
    }

    @Test
    public void novoEndereco(){
        Endereco novoEndereco = enderecoService.novoEndereco(2L, EnderecoDTOFixtures.enderecoDTOManhattan());

        assertTrue(novoEndereco.getLogradouro().equalsIgnoreCase("Rua das Azaléias"));
        assertTrue(novoEndereco.getCidade().equalsIgnoreCase("Manhattan"));

        assertTrue(novoEndereco.getPessoa().getCpf().equalsIgnoreCase("45317125871"));
        assertTrue(novoEndereco.getPessoa().getNome().equalsIgnoreCase("Lenny Kravitz"));
    }

    @Test
    public void save() {
        Endereco enderecoAtualizado =
            new Endereco().builder().id(3L).cep("888665321").logradouro("Av. Nildo Ribeiro")
                    .numero("60").cidade("Cianorte").uf("GO").bairro("Jardim Italia").build();

        enderecoAtualizado.setPessoa(pessoaService.obterPessoaPorId(3L));

        EnderecoDTO dto = enderecoDTOConverter.to(enderecoAtualizado);

        enderecoService.save(3L, dto);

        Endereco enderecoConferencia = enderecoService.findById(3L);

        assertTrue(enderecoConferencia.getLogradouro().equalsIgnoreCase("Av. Nildo Ribeiro"));
        assertTrue(enderecoConferencia.getUf().equalsIgnoreCase("GO"));
        assertTrue(enderecoConferencia.getPessoa().getNome().equalsIgnoreCase("Jon Yang"));
    }

    @Test
    public void deleteById(){
        enderecoService.deleteById(2L);
        enderecoService.deleteById(3L);
        assertThrows(EntityNotFoundException.class, () -> enderecoService.findById(2L));
        assertThrows(EntityNotFoundException.class, () -> enderecoService.findById(3L));
    }

}
