package service.algorithm;

import entity.Chromosome;
import entity.DataPool;
import utils.DataUtils;

public class CostMin {
    @Deprecated
    public static Chromosome getMinCostChromosome(){
        int n = DataPool.tasks.length;
        int[] order= DataUtils.getRandomTopologicalSorting();
        int[] ins=new int[n];
        int[] type=new int[n];
        for(int i=0;i<n;++i){
            double cost=Double.MAX_VALUE;
            int index=0;
            for(int j=0;j<DataPool.types.length;++j){
                double temp=(DataPool.tasks[i].getReferTime()/DataPool.types[j].cu)*DataPool.types[j].p;
                if(temp < cost){
                    index=j;
                    cost=temp;
                }
            }
            ins[i]=i;
            type[i]=DataPool.types[index].id;
        }
        Chromosome chromosome=new Chromosome(order,ins,type);
        return chromosome;
    }
}
