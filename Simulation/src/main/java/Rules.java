public class Rules {

    private static int getMooreAliveNeighbours2d(Cell cellToCheck, Grid2d grid2d) {
        int aliveNeighbours = 0;
        boolean[][] grid = grid2d.getGridMatrix();
        int startRow = cellToCheck.getRow() > 0 ? cellToCheck.getRow()-1 : cellToCheck.getRow();
        int finishRow = cellToCheck.getRow() < grid.length-1 ? cellToCheck.getRow()+1 : cellToCheck.getRow();
        int startCol = cellToCheck.getCol() > 0 ? cellToCheck.getCol()-1 : cellToCheck.getCol();
        int finishCol = cellToCheck.getCol() < grid[0].length-1 ? cellToCheck.getCol()+1 : cellToCheck.getCol();

        for(int row = startRow; row <= finishRow; row++)
            for(int col = startCol; col <= finishCol; col++)
                if(!(row == cellToCheck.getRow() && col == cellToCheck.getCol()) && grid[row][col])
                    aliveNeighbours++;
        return aliveNeighbours;
    }
    private static int getMooreAliveNeighbours3d(Cell cellToCheck, Grid3d grid3d) {
        int aliveNeighbours = 0;
        boolean[][][] grid = grid3d.getGridMatrix();
        int startRow = cellToCheck.getRow() > 0 ? cellToCheck.getRow()-1 : cellToCheck.getRow();
        int finishRow = cellToCheck.getRow() < grid.length-1 ? cellToCheck.getRow()+1 : cellToCheck.getRow();
        int startCol = cellToCheck.getCol() > 0 ? cellToCheck.getCol()-1 : cellToCheck.getCol();
        int finishCol = cellToCheck.getCol() < grid[0].length-1 ? cellToCheck.getCol()+1 : cellToCheck.getCol();
        int startDepth = cellToCheck.getDepth() > 0 ? cellToCheck.getDepth()-1 : cellToCheck.getDepth();
        int finishDepth = cellToCheck.getDepth() < grid[0][0].length-1 ? cellToCheck.getDepth()+1 : cellToCheck.getDepth();

        for(int row = startRow; row <= finishRow; row++)
            for(int col = startCol; col <= finishCol; col++)
                for(int depth = startDepth; depth <= finishDepth; depth++)
                    if(!(row == cellToCheck.getRow() && col == cellToCheck.getCol() && depth == cellToCheck.getDepth()) && grid[row][col][depth])
                        aliveNeighbours++;
        return aliveNeighbours;
    }
    private static int getNeumannAliveNeighbours2d(Cell cellToCheck, Grid2d grid2d) {
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
    private static int getNeumannAliveNeighbours3d(Cell cellToCheck, Grid3d grid3d) {
        int aliveNeighbours = 0;
        boolean[][][] grid = grid3d.getGridMatrix();
        if(cellToCheck.getCol() > 0)
            if(grid[cellToCheck.getRow()][cellToCheck.getCol()-1][cellToCheck.getDepth()])
                aliveNeighbours++;
        if(cellToCheck.getCol() < grid[0].length-1)
            if(grid[cellToCheck.getRow()][cellToCheck.getCol()+1][cellToCheck.getDepth()])
                aliveNeighbours++;
        if(cellToCheck.getRow() > 0)
            if(grid[cellToCheck.getRow()-1][cellToCheck.getCol()][cellToCheck.getDepth()])
                aliveNeighbours++;
        if(cellToCheck.getRow() < grid.length-1)
            if(grid[cellToCheck.getRow()+1][cellToCheck.getCol()][cellToCheck.getDepth()])
                aliveNeighbours++;
        if(cellToCheck.getDepth() > 0)
            if(grid[cellToCheck.getRow()][cellToCheck.getCol()][cellToCheck.getDepth()-1])
                aliveNeighbours++;
        if(cellToCheck.getDepth() < grid[0][0].length-1)
            if(grid[cellToCheck.getRow()][cellToCheck.getCol()][cellToCheck.getDepth()+1])
                aliveNeighbours++;
        return aliveNeighbours;
    }

    public static Rule2d lifeGame2d() {
        return ((cellToCheck, grid2d) -> {
            int aliveNeighbours = getMooreAliveNeighbours2d(cellToCheck, grid2d);


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

    public static Rule2d nobodyAlone2d() {
        return ((cellToCheck, grid2d) -> {
            int aliveNeighbours = getMooreAliveNeighbours2d(cellToCheck, grid2d);

            if(cellToCheck.isAlive()) {
                if(aliveNeighbours >= 1)
                    return null; //Permanece en su estado original
                return false;
            } else {
                if(aliveNeighbours == 1)
                    return true;
                return null; //Permanece en su estado original
            }
        });
    }

    public static Rule2d couples2d() {
        return ((cellToCheck, grid2d) -> {
            int aliveNeighbours = getNeumannAliveNeighbours2d(cellToCheck, grid2d);


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
            int aliveNeighbours = getMooreAliveNeighbours3d(cellToCheck, grid3d);


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

    public static Rule3d fixedNumber3d() {
        return ((cellToCheck, grid3d) -> {
            int aliveNeighbours = getMooreAliveNeighbours3d(cellToCheck, grid3d);

            if(cellToCheck.isAlive()) {
                if(aliveNeighbours == 8)
                    return null; //Permanece en su estado original
                return false;
            } else {
                if(aliveNeighbours == 1)
                    return true;
                return null; //Permanece en su estado original
            }
        });
    }

    public static Rule3d spatialMove3d() {
        return ((cellToCheck, grid3d) -> {
            int aliveNeighbours = getNeumannAliveNeighbours3d(cellToCheck, grid3d);

            if(cellToCheck.isAlive()) {
                if(aliveNeighbours >= 1)
                    return null;
                return false;
            } else {
                if(cellToCheck.getCol() < grid3d.getGridMatrix()[0].length/2) {
                    if(aliveNeighbours >= 3)
                        return true;
                } else {
                    if(aliveNeighbours >= 1)
                        return true;
                }

                return null; //Permanece en su estado original
            }
        });
    }
}
