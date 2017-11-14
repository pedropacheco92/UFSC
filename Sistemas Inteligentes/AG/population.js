const popSize = 20;

function Population(target, pesos, valores) {
    // atualiza label da população
    $(".populacao").text(popSize);

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

    // cria o mating poll
    this.calcFitness = function() {
        let sumFitness = 0;
        // para cada elemento da população
        for (var i = 0; i < popSize; i++){
            // pega o dna e calcula o fitness em %
            let dna = this.population[i];
            let fitness = dna.fitness(target, pesos, valores);
            // para cada valor em fitness, adiciona um membro no mating poll
            for (var j = 0; j < fitness[0]; j++){
                this.matingPool.push(dna);
            }

            // calcula porcentagem
            let fitnessPercentage = (fitness[0] * 100) / this.target;
            
            sumFitness += fitnessPercentage;
        }
        // atualiza o valor na tela
        $(".fitness").text(Math.floor(sumFitness/popSize) + "%");
    }

    // faz a reprodução dos elementos
    this.reproduce = function() {
        for (var i = 0; i < popSize; i++){
            // pega 2 elementos aleatorios do mating poll
            let pos1 = Math.floor(Math.random() * (popSize) + 1) -1;
            let pos2 = Math.floor(Math.random() * (popSize) + 1) -1;
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

    this.evaluate = function() {
        // para cada elemento da população
        for (var i = 0; i < popSize; i++){
            // pega o dna e calcula o fitness em %
            let dna = this.population[i];
            let fitness = dna.fitness(target, this.pesos, this.valores);
            console.log(fitness);
            if (fitness[1] <= target){
                $(".valor").text(fitness[0]);
                dna.evaluate();
                console.log(dna);
                return true;
            }            
        }
        return false;
    }
}