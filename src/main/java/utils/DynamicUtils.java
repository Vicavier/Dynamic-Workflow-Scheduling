package utils;

import entity.Chromosome;
import entity.DataPool;
import entity.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DynamicUtils {


    public static double HV(List<Chromosome> list){
        double cost = 3;
        double makespan = 3;
        double hv = 0;
        list.sort(Comparator.comparingDouble(Chromosome::getCost));
        hv+=(cost - list.get(0).getCost())*(makespan - list.get(0).getMakeSpan());
        for(int i=1;i<list.size();++i){
            hv += (cost - list.get(i).getCost())*(list.get(i-1).getMakeSpan() - list.get(i).getMakeSpan());
        }
        return hv;
    }


}
