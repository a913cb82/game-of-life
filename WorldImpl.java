

package local.tick5;

import java.awt.Color;
import java.awt.Graphics;
import java.io.PrintWriter;
import java.io.Writer;

public abstract class WorldImpl
    implements World
{
  private int generation;

  private int width;

  private int height;

  public WorldImpl(int w, int h)
  {
    width = w;
    height = h;
    generation = 0;
  }

  protected WorldImpl(WorldImpl prev)
  {
    width = prev.getWidth();
    height = prev.getHeight();
    generation = prev.getGeneration() + 1;
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

  protected String getCellAsString(int col, int row)
  {
    return getCell(col, row) ? "#" : "_";
  }

  protected Color getCellAsColour(int col, int row)
  {
    return getCell(col, row) ? Color.BLACK : Color.WHITE;
  }

  public void draw(Graphics g, int width, int height)
  {
    int worldWidth = getWidth();
    int worldHeight = getHeight();

    double colScale = (double) width / (double) worldWidth;
    double rowScale = (double) height / (double) worldHeight;

    for (int col = 0; col < worldWidth; ++col)
      {
        for (int row = 0; row < worldHeight; ++row)
          {
            int colPos = (int) (col * colScale);
            int rowPos = (int) (row * rowScale);
            int nextCol = (int) ((col + 1) * colScale);
            int nextRow = (int) ((row + 1) * rowScale);

            if (g.hitClip(colPos, rowPos, nextCol - colPos, nextRow - rowPos))
              {
                g.setColor(getCellAsColour(col, row));
                g.fillRect(colPos, rowPos, nextCol - colPos, nextRow - rowPos);
              }
          }
      }
  }

  public World nextGeneration(int log2StepSize)
  {
    WorldImpl world = this;
    for (int i = 0; i < 1 << log2StepSize; i++)
      {
        world = world.nextGeneration();
      }
    return world;
  }

  public void print(Writer w)
  {
    PrintWriter pw = new PrintWriter(w);
    pw.println('-');
    for (int row = 0; row < height; row++)
      {
        for (int col = 0; col < width; col++)
          {
            pw.print(getCell(col, row) ? '#' : '_');
          }
        pw.println();
      }
    pw.flush();
  }

  protected int countNeighbours(int col, int row)
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

  protected boolean computeCell(int col, int row)
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

  // Return true is cell(col, row) is alive
  public abstract boolean getCell(int col, int row);

  // set a cell to live or dead
  public abstract void setCell(int col, int row, boolean alive);

  // step forward one generation
  protected abstract WorldImpl nextGeneration();
}
