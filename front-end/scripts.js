const url = "https://objetivo-liv5.onrender.com/login";

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

    const response = await fetch("https://objetivo-liv5.onrender.com/login",
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
        redirectUser(token);
    };

}

async function redirectUser(token) {
    window.location.href = `/home/home.html?auth=${token}`;
};