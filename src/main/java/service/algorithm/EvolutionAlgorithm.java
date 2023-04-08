package service.algorithm;

import entity.Chromosome;

import java.util.List;

public interface EvolutionAlgorithm {
    Chromosome mutate(Chromosome c);
    List<Chromosome> crossover(Chromosome a, Chromosome b);

}
