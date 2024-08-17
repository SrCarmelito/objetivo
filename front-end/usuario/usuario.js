document.querySelector("#submit").addEventListener("click", (e) => {
    e.preventDefault();

    let user = {
        nome: document.querySelector("#nome").value,
        email: document.querySelector("#email").value,
        login: document.querySelector("#login").value,
        senha: document.querySelector("#password").value,
        senhaConfirmacao: document.querySelector("#passwordConfirm").value,
    };

    createUser(user);
});

async function createUser(user) {

    const response = await fetch("https://objetivo-liv5.onrender.com/auth/novo-usuario",
    {
        body: JSON.stringify(user),
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
    })
    .then((e) => {
        if (e.ok != true) {
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
            alert("Usuário Cadastrado corretamente!");
            window.location.href = "/index.html"
        };     
    });

}
