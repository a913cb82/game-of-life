

package local.tick5;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class RefactorLife
{

  public static void main(String[] args) throws Exception
  {
    args = new String[] {
                         "--aging",
                         "http://example.invalid/teaching/current/ProgJava/life.txt",
                         "27" };

    int i = 0;
    if (args.length == 3)
      {
        i++;
      }

    List<Pattern> patterns = null;
    if (args[i].startsWith("http://"))
      {
        patterns = PatternLoader.loadFromURL(args[i]);
      }
    else
      {
        patterns = PatternLoader.loadFromDisk(args[i]);
      }

    try
      {
        int index = 0;
        if (args.length > 0)
          {
            index = Integer.parseInt(args[i + 1]);
          }
        if (index < 0)
          {
            throw new Exception("Index less than 0");
          }
        Pattern p = patterns.get(index);
        World world = new ArrayWorld(p.getWidth(), p.getHeight());
        if (args[0].equals("--long"))
          {
            world = new PackedWorld();
          }
        else if (args[0].equals("--aging"))
          {
            world = new AgingWorld(p.getWidth(), p.getHeight());
          }
        else if (args.length != 2 && ! args[0].equals("--array"))
          {
            throw new Exception("Unknown type of optional argument");
          }
        p.initialise(world);

        // PLAY
        int userResponce = 0;
        WorldViewer viewer = new WorldViewer();
        while (userResponce != 'q')
          {
            Writer w = new OutputStreamWriter(System.out);
            world.print(w);
            viewer.show(world);
            userResponce = System.in.read();
            world = world.nextGeneration(0);
          }
        viewer.close();
      }
    catch (Exception e)
      {
        System.out.println(e.getMessage());
      }
  }
}
