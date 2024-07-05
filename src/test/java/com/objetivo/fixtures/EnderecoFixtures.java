package com.objetivo.fixtures;

import com.objetivo.entities.Endereco;

public class EnderecoFixtures {

    public static Endereco endereco() {
        return new Endereco(730L, "75830112", "Rua das manções de Las Vegas", "6930", "HollyWood", "SP", "Centro", PessoaFixtures.pessoaJamesGosling());
    }

}
