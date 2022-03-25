import java.util.ArrayList;
import java.util.List;

public class Grid2d implements Grid {
    boolean[][] grid;
    double sideLength;
    public Grid2d(int cantCellsX, int cantCellsY, double sideLength, List<Cell> cells) {
        this.sideLength = sideLength;
        grid = new boolean[cantCellsX][cantCellsY];
        for (Cell cell : cells) {
            grid[cell.getX()][cell.getY()] = true;
        }

    }

    @Override
    public boolean canMove() {
        boolean isSomeoneAlive = false;
        for(int i = 0; i < grid.length && !isSomeoneAlive; i++)
            for(int j = 0; j < grid[i].length && !isSomeoneAlive; j++)
                if (grid[i][j]) {
                    isSomeoneAlive = true;
                }
        if(!isSomeoneAlive)
            return false;

        for(int i = 0; i < grid.length; i++) // Con columna = 0
            if(grid[i][0])
                return false;
        for(int i = 0; i < grid.length; i++) // Con columna = ultima
            if(grid[i][grid[i].length-1])
                return false;
        for(int j = 0; j < grid[0].length; j++) // Con fila = 0
            if(grid[0][j])
                return false;
        for(int j = 0; j < grid[0].length; j++) // Con fila = ultima
            if(grid[grid.length-1][j])
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
                if (grid[i][j]) {
                    cellsAlive.add(new Cell(i, j));
                }
        return cellsAlive;
    }
}
