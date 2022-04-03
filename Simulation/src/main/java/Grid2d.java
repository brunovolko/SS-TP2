import java.util.ArrayList;
import java.util.List;

public class Grid2d implements Grid {
    boolean[][] grid;
    double sideLength;
    Rule2d rule;
    public Grid2d(int cantCellsX, int cantCellsY, double sideLength, List<Cell> cells, Rule2d rule) {
        this.sideLength = sideLength;
        this.rule=rule;
        grid = new boolean[cantCellsX][cantCellsY];
        for (Cell cell : cells) {
            grid[cell.getRow()][cell.getCol()] = true;
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
        List<Cell> deadToAlive = new ArrayList<>();
        List<Cell> aliveToDead = new ArrayList<>();
        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid[0].length; col++) {
                Cell cell = new Cell(row,col, grid[row][col]);
                Boolean nextState = rule.checkLife(cell,this);
                if(nextState == null) {
                    continue;
                }else if(nextState) {
                    deadToAlive.add(cell);
                }else
                    aliveToDead.add(cell);
            }
        }
        for (Cell cell:deadToAlive)
            grid[cell.getRow()][cell.getCol()] = true;
        for (Cell cell:aliveToDead)
            grid[cell.getRow()][cell.getCol()] = false;

    }

    @Override
    public List<Cell> getCellsAlive() {
        List<Cell> cellsAlive = new ArrayList<>();
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[i].length; j++)
                if (grid[i][j]) {
                    cellsAlive.add(new Cell(i, j, grid[i][j]));
                }
        return cellsAlive;
    }

    @Override
    public double getMaxDistFromCenter() {
        List<Cell> alivecells=getCellsAlive();
        double centX=(double)grid.length/2;
        double centY=(double)grid[0].length/2;
        double MaxDist=0;
        for (Cell cell:alivecells) {
            double cellXDist=Math.abs(cell.getRow()-centX);
            double cellYDist=Math.abs(cell.getCol()-centY);
            double aux=Math.sqrt(cellXDist*cellXDist+cellYDist*cellYDist);
            if (aux>MaxDist)
                MaxDist=aux;
        }
        return MaxDist;
    }

    public boolean[][] getGridMatrix(){
        return grid;
    }
}
