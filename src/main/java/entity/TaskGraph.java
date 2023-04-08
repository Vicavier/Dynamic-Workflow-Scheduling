package entity;

import java.util.*;

import static entity.DataPool.edges;
import static entity.DataPool.tasks;

public class TaskGraph implements Cloneable{
    private int size;
    private boolean isFirst=false;
    Vertex start;
    Vertex end = new Vertex(Integer.MAX_VALUE);
    private Random random=new Random();
    Vertex[] vertices;
    public TaskGraph(int n) {
        vertices = new Vertex[n];
        for (int i = 0; i < n; ++i) {
            vertices[i] = new Vertex(i);
        }
        start = new Vertex(-1);
        size = n;
    }

    //添加有向边：ver1->ver2
    public void addEdge(int ver1, int ver2) {
        vertices[ver1].next.add(vertices[ver2]);
        vertices[ver2].fa.add(vertices[ver1]);
        vertices[ver2].inner++;
        vertices[ver1].outer++;
        if(edges==null) {
            edges=new LinkedList<>();
            isFirst=true;
        }
        if(isFirst){
            edges.add(new int[]{ver1,ver2});
            tasks[ver1].getSuccessor().add(ver2);
            tasks[ver2].getPredecessor().add(ver1);
        }
    }

    public int[] TopologicalSorting() {
        List<Integer> list=new LinkedList<>();
        //起始节点与终止节点分别对入度为0，出度为0的节点进行连接
        for (Vertex vertex : vertices) {
            if (vertex == null) continue;
            if (vertex.inner == 0) {
                start.next.add(vertex);
                start.outer++;
                vertex.inner++;
            }
            if (vertex.outer == 0) {
                vertex.next.add(end);
                vertex.outer++;
                end.inner++;
            }
        }
        int[] ans = new int[size];
        int k = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(-1);
        while (!queue.isEmpty()) {
            int index=queue.poll();
            if(index==end.id) break;
            Vertex temp;
            if(index!=-1) temp = vertices[index];
            else temp=start;
            if(index!=-1) {
                addRandomNum(list,temp.id);
            }
            for (Vertex vertex : temp.next) {
                //伪删除前驱节点
                vertex.inner -= 1;
                if (!vertex.isVisited) {
                    if (vertex.inner == 0) {
                        queue.add(vertex.id);
                        vertex.isVisited = true;
                    }
                }
            }
        }
        for(int i=0;i<list.size();++i){
            ans[i]=list.get(i);
        }
        return ans;

    }

    public void addRandomNum(List<Integer> list,int index){
        List<Vertex> fa=vertices[index].fa;
        List<Vertex> next=vertices[index].next;
        int start = 0;
        int end = list.size();
        for(int i=list.size()-1;i>=0;--i){
            if(fa.contains(vertices[list.get(i)])){
                start=i+1;
                break;
            }
        }
        for(int i=0;i<list.size();++i){
            if(next.contains(vertices[list.get(i)])){
                end=i;
                break;
            }
        }
        int pos = random.nextInt(start,end+1);
        list.add(pos,index);
    }

    @Override
    public TaskGraph clone() {
        TaskGraph taskGraph=new TaskGraph(size);
        for (int[] arr : edges) {
            taskGraph.addEdge(arr[0], arr[1]);
        }
        return taskGraph;
    }
}

class Vertex{
    //节点入度
    int id;
    int inner = 0;
    int outer = 0;
    boolean isVisited = false;
    List<Vertex> next = new ArrayList<>();
    List<Vertex> fa = new ArrayList<>();

    public Vertex(int id) {
        this.id = id;
    }
}

