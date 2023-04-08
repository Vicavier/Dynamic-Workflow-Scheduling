package controller.impl;

import controller.DynamicSimulation;
import entity.Chromosome;
import service.io.Output;
import service.io.impl.ChartOutputImpl;
import utils.WriterUtils;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDynamicSimulation implements DynamicSimulation {
    @Override
    public List<List<Chromosome>> sim(List<Chromosome> list1,List<Chromosome> list2) {
        try {
            List<Chromosome> temp1 = new ArrayList<>();
            List<Chromosome> temp2 = new ArrayList<>();
            for(Chromosome chromosome:list1){
                temp1.add(chromosome.clone());
            }
            for(Chromosome chromosome:list2){
                temp2.add(chromosome.clone());
            }
            doSim(temp1);
            doSim(temp2);
            List<Chromosome> front1 = paretoEstimate(true,temp1);//50
            List<Chromosome> front2 = paretoEstimate(true,temp2);//500
            Output output = new ChartOutputImpl();
            List<List<Chromosome>> ans = new ArrayList<>();
            ans.add(front1);
            ans.add(front2);
            StringBuilder str1 = new StringBuilder();
            StringBuilder str2 = new StringBuilder();
            for(Chromosome chromosome:front1){
                str1.append(chromosome.getMakeSpan()).append(" ").append(chromosome.getCost()).append("\n");
            }
            for(Chromosome chromosome:front2){
                str2.append(chromosome.getMakeSpan()).append(" ").append(chromosome.getCost()).append("\n");
            }
            WriterUtils.write("src\\main\\resources\\output\\400_sim.txt",str1.toString());
            WriterUtils.write("src\\main\\resources\\output\\500_sim.txt",str2.toString());
//            System.out.println("HV{before: "+ beforeHV + " | after: " + afterHV + " | " + "reduce: " + (beforeHV - afterHV)/beforeHV + "}");
            output.output(ans);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    public List<Chromosome> paretoEstimate(boolean isFront,List<Chromosome> list){
        for(Chromosome chromosome:list){
            chromosome.setBetterNum(0);
            chromosome.setPoorNum(0);
        }
        if(isFront){
            for(int i=0;i<list.size();++i){
                Chromosome chromosome1 = list.get(i);
                for(int j=i+1;j<list.size();++j){
                    Chromosome chromosome2 = list.get(j);
                    if(chromosome1.getMakeSpan()>chromosome2.getMakeSpan()&&chromosome1.getCost()>chromosome2.getCost()){
                        chromosome1.setBetterNum(chromosome1.getBetterNum()+1);
                        chromosome2.setPoorNum(chromosome2.getPoorNum()+1);
                    }else if(chromosome1.getMakeSpan()<chromosome2.getMakeSpan()&&chromosome1.getCost()<chromosome2.getCost()){
                        chromosome1.setPoorNum(chromosome1.getPoorNum()+1);
                        chromosome2.setBetterNum(chromosome2.getBetterNum()+1);
                    }
                }
            }
            List<Chromosome> ans = new ArrayList<>();
            for(Chromosome chromosome:list){
                if(chromosome.getBetterNum()==0) ans.add(chromosome);
            }
            return ans;
        }
        return list;
    }
    abstract void doSim(List<Chromosome> list);
}
