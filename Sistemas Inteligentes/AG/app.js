// valor total da mochila
const target = 15
// possíveis items, pareados valor-peso
const items = [4, 12, 2, 1, 10, 4, 1, 1, 2, 2];
// tamanho máximo de gerações
const maxGen = 1000;


$(document).ready(function() {

  function setup() {
  
    $(".best").text('-');
    $(".qtGen").text(0);
    $(".fitness").text('0%');

    // cria uma nova população
    this.pop = new Population(target, items);
    pop.createMatingPool();
  }

  function draw() {
    let newPop = [];

   

  }

  setup();
  draw();
});