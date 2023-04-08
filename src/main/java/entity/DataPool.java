package entity;


import service.algorithm.impl.NSGAII;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class DataPool {
    public static Task[] tasks;
    public static TaskGraph graph;
    public static List<int[]> edges;
    public static NSGAII nsgaii = new NSGAII();
    public static int insNum;
    public static int typeNum;
    public static Type[] types;

    public static List<Integer> insToType=new ArrayList<>(250);
    public static List<Integer> accessibleIns=new ArrayList<>();
    public static HashSet<Integer> disabledIns=new HashSet<>();

    public static List<List<Chromosome>> all = new LinkedList<>();

}
