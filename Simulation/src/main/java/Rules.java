public class Rules {
    public static Rule2d lifeGame2d() {
        return ((cellToCheck, grid2d) -> {
            int aliveNeighbours = 0;
            boolean[][] grid = grid2d.getGridMatrix();
            for(int row = cellToCheck.getRow()-1; row <= cellToCheck.getRow()+1 && row > 0 && row < grid.length-1; row++)
                for(int col = cellToCheck.getCol()-1; col <= cellToCheck.getCol()+1 && col > 0 && col < grid[0].length-1; col++)
                    if(!(row == cellToCheck.getRow() && col == cellToCheck.getCol()) && grid[row][col])
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

    public static Rule3d lifeGame3d() {
        return ((cellToCheck, grid3d) -> {
            int aliveNeighbours = 0;
            boolean[][][] grid = grid3d.getGridMatrix();
            for(int row = cellToCheck.getRow()-1; row <= cellToCheck.getRow()+1 && row > 0 && row < grid.length-1; row++)
                for(int col = cellToCheck.getCol()-1; col <= cellToCheck.getCol()+1 && col > 0 && col < grid[0].length-1; col++)
                    for(int dep = cellToCheck.getDeep()-1; dep <= cellToCheck.getDeep()+1 && dep > 0 && dep < grid[0][0].length-1; dep++)
                        if(!(row == cellToCheck.getRow() && col == cellToCheck.getCol()&& dep == cellToCheck.getDeep()) && grid[row][col][dep])
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
