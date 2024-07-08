package com.objetivo.converter;

import com.objetivo.dto.PessoaDTO;
import com.objetivo.entities.Pessoa;

public class PessoaDTOConverter implements DTOConverter<Pessoa, PessoaDTO> {

    @Override
    public Pessoa from(PessoaDTO dto, Pessoa entity) {
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setCpf(dto.getCpf());
        entity.setTelefone(dto.getTelefone());
        entity.setEnderecos(entity.getEnderecos());
        return entity;
    }

    @Override
    public PessoaDTO to(Pessoa entity) {
        PessoaDTO dto = new PessoaDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setCpf(entity.getCpf());
        dto.setDataNascimento(entity.getDataNascimento());
        dto.setTelefone(entity.getTelefone());
        dto.setEnderecos(entity.getEnderecos());
        return dto;
    }

}
