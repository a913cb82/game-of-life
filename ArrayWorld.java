

package local.tick5;

public class ArrayWorld
    extends WorldImpl
{
  private boolean[][] cells;

  public ArrayWorld(int w, int h)
  {
    super(w, h);
    cells = new boolean[h][w];
  }

  protected ArrayWorld(ArrayWorld prev)
  {
    super(prev);
    cells = new boolean[prev.getHeight()][prev.getWidth()];
  }

  @Override
  public boolean getCell(int col, int row)
  {
    if (row < 0 || row > cells.length - 1)
      return false;
    if (col < 0 || col > cells[row].length - 1)
      return false;

    return cells[row][col];
  }

  @Override
  public void setCell(int col, int row, boolean alive)
  {
    if (row < 0 || row > this.getHeight() - 1)
      return;
    if (col < 0 || col > this.getWidth() - 1)
      return;

    cells[row][col] = alive;
  }

  @Override
  public WorldImpl nextGeneration()
  {
    WorldImpl world = new ArrayWorld(this);
    for (int row = 0; row < getHeight(); ++row)
      {
        for (int col = 0; col < getWidth(); ++col)
          {
            boolean nextLive = computeCell(col, row);
            world.setCell(col, row, nextLive);
          }
      }
    return world;
  }

}
