import controller.DynamicSimulation;
import controller.PopulationController;
import controller.impl.GaussianDynamicSimulation;
import controller.impl.NSGAIIPopulationController;
import entity.Chromosome;
import entity.DataPool;
import entity.Type;
import service.io.Input;
import service.io.Output;
import service.io.impl.ChartOutputImpl;
import service.io.impl.ConsoleOutputImpl;
import service.io.impl.FileOutputImpl;
import service.io.impl.XMLInputImpl;
import utils.DataUtils;
import utils.DynamicUtils;
import utils.InitUtils;
import utils.WriterUtils;

import java.util.LinkedList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        InitUtils.init();
        PopulationController controller = new NSGAIIPopulationController();
        List<List<Chromosome>> list=controller.iterate();



    }

    public static void setBetterAndPoor(Chromosome better, Chromosome poor) {
        better.getPoor().add(poor);
        poor.getBetter().add(better);
        better.addPoor();
        poor.addBetter();
    }
}
