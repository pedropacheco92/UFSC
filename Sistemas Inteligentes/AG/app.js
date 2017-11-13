$(document).ready(function() {
let target;
let popmax;
let mutationRate;
let population;
let items;

  function setup() {
    // valor total da mochila
    target = 15;
    // possíveis items, pareados valor-peso
    items = [4, 12, 2, 1, 10, 4, 1, 1, 2, 2];
    // população máxima
    popmax = 200;
    // taxa de mutação
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