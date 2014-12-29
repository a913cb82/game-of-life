

package local.tick5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

public class PatternLoader
{
  public static List<Pattern> load(Reader r) throws IOException
  {
    BufferedReader buff = new BufferedReader(r);
    String line = buff.readLine();
    Pattern p = null;
    List<Pattern> patterns = new LinkedList<Pattern>();
    // TODO
    // Read available patterns from reader object
    while (line != null)
      {
        try
          {
            // Convert pattern strings into Pattern Objects
            // Store valid patterns in a List
            p = new Pattern(line);
            patterns.add(p);
          }
        catch (Exception e)
          {

          }
        line = buff.readLine();
      }
    // Return List
    return patterns;
  }

  public static List<Pattern> loadFromURL(String url) throws IOException
  {
    URL destination = new URL(url);
    URLConnection conn = destination.openConnection();
    return load(new InputStreamReader(conn.getInputStream()));
  }

  public static List<Pattern> loadFromDisk(String filename) throws IOException
  {
    return load(new FileReader(filename));
  }
}
