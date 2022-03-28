import java.util.ArrayList;
import java.util.List;

public class Grid3d implements Grid {
    boolean[][][] grid;
    double sideLength;
    Rule3d rule;
    public Grid3d(int cantCellsX, int cantCellsY, int cantCellsZ, double sideLength, List<Cell> cells, Rule3d rule) {
        this.sideLength = sideLength;
        this.rule = rule;
        grid = new boolean[cantCellsX][cantCellsY][cantCellsZ];
        for (Cell cell : cells) {
            grid[cell.getRow()][cell.getCol()][cell.getDeep()] = true;
            System.out.println(cell.getRow() + " " + cell.getCol() + " " + cell.getDeep());
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

//        for(int k= 0; k < grid[0][0].length; k++) // Con columna = 0, fila = 0
//            if(grid[0][0][k])
//                return false;
//        for(int k= 0; k < grid[0][0].length; k++) // Con columna = 0, fila = ultima
//            if(grid[grid.length-1][0][k])
//                return false;
//        for(int k= 0; k < grid[0][0].length; k++) // Con columna = ultima, fila = 0
//            if(grid[0][grid[0].length][k])
//                return false;
//        for(int k= 0; k < grid[0][0].length; k++) // Con columna = ultima, fila = ultima
//            if(grid[grid.length-1][grid[0].length-1][k])
//                return false;
//        for(int j= 0; j < grid[0].length; j++) // Con profundidad = 0, fila = 0
//            if(grid[0][j][0])
//                return false;
//        for(int j= 0; j < grid[0].length; j++) // Con profundidad = 0, fila = ultima
//            if(grid[grid.length-1][j][0])
//                return false;
//        for(int j= 0; j < grid[0].length; j++) // Con profundidad = ultima, fila = 0
//            if(grid[0][j][grid[0][0].length-1])
//                return false;
//        for(int j= 0; j < grid[0].length; j++) // Con profundidad = ultima, fila = ultima
//            if(grid[grid.length-1][j][grid[0][0].length-1])
//                return false;
//        for(int i= 0; i < grid.length; i++) // Con profundidad = 0, columna = 0
//            if(grid[i][0][0])
//                return false;
//        for(int i= 0; i < grid.length; i++) // Con profundidad = 0, columna = ultima
//            if(grid[i][grid[0].length-1][0])
//                return false;
//        for(int i= 0; i < grid.length; i++) // Con profundidad = ultima, columna = 0
//            if(grid[i][0][grid[0][0].length-1])
//                return false;
//        for(int i= 0; i < grid.length; i++) // Con profundidad = ultima, columna = ultima
//            if(grid[i][grid[0].length-1][grid[0][0].length-1])
//                return false;


        for (int j = 0; j < grid[0].length; j++) {
            for (int k = 0; k < grid[0][j].length; k++) {
                if (grid[0][j][k])
                    return false;
            }
        }
        for (int j = 0; j < grid[0].length; j++) {
            for (int k = 0; k < grid[0][j].length; k++) {
                if (grid[grid.length-1][j][k])
                    return false;
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int k = 0; k < grid[i][0].length; k++) {
                if (grid[i][0][k])
                    return false;
            }
        }
        for (int i = 0; i < grid.length; i++) {
            for (int k = 0; k < grid[i][grid[i].length-1].length; k++) {
                if (grid[i][grid[i].length-1][k])
                    return false;
            }
        }


        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j][0])
                    return false;
            }
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j][grid[i][j].length-1])
                    return false;
            }
        }


        return true;
    }

    @Override
    public void move() {

        List<Cell> deadToAlive = new ArrayList<>();
        List<Cell> aliveToDead = new ArrayList<>();
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                for (int k = 0; k < grid[i][j].length; k++) {
                    Cell cell = new Cell(i,j, grid[i][j][k]);
                    Boolean nextState = rule.checkLife(cell,this);
                    if(nextState == null)
                        continue;
                    else if(nextState)
                        deadToAlive.add(cell);
                    else
                        aliveToDead.add(cell);
                }
            }
        }
        for (Cell cell:deadToAlive)
            grid[cell.getRow()][cell.getCol()][cell.getDeep()] = true;
        for (Cell cell:aliveToDead)
            grid[cell.getRow()][cell.getCol()][cell.getDeep()] = false;

    }

    @Override
    public List<Cell> getCellsAlive() {
        List<Cell> cellsAlive = new ArrayList<>();
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[i].length; j++)
                for(int k = 0; k < grid[i][j].length; k++)
                    if (grid[i][j][k]){
                        cellsAlive.add(new Cell(i, j, k, grid[i][j][k]));
                    }
        return cellsAlive;
    }

    @Override
    public double getMaxDistFromCenter() {
        List<Cell> alivecells=getCellsAlive();
        double centX=((double)grid.length+1)/2;
        double centY=((double)grid[0].length+1)/2;
        double centZ=((double)grid[0][0].length+1)/2;
        double MaxDist=0;
        for (Cell cell:alivecells) {
            double cellXDist=Math.abs(cell.getRow()-centX);
            double cellYDist=Math.abs(cell.getCol()-centY);
            double cellZDist=Math.abs(cell.getDeep()-centZ);
            double aux=Math.sqrt(cellXDist*cellXDist+cellYDist*cellYDist+cellZDist*cellZDist);
            if (aux>MaxDist)
                MaxDist=aux;
        }
        return MaxDist;
    }

    public boolean[][][] getGridMatrix(){
        return grid;
    }
}
