const popSize = 100;

class Population {
    constructor(target, pesos, valores) {
    // atualiza label da população
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

        for (var i = 0; i < popSize; i++) {
           this.matingPool.push(this.population[i].fitness);
        }
    }

    // faz a reprodução dos elementos
    reproduce() {
        let maxFitness = 0;
        for (var i = 0; i < this.matingPool.length; i++){
            if (this.matingPool[i] > maxFitness) {
                maxFitness = this.matingPool[i];
            }
        }

        for (var i = 0; i < popSize; i++){
            // pega 2 elementos aleatorios do mating poll
            let element1 = this.acceptReject(maxFitness);
            let element2 = this.acceptReject(maxFitness);

            // faz o crossover entre esses dois elementos
            let child = element1.crossover(element2);
            // faz uma possível mutação do filho
            child.mutation();

            // adiciona o filho na população
            this.population[i] = child;
        }
    }

    // accept reject
    acceptReject(maxFitness) {
        let count = 0;
        while (true && count < 100) {
            let index = Math.floor(Math.random() * popSize);
            let r = Math.floor(Math.random() * maxFitness);
            let partner = this.matingPool[index];
            if (r < partner){
                return this.population[index];
            }
            count++;
        }
        // caso passe por 100 elementos e nao ache nenhum, para nao ficar em loop forever, pega um random
        return this.population[Math.floor(Math.random() * popSize)];
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
        let maxFitness = allFitness.reduce((p, v) =>  p > v ? p : v );
        let avg = sum/popSize;

        document.getElementById("fitness").innerHTML = Math.floor((100*avg)/maxFitness) + "%";
            
        let targetPercent = (popSize * 9) / 10;
        for (var i = 0; i < sumFitness.length; i++) {
            if (sumFitness[i] >= targetPercent){
                    for (var i = 0; i < popSize; i++) {
                        if (this.population[i].fitness == maxFitness){
                            this.population[i].evaluate();
                            console.log(this.population[i])
                            break;
                        }
                    }
                return allFitness[i];
            }
        }
        return null;
    }
}