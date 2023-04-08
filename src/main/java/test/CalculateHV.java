package test;

import java.io.*;
import java.util.ArrayList;

public class CalculateHV {
    public static void main(String[] args) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\output\\HV.txt"));
        ArrayList<double[]> list=new ArrayList<>();
        for(int k=0;k<15;++k) {
            double[] arr = new double[1000];
            File file = new File("src\\main\\resources\\output\\HV" + k + ".txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            for (int i = 0; i < 1000; ++i) {
                arr[i]= Double.parseDouble(reader.readLine());
            }
            list.add(arr);
        }
        for(int j = 0; j<1000 ;++j) {
            double sum = 0;
            for (int i = 0; i < 15; ++i) {
                sum+=list.get(i)[j];
            }
            writer.write(sum/15 + "");
            writer.newLine();
        }


    }
}
