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
        EnderecoDTO dto = new EnderecoDTO();
        dto.setId(entity.getId());
        dto.setPessoa(entity.getPessoa());
        dto.setCep(entity.getCep());
        dto.setCidade(entity.getCidade());
        dto.setBairro(entity.getBairro());
        dto.setUf(entity.getUf());
        dto.setNumero(entity.getNumero());
        dto.setLogradouro(entity.getLogradouro());
        return dto;
    }
}
