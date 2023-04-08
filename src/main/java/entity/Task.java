package entity;

import java.util.LinkedList;
import java.util.List;

public class Task implements Cloneable{
    private int index;
    private double inputSize;
    private double outputSize;
    private double referTime;
    private double startTime;
    private double finalTime;
    private List<Integer> successor;
    private List<Integer> predecessor;
    public Task(int index){
        this.index = index;
        successor=new LinkedList<>();
        predecessor=new LinkedList<>();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getInputSize() {
        return inputSize;
    }

    public void setInputSize(double inputSize) {
        this.inputSize = inputSize;
    }

    public double getOutputSize() {
        return outputSize;
    }

    public void setOutputSize(double outputSize) {
        this.outputSize = outputSize;
    }

    public double getReferTime() {
        return referTime;
    }

    public void setReferTime(double referTime) {
        this.referTime = referTime;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(double finalTime) {
        this.finalTime = finalTime;
    }

    public List<Integer> getSuccessor() {
        return successor;
    }

    public void setSuccessor(List<Integer> successor) {
        this.successor = successor;
    }

    public List<Integer> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(List<Integer> predecessor) {
        this.predecessor = predecessor;
    }

    public List<Integer> getSuccessors() {
        return successor;
    }

    @Override
    public Task clone() {
        Task task=new Task(index);
        task.setInputSize(inputSize);
        task.setOutputSize(outputSize);
        task.setReferTime(referTime);
        task.setSuccessor(successor);
        task.setPredecessor(predecessor);
        return task;
    }
}