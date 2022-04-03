import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class Main {
    private static final long MAX_DURATION =  (300);
    public static void main(String[] args) throws Exception {


        Config config;
        try {
            config = new Config("static_input.txt", "dynamic_input.txt",0.3);
        } catch (Exception exception) {
            System.out.println("Wrong file format. " + exception.getMessage());
            return;
        }
        Grid grid;


        long start = System.currentTimeMillis();
        if(config.is3d())
            grid = new Grid3d(config.getW(), config.getH(), config.getD(), config.getL(), config.getCells(), Rules.lifeGame3d());
        else
            grid = new Grid2d(config.getW(), config.getH(), config.getL(), config.getCells(), Rules.lifeGame2d());


        File dynamicOutputFile = new File("dynamic_output" + ".txt");
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
        }
        config.shuffleParticles();


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
