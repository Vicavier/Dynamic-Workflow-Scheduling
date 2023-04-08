package controller;

import entity.Chromosome;

import java.util.List;

public interface PopulationController {
    void initialPopulation();

    void produceOffspring();

    void sorting();

    void eliminate();

    List<List<Chromosome>> iterate();

    List<List<Chromosome>> rankReturnIterate(int x,int y);

}
