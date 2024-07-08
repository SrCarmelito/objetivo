package com.objetivo.converter;

import com.objetivo.dto.EnderecoDTO;
import com.objetivo.entities.Endereco;
import org.modelmapper.ModelMapper;

public class EnderecoDTOConverter implements DTOConverter<Endereco, EnderecoDTO> {

    @Override
    public Endereco from(EnderecoDTO dto, Endereco entity) {
        entity.setId(dto.getId());
        entity.setPessoa(entity.getPessoa());
        entity.setUf(dto.getUf());
        entity.setCidade(dto.getCidade());
        entity.setCep(dto.getCep());
        entity.setBairro(dto.getBairro());
        entity.setLogradouro(dto.getLogradouro());
        entity.setNumero(dto.getNumero());

        return entity;
    }

    @Override
    public EnderecoDTO to(Endereco entity) {
        throw new IllegalArgumentException("Not Implemented Yet");
    }
}
