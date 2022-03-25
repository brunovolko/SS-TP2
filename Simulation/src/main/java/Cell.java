public class Cell {

    // El punto (0,0,0) es abajo a la izquierda

    private int row;
    private int col;
    private int deep;
    private boolean alive;
    public Cell(int row, int col, boolean alive) {
        this.row = row;
        this.col = col;
        this.alive = alive;
    }
    public Cell(int row, int col, int deep, boolean alive) {
        this.row = row;
        this.col = col;
        this.deep = deep;
        this.alive = alive;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getDeep() {
        return deep;
    }

    public boolean isAlive() {
        return alive;
    }
}
