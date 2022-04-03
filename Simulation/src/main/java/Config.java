
import java.io.File;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

public class Config {
    private boolean is3d;
    private int W; // W es largo del eje X
    private int H; // H es largo del eje Y
    private int D; // D es la profundidad del eje Z (De existir)
    private double L; //Lado de cada cuadradito/cubito
    private double p;
    List<Cell> cells = new ArrayList<>();


    public Config (String staticInputFilename, String dynamicInputFilename,double p) throws Exception {
        File staticInputFile = new File(staticInputFilename);
        File dynamicInputFile = new File(dynamicInputFilename);
        Scanner staticReader = new Scanner(staticInputFile);
        Scanner dynamicReader = new Scanner(dynamicInputFile);
        int aux;
        if(staticReader.hasNextLine()) {
            aux = Integer.parseInt(staticReader.nextLine());
            is3d= aux != 0;
        }


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
        this.p=p;
        shuffleParticles();





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

    public void shuffleParticles() throws Exception {

        String particles = ParticlesGenerator.generate(is3d,W,p);
        String header = particles.substring(0,particles.indexOf('\n')+1);
        if(!header.equals("t0\n"))
            throw new Exception("t0 not found");


        String row;
        int rowCount = 1;
        String[] parts;
        int i =header.length();
        while(i<particles.length()) {
            int aux = StringUtils.ordinalIndexOf(particles, "\n",rowCount+1 );
            if(aux == -1)
                aux = particles.length();
            row = particles.substring(i,aux);
            parts = row.split(" ");
            if(parts.length == 2) {
                cells.add(new Cell(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), true));
            } else if(parts.length == 3) {
                cells.add(new Cell(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), true));
            } else {
                throw new Exception("Wrong coordinates");
            }
            i+=row.length()+1;
            rowCount++;
        }
    }
}
