document.querySelector("#submit-new-password").addEventListener("click", (e) => {
    e.preventDefault();
    let email ={
        email: document.querySelector("#email").value
    }
    resetPassword(email);
});

async function resetPassword(email) {
    const response = await fetch("http://localhost:8080/auth/reset-password",
    {
        body: JSON.stringify(email),
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
            alert(`Foi enviado um link contendo as instruções de troca de senha para ${email.email}, verifique seu e-mail!`);
            window.location.href = "/index.html"
        };     
    });
}

document.querySelector("#submit-confirm-new-password").addEventListener("click", (e) => {
    e.preventDefault();
    let email ={
        email: document.querySelector("#email").value
    }
    resetPassword(email);
});