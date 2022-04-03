import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipleIteration {
    private static final int QTY_OF_SIMULATIONS = 4;
    private static final long MAX_DURATION =  (300);
    public static void main(String[] args) throws Exception {


        File f = new File("results");
        System.out.println(f.mkdir());
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
        Map<Integer,List<Integer>> accumAliveNodes = new HashMap<>();
        Map<Integer,List<Double>> accumMaxDistances = new HashMap<>();
        //TODO en realidad creo q no hay q crear n archivos,
        // nomas calclular promedio de vivos y el radio para cada t
        while(qtyOfSims<QTY_OF_SIMULATIONS) {
            long start = System.currentTimeMillis();
            if(config.is3d())
                grid = new Grid3d(config.getW(), config.getH(), config.getD(), config.getL(), config.getCells(), Rules.lifeGame3d());
            else
                grid = new Grid2d(config.getW(), config.getH(), config.getL(), config.getCells(), Rules.lifeGame2d());



            int t =0;
            accumAliveNodes.put(t,new ArrayList<>());
            accumMaxDistances.put(t,new ArrayList<>());




                while (grid.canMove()) {
                    grid.move();
                    t++;
                    accumAliveNodes.get(t).add(grid.getCellsAlive().size());
                    accumMaxDistances.get(t).add(grid.getMaxDistFromCenter());

                    if (System.currentTimeMillis() - start > MAX_DURATION)
                        break;
                }


            config.shuffleParticles();
            qtyOfSims++;
        }
        File dynamicOutputFile = new File("multiple_iteration.txt");

        //escribo los promedios
        System.out.println(accumAliveNodes.size()==accumMaxDistances.size());
        try (PrintWriter pw = new PrintWriter(dynamicOutputFile)) {

            pw.write("alives,desviation,max_distance,desviation\n");

            for (int t = 0; t < accumAliveNodes.size(); t++) {
                int nodesAvg = 0;
                for(int i = 0; i < accumAliveNodes.size(); i++){
                    nodesAvg+=accumAliveNodes.get(t).get(i);
                }
                nodesAvg /= QTY_OF_SIMULATIONS;

                double distancesAvg = 0.0;
                for(int i = 0; i < accumMaxDistances.size(); i++){
                    distancesAvg+=accumMaxDistances.get(t).get(i);
                }
                distancesAvg /= QTY_OF_SIMULATIONS;

                pw.println(String.format("%d,%g,%g,%g",nodesAvg,0.0,distancesAvg,0.0));
            }
        }

    }
}
