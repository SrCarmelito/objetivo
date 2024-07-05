const pessoaContainer = document.querySelector("#pessoas-container");

//let url = `http://localhost:8080/pessoas?sort=nome,asc`;
let url = `http://localhost:8080/pessoas?sort=nome,asc`;

const urlSearchParams = new URLSearchParams(window.location.search);
const pessoaId = urlSearchParams.get("id");

let filtroValorCpf = "";
let filtroValorNome = "";
let filtroValorId = "";

let page = 0;
let size = 20;

document.querySelector("#executa-pesquisa").addEventListener("click", (e) => {
    e.preventDefault();

    filtroValorCpf = document.querySelector("#input-filter-cpf").value;
    filtroValorNome = document.querySelector("#input-filter-nome").value;
    //filtroValorId = document.querySelector("#input-filter-id").value;
    document.querySelector("#pessoas-container-dados").remove();

    getAll(filtroValorCpf, filtroValorNome);
});

document.querySelector("#filter").addEventListener("keypress", (e) => {
    if (e.key === 'Enter') {
        document.querySelector("#executa-pesquisa").click();
    };
});

getAll();

async function getAll() {
        
    //url = `http://localhost:8080/pessoas?cpf=${filtroValorCpf}&nome=${filtroValorNome}&page=${page}&size=${size}&sort=nome,asc`;
    url = `http://localhost:8080/pessoas?cpf=${filtroValorCpf}&nome=${filtroValorNome}&page=${page}&size=${size}&sort=nome,asc`;

    const response = await fetch(url);
    const data = await response.json();

    //const responseCount = await fetch("http://localhost:8080/pessoas/count");
    const responseCount = await fetch("http://localhost:8080/pessoas/count");
    const dataCount = await responseCount.json();
    const lbCount = document.querySelector("#lb-count");
    lbCount.textContent = `${dataCount} Pessoas Cadastradas`;
    const pessoaContainerDados = document.createElement("div");
    pessoaContainerDados.setAttribute("id", "pessoas-container-dados");
    pessoaContainer.appendChild(pessoaContainerDados);

    page = data.pageable.pageNumber
    pageSize = data.pageable.pageSize; 
    totalPages = data.totalPages;

    criaBotoes(page, totalPages);

    if(data.content <= 0) {
        const semResultados = document.createElement("p");
        semResultados.innerText = "No Data to Show, Try Again!";
        pessoaContainerDados.appendChild(semResultados);
        semResultados.style.textAlign = "center";
        semResultados.style.fontSize = "40px";
        if(confirm("No Data to Show, Try Again!")){
            document.querySelector("#input-filter-cpf").value = '';
            document.querySelector("#input-filter-nome").value = '';
           // document.querySelector("#input-filter-id").value = '';
        } 
    }

    data.content.map((pessoa) => {

        const div = document.createElement("div");
        const name = document.createElement("h2");
        const birthDate = document.createElement("p");
        const cpf = document.createElement("p");
        const phone = document.createElement("p");
        const age = document.createElement("p");
        const link = document.createElement("a");
        const trash = document.createElement("a");

        name.innerText = `${pessoa.id} - ${pessoa.nome}`;
        name.setAttribute("id", "meuh2");
  
        birthDate.innerText = `Data de Nascimento: ${dataFormatada(new Date(pessoa.dataNascimento))}`;
 
        cpf.innerText = `CPF: ${formataCpf(pessoa.cpf)}`;
        phone.innerText = `Telefone: ${phoneMask(pessoa.telefone)}`;
        age.innerText = `Idade: ${pessoa.idade}`;
        link.setAttribute("class", "fa-solid fa-pen-to-square fa-1x");
        link.setAttribute("href", `/EditPessoa/edit.html?id=${pessoa.id}`);
        trash.setAttribute("class", "fa-solid fa-trash-can fa 1x");
        div.setAttribute("id", "dados-pessoas");
        
        div.appendChild(name);
        div.appendChild(birthDate);
        div.appendChild(cpf);
        div.appendChild(phone);
        div.appendChild(age);
        name.appendChild(link);
        name.appendChild(trash);

        trash.value = pessoa.id;

        trash.addEventListener("click", (e) => {
            e.preventDefault();
            const pessoaExcluida = pessoa.id;
            if (confirm(`Confirma a exclusão da Pessoa:\n ${pessoa.id} - ${pessoa.nome}?`) == true) {
                deletePessoa(pessoaExcluida);
            };
        });
      
        pessoaContainerDados.appendChild(div);
       
        const enderecos = pessoa.enderecos;

        enderecos.map((e) => {

            const tigas = document.createElement("div");
            const logradouro = document.createElement("p");
            const numero = document.createElement("p");

            logradouro.innerText = 
                `${e.logradouro}, Nrº ${e.numero}, Bairro: ${e.bairro} - CEP: ${e.cep} - ${e.cidade}/${e.uf}`;
            numero.innerText = e.numero;

            tigas.appendChild(logradouro);
            logradouro.setAttribute("id", "log");
            
            div.appendChild(tigas);
        });
        
    });

};

