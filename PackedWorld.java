

package local.tick5;

import local.tick2.PackedLong;

public class PackedWorld
    extends WorldImpl
{
  private long cells;

  public PackedWorld()
  {
    super(8, 8);
    cells = 0L;
  }

  protected PackedWorld(PackedWorld prev)
  {
    super(prev);
    cells = 0L;
  }

  @Override
  public boolean getCell(int col, int row)
  {
    if (col < 0 || col > 7 || row > 7 || row < 0)
      {
        return false;
      }
    else
      {
        int bit = col + row * 8;
        return PackedLong.get(cells, bit);
      }
  }

  @Override
  public void setCell(int col, int row, boolean alive)
  {
    if (col < 0 || col > 7 || row > 7 || row < 0)
      {
        return;
      }
    else
      {
        int bit = col + row * 8;
        cells = PackedLong.set(cells, bit, alive);
      }
  }

  @Override
  public WorldImpl nextGeneration()
  {
    // Construct a new TestArrayWorld object to hold the next generation
    WorldImpl world = new PackedWorld(this);

    for (int col = 0; col < this.getHeight(); col++)
      {
        for (int row = 0; row < this.getWidth(); row++)
          {
            if (computeCell(col, row))
              {
                world.setCell(col, row, true);
              }
          }
      }
    return world;
  }
}
