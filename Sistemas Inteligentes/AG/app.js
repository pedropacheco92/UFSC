// valor total da mochila
const target = 15
// possíveis items, pareados valor-peso
const items = [4, 12, 2, 1, 10, 4, 1, 1, 2, 2];
// tamanho máximo de gerações
const maxGen = 1000;


$(document).ready(function() {

  function setup() {
  
    $(".fitness").text('0%');
    
    // cria uma nova população
    this.pop = new Population(target, items);
  }
  
  function draw() {
    for (var i = 0; i < maxGen; i++) {
      // calcula o fitness de toda a população e insere no mating pool
      this.pop.calcFitness();
      // cria a proxima geração
      this.pop.reproduce();
      $(".qtGen").text(i);

      let hasFinished = this.pop.evaluate();
      if (hasFinished) {
        break;
      }
    }
  }

  setup();
  draw();
});