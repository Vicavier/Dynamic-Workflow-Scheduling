package service.algorithm;

import entity.Chromosome;
import entity.DataPool;
import entity.Task;
import entity.Type;

/**
 * Heterogeneous Earliest-Finish-Time (HEFT)
 * The HEFT algorithm selects the task with the highest upward rank value at each step
 * and assigns the selected task to the processor,
 * which minimizes its earliest finish time with an insertion-based approach.
 * <p>
 * Another difference is in the processor selection phase,
 * which schedules the critical tasks onto the processor
 * that minimizes the total execution time of the critical tasks.
 * <p>
 * Because here we are in the cloud environment, we have infinite instances and infinite types,
 * we can all choose the best performance type.
 * All we need to do is decide which instance to take.
 */
public class HEFT {
    public static double[] upward_rank = new double[DataPool.tasks.length];

    public static Chromosome generateChromosome() {
        int n = DataPool.tasks.length;
        Type best_type = DataPool.types[0];
        for (Type type : DataPool.types) {
            if (type.cu > best_type.cu) {
                best_type = type;
            }
        }
        //Task Prioritize Phase
        for (Task task : DataPool.tasks) {
            if (task.getPredecessor().size() == 0) {
                calculate_rank(task.getIndex(), best_type);
            }
        }

//        Arrays.stream(upward_rank).forEach(System.out::println);

        //Processor Selection Phase\
        int[] ins2type = new int[n];
        for (int i = 0; i < n; i++) {
            ins2type[i] = best_type.id;
        }

        int[] order = new int[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (upward_rank[j] > upward_rank[i]) {
                    double temp = upward_rank[j];
                    upward_rank[j] = upward_rank[i];
                    upward_rank[i] = temp;

                    int t = order[i];
                    order[i] = order[j];
                    order[j] = t;

                }
            }
        }
        double[] available_time = new double[n];

        int[] task2ins = new int[n];
        task2ins[0] = 0;
        available_time[0] = DataPool.tasks[0].getReferTime() / best_type.cu;
        for (int i = 1; i < n; i++) {
            if(DataPool.tasks[i].getIndex() == 78){
                System.out.println("---------------");
            }
            double earliest_time = Double.MAX_VALUE;//找到最小的starttime
            int instance_index = 0;//记录将要安排给的instance
            Task task = DataPool.tasks[order[i]];
            //试探性地在每个机子上面进行时间计算
            for (int instance = 0; instance < n; instance++) {
                double start_time = available_time[instance];//对应这台instance的开始时间
                for (int pre : task.getPredecessor()) {

                    double transmission_time = DataPool.tasks[pre].getOutputSize() / best_type.bw;

                    if (task2ins[pre] == instance) {
                        start_time = Math.max(available_time[instance], start_time);
                    } else {
                        start_time = Math.max(start_time, transmission_time + available_time[task2ins[pre]]);
                    }
                }
                if (start_time < earliest_time) {
                    earliest_time = start_time;
                    instance_index = instance;
                }
            }
            available_time[instance_index] = earliest_time + task.getReferTime() / best_type.cu;
            task2ins[i] = instance_index;
        }
        return null;
    }

    public static void calculate_rank(int task_index, Type best_type) {
        if (DataPool.tasks[task_index].getSuccessor().size() == 0) {
            upward_rank[task_index] = DataPool.tasks[task_index].getReferTime() / best_type.cu;
        } else {
            for (int suc_index : DataPool.tasks[task_index].getSuccessor()) {
                calculate_rank(suc_index, best_type);
                upward_rank[task_index] = Math.max(upward_rank[task_index],
                        upward_rank[suc_index] + DataPool.tasks[task_index].getOutputSize() / best_type.bw);
            }
        }
    }
}