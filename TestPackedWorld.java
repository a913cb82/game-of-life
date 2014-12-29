

package local.tick5;

import java.io.Writer;
import java.awt.Graphics;
import java.io.PrintWriter;

import local.tick2.PackedLong;

public class TestPackedWorld
    implements World
{
  private int generation;

  private int width;

  private int height;

  private long cells;

  public TestPackedWorld()
  {
    width = 8;
    height = 8;
    generation = 0;
    cells = 0L;
  }

  protected TestPackedWorld(TestPackedWorld prev)
  {
    width = prev.width;
    height = prev.height;
    generation = prev.generation + 1;
    cells = 0L;
  }

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

  private int countNeighbours(int col, int row)
  {
    int neighbours = 0;
    for (int c = col - 1; c < col + 2; c++)
      {
        for (int r = row - 1; r < row + 2; r++)
          {
            if (r != row || c != col)
              {
                if (getCell(c, r))
                  neighbours++;
              }
          }
      }
    return neighbours;
  }

  private boolean computeCell(int col, int row)
  {
    boolean liveCell = getCell(col, row);
    int neighbours = countNeighbours(col, row);

    boolean nextCell = false;
    if (liveCell && neighbours < 2)
      {
        nextCell = false;
      }
    if (liveCell && neighbours > 1 && neighbours < 4)
      {
        nextCell = true;
      }
    if (liveCell && neighbours > 3)
      {
        nextCell = false;
      }
    if ((liveCell == false) && neighbours == 3)
      {
        nextCell = true;
      }

    return nextCell;
  }

  public int getWidth()
  {
    return width;
  }

  public int getHeight()
  {
    return height;
  }

  public int getGeneration()
  {
    return generation;
  }

  public int getPopulation()
  {
    return 0;
  }

  public void print(Writer w)
  {
    PrintWriter pw = new PrintWriter(w);
    pw.println('-');
    for (int row = 0; row < width; row++)
      {
        for (int col = 0; col < height; col++)
          {
            pw.print(getCell(col, row) ? '#' : '_');
          }
        pw.println();
      }
    pw.flush();

  }

  public void draw(Graphics g, int width, int height)
  {
    // Leave empty
  }

  private TestPackedWorld nextGeneration()
  {
    // Construct a new TestArrayWorld object to hold the next generation
    TestPackedWorld world = new TestPackedWorld(this);

    for (int col = 0; col < height; col++)
      {
        for (int row = 0; row < width; row++)
          {
            if (computeCell(col, row))
              {
                world.setCell(col, row, true);
              }
          }
      }
    return world;
  }

  public World nextGeneration(int log2StepSize)
  {
    TestPackedWorld world = this;
    for (int i = 0; i < Math.pow(2, Math.log(log2StepSize) / Math.log(2)); i++)
      {
        world = world.nextGeneration();
      }
    return world;
  }
}
