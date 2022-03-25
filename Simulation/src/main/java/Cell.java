public class Cell {

    // El punto (0,0,0) es abajo a la izquierda

    private int x;
    private int y;
    private int z;
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Cell(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
