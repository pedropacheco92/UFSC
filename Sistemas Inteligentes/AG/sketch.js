let target;
let popmax;
let mutationRate;
let population;

let bestPhrase;
let allPhrases;
let stats;

funtion setup() {
  bestPhrase = createP("Best phrase:");
  bestPhrase.class("best");

  allPhrases = createP("All phrases:");
  allPhrases.position(600, 10);
  allPhrases.class("all");

  stats = createP("Stats");

  stats.class("stats");

  target = "To be or not to be."
  popmax = 200;
  mutationRate = 0.01;

  population = new Population(target, mutationRate, popmax);

}