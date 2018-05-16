let text1 = "Abordagem mais conservadora: indexar o conteúdo completo do texto disponível. Completude de conteúdo disponível no momento da busca; uso de termos pouco significativos (ruído); abrangência do resultado obtido, tamanho do índice gerado";
let text2 = "Abordagem menos conservadora: indexar conteúdo selecionado. Apenas conteúdo relevante disponível no momento da busca; desempenho de busca; perda de termos significativos para alguns contextos; abrangência do resultado obtido";

let resultCount1 = new Array();
let resultCount2 = new Array();
let docCount = new Array();
let pos = new Array();

let result1 = text1.split(" ");
for (r  in result1) {
    let result = result1[r];    
    if (resultCount1[result]) {
        resultCount1[result]++;
    } else {
        resultCount1[result] = 1;
        docCount[result] = 1;
    }
}

let result2 = text1.split(" ");
for (r  in result2) {
    let result = result2[r];
    if (resultCount2[result]) {
        resultCount2[result]++;
    } else {
        resultCount2[result] = 1;
    }
    if (docCount[result]) {
        docCount[result] = 2;
    } else {
        docCount[result] = 1;
    }
}

// for (x in resultCount) {
//     console.log(x + "," + resultCount[x]);
// }
// for (x in docCount) {
//     console.log(x + "," + docCount[x]);
// }

for (i = 0; i < result1.length; i++) {
    let print = "[1,";
    print += resultCount1[result1[i]] + "["



    print += "]";
    console.log(print);
    console.log(result1[i]);
}
