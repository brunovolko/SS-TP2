import java.io.File;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

public class Config {
    private boolean is3d;
    private int W; // W es largo del eje X
    private int H; // H es largo del eje Y
    private int D; // D es la profundidad del eje Z (De existir)
    private double L; //Lado de cada cuadradito/cubito

    List<Cell> cells = new ArrayList<>();


    public Config (String staticInputFilename, String dynamicInputFilename) throws Exception {
        File staticInputFile = new File(staticInputFilename);
        File dynamicInputFile = new File(dynamicInputFilename);
        Scanner staticReader = new Scanner(staticInputFile);
        Scanner dynamicReader = new Scanner(dynamicInputFile);
        if(staticReader.hasNextLine())
            is3d = Boolean.parseBoolean(staticReader.nextLine());
        else
            throw new Exception("is3d not found");

        if(staticReader.hasNextLine())
            W = Integer.parseInt(staticReader.nextLine());
        else
            throw new Exception("W not found");

        if(staticReader.hasNextLine())
            H = Integer.parseInt(staticReader.nextLine());
        else
            throw new Exception("H not found");

        if(staticReader.hasNextLine()){
            if(is3d)
                D = Integer.parseInt(staticReader.nextLine());
            else
                L = Double.parseDouble(staticReader.nextLine());
        } else {
            throw new Exception("D or L not found");
        }
        if (is3d) {
            if(staticReader.hasNextLine()) {
                L = Double.parseDouble(staticReader.nextLine());
            } else {
                throw new Exception("L not found");
            }
        }

        if(dynamicReader.hasNextLine()) {
            dynamicReader.nextLine(); // t0
        } else {
            throw new Exception("t0 not found");
        }

        String row;
        String[] parts;
        while(dynamicReader.hasNextLine()) {
            row = dynamicReader.nextLine();
            parts = row.split(" ");
            if(parts.length == 2) {
                cells.add(new Cell(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), true));
            } else if(parts.length == 3) {
                cells.add(new Cell(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), true));
            } else {
                throw new Exception("Wrong coordinates");
            }
        }





        staticReader.close();
        dynamicReader.close();

    }

    public boolean is3d() {
        return is3d;
    }

    public int getW() {
        return W;
    }

    public int getH() {
        return H;
    }

    public int getD() {
        return D;
    }

    public double getL() {
        return L;
    }

    public List<Cell> getCells() {
        return cells;
    }
}
