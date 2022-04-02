public class Rules {

    private static int getMooreAliveNeighbours(Cell cellToCheck, Grid2d grid2d) {
        int aliveNeighbours = 0;
        boolean[][] grid = grid2d.getGridMatrix();
        for(int row = cellToCheck.getRow()-1; row <= cellToCheck.getRow()+1 && row > 0 && row < grid.length-1; row++)
            for(int col = cellToCheck.getCol()-1; col <= cellToCheck.getCol()+1 && col > 0 && col < grid[0].length-1; col++)
                if(!(row == cellToCheck.getRow() && col == cellToCheck.getCol()) && grid[row][col])
                    aliveNeighbours++;
        return aliveNeighbours;
    }
    private static int getNeumannAliveNeighbours(Cell cellToCheck, Grid2d grid2d) {
        int aliveNeighbours = 0;
        boolean[][] grid = grid2d.getGridMatrix();
        if(cellToCheck.getCol() > 0)
            if(grid[cellToCheck.getRow()][cellToCheck.getCol()-1])
                aliveNeighbours++;
        if(cellToCheck.getCol() < grid[0].length-1)
            if(grid[cellToCheck.getRow()][cellToCheck.getCol()+1])
                aliveNeighbours++;
        if(cellToCheck.getRow() > 0)
            if(grid[cellToCheck.getRow()-1][cellToCheck.getCol()])
                aliveNeighbours++;
        if(cellToCheck.getRow() < grid.length-1)
            if(grid[cellToCheck.getRow()+1][cellToCheck.getCol()])
                aliveNeighbours++;
        return aliveNeighbours;
    }

    public static Rule2d lifeGame2d() {
        return ((cellToCheck, grid2d) -> {
            int aliveNeighbours = getMooreAliveNeighbours(cellToCheck, grid2d);


            if(cellToCheck.isAlive()) {
                if(aliveNeighbours == 2 || aliveNeighbours == 3)
                    return null; //Permanece en su estado original
                return false;
            } else {
                if(aliveNeighbours == 3)
                    return true;
                return null; //Permanece en su estado original
            }
        });
    }

    public static Rule2d couples2d() {
        return ((cellToCheck, grid2d) -> {
            int aliveNeighbours = getNeumannAliveNeighbours(cellToCheck, grid2d);


            if(cellToCheck.isAlive()) {
                if(aliveNeighbours == 1)
                    return null; //Permanece en su estado original
                return false;
            } else {
                if(aliveNeighbours == 1)
                    return true;
                return null; //Permanece en su estado original
            }
        });
    }

    public static Rule3d lifeGame3d() {
        return ((cellToCheck, grid3d) -> {
            int aliveNeighbours = 0;
            boolean[][][] grid = grid3d.getGridMatrix();
            for(int row = cellToCheck.getRow()-1; row <= cellToCheck.getRow()+1 && row > 0 && row < grid.length-1; row++)
                for(int col = cellToCheck.getCol()-1; col <= cellToCheck.getCol()+1 && col > 0 && col < grid[0].length-1; col++)
                    for(int dep = cellToCheck.getDepth()-1; dep <= cellToCheck.getDepth()+1 && dep > 0 && dep < grid[0][0].length-1; dep++)
                        if(!(row == cellToCheck.getRow() && col == cellToCheck.getCol()&& dep == cellToCheck.getDepth()) && grid[row][col][dep])
                            aliveNeighbours++;


            if(cellToCheck.isAlive()) {
                if(aliveNeighbours == 2 || aliveNeighbours == 3)
                    return null; //Permanece en su estado original
                return false;
            } else {
                if(aliveNeighbours == 3)
                    return true;
                return null; //Permanece en su estado original
            }
        });
    }
}
