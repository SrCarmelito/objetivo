const url = "http://localhost:8080/pessoas";
//const url = "http://localhost:8080/pessoas";
const urlEndereco = "http://localhost:8080/enderecos";

const urlSearchParams = new URLSearchParams(window.location.search);
const pessoaId = urlSearchParams.get("id");

const cadPessoa = document.querySelector("#pessoa");

async function getPessoa(id) {

    const response = await fetch(`${url}/${id}`);
    const pessoa = await response.json();

    const div = document.createElement("div");
    const idPessoa = document.createElement("input");
    const name = document.createElement("input");
    const birthDate = document.createElement("input");
    const cpf = document.createElement("input");
    const phone = document.createElement("input");
    const age = document.createElement("input");
    const lbBirth = document.createElement("label");
    const lbcpf = document.createElement("label");
    const lbtelefone = document.createElement("label");
    const lbidade = document.createElement("label");
    const lbname = document.createElement("label");
    const lbid = document.createElement("label");

    div.setAttribute("id", "dados-pessoais");
    lbid.textContent = "Id:";
    idPessoa.value = pessoa.id;
    lbname.textContent = "Nome:";
    name.value = pessoa.nome;
    name.setAttribute("id", "meuh2");
    birthDate.value = pessoa.dataNascimento;
    cpf.value = formataCpf(pessoa.cpf);
    phone.value = phoneMask(pessoa.telefone);
    age.value = pessoa.idade;

    lbBirth.textContent = "Data Nascimento";
    lbcpf.textContent = "CPF:";
    lbtelefone.textContent = "Telefone:";
    lbidade.textContent = "Idade:";

    idPessoa.readOnly = true;
    birthDate.setAttribute("name", "dataNascimento");
    birthDate.setAttribute("type", "date");
    cpf.setAttribute("name", "cpf");
    phone.setAttribute("name", "telefone");
    age.setAttribute("name", "idade");
    age.readOnly = true;
    idPessoa.setAttribute("name", "id");
    name.setAttribute("name", "nome");
    birthDate.setAttribute("id", "birth");
    cpf.setAttribute("id", "cpf");
    cpf.setAttribute("type", "tel");
    cpf.setAttribute("maxlength", "14");
    cpf.setAttribute("onkeyup", "handleCpf(event)");
    phone.setAttribute("id", "telefone");
    phone.setAttribute("type", "tel");
    phone.setAttribute("maxlength", "15");
    phone.setAttribute("onkeyup", "handlePhone(event)");
    age.setAttribute("id", "idade");
    idPessoa.setAttribute("id", "id");
    name.setAttribute("id", "nome");

    div.appendChild(lbid);
    div.appendChild(idPessoa);
    div.appendChild(lbname);
    div.appendChild(name);
    div.appendChild(lbBirth);
    div.appendChild(birthDate);
    div.appendChild(lbcpf);
    div.appendChild(cpf);
    div.appendChild(lbtelefone);;
    div.appendChild(phone);
    div.appendChild(lbidade);
    div.appendChild(age);

    cadPessoa.appendChild(div);

    const divEnderec = document.createElement("div");
    divEnderec.setAttribute("id", "enderecos");

    cadPessoa.appendChild(divEnderec);

    const btnNovoEndereco = document.createElement("button");
    const icoConfirmar = document.createElement("i");
    const textDivEnderec = document.createElement("h3");

    textDivEnderec.textContent = "Endereços de " + pessoa.nome ;
    textDivEnderec.setAttribute("id", "texto-div-enderec");
    icoConfirmar.setAttribute("id", "icon-new");
    icoConfirmar.setAttribute("class", "fa-solid fa-circle-plus fa-2x");
    btnNovoEndereco.setAttribute("id", "new-enderec");

    btnNovoEndereco
    divEnderec.appendChild(textDivEnderec);
    btnNovoEndereco.append(icoConfirmar);
    textDivEnderec.append(btnNovoEndereco);

    const enderecos = pessoa.enderecos;

    enderecos.map((e) => {

        const divEndEdit = document.createElement("div");
        const lbLogradouro = document.createElement("label");
        const lbNumero = document.createElement("label");
        const lbCep = document.createElement("label");
        const lbCidade = document.createElement("label");
        const lbUf = document.createElement("label");
        const lbId = document.createElement("label");
        const lbBairro = document.createElement("label");
        const iLogradouro = document.createElement("input");
        const iNumero = document.createElement("input");
        const iCep = document.createElement("input");
        const iCidade = document.createElement("input");
        const iUf = document.createElement("input");
        const iId = document.createElement("input");
        const iBairro = document.createElement("input");
        const btnEditEnd = document.createElement("a");
        const btnApagaEnd = document.createElement("a");
        const iconEditEnd = document.createElement("i");
        const iconApagaEnd = document.createElement("i");

        iLogradouro.setAttribute("id", "iLogradouro");
        iNumero.setAttribute("id", "iNumero");
        iCep.setAttribute("id", "iCep");
        iCidade.setAttribute("id", "iCidade");
        iUf.setAttribute("id", "iUf");
        iId.setAttribute("id", "iId");
        iBairro.setAttribute("id", "iBairro");
        btnEditEnd.setAttribute("id", "btn-edit-end");
        btnApagaEnd.setAttribute("id", "btn-apaga-end");
        iconEditEnd.setAttribute("class", "fa-solid fa-pen-to-square fa-1x");
        iconApagaEnd.setAttribute("class", "fa-solid fa-trash-can fa-1x");
        iconEditEnd.setAttribute("id", "ico-edit-end");
        btnEditEnd.setAttribute("href", `/Endereco/endereco.html?id=${e.id}`);

        btnApagaEnd.value = e.id;

        btnApagaEnd.addEventListener("click", (e) => {
            e.preventDefault();
            const enderecoExcluido = btnApagaEnd.value;
            if (confirm(`Confirma a exclusão do Endereço:\n ${enderecoExcluido}?`) == true) {
                deleteEndereco(enderecoExcluido);
            };
        });

        iId.readOnly = true;
        iLogradouro.readOnly = true;
        iNumero.readOnly = true;
        iCep.readOnly = true;
        iUf.readOnly = true;
        iBairro.readOnly = true;
        iCidade.readOnly = true;
        lbLogradouro.innerText = "Logradouro";
        lbNumero.innerText = "Nrº";
        lbBairro.innerText = "Bairro";
        lbCep.innerText = "CEP:";
        lbCidade.innerText = "Cidade: ";
        lbUf.innerText = "UF";
        lbId.innerText = "ID";
        divEndEdit.setAttribute("id", "endereco-pessoa");

        divEndEdit.appendChild(lbId);
        divEndEdit.appendChild(iId);
        divEndEdit.appendChild(lbLogradouro);
        divEndEdit.appendChild(iLogradouro);
        divEndEdit.appendChild(lbNumero);
        divEndEdit.appendChild(iNumero);
        divEndEdit.appendChild(lbBairro);
        divEndEdit.appendChild(iBairro);
        divEndEdit.appendChild(lbCep);
        divEndEdit.appendChild(iCep);
        divEndEdit.appendChild(lbCidade);
        divEndEdit.appendChild(iCidade);
        divEndEdit.appendChild(lbUf);
        divEndEdit.appendChild(iUf);
        btnEditEnd.appendChild(iconEditEnd);
        btnApagaEnd.appendChild(iconApagaEnd);
        divEndEdit.appendChild(btnEditEnd);
        divEndEdit.appendChild(btnApagaEnd);

        iId.value = e.id;
        iLogradouro.value = e.logradouro;
        iNumero.value = e.numero;
        iCep.value = e.cep;
        iCidade.value = e.cidade;
        iUf.value = e.uf;
        iBairro.value = e.bairro;

        divEnderec.appendChild(divEndEdit);
        
    });
    
    btnNovoEndereco.addEventListener("click", (e) => {
        e.preventDefault();
        const divEnderec = document.querySelector("#enderecos");
        const countEditEndereco = document.querySelector("#edit-endereco");

        if (countEditEndereco === null) {
            const divEndEdit = document.createElement("div");
            const lbLogradouro = document.createElement("label");
            const lbNumero = document.createElement("label");
            const lbCep = document.createElement("label");
            const lbCidade = document.createElement("label");
            const lbUf = document.createElement("label");
            const lbBairro = document.createElement("label");
            const iLogradouro = document.createElement("input");
            const iNumero = document.createElement("input");
            const iCep = document.createElement("input");
            const iCidade = document.createElement("input");
            const iUf = document.createElement("input");
            const iBairro = document.createElement("input");
            const gravaEndereco = document.createElement("button");

            divEndEdit.setAttribute("id", "edit-endereco");
            iLogradouro.setAttribute("id", "iLogradouroNew");
            iNumero.setAttribute("id", "iNumeroNew");
            iCep.setAttribute("id", "iCepNew");
            iCidade.setAttribute("id", "iCidadeNew");
            iUf.setAttribute("id", "iUfNew");
            iBairro.setAttribute("id", "iBairroNew");
            gravaEndereco.setAttribute("id", "grava-endereco");
            gravaEndereco.innerText = 'Gravar Endereço';

            lbLogradouro.innerText = "Logradouro";
            lbNumero.innerText = "Nrº";
            lbCep.innerText = "CEP:";
            lbCidade.innerText = "Cidade: ";
            lbUf.innerText = "UF";
            lbBairro.innerText = "Bairro:";

            divEndEdit.appendChild(lbCep);
            divEndEdit.appendChild(iCep);
            divEndEdit.appendChild(lbCidade);
            divEndEdit.appendChild(iCidade);
            divEndEdit.appendChild(lbUf);
            divEndEdit.appendChild(iUf);
            divEndEdit.appendChild(lbBairro);
            divEndEdit.appendChild(iBairro);
            divEndEdit.appendChild(lbLogradouro);
            divEndEdit.appendChild(iLogradouro);
            divEndEdit.appendChild(lbNumero);
            divEndEdit.appendChild(iNumero);
            
            divEnderec.appendChild(divEndEdit);

            divEndEdit.append(gravaEndereco);

            iCep.addEventListener("blur", (e) => {
                const cepBusca = iCep.value;
                getDadosPorCep(cepBusca);
                iNumero.focus();
            });

            gravaEndereco.addEventListener("click", (e) => {
                e.preventDefault();
                
                const cep = document.querySelector("#iCepNew").value;
                const logradouro = document.querySelector("#iLogradouroNew").value;
                const numero = document.querySelector("#iNumeroNew").value;
                const cidade = document.querySelector("#iCidadeNew").value;
                const uf = document.querySelector("#iUfNew").value;
                const bairro = document.querySelector("#iBairroNew").value;
    
                let novoEndereco = {
                    cep: cep,
                    logradouro: logradouro,
                    numero: numero,
                    cidade: cidade,
                    uf: uf,
                    bairro: bairro
                };
    
                postEndereco(novoEndereco);
            });  
        };
    });  
};

