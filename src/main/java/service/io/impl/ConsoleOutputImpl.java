package service.io.impl;

import entity.Chromosome;
import service.io.Output;

import java.util.List;

public class ConsoleOutputImpl implements Output {
    @Override
    public void output(List<List<Chromosome>> list) {
        System.out.println("--------------------Pareto Set--------------------");
        int k = 1;
        for (List<Chromosome> list1 : list) {
            System.out.println("--------------------Rank" + k + "--------------------");
            for (Chromosome chromosome : list1) {
                System.out.println("MakeSpan: " + chromosome.getMakeSpan() + " & " + "Cost: " + chromosome.getCost());
            }
            k++;
        }


        System.out.println("--------------------End Line--------------------");
    }
}
