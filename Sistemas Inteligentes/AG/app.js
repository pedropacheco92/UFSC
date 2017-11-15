// valor total da mochila
const target = 6;
// pesos
const pesos = [12, 1, 4, 1, 2];
// valores 
const valores = [4, 2, 10, 1, 2];
// tamanho máximo de gerações
const maxGen = 1000;


$(document).ready(function() {

  function setup() {
      $(".fitness").text('0%');
      // cria uma nova população
      this.pop = new Population(target, pesos, valores);
  }
  
  function draw() {
    for (var i = 0; i < maxGen; i++) {
      // calcula o fitness de toda a população e insere no mating pool
      this.pop.calcFitness();
      // cria a proxima geração
      this.pop.reproduce();
      $(".qtGen").text(i);

      let hasFinished = this.pop.evaluate();
      if (hasFinished != null) {
        console.log(hasFinished);
        break;
      }
    }
  }

  setup();
  draw();
});