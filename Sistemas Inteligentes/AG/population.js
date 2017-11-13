function Population(p, num) {
    this.population;
    this.matingPool;
    this.generations = 0;
    this.finished = false;
    this.target = p;
    this.perfectScore = 1;

    this.best = 0;

    this.population = [];
    for (let i = 0; i < num; i++){
        this.population[i] = new DNA(null, m);
    }

    this.matingPool = [];
}