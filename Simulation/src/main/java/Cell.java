import java.util.Objects;

public class Cell {

    // El punto (0,0,0) es abajo a la izquierda

    private int row;
    private int col;
    private int depth;
    private boolean alive;
    public Cell(int row, int col, boolean alive) {
        this.row = row;
        this.col = col;
        this.alive = alive;
    }
    public Cell(int row, int col, int depth, boolean alive) {
        this.row = row;
        this.col = col;
        this.depth = depth;
        this.alive = alive;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row && col == cell.col && depth == cell.depth;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, depth);
    }
}
