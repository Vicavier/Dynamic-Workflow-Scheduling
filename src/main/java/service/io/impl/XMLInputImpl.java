package service.io.impl;

import entity.DataPool;
import entity.Task;
import entity.TaskGraph;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import service.io.Input;

import java.util.ResourceBundle;

public class XMLInputImpl implements Input {
    @Override
    public void input() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("config");
            int size= Integer.parseInt(bundle.getString("file.taskGraph.size"));
            DataPool.graph=new TaskGraph(size);
            DataPool.insNum=size;
            DataPool.tasks=new Task[size];
            for(int i=0;i<size;++i){
                DataPool.tasks[i]=new Task(i);
            }


            SAXReader reader=new SAXReader();
            Document document=reader.read(XMLInputImpl.class.getClassLoader().getResource(bundle.getString("file.taskGraph.path")));
            Element root=document.getRootElement();
            for(Element child:root.elements()){
                if(child.getName().equals("child")){
                    int ver2=Integer.parseInt(child.attributeValue("ref").substring(2));
                    for(Element parent:child.elements()){
                        int ver1= Integer.parseInt(parent.attributeValue("ref").substring(2));
                        DataPool.graph.addEdge(ver1,ver2);
                    }
                }else if(child.getName().equals("job")){
                    int id=Integer.parseInt(child.attributeValue("id").substring(2));
                    double referTime=Double.parseDouble(child.attributeValue("runtime"));
                    long inputSize=0;
                    long outputSize=0;
                    for(Element element:child.elements()){
                        if(element.attributeValue("link").equals("input")) inputSize+=Long.parseLong(element.attributeValue("size"));
                        else if(element.attributeValue("link").equals("output")) outputSize+=Long.parseLong(element.attributeValue("size"));
                    }
                    DataPool.tasks[id].setInputSize(inputSize);
                    DataPool.tasks[id].setOutputSize(outputSize);
                    DataPool.tasks[id].setReferTime(referTime);
                }
            }

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}
