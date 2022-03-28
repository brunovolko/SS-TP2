import java.util.List;

public interface Grid {
    public boolean canMove();
    public void move();
    public List<Cell> getCellsAlive();
    public double getMaxDistFromCenter();
}
