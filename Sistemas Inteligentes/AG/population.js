const popSize = 1000;

class Population {
    constructor(target, pesos, valores) {
    // atualiza label da população

    // $(".populacao").text(popSize);
    document.getElementById("populacao").innerHTML = popSize;

    this.target = target;
    this.pesos = pesos;
    this.valores = valores;

    this.population;
    this.matingPool;
    this.generations = 0;
    this.finished = false;
    this.perfectScore = 1;

    this.best = 0;

    this.population = [];
    for (var i = 0; i < popSize; i++){
        this.population[i] = new DNA(null, valores.length);
    }

    this.matingPool = [];
}

    calcFitness() {
        // para cada elemento da população
        for (var i = 0; i < popSize; i++){
            this.population[i].calcFitness(target, pesos, valores);
        }     
    }
    
    // cria o mating poll
    naturalSelection() {
        // limpa o array
        this.matingPool = [];
        
        let maxFitness = 0;
        let totalFitness = 0;
        for (var i = 0; i < popSize; i++){
            if (this.population[i].fitness > maxFitness) {
                maxFitness = this.population[i].fitness;
            }
            totalFitness += this.population[i].fitness;
        }
        
        for (var i = 0; i < popSize; i++){            
            let fitness = this.population[i].fitness/totalFitness; // valor entre 0 e 1 sendo totalFitness 1
            let n = Math.floor(fitness * 100); // coloca em %
            for (var j = 0; j < n; j++) {
                this.matingPool.push(this.population[i]);
            }
        }
    }

    // faz a reprodução dos elementos
    reproduce() {
        for (var i = 0; i < popSize; i++){
            // pega 2 elementos aleatorios do mating poll
            let pos1 = Math.floor(Math.random() * (this.matingPool.length) + 1) -1;
            let pos2 = Math.floor(Math.random() * (this.matingPool.length) + 1) -1;
            let element1 = this.matingPool[pos1];
            let element2 = this.matingPool[pos2];

            // faz o crossover entre esses dois elementos
            let child = element1.crossover(element2);
            // faz uma possível mutação do filho
            child.mutation();

            // adiciona o filho na população
            this.population[i] = child;
        }
    }

    evaluate() {
        // calcula o fitness da nova população
        let allFitness = [];
        let sumFitness = [];
        for (var i = 0; i < popSize; i++){
            this.population[i].calcFitness(target, pesos, valores);
            let contains = false;
            for (var k = 0; k < allFitness.length; k++) {
                if (allFitness[k] == this.population[i].fitness){
                    sumFitness[k]++;
                    contains = true;
                    break;
                }
            }
    
            if (!contains) {
                allFitness.push(this.population[i].fitness)
                sumFitness.push(1);
            }         
        }

        let aux = [];
        // somente para atualizar a tela
        for (var i = 0; i < sumFitness.length; i++) {
            aux.push(allFitness[i]*sumFitness[i]);
        }
        let sum = aux.reduce((a, b) => a + b, 0);
        
        document.getElementById("fitness").value = Math.floor(sum/aux.length) + "%";
        // $(".fitness").text(Math.floor(sum/aux.length) + "%");
        let targetPercent = (popSize * 9) / 10;
        for (var i = 0; i < sumFitness.length; i++) {
            if (sumFitness[i] >= targetPercent){
                return allFitness[i];
            }
        }
        console.log(targetPercent);
        return null;
    }
}