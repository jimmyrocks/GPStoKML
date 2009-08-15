/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GPStoKML;
import java.io.File;

/**
 *
 * @author jim
 */
public class ExtentionFilter extends
javax.swing.filechooser.FileFilter{
  /**
    This is the one of the methods that is declared in
    the abstract class
   */
  public boolean SetExtention(String lsExtention)
  {
      gsExtention = lsExtention;

      return (lsExtention.equalsIgnoreCase(gsExtention));
  }

  public boolean accept(File f)
  {
    //if it is a directory -- we want to show it so return true.
    if (f.isDirectory())
      return true;

    //get the extension of the file
    String extension = getExtension(f);
    //check to see if the extension is equal to "html" or "htm"
    if ((extension.equals(gsExtention.toLowerCase())))
       return true;
    //default -- fall through. False is return on all
    //occasions except:
    //a) the file is a directory
    //b) the file's extension is what we are looking for.
    return false;
  }

  /**
    Again, this is declared in the abstract class
    The description of this filter
   */
  public String getDescription()
  {
      return gsExtention + " files";
  }
  /**
    Method to get the extension of the file, in lowercase
   */
  public String getExtension(File f)
  {
    String s = f.getName();
    int i = s.lastIndexOf('.');
    if (i > 0 &&  i < s.length() - 1)
      return s.substring(i+1).toLowerCase();
    return "";
  }

  public String StripExtension(File f)
  {
    String s = f.getPath();
    int i = s.lastIndexOf('.');
    if (i > 0 &&  i < s.length() - 1)
      return s.substring(0, i);
    return "";
  }

  private String gsExtention = "*";

}