async function deletePessoa(pessoaExcluida) {

    //url = "http://localhost:8080/pessoas";
    url = "http://localhost:8080/pessoas";

    const response = await fetch(`${url}/${pessoaExcluida}`,
    {
        body: JSON.stringify(pessoaExcluida),
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
    });

    if(response.status != 200) {
        alert("Ops... Não foi possível Excluir a Pessoa!");
    } else {
        alert("Cadastro Excluído com Sucesso!");
        const modal = document.querySelector('.modal');
        modal.style.display = 'none';
        location.reload()
    };

};

////////// Parte do Modal //////////////

const btnNovo = document.querySelector("#btn-novo");

btnNovo.addEventListener("click", (e) => {
    e.preventDefault();
    switchModal();
});

// alterna para o modal ficar ativo ou não
const switchModal = () => {

    const modal = document.querySelector(".modal");
    const actualStyle = modal.style.display;

    if (actualStyle == 'block') {
        modal.style.display = 'none';

        // destroy os elementos do modal ao fechar o modal
        const modalContent = document.querySelector(".content");
        const iNome = document.querySelector("#nome-modal");
        const iDatanascimento = document.querySelector("#nome-modal");
        const iCpf = document.querySelector("#nome-modal");
        const iTelefone = document.querySelector("#nome-modal");
        modalContent.remove();
        iNome.remove();
        iDatanascimento.remove();
        iCpf.remove();
        iTelefone.remove();
    } else {
        modal.style.display = 'block';

        // cria os elementos do modal ao clicar em Nova Pessoa
        const modalContent = document.createElement("div");
        const modalTitle = document.createElement("h2");
        const iNome = document.createElement("input");
        const iDatanascimento = document.createElement("input");
        const iCpf = document.createElement("input");
        const iTelefone = document.createElement("input");
        const lbNome = document.createElement("label");
        const lbDataNascimento = document.createElement("label");
        const lbCpf = document.createElement("label");
        const lbTelefone = document.createElement("label");

        const btnConfirmar = document.createElement("button");
  
        modalTitle.innerText = "Insira os dados da Nova Pessoa";
        modalContent.setAttribute("class", "content");
        iNome.setAttribute("id", "nome-modal");
        iDatanascimento.setAttribute("id", "data-nascimento-modal");
        iDatanascimento.setAttribute("type", "date");
        iCpf.setAttribute("id", "cpf-modal");
        iCpf.setAttribute("type", "tel");
        iCpf.setAttribute("maxlength", "14");
        iCpf.setAttribute("onkeyup", "handleCpf(event)");
        iTelefone.setAttribute("id", "telefone-modal");
        iTelefone.setAttribute("type", "tel");
        iTelefone.setAttribute("maxlength", "15");
        iTelefone.setAttribute("onkeyup", "handlePhone(event)");

        btnConfirmar.innerText = "Inserir";
        btnConfirmar.setAttribute("id", "btn-confirmar");
        
        lbNome.innerText = "Nome";
        lbDataNascimento.innerText = "Data de Nascimento";
        lbCpf.innerText = "CPF";
        lbTelefone.innerText = "Telefone";
        
        modalContent.appendChild(modalTitle);
        
        modalContent.appendChild(lbNome);
        modalContent.appendChild(iNome);
        modalContent.appendChild(lbDataNascimento);
        modalContent.appendChild(iDatanascimento);
        modalContent.appendChild(lbCpf);
        modalContent.appendChild(iCpf);
        modalContent.appendChild(lbTelefone);
        modalContent.appendChild(iTelefone);

        modal.appendChild(modalContent);       
        
        modalContent.append(btnConfirmar);

        btnConfirmar.addEventListener("click", (e) => {
            e.preventDefault();

            const dataNascInput = document.querySelector("#data-nascimento-modal");
            const cpfInput = document.querySelector("#cpf-modal");
            const telefoneInput = document.querySelector("#telefone-modal");
            const nomeInput = document.querySelector("#nome-modal");

            let telefoneNumerico = "";
            for (let i = 0; i < telefoneInput.value.length; i++) {
                if ((telefoneInput.value[i] !== '(') && (telefoneInput.value[i] !== ')') &&
                (telefoneInput.value[i] !== ' ') && (telefoneInput.value[i] !== '-')) {
                    telefoneNumerico = telefoneNumerico + telefoneInput.value[i];
                };
            };

            let cpfNumerico = "";
            for (let i = 0; i < cpfInput.value.length; i++) {
                if ((cpfInput.value[i] !== '.') && (cpfInput.value[i] !== '-')) {
                    cpfNumerico = cpfNumerico + cpfInput.value[i];
                };
            };

            let novaPessoa = {
                nome: nomeInput.value,
                dataNascimento: dataNascInput.value,
                cpf: cpfNumerico,            
                telefone: telefoneNumerico
            };

            postPessoa(novaPessoa);
        });
    };
}


