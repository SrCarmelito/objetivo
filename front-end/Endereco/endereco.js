const url = "objetivo.onrender.com/enderecos";

const urlSearchParams = new URLSearchParams(window.location.search);
const enderecoId = urlSearchParams.get("id");

const enderecoContainer = document.querySelector("#endereco-container");

async function getEndereco(enderecoId) {

    const response = await fetch(`${url}/${enderecoId}`);

    const endereco = await response.json();

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

    iId.readOnly = true;
    iLogradouro.setAttribute("id", "iLogradouro");
    iNumero.setAttribute("id", "iNumero");
    iCep.setAttribute("id", "iCep");
    iCidade.setAttribute("id", "iCidade");
    iUf.setAttribute("id", "iUf");
    iId.setAttribute("id", "iId");
    iBairro.setAttribute("id", "iBairro");

    lbLogradouro.innerText = "Logradouro";
    lbNumero.innerText = "Nrº";
    lbCep.innerText = "CEP:";
    lbCidade.innerText = "Cidade: ";
    lbUf.innerText = "UF";
    lbId.innerText = "ID";
    lbBairro.innerText = "Bairro: ";
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

    iId.value = endereco.id;
    iLogradouro.value = endereco.logradouro;
    iNumero.value = endereco.numero;
    iCep.value = endereco.cep;
    iCidade.value = endereco.cidade;
    iUf.value = endereco.uf;
    iBairro.value = endereco.bairro;

    enderecoContainer.appendChild(divEndEdit);

};

const btnCancelar = document.querySelector("#btn-cancelar");

btnCancelar.addEventListener("click", (e) => {
    if (confirm("Deseja relamente Cancelar Esta Edição?") == true) {
        history.back();
    }; 
});

const btnConfirmar = document.querySelector("#btn-confirmar");

btnConfirmar.addEventListener("click", (e) => {
    e.preventDefault();
    preparaEnderecoAtualizado(enderecoId);
});

async function preparaEnderecoAtualizado(enderecoId) {
    
    const iLogradouro = document.querySelector("#iLogradouro");
    const iNumero = document.querySelector("#iNumero");
    const iCep = document.querySelector("#iCep");
    const iCidade = document.querySelector("#iCidade");
    const iUf = document.querySelector("#iUf");
    const iIdEnderec = document.querySelector("#iId");
    const iBairro = document.querySelector("#iBairro");

    let enderecoAtualizado = {
        id: iIdEnderec.value,
        cep: iCep.value,
        logradouro: iLogradouro.value,
        numero: iNumero.value,
        cidade: iCidade.value,
        uf: iUf.value,
        bairro: iBairro.value
    };

    putEndereco(enderecoAtualizado);
};

async function putEndereco(enderecoAtualizado) {

    await fetch(`${url}/${enderecoId}`,
    {
        body: JSON.stringify(enderecoAtualizado),
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
                    mensagens = mensagens + "\n" + e.defaultMessage;
                })

                confirm(mensagens);
            }) 
        }
        else {
            alert("Endereço Atualizado corretamente!");

            getPessoaId(enderecoId);
            async function getPessoaId(enderecoId) {
                const response = await fetch(`${url}/end/${enderecoId}`);
                const pessoaId = await response.json();
                window.location.href = `/EditPessoa/edit.html?id=${pessoaId}`;
            }; 
        }
    });
};

getEndereco(enderecoId);