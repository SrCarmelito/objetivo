const urlSearchParams = new URLSearchParams(window.location.search);

const token = urlSearchParams.get("token");

document.querySelector("#submit-confirm-new-password").addEventListener("click", (e) => {
    e.preventDefault();
    console.log(token);
    let newPassword = {
        senha: document.querySelector("#password").value,
        senhaConfirmacao: document.querySelector("#passwordConfirm").value,
        token: token
    };

    confirmPassword(newPassword);
});

async function confirmPassword(newPassword) {

    const response = await fetch("http://localhost:8080/auth/confirm-reset-password",
    {
        body: JSON.stringify(newPassword),
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
                window.location.href = "/index.html"
            }) 
        }
        else {
            alert("Senha atualizada corretamente!");
            window.location.href = "/index.html"
        };     
    });

}
