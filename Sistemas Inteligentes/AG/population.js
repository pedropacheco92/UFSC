function Population(p, m, num) {
    this.population;
    this.matingPool;
    this.generations = 0;
    this.finished = false;
    this.target = p;
    this.mutationRate = m;
    this.perfectScore = 1;

    this.best = "";

    this.population = [];
    for (let i = 0; i < num; i++){
        this.population[i] = new DNA(this.target.length);
    }

    this.matingPool = [];
}