const dnaSize = 5;
const mutationRate = 0.01;

function DNA(genes) {
  // update a view com a taxa de mutação
  $(".mutacao").text(Math.floor(mutationRate * 100) + "%");
  // recebe os genes e cria um DNA
  if (genes) {
    this.genes = genes;
  }
  // se nao tem genes, cria um DNA aleatório
  else {
    this.genes = [];
    for (var i = 0; i < dnaSize; i++) {
      // cria valores aleatórios
      this.genes[i] = Math.random() >= 0.5 ? 1 : 0;
    }
  }

  // CROSSOVER
  // faz o crossover com outro membro da espécie
  this.crossover = function(outroDNA) {
    let novosGenes = [];
    for (var i = 0; i < dnaSize; i++) {
        // se o random for maior que 0,5 pega o gene atual, senao pega o outro
        novosGenes[i] = Math.random() >= 0.5 ? this.genes[i] : outroDNA.genes[i];
    }
    // retorna um novo DNA
    return new DNA(novosGenes);
  }

  // MUTAÇÂO
  // adiciona uma mutação aleatória
  this.mutation = function() {
    for (var i = 0; i < dnaSize; i++) {
      // se o numero aletorio for menor que a taxa de mutação, é mutacionado o gene
      if (Math.random() < mutationRate) {
        // troca o bit do gene
        this.genes[i] = genes[i] == 1 ? 0 : 1;        
      }
    }
  }

  // FITNESS
  // calcula o fitness do elemento
  this.fitness = function(target, items) {
    let sumBenefits = 0;
    let sumWeight = 0;
    for (var i = 0; i < dnaSize; i++) {
      let gene = this.genes[i];
      // se eu tiver o gene
      if (gene == 1){
        // pego o peso eo custo e vou somando
        sumBenefits += items[i*2];
        sumWeight += items[(i*2)+1];
      }
    }
      
    if (sumWeight > target){
      let pos = Math.floor(Math.random() * (dnaSize) + 1) -1;
      this.genes[pos] = this.genes[pos] == 1 ? 0 : 1;
      return this.fitness(target, items);
     } 
    return sumBenefits;
 }

  this.evaluate = function(){
    for (var i = 0; i < dnaSize; i++) {
      let s = ".p" + (i + 1);
      console.log(s);
      this.showGene(this.genes[i] == 1, s);    
    }
  }

  this.showGene = function(b, s) {
    if (b){
      $(s).show();
    } else {
      $(s).hide();
    } 
  }
}