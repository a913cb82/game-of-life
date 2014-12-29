

package local.tick5;

import java.io.Writer;
import java.awt.Graphics;
import java.io.PrintWriter;

public class TestArrayWorld
    implements World
{
  private int generation;

  private int width;

  private int height;

  private boolean[][] cells;

  public TestArrayWorld(int w, int h)
  {
    width = w;
    height = h;
    generation = 0;
    cells = new boolean[height][width];
  }

  protected TestArrayWorld(TestArrayWorld prev)
  {
    width = prev.width;
    height = prev.height;
    generation = prev.generation + 1;
    cells = new boolean[height][width];
  }

  public boolean getCell(int col, int row)
  {
    if (row < 0 || row > cells.length - 1)
      return false;
    if (col < 0 || col > cells[row].length - 1)
      return false;

    return cells[row][col];
  }

  public void setCell(int col, int row, boolean alive)
  {
    if (row < 0 || row > cells.length - 1)
      return;
    if (col < 0 || col > cells[row].length - 1)
      return;

    cells[row][col] = alive;
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
    for (int row = 0; row < cells.length; row++)
      {
        for (int col = 0; col < cells[row].length; col++)
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

  private TestArrayWorld nextGeneration()
  {
    // Construct a new TestArrayWorld object to hold the next generation
    TestArrayWorld world = new TestArrayWorld(this);

    for (int col = 0; col < cells[0].length; col++)
      {
        for (int row = 0; row < cells.length; row++)
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
    TestArrayWorld world = this;
    for (int i = 0; i < Math.pow(2, Math.log(log2StepSize) / Math.log(2)); i++)
      {
        world = world.nextGeneration();
      }
    return world;
  }
}
