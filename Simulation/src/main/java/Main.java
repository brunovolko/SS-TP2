import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Config config;
        try {
            config = new Config("static_input.txt", "dynamic_input.txt");
        } catch (Exception exception) {
            System.out.println("Wrong file format. " + exception.getMessage());
            return;
        }
        Grid grid;
        if(config.is3d())
            grid = new Grid3d(config.getW(), config.getH(), config.getD(), config.getL(), config.getCells(), null);
        else
            grid = new Grid2d(config.getW(), config.getH(), config.getL(), config.getCells(), Rules.lifeGame2d());


        File dynamicOutputFile = new File("dynamic_output.txt");
        try(PrintWriter pw = new PrintWriter(dynamicOutputFile)){
            int t = 0;
            saveSnapshotToFile(grid.getCellsAlive(), t, config.is3d(), pw);
            t++;
            while(grid.canMove()) {
                grid.move();
                saveSnapshotToFile(grid.getCellsAlive(), t, config.is3d(), pw);
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
                pw.println(cell.getRow() + " " + cell.getCol() + " " + cell.getDeep());
            else
                pw.println(cell.getRow() + " " + cell.getCol());
        }
    }
}
