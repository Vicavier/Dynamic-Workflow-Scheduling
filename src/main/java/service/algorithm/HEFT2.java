package service.algorithm;//package service.algorithm;
//
//import entity.Chromosome;
//import entity.DataPool;
//import entity.Task;
//import entity.Type;
//import utils.DataUtils;
//
//import javax.swing.*;
//import java.util.*;
//
///**
// * Heterogeneous Earliest-Finish-Time (HEFT)
// * The HEFT algorithm selects the task with the highest upward rank value at each step
// * and assigns the selected task to the processor,
// * which minimizes its earliest finish time with an insertion-based approach.
// *
// * Another difference is in the processor selection phase,
// * which schedules the critical tasks onto the processor
// * that minimizes the total execution time of the critical tasks.
// *
// * Because here we are in the cloud environment, we have infinite instances and infinite types,
// * we can all choose the best performance type.
// * All we need to do is decide which instance to take.
// */
//public class HEFT2 {
//    public static Chromosome generateChromosome(){
//        Chromosome c =  new Chromosome();
//        Task[] tasks = DataPool.tasks;
//        int n = tasks.length;
//        Type[] types = DataPool.types;
//        Type best_type = types[0];
//        for(Type type : types){
//            if (type.cu > best_type.cu){
//                best_type = type;
//            }
//        }
//
//        int[] order = DataUtils.getRandomTopologicalSorting();
//        int[] task2ins = new int[n];
//        int[] ins2type = new int[n];
//
//        double[] rank = new double[n];
//        Queue<Task> waiting = new LinkedList<Task>();
//        Set<Task> finished = new HashSet<Task>();
//        for(Task task : tasks){
//            if(task.getSuccessor().size() == 0){
//                waiting.add(task);
//                finished.add(task);
//                rank[task.getIndex()] = task.getReferTime() / best_type.cu;
//            }
//        }
//        while(!waiting.isEmpty()){
//            Task cur = waiting.remove();
//            finished.add(cur);
//            for (int index : cur.getPredecessor()){
//                Task pre = tasks[index];
//                if(!finished.contains(pre)){
//                    waiting.add(pre);
//                }
//                rank[index] = Math.max(rank[index], rank[cur.getIndex()] + cur.getDataSize() / best_type.bw);
//            }
//        }
//        int[] indexes = new int[n];
//        for (int i = 0; i < n; i++) {
//            indexes[i] = i;
//        }
//        for (int i = 0; i < n; i++) {
//            for (int j = i+1;j <n;j++){
//                if (rank[i] < rank[j]){
//                    double temp = rank[i];
//                    rank[i] = rank[j];
//                    rank[j] = temp;
//
//                    int t = indexes[i];
//                    indexes[i] = indexes[j];
//                    indexes[j] = t;
//                }
//            }
//        }
//
//
//        Arrays.stream(rank).forEach(System.out::print);
//        Arrays.stream(indexes).forEach(System.out::print);
//
//        c.setTask(indexes);
//        c.setTask2ins(task2ins);
//        c.setIns2type(ins2type);
//
//        return c;
//    }
//}