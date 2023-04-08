package test;

import controller.PopulationController;
import controller.impl.NSGAIIPopulationController;
import entity.Chromosome;
import entity.DataPool;
import utils.InitUtils;
import utils.WriterUtils;

import java.util.List;
import java.util.Random;

public class ReliabilityTest {
    private static final Random random=new Random();
    private static double failureFrequency;
    private static double []failureSeverity = {0.01,0.03,0.08,0.15};

    public static void main(String[] args) {
        InitUtils.init();
        for (int k = 0; k < 4; k++) {
            System.out.println("k:" + k);
            StringBuilder log = new StringBuilder();
            for (int i = 0; i < 100; i++) {
                System.out.println("epoch: " + i + "...");
                DataPool.all.clear();
                DataPool.disabledIns.clear();
                PopulationController controller = new NSGAIIPopulationController();
                List<List<Chromosome>> list=controller.iterate();
                List<Chromosome> POF = list.get(0);
                int numberOfFailure = (int) (DataPool.insNum * failureSeverity[k]);
                for (int j = 0; j < numberOfFailure; j++) {
                    int failureIns = random.nextInt(DataPool.insNum);
                    if (DataPool.disabledIns.contains(failureIns)){
                        j--;
                    } else {
                        DataPool.disabledIns.add(failureIns);
                    }
                }
                int failure = 0;
                for (Chromosome c: POF){
                    for (int ins: c.getTask2ins()){
                        if (DataPool.disabledIns.contains(ins)){
                            failure++;
                            break;
                        }
                    }

                }

                double reliability = (1- (double)failure/POF.size());
//            log.append("第").append(i+1).append("轮可靠性: ").append(String.format("%.6f",reliability)).append("\n");
                log.append(String.format("%.6f",reliability)).append("\n");

            }
            String path = "./Montage_100task_80ins_"+failureSeverity[k] +"_reliability.txt";
            WriterUtils.write(path, log.toString());
        }

    }
}