getPessoa(pessoaId);

async function getDadosPorCep(cepBusca) {
    const response = await fetch(`http://localhost:8080/enderecos/cep/${cepBusca}`);
    //const response = await fetch(`http://localhost:8080/enderecos/cep/${cepBusca}`);
    const cep = await response.json();

    document.querySelector("#iCidadeNew").value = cep.localidade;
    document.querySelector("#iUfNew").value = cep.uf;
    document.querySelector("#iLogradouroNew").value = cep.logradouro;
    document.querySelector("#iBairroNew").value = cep.bairro;
};

const send = document.querySelector("#submit");
send.addEventListener("click", (e) => {
    e.preventDefault();

    preparaPessoaAtualizada();

});

async function preparaPessoaAtualizada() {

    const dataNascInput = document.querySelector("#birth");
    const cpfInput = document.querySelector("#cpf");
    const telefoneInput = document.querySelector("#telefone");
    const nomeInput = document.querySelector("#nome");
    const idInput = document.querySelector("#id");
 
    const telefone = await telefoneNumerico(telefoneInput.value);
    const cpf = await cpfNumerico(cpfInput.value);

    let pessoaAtualizada = {
        id: idInput.value,
        nome: nomeInput.value,
        dataNascimento: dataNascInput.value,
        cpf: cpf,            
        telefone: telefone
    };

    putPessoa(pessoaAtualizada);
};