// fecha o modal clicando fora se ele estiver aberto
window.onclick = function(event) {
    const modal = document.querySelector('.modal');
  if (event.target == modal) {
    switchModal();
  }
}

// função para atualizar a pessoa
async function postPessoa(novaPessoa) {

    //await fetch("http://localhost:8080/pessoas",
    await fetch("http://localhost:8080/pessoas",
        {
            body: JSON.stringify(novaPessoa),
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
                e.json().then((e) => {
                    alert("Pessoa Cadastrada corretamente!");
                    const modal = document.querySelector('.modal');
                    modal.style.display = 'none';
                    redirectPessoa(e.id);
                });
            };     
        });
};

async function redirectPessoa(novaPessoa) {
    const response = await fetch(`http://localhost:8080/pessoas?id=${novaPessoa}`);
    const pessoa = await response.json();
    window.location.href = `/EditPessoa/edit.html?id=${novaPessoa}`;
};

///////////////////////Paginação /////////////////////////
document.querySelector("#cbPageSize").addEventListener("change", (e => {
    size = cbPageSize.value
    document.querySelector("#pessoas-container-dados").remove();
    document.querySelector("#paginas").remove();

    getAll(page, size);
    criaBotoes(page, totalPages);
}));

document.querySelector("#next").addEventListener("click", (e) => {
    if (page +1 < totalPages) {
        page++;
        document.querySelector("#pessoas-container-dados").remove();
        getAll(page, size);
        criaBotoes(page, totalPages);
    }; 
});

document.querySelector("#previous").addEventListener("click", (e) => {
    if (page > 0) {
        page--;
        document.querySelector("#pessoas-container-dados").remove();
        getAll(page, size);
        criaBotoes(page, totalPages);
    };
});

document.querySelector("#last-page").addEventListener("click", (e) => {
    page = totalPages -1;
    document.querySelector("#pessoas-container-dados").remove();
    getAll(page, size);
    criaBotoes(page, totalPages);
});

document.querySelector("#first-page").addEventListener("click", (e) => {
    page = 0;
    document.querySelector("#pessoas-container-dados").remove();
    getAll(page, size);
    criaBotoes(page, totalPages);
});

document.addEventListener('DOMContentLoaded', function() {
    document.addEventListener('click', (e) => {
        if(e.target.classList.contains('bt-paginacao')){
            page = e.target.innerText -1;
            document.querySelector("#pessoas-container-dados").remove();
            getAll(page, size);
            criaBotoes(page, totalPages);
        }
    });
});

function criaBotoes(page, totalPages) {

    if (document.querySelector("#paginas")) {
        document.querySelector("#paginas").remove();
    };

    const fivePages = document.querySelector("#five-pages");
    const divPages = document.createElement("div");
    divPages.setAttribute("id", "paginas");
    fivePages.appendChild(divPages);
    
    if((totalPages - page) > 5) {
        for (let i = 1; i < 6; i++) {
            let bt = document.createElement("button");
            bt.setAttribute("id", `page-${i}`);
            bt.setAttribute("class", "bt-paginacao");
            bt.innerText = page + i;

            divPages.append(bt);
            if (bt.innerText == page + 1) {
                bt.style.backgroundColor = "limegreen";
            }
        }
    } else if(totalPages > 4) {
        
        for (let i = 3; i > -2; i--) {
            let bt = document.createElement("button");
            bt.setAttribute("id", `page-${i}`);
            bt.setAttribute("class", "bt-paginacao");
            bt.innerText = page - i;
                        
            divPages.append(bt);
            if (bt.innerText == page + 1) {
                bt.style.backgroundColor = "limegreen";
            }
        };
    } else {
        for (let i = 0; i < totalPages; i++) {
            let bt = document.createElement("button");
            bt.setAttribute("id", `page-${i}`);
            bt.setAttribute("class", "bt-paginacao");
            bt.innerText = page + i;

            divPages.append(bt);
            if (bt.innerText == page + 1) {
                bt.style.backgroundColor = "limegreen";
            } 
        }
    }
};