
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

        int newLimit = (int)Math.ceil(Math.sqrt(qtyOfParticlesToGenerate));
        int min = (n- newLimit)/2;
        int max = min + newLimit;
        Random random = new Random(System.currentTimeMillis());
        Set<Pair<Integer,Integer>> set = new HashSet<>();
        for(int i=0;i<qtyOfParticlesToGenerate;){


            Pair<Integer,Integer> pair = new Pair<>(random.ints(min, max)
                    .findFirst()
                    .getAsInt(),
                    random.ints(min, max)
                            .findFirst()
                            .getAsInt());

            if(set.add(pair))
                i++;
        }



        File file = new File("dynamic_input.txt");
        try(PrintWriter pw = new PrintWriter(file)){
            pw.println("t0");
           int i=0;
           for(Pair<Integer,Integer> pair : set){
               pw.print(pair.getKey() + " " + pair.getValue());
               if(i++ < set.size() -1 )
                   pw.print('\n');
           }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