async function putPessoa(pessoaAtualizada) {

    minhaPessoa = JSON.stringify(pessoaAtualizada);

    await fetch(`${url}/${pessoaId}`,
    {
        body: minhaPessoa,
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
    })
    .then((e) => {
        if (e.status != 200) {
            e.json().then((e) => {
                const erros = e.errors;
                let mensagens = "";

                erros.map((e) => {
                    mensagens = mensagens + "\n" + e;
                })

                confirm(mensagens);
            }) 
        }
        else {
            alert("Pessoa Atualizada corretamente!");
            location.reload()
        }
    });
};

async function postEndereco(novoEndereco) {

    const url = "http://localhost:8080/enderecos";

    await fetch(`${url}/${pessoaId}`,
    {
        body: JSON.stringify(novoEndereco),
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
    })
    .then((e) => {
        if (e.status != 200) {
            e.json().then((e) => {
                const erros = e.errors;
                let mensagens = "";

                erros.map((e) => {
                    mensagens = mensagens + "\n" + e;
                })

                confirm(mensagens);
            }) 
        }
        else {
            alert("Endereço Cadastrado corretamente!");
            const modal = document.querySelector('.modal');
            modal.style.display = 'none';
            location.reload()
        }
    });
};

async function deleteEndereco(enderecoExcluido) {

    const response = await fetch(`${urlEndereco}/${enderecoExcluido}`,
    {
        body: JSON.stringify(enderecoExcluido),
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
    });

    if(response.status != 200) {
        alert("Ops... Não foi possível Excluir o Endereço!");
    } else {
        alert("Cadastro Excluído com Sucesso!");
        const modal = document.querySelector('.modal');
        modal.style.display = 'none';
        location.reload()
    };

};

