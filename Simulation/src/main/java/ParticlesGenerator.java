

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.System.exit;

public class ParticlesGenerator {
    public static void main(String[] args) {
        if(args.length<1){
            System.err.println("Program arg needed:percentage of occupation");
            exit(1);
        }
        int n= 0;
        boolean is3d=true;
        Double p = 0.5;

        try{
            n = Integer.parseInt(args[0]);
            int aux = Integer.parseInt(args[1]);
            p = Double.parseDouble(args[2]);
            if(aux==0)
                is3d = false;


        }catch(NumberFormatException  | ArrayIndexOutOfBoundsException e){
            System.err.println("first arg:size of the grid. Second arg:is3d");
            exit(1);
        }


        double percentage = p;


        int newLimit = (int) Math.floor(n / 2.0);
        int qtyOfParticlesToGenerate = (int) Math.floor(newLimit * newLimit * percentage);

        int min = (n- newLimit)/2;
        int max = min + newLimit;
        Random random = new Random(System.currentTimeMillis());


        Set<Cell> set = new HashSet<>();
        while(set.size()<qtyOfParticlesToGenerate){
            Cell toAdd;
            if(is3d){
                toAdd = new Cell(random.ints(min, max)
                        .findFirst()
                        .getAsInt(),
                        random.ints(min, max)
                                .findFirst()
                                .getAsInt(),
                        random.ints(min, max)
                                .findFirst()
                                .getAsInt(),true
                );

            }else{
                toAdd = new Cell(random.ints(min, max)
                        .findFirst()
                        .getAsInt(),
                        random.ints(min, max)
                                .findFirst()
                                .getAsInt(),true
                );

            }


            set.add(toAdd);
        }

        File file = new File("dynamic_input.txt");
        try(PrintWriter pw = new PrintWriter(file)){
            pw.println("t0");
            int aux=0;
            for(Cell cell : set){
                if(is3d)
                    pw.print(cell.getRow() + " " + cell.getCol()+" "+cell.getDepth());
                else
                    pw.print(cell.getRow() + " " + cell.getCol());
                if(aux++ < set.size() -1 )
                    pw.print('\n');
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static String generate(boolean is3d,int n, double p) {

        /*
        int n= 0;
        boolean is3d=true;

        try{
            n = Integer.parseInt(args[0]);
            int aux = Integer.parseInt(args[1]);
            if(aux==0)
                is3d = false;


        }catch(NumberFormatException  | ArrayIndexOutOfBoundsException e){
            System.err.println("first arg:size of the grid. Second arg:is3d");
            exit(1);
        }


         */

        double percentage = p;


        int newLimit = (int) Math.floor(n / 3.0);
        int qtyOfParticlesToGenerate = (int) Math.floor(newLimit * newLimit * percentage);

        int min = (n- newLimit)/2;
        int max = min + newLimit;
        Random random = new Random(System.currentTimeMillis());


        Set<Cell> set = new HashSet<>();
        while(set.size()<qtyOfParticlesToGenerate){
            Cell toAdd;
            if(is3d){
                toAdd = new Cell(random.ints(min, max)
                        .findFirst()
                        .getAsInt(),
                        random.ints(min, max)
                                .findFirst()
                                .getAsInt(),
                        random.ints(min, max)
                                .findFirst()
                                .getAsInt(),true
                        );

            }else{
                toAdd = new Cell(random.ints(min, max)
                        .findFirst()
                        .getAsInt(),
                        random.ints(min, max)
                                .findFirst()
                                .getAsInt(),true
                );

            }


            set.add(toAdd);
        }

        //File file = new File("dynamic_input.txt");
        StringBuilder stringBuilder = new StringBuilder("t0\n");
        int aux=0;
        for(Cell cell : set){
            if(is3d)
                stringBuilder.append(cell.getRow()).append(" ").append(cell.getCol()).append(" ").append(cell.getDepth());
            else
                stringBuilder.append(cell.getRow()).append(" ").append(cell.getCol());
            if(aux++ < set.size() -1 )
                stringBuilder.append('\n');
        }

        return stringBuilder.toString();
        /*
        try(PrintWriter pw = new PrintWriter(file)){
            pw.println("t0");
           int aux=0;
           for(Cell cell : set){
               if(is3d)
                   pw.print(cell.getRow() + " " + cell.getCol()+" "+cell.getDepth());
               else
                   pw.print(cell.getRow() + " " + cell.getCol());
               if(aux++ < set.size() -1 )
                   pw.print('\n');
           }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


         */

    }
}
