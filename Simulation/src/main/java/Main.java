import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class Main {
    private static final long MAX_DURATION =  (350);
    public static void main(String[] args) throws Exception {


        Config config;
        try {
            config = new Config("static_input.txt", "dynamic_input.txt",true);
        } catch (Exception exception) {
            System.out.println("Wrong file format. " + exception.getMessage());
            return;
        }
        Grid grid;


        if(config.is3d())
            grid = new Grid3d(config.getW(), config.getH(), config.getD(), config.getCells(), config.getRule3d());
        else
            grid = new Grid2d(config.getW(), config.getH(), config.getCells(), config.getRule2d());

        int qtyOfIterations =0;
        File dynamicOutputFile = new File("dynamic_output" + ".txt");
        try (PrintWriter pw = new PrintWriter(dynamicOutputFile)) {
            int t = 0;

            saveSnapshotToFile(grid.getCellsAlive(), t, config.is3d(), pw);

            t++;
            while (grid.canMove() && qtyOfIterations < MAX_DURATION) {
                grid.move();
                saveSnapshotToFile(grid.getCellsAlive(), t, config.is3d(), pw);
                qtyOfIterations++;
                t++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
