const handlePhone = (event) => {
    let input = event.target
    input.value = phoneMask(input.value)
};
  
const phoneMask = (value) => {
    if (!value) return ""
    value = value.replace(/\D/g,'')
    value = value.replace(/(\d{2})(\d)/,"($1) $2")
    value = value.replace(/(\d)(\d{4})$/,"$1-$2")
    return value
};

const handleCpf = (event) => {
    let input = event.target
    input.value = formataCpf(input.value)
};

const formataCpf = (v) => {
    v=v.replace(/\D/g,"")                    //Remove tudo o que não é dígito
    v=v.replace(/(\d{3})(\d)/,"$1.$2")       //Coloca um ponto entre o terceiro e o quarto dígitos
    v=v.replace(/(\d{3})(\d)/,"$1.$2")       //Coloca um ponto entre o terceiro e o quarto dígitos
                                             //de novo (para o segundo bloco de números)
    v=v.replace(/(\d{3})(\d{1,2})$/,"$1-$2") //Coloca um hífen entre o terceiro e o quarto dígitos
    return v;
};

async function telefoneNumerico(telefone) {
    let telefoneNumerico = "";
    for (let i = 0; i < telefone.length; i++) {
        if ((telefone[i] !== '(') && (telefone[i] !== ')') &&
        (telefone[i] !== ' ') && (telefone[i] !== '-')) {
            telefoneNumerico = telefoneNumerico + telefone[i];
        };
    };
    return telefoneNumerico;
};

async function cpfNumerico(cpf) {
    let cpfNumerico = "";
    for (let i = 0; i < cpf.length; i++) {
        if ((cpf[i] !== '.') && (cpf[i] !== '-')) {
            cpfNumerico = cpfNumerico + cpf[i];
        };
    };
    return cpfNumerico;
};

function dataFormatada(date) {    
    // let zeroDia = "0";
    // let zeroMes = "0";
    // date.getDate() > 9 ? zeroDia = "" : zeroDia = "0";
    // date.getMonth() > 9 ? zeroMes = "" : zeroMes = "0";

    // const dataFormatada = 
    //     // (zeroDia + date.getDate()).substring(-2) + 
    //     // "/" +
    //     // (zeroMes + (date.getMonth() + 1)).substring(-2) +
    //     // "/" +
    //     // date.getFullYear();

    //Uma alternativa mais legível é toISOString, porém ela não tem suporte em IE8

    return date.toISOString().substr(0, 10).split('-').reverse().join('/');
}