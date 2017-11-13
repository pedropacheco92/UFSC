$(document).ready(function() {
let target;
let popmax;
let mutationRate;
let population;

  function setup() {  
    target = "To be or not to be."
    let items = [4, 12, 2, 1, 10, 4, 1, 1, 2, 2]; // pair value-weight
    popmax = 200;
    mutationRate = 0.01;
  
    $(".best").text('-');
    $(".qtGen").text(0);
    $(".fitness").text('0%');
    $(".populacao").text(popmax);
    $(".mutacao").text(Math.floor(mutationRate * 100) + "%");

    population = new Population(target, mutationRate, popmax);
  }
  
  setup();
});