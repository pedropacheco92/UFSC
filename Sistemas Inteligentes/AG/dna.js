const geneSize = 5;
const mutationRate = 0.01;

function DNA(genes) {
  // Recieves genes and create a dna object
  if (genes) {
    this.genes = genes;
  }
  // If no genes just create random dna
  else {
    this.genes = [];
    for (var i = 0; i < lifespan; i++) {
      // Gives random vectors
      this.genes[i] = p5.Vector.random2D();
      // Sets maximum force of vector to be applied to a rocket
      this.genes[i].setMag(maxforce);
    }
  }

  // CROSSOVER
  // faz o crossover com outro membro da espécie
  this.crossover = function(outroDNA) {
    let novosGenes = [];
    for (var i = 0; i < this.genes.length; i++) {
        // se o random for maior que 0,5 pega o gene atual, senao pega o outro
        novosGenes[i] = Math.random() >= 0.5 ? this.genes[i] : outroDNA.genes[i];
    }
    // retorna um novo DNA
    return new DNA(novosGenes);
  }

  //MUTAÇÂO
  // adiciona uma mutação aleatória
  this.mutation = function(mutationRate) {
    for (var i = 0; i < this.genes.length; i++) {
      // se o numero aletorio for menor que a taxa de mutação, é mutacionado o gene
      if (Math.random() < mutationRate) {
        // troca o bit do gene
        this.genes[i] = genes[i] == 1 ? 0 : 1;        
      }
    }
  }
}