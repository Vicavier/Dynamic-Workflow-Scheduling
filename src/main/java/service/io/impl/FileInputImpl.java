package service.io.impl;

import entity.DataPool;
import entity.Task;
import entity.TaskGraph;
import service.io.Input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;

public class FileInputImpl implements Input {
    private ResourceBundle bundle = ResourceBundle.getBundle("config");

    @Override
    public void input() {
        String path = bundle.getString("file.taskGraph.path");
        System.out.println("Input from: " + path);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            int totalNum = Integer.parseInt(reader.readLine());
            DataPool.tasks = new Task[totalNum];
            DataPool.graph = new TaskGraph(totalNum);
            for (int i = 0; i < totalNum; ++i) {
                DataPool.tasks[i] = new Task(i);
            }
            String line;
            while ((line = reader.readLine()) != null) {
                String[] edge = line.split(" ");
                int ver1 = Integer.parseInt(edge[0]);
                int ver2 = Integer.parseInt(edge[1]);

                DataPool.graph.addEdge(ver1, ver2);
            }
            System.out.println("Input success!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
