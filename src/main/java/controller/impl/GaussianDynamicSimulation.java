package controller.impl;

import entity.Chromosome;
import entity.DataPool;
import entity.Task;
import utils.DataUtils;
import utils.MakeSpanUtils;

import java.util.List;
import java.util.Random;

import static utils.DataUtils.getStartTime;

public class GaussianDynamicSimulation extends AbstractDynamicSimulation{

    private final Random random=new Random();

    @Override
    void doSim(List<Chromosome> list) {
        for(Chromosome chromosome:list){
            setDynamicTarget(chromosome);
        }
    }

    public void setDynamicTarget(Chromosome chromosome){
        double[] availableTime = new double[DataPool.insNum];
        double exitTime = 0;
        for (int taskIndex : chromosome.getTask()) {
            Task task = DataPool.tasks[taskIndex].clone();
            int insIndex = chromosome.getTask2ins()[taskIndex];
            int typeIndex =  DataPool.insToType.get(insIndex);
            if (task.getPredecessor().size() == 0) {
                chromosome.getStart()[taskIndex] = Math.max(0, availableTime[insIndex]);
                chromosome.getEnd()[taskIndex] = chromosome.getStart()[taskIndex] + task.getReferTime() / DataPool.types[typeIndex].cu + getGaussianRand(task.getReferTime() / DataPool.types[typeIndex].cu);
                availableTime[insIndex] = chromosome.getEnd()[taskIndex];
            } else {
                chromosome.getStart()[taskIndex] = Math.max(getStartTime(chromosome, taskIndex), availableTime[insIndex]);
                chromosome.getEnd()[taskIndex] = chromosome.getStart()[taskIndex] + DataPool.tasks[taskIndex].getReferTime() / DataPool.types[typeIndex].cu + getGaussianRand(task.getReferTime() / DataPool.types[typeIndex].cu);
                availableTime[insIndex] = chromosome.getEnd()[taskIndex];
            }
            if (chromosome.launchTime[insIndex] == 0) {
                chromosome.launchTime[insIndex] = chromosome.getStart()[taskIndex];
            }
            chromosome.shutdownTime[insIndex] = chromosome.getEnd()[taskIndex];
            if (task.getSuccessor().size() == 0) {
                exitTime = Math.max(exitTime, chromosome.getEnd()[taskIndex]);
            }
        }
        chromosome.setMakeSpan(exitTime);
        chromosome.setCost(DataUtils.getCost(chromosome));
    }

    public double getGaussianRand(double time){
        return Math.abs(random.nextGaussian(0,time*0.1));
    }

}
