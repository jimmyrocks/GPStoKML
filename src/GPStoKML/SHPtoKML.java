/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GPStoKML;
import java.io.*;

/**
 *
 * @author jim
 */
public class SHPtoKML {


    public int ConvertFiles(String lsShape, String lsDBF, int liColumn)
    {
        int liReturnValue = 0;

	int liFile1 = liOpenDBFFile(lsDBF);
	if (liFile1>0)
	{
		//liReturnValue = liOpenSHPFile(lsShape, lsDBF, liColumn);
            System.out.println("File good, ready to be opened!");
	}
    return liReturnValue;
    }

    private int liOpenDBFFile(String lsDBF)
    {

	int liReturnValue = 0;
        int i=0;
        File file = new File (lsDBF);
        String lsString;
        char lcChar[] = new char[256];
        int liRecords = 0;

        try
            {
                // Wrap the FileInputStream with a DataInputStream
                    BufferedInputStream DBF_input = new BufferedInputStream (new FileInputStream (file));
                    BufferedReader DBF_reader = new BufferedReader(new InputStreamReader(DBF_input));
                    //DBF_input.close ();

                    DBF_reader.read(lcChar, 4, 4);
                    System.out.println ("roar: " + lcChar);
                    liRecords += (int)lcChar[0];
                    DBF_reader.read(lcChar, 5, 1);
                    liRecords += (int)lcChar[0]*256;
                    DBF_reader.read(lcChar, 6, 1);
                    liRecords += (int)lcChar[0]*256*256;
                    DBF_reader.read(lcChar, 7, 1);
                    liRecords += (int)lcChar[0]*256*256*256;
                    System.out.println (liRecords);


                while ((lsString = DBF_reader.readLine()) != null)
                {
                    //Print the content on the console
                    //System.out.println (lsString);
                    liReturnValue = 1;
                }
            }
        catch  (IOException e)
            {
               System.out.println ( "IO Exception =: " + e );
            }

	return liReturnValue;
}



}
