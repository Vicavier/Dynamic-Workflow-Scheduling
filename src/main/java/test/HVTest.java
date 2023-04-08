package test;

import org.uma.jmetal.qualityindicator.impl.hypervolume.Hypervolume;
import org.uma.jmetal.qualityindicator.impl.hypervolume.impl.WFGHypervolume;

public class HVTest {
    public static void main(String[] args) {
        double[][] targets = new double[][]{{0.9,0.1},{0.1,0.9}};
        double[] refer = new double[]{1,1};
        Hypervolume hypervolume=new WFGHypervolume(refer);
        System.out.println(hypervolume.compute(targets));
    }
}
