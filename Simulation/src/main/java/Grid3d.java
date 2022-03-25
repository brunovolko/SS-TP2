import java.util.ArrayList;
import java.util.List;

public class Grid3d implements Grid {
    boolean[][][] grid;
    double sideLength;
    public Grid3d(int cantCellsX, int cantCellsY, int cantCellsZ, double sideLength, List<Cell> cells) {
        this.sideLength = sideLength;
        grid = new boolean[cantCellsX][cantCellsY][cantCellsZ];
        for (Cell cell : cells) {
            grid[cell.getX()][cell.getY()][cell.getZ()] = true;
            System.out.println(cell.getX() + " " + cell.getY() + " " + cell.getZ());
        }
    }

    @Override
    public boolean canMove() {
        boolean isSomeoneAlive = false;
        for(int i = 0; i < grid.length && !isSomeoneAlive; i++)
            for(int j = 0; j < grid[i].length && !isSomeoneAlive; j++)
                for(int k = 0; k < grid[i][j].length && !isSomeoneAlive; k++)
                if (grid[i][j][k]) {
                    isSomeoneAlive = true;
                }
        if(!isSomeoneAlive)
            return false;

        for(int k= 0; k < grid[0][0].length; k++) // Con columna = 0, fila = 0
            if(grid[0][0][k])
                return false;
        for(int k= 0; k < grid[0][0].length; k++) // Con columna = 0, fila = ultima
            if(grid[grid.length-1][0][k])
                return false;
        for(int k= 0; k < grid[0][0].length; k++) // Con columna = ultima, fila = 0
            if(grid[0][grid[0].length][k])
                return false;
        for(int k= 0; k < grid[0][0].length; k++) // Con columna = ultima, fila = ultima
            if(grid[grid.length-1][grid[0].length-1][k])
                return false;
        for(int j= 0; j < grid[0].length; j++) // Con profundidad = 0, fila = 0
            if(grid[0][j][0])
                return false;
        for(int j= 0; j < grid[0].length; j++) // Con profundidad = 0, fila = ultima
            if(grid[grid.length-1][j][0])
                return false;
        for(int j= 0; j < grid[0].length; j++) // Con profundidad = ultima, fila = 0
            if(grid[0][j][grid[0][0].length-1])
                return false;
        for(int j= 0; j < grid[0].length; j++) // Con profundidad = ultima, fila = ultima
            if(grid[grid.length-1][j][grid[0][0].length-1])
                return false;
        for(int i= 0; i < grid.length; i++) // Con profundidad = 0, columna = 0
            if(grid[i][0][0])
                return false;
        for(int i= 0; i < grid.length; i++) // Con profundidad = 0, columna = ultima
            if(grid[i][grid[0].length-1][0])
                return false;
        for(int i= 0; i < grid.length; i++) // Con profundidad = ultima, columna = 0
            if(grid[i][0][grid[0][0].length-1])
                return false;
        for(int i= 0; i < grid.length; i++) // Con profundidad = ultima, columna = ultima
            if(grid[i][grid[0].length-1][grid[0][0].length-1])
                return false;


        return true;
    }

    @Override
    public void move() {

    }

    @Override
    public List<Cell> getCellsAlive() {
        List<Cell> cellsAlive = new ArrayList<>();
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[i].length; j++)
                for(int k = 0; k < grid[i][j].length; k++)
                    if (grid[i][j][k]){
                        cellsAlive.add(new Cell(i, j, k));
                    }
        return cellsAlive;
    }
}
