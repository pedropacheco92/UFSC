// valor total da mochila
const target = 15;
// pesos
const pesos = [12, 1, 4, 1, 2];
// valores 
const valores = [4, 2, 10, 1, 2];
// tamanho máximo de gerações
const maxGen = 1000;


// $(document).ready(function() {
window.onload = function() {
  function setup() {
      // $(".fitness").text('0%');
      document.getElementById("fitness").innerHTML = "0%";

      for (var i = 0; i < valores.length; i++) {
        let e = document.createElement("p");
        e.className = "item"
        e.id = "i"+i;
        e.innerHTML = "R$ " + valores[i] + " | " + pesos[i] + "kg"; 
        document.getElementById("best").appendChild(e);
      }

      // cria uma nova população
      this.pop = new Population(target, pesos, valores);
  }
  
  function draw() {
    for (var i = 0; i < maxGen; i++) {
      // calcula o fitness de toda a população e insere no mating pool
      this.pop.calcFitness();

      this.pop.naturalSelection();
      // cria a proxima geração
      this.pop.reproduce();
      // $(".qtGen").text(i);
      document.getElementById("qtGen").innerHTML = i;

      let hasFinished = this.pop.evaluate();
      if (hasFinished != null) {
        document.getElementById("valor").innerHTML = hasFinished;
        break;
      }
    }
  }

  setup();
  draw();
}