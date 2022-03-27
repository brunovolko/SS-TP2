
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.System.exit;

public class ParticlesGenerator {
    public static void main(String[] args) {
        int n= 0;
        try{
            n = Integer.parseInt(args[0]);
        }catch(NumberFormatException  | ArrayIndexOutOfBoundsException e){
            System.err.println("must pass size of the grid");
            exit(1);
        }


        double percentage = 0.6;

        int qtyOfParticlesToGenerate = (int) Math.floor(n * n * percentage);

        int newLimit = (int) Math.floor(n / 2.5);
        int min = (n- newLimit)/2;
        int max = min + newLimit;
        Random random = new Random(System.currentTimeMillis());
        List<Pair<Integer,Integer>> list = new ArrayList<>();
        for(int i=0;i<qtyOfParticlesToGenerate;i++){

            Pair<Integer,Integer> pair = new Pair(random.ints(min, max)
                    .findFirst()
                    .getAsInt(),
                    random.ints(min, max)
                            .findFirst()
                            .getAsInt());
            list.add(pair);

        }



        File file = new File("particles.txt");
        try(PrintWriter pw = new PrintWriter(file)){
           for(int i = 0; i <list.size();i++){
               pw.print(list.get(i).getKey() +" " + list.get(i).getValue());
               if(i<list.size()-1)
                   pw.print('\n');
           }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
