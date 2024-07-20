package com.objetivo.converter;

import com.objetivo.dto.PessoaDTO;
import com.objetivo.entities.Endereco;
import com.objetivo.entities.Pessoa;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PessoaDTOConverter implements DTOConverter<Pessoa, PessoaDTO> {

    @Override
    public Pessoa from(PessoaDTO dto, Pessoa entity) {
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setCpf(dto.getCpf());
        entity.setTelefone(dto.getTelefone());

        if(dto.getEnderecos() != null) {
            dto.getEnderecos().forEach(enderecoDTO ->
                entity.getEnderecos().add(new EnderecoDTOConverter().from(enderecoDTO, new Endereco()))
            );
        }

        return entity;
    }

    @Override
    public PessoaDTO to(Pessoa entity) {
        PessoaDTO dto  = new PessoaDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setCpf(entity.getCpf());
        dto.setDataNascimento(entity.getDataNascimento());
        dto.setTelefone(entity.getTelefone());

        if(entity.getEnderecos() != null) {
            entity.getEnderecos().forEach(endereco ->
                dto.getEnderecos().add(new EnderecoDTOConverter().to(endereco))
            );
        }

        return dto;
    }

}
