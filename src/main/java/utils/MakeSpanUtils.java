package utils;

import entity.DataPool;
import entity.Task;
import entity.Type;

public class MakeSpanUtils {
    public static Type[] task2types;

    @Deprecated
    public static double getCompTime(double referTime,double cu){
        return referTime/cu;
    }

    public static double getCommTime(double dataSize,double brandWidth){
        return dataSize/brandWidth;
    }

    @Deprecated
    public static double getStartTime(double availableTime, Task task, double dataSize, double brandWidth){
        double max = 0;
        for(int temp: task.getPredecessor()){
            double bw = Math.min(brandWidth,task2types[temp].bw);
            double commTime = getCommTime(dataSize,bw);
            max = Math.max(max,commTime+DataPool.tasks[temp].getFinalTime());
        }
        return Math.max(availableTime,max);
    }


}
