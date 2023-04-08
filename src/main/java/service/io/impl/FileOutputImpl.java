package service.io.impl;

import entity.Chromosome;
import service.io.Output;
import utils.ConfigUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
public class FileOutputImpl implements Output {
    @Override
    public void output(List<List<Chromosome>> list) {
        String path="src/main/resources/output/";
        String name= ConfigUtils.get("file.taskGraph.path").split("/")[1].split("\\.")[0]+".txt";
        File file=new File(path+name);
        try {
            if(!file.exists()) file.createNewFile();
            BufferedWriter writer=new BufferedWriter(new FileWriter(file));
            for(Chromosome chromosome:list.get(0)){
                writer.write(chromosome.getMakeSpan()+" "+chromosome.getCost());
                writer.flush();
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
