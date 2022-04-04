import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.exit;

public class MultipleIteration {
    private static final int QTY_OF_SIMULATIONS = 9;
    private static final long MAX_DURATION =  (350);
    public static void main(String[] args) throws Exception {


        File f = new File("results");
        for(File file: f.listFiles())
            file.delete();
        Config config;
        try {
            config = new Config("static_input.txt", "dynamic_input.txt",0.3);
        } catch (Exception exception) {
            System.out.println("Wrong file format. " + exception.getMessage());
            return;
        }
        Grid grid;


        int qtyOfSims = 0;
        int qtyOfIterations = 0;
        Map<Integer,List<Integer>> accumAliveNodes = new HashMap<>();
        Map<Integer,List<Double>> accumMaxDistances = new HashMap<>();

        while(qtyOfSims<QTY_OF_SIMULATIONS) {
            if(config.is3d())
                grid = new Grid3d(config.getW(), config.getH(), config.getD(), config.getL(), config.getCells(), Rules.lifeGame3d());
            else
                grid = new Grid2d(config.getW(), config.getH(), config.getL(), config.getCells(), Rules.lifeGame2d());



            int t =0;
            accumAliveNodes.putIfAbsent(t,new ArrayList<>());
            accumMaxDistances.putIfAbsent(t,new ArrayList<>());
            accumAliveNodes.get(t).add(grid.getCellsAlive().size());
            accumMaxDistances.get(t).add(grid.getMaxDistFromCenter());



                while (grid.canMove() && qtyOfIterations < MAX_DURATION) {
                    grid.move();
                    t++;
                    if(!accumMaxDistances.containsKey(t))
                        accumMaxDistances.put(t,new ArrayList<>());
                    if(!accumAliveNodes.containsKey(t))
                        accumAliveNodes.put(t,new ArrayList<>());


                    accumAliveNodes.get(t).add(grid.getCellsAlive().size());
                    accumMaxDistances.get(t).add(grid.getMaxDistFromCenter());

                    qtyOfIterations++;
                }


            config.shuffleParticles();
            qtyOfSims++;
        }
        File dynamicOutputFile = new File("multiple_iteration.txt");

        //escribo los promedios
        System.out.println(accumAliveNodes.size()==accumMaxDistances.size());
        try (PrintWriter pw = new PrintWriter(dynamicOutputFile)) {

            pw.write("alives\tdesviation\tmax_distance\tdesviation\n");

            for (int t = 0; t < accumAliveNodes.size(); t++) {
                int nodesAvg = 0;
                for(int i = 0; i < accumAliveNodes.get(t).size(); i++){
                    nodesAvg+=accumAliveNodes.get(t).get(i);
                }
                nodesAvg /= QTY_OF_SIMULATIONS;

                double desv1 = 0.0;

                for(int i = 0; i < accumAliveNodes.get(t).size(); i++){
                    desv1+= (accumAliveNodes.get(t).get(i) - nodesAvg) * (accumAliveNodes.get(t).get(i) - nodesAvg);
                }
                desv1 /= QTY_OF_SIMULATIONS;

                double distancesAvg = 0.0;
                for(int i = 0; i < accumAliveNodes.get(t).size(); i++){
                    desv1+= Math.pow(accumAliveNodes.get(t).get(i) - nodesAvg, 2);

                }
                desv1 = Math.sqrt(desv1/QTY_OF_SIMULATIONS);

                double desv2 =0.0;

                for(int i = 0; i < accumMaxDistances.get(t).size(); i++){
                    desv2+= Math.pow(accumMaxDistances.get(t).get(i) - distancesAvg, 2);

                }
                desv2 = Math.sqrt(desv2/QTY_OF_SIMULATIONS);






                pw.println(String.format("%d\t%g\t%g\t%g",nodesAvg,desv1,distancesAvg,desv2));
            }
        }

    }
}
