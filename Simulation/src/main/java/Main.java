import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import static java.lang.System.exit;

public class Main {
    private static final int QTY_OF_SIMULATIONS = 4;
    private static final long MAX_DURATION =  (300);
    public static void main(String[] args) throws Exception {


        File f = new File("results");
        System.out.println(f.mkdir());
        for(File file: f.listFiles())
            file.delete();
        Config config;
        try {
            config = new Config("static_input.txt", "dynamic_input.txt");
        } catch (Exception exception) {
            System.out.println("Wrong file format. " + exception.getMessage());
            return;
        }
        Grid grid;


        int i = 0;
        //TODO en realidad creo q no hay q crear n archivos,
        // nomas calclular promedio de vivos y el radio para cada t
        while(i<QTY_OF_SIMULATIONS) {
            long start = System.currentTimeMillis();
            if(config.is3d())
                grid = new Grid3d(config.getW(), config.getH(), config.getD(), config.getL(), config.getCells(), Rules.lifeGame3d());
            else
                grid = new Grid2d(config.getW(), config.getH(), config.getL(), config.getCells(), Rules.lifeGame2d());


            File dynamicOutputFile = new File("results/dynamic_output" + i + ".txt");
            try (PrintWriter pw = new PrintWriter(dynamicOutputFile)) {
                int t = 0;
                saveSnapshotToFile(grid.getCellsAlive(), t, config.is3d(), pw);
                t++;
                while (grid.canMove()) {
                    grid.move();
                    saveSnapshotToFile(grid.getCellsAlive(), t, config.is3d(), pw);
                    t++;
                    if (System.currentTimeMillis() - start > MAX_DURATION)
                        break;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                break;
            }
            config.shuffleParticles();
            i++;
        }

    }

    private static void saveSnapshotToFile(List<Cell> cellsAlive, int t, boolean is3d, PrintWriter pw) {
        pw.println("t"+t);
        for (Cell cell : cellsAlive) {
            if(is3d)
                pw.println(cell.getRow() + " " + cell.getCol() + " " + cell.getDepth());
            else
                pw.println(cell.getRow() + " " + cell.getCol());
        }
    }
}
