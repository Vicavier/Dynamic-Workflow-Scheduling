package test;

import controller.impl.GaussianDynamicSimulation;
import entity.Chromosome;
import entity.DataPool;
import entity.Task;
import entity.Type;
import utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

public class DynamicTest {
    public static void main(String[] args) {
        Task task0 = new Task(0);
        Task task1 = new Task(1);
        Task task2 = new Task(2);
        List<Integer> list=new ArrayList<>();
        list.add(task1.getIndex());
        task0.setSuccessor(list);
        List<Integer> pre1 = new ArrayList<>();
        pre1.add(task0.getIndex());
        task1.setPredecessor(pre1);
        task0.setReferTime(1);
        task1.setReferTime(4);
        task2.setReferTime(3);
        DataPool.tasks = new Task[]{
                task0,task1,task2
        };
        Type type0 = new Type(0,1,1,1);
        Type type1 = new Type(0,2,2,2);
        Type type2 = new Type(0,3,3,3);

        DataPool.types = new Type[]{
                type0,type1,type2
        };

        Chromosome chromosome1=new Chromosome();

        int[] task = new int[]{0,1,2};
        int[] ins = new int[]{0,1,2};
        int[] type = new int[]{0,1,2};

        chromosome1.setTask(task);
        chromosome1.setTask2ins(ins);
        GaussianDynamicSimulation gaussianDynamicSimulation=new GaussianDynamicSimulation();
        gaussianDynamicSimulation.setDynamicTarget(chromosome1);
        System.out.println(DataUtils.getMakeSpan(chromosome1));
        System.out.println(chromosome1.getMakeSpan()+" "+chromosome1.getCost());
    }
}
