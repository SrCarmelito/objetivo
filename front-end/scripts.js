const url = "http://localhost:8080/login";

document.querySelector("#submit").addEventListener("click", (e) => {
    e.preventDefault();

    let user = {
        login: document.querySelector("#login").value,
        password: document.querySelector("#password").value
    };

    //imaginar possíveis erros nesse ponto
    //validar campos nulos

    login(user);
});

async function login(user) {

    const response = await fetch("http://localhost:8080/login",
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