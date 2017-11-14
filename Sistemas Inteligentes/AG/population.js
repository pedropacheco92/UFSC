const popSize = 200;

function Population(target, items) {
    // atualiza label da população
    $(".populacao").text(popSize);

    this.target = target;
    this.items = items;

    this.population;
    this.matingPool;
    this.generations = 0;
    this.finished = false;
    this.perfectScore = 1;

    this.best = 0;

    this.population = [];
    for (var i = 0; i < popSize; i++){
        this.population[i] = new DNA(null);
    }

    this.matingPool = [];

    // cria o mating poll
    this.createMatingPool = function() {
        let sumFitness = 0;
        // para cada elemento da população
        for (var i = 0; i < popSize; i++){
            // pega o dna e calcula o fitness em %
            let dna = this.population[i];
            let fitness = dna.fitness(target, items);
            // para cada valor em fitness, adiciona um membro no mating poll
            for (var j = 0; j < fitness; j++){
                this.matingPool.push(dna);
            }

            let fitnessPercentage = (fitness * 100) / this.target;
            sumFitness += fitnessPercentage;
        }
        $(".fitness").text(sumFitness/popSize);
    }



}