

package local.tick5;

public class Pattern
{
  private String name;

  private String author;

  private int width;

  private int height;

  private int startCol;

  private int startRow;

  private String cells;

  public String getName()
  {
    return name;
  }

  public String getAuthor()
  {
    return author;
  }

  public int getWidth()
  {
    return width;
  }

  public int getHeight()
  {
    return height;
  }

  public int getStartCol()
  {
    return startCol;
  }

  public int getStartRow()
  {
    return startRow;
  }

  public String getCells()
  {
    return cells;
  }

  public Pattern(String format) throws PatternFormatException
  {
    String[] data = format.split(":");
    if (data.length != 7)
      {
        throw new PatternFormatException("Incorrect quantity of input data");
      }

    name = data[0];
    author = data[1];
    try
      {
        width = Integer.parseInt(data[2]);
        height = Integer.parseInt(data[3]);
        startCol = Integer.parseInt(data[4]);
        startRow = Integer.parseInt(data[5]);
      }
    catch (Exception e)
      {
        throw new PatternFormatException(
                                         "Width, height, StartCol and StartRow must be integer values");
      }

    cells = data[6];
  }

  public void initialise(World world) throws PatternFormatException
  {
    String[] cells2 = cells.split(" ");
    if (cells2.length + startRow > width)
      {
        throw new PatternFormatException("Row out of bounds");
      }
    if (cells2[0].length() + startCol > height)
      {
        throw new PatternFormatException("Col out of bounds");
      }
    for (int row = 0; row < cells2.length; row++)
      {

        if (cells2[row].length() != cells2[0].length())
          {
            throw new PatternFormatException("Cell row length inconsistent");
          }

        for (int col = 0; col < cells2[row].length(); col++)
          {
            if (49 != (int) cells2[row].charAt(col)
                && (int) cells2[row].charAt(col) != 48)
              {
                throw new PatternFormatException("Cells must be integer values");
              }
            world.setCell(col + startCol, row + startRow,
                          0 != (cells2[row].charAt(col) & 1));
          }
      }
  }

  public void print()
  {
    String output = name + ":" + author + ":" + width + ":" + height + ":"
                    + startCol + ":" + startRow + ":" + cells;
    System.out.println(output);
  }
}
