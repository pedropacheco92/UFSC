const mutationRate = 0.01;
let genes;
let fitness = 0;
let volume = 0;

class DNA {
  constructor(g, d) {
  this.dnaSize = d;
  // update a view com a taxa de mutação
  // $(".mutacao").text(Math.floor(this.mutationRate * 100) + "%");
  document.getElementById("mutacao").innerHTML = mutationRate * 100 + "%";
  // recebe os genes e cria um DNA
  if (g != null) {
    this.genes = g;
  }
  // se nao tem genes, cria um DNA aleatório
  else {
    this.genes = [];
    for (var i = 0; i < this.dnaSize; i++) {
      // cria valores aleatórios
      this.genes[i] = Math.random() >= 0.5 ? 1 : 0;
    }
  }
}
  // CROSSOVER
  // faz o crossover com outro membro da espécie
  crossover(outroDNA) {
    let novosGenes = [];
    for (var i = 0; i < this.dnaSize; i++) {
        // se o random for maior que 0,5 pega o gene atual, senao pega o outro
        novosGenes[i] = Math.random() >= 0.5 ? this.genes[i] : outroDNA.genes[i];
    }
    // retorna um novo DNA
    return new DNA(novosGenes, this.dnaSize);
  }

  // MUTAÇÂO
  // adiciona uma mutação aleatória
  mutation() {
    for (var i = 0; i < this.dnaSize; i++) {
      // se o numero aletorio for menor que a taxa de mutação, é mutacionado o gene
      if (Math.random() < mutationRate) {
        // troca o bit do gene
        this.genes[i] = this.genes[i] == 1 ? 0 : 1;        
      }
    }
  }

  // FITNESS
  // calcula o fitness do elemento
  calcFitness(target, pesos, valores) {
    let sumBenefits = 0;
    let sumWeight = 0;
    for (var i = 0; i < this.dnaSize; i++) {
      let gene = this.genes[i];
      // se eu tiver o gene
      if (gene == 1){
        // pego o peso eo custo e vou somando
        sumBenefits += valores[i];
        sumWeight += pesos[i];
      }
    }

    // se a soma dos valores for maior que o limite, remove um item aleatóriamente e recalcula o fitness
    if (sumWeight > target){
      let pos = Math.floor(Math.random() * (this.dnaSize) + 1) -1;
      this.genes[pos] = this.genes[pos] == 1 ? 0 : 1;
      this.calcFitness(target, pesos, valores);
     }
     this.fitness = sumBenefits;
     this.volume = sumWeight;
 }

  evaluate(){
    for (var i = 0; i < this.dnaSize; i++) {
      let s = "i" + i;
      this.showGene(this.genes[i] == 1, s);    
    }
  }

  showGene(b, s) {
    var x = document.getElementById(s);
    console.log(s);
    if (b){
        x.style.display = "inline";
      
    } else {
        x.style.display = "none";
    } 
  }
}