const url = "http://localhost:8080/auth/login";

document.querySelector("#submit").addEventListener("click", (e) => {
    e.preventDefault();

    let user = {
        login: document.querySelector("#login").value,
        senha: document.querySelector("#password").value
    };

    //imaginar possíveis erros nesse ponto
    //validar campos nulos

    login(user);
});

async function login(user) {

    const response = await fetch("http://localhost:8080/auth/login",
    {
        body: JSON.stringify(user),
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
    });

    if(response.ok != true) {
        alert("Usuário ou senha Inválidos, tente novamente!");
    } else {
        const token = await response.text();
        window.sessionStorage.setItem("token", token);
        window.location.href = "/home/home.html";
    };

}
