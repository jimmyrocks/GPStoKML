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
public class GPStoKML {

    // /////////////////////////////////////
    //

        //KML Strings
        String KML_Header[] = new String[2];
        String KML_Footer = null;
        String KML_Style_Defs[] = new String[3];
        String KML_Style_Elements[] = new String[3];
        String KML_Placemark[] = new String[4];

    //
    // /////////////////////////////////////


private void DefineKML()
{
    // /////////////////////////////////////
    //

        //Define the XML style code
        KML_Header[0] = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n"
                      + "<kml xmlns=\"http://www.opengis.net/kml/2.2\">"  + "\n"
                      + "<Document>"  + "\n"
                      + "<name>";
        KML_Header[1] = "</name>"  + "\n"
                      + "  <open>0</open> " + "\n";

        KML_Footer = "\n\t\t</Document>\n\t</kml>";

        KML_Style_Defs[0] = "\n\t\t\t<StyleMap id=\"color_";
        KML_Style_Defs[1] = "\">\n"
                          + "\t\t\t\t<Pair>\n"
                          + "\t\t\t\t\t<key>normal</key>\n"
                          + "\t\t\t\t\t\t<styleUrl>#default_color_";
        KML_Style_Defs[2] = "</styleUrl>\n"
                          + "\t\t\t\t</Pair>\n"
                          + "\t\t\t</StyleMap>\n";


        KML_Style_Elements[0] = "\t<Style id=\"default_color_";

        KML_Style_Elements[1] = "\">\n"
                              + "\t\t<LineStyle>\n"
                              + "\t\t\t<color>";

        KML_Style_Elements[2] = "</color>\n"
                              + "\t\t\t<width>5</width>\n"
                              + "\t\t</LineStyle>\n"
                              + "\t</Style>\n";

        KML_Placemark[0] = "\t<Placemark>\n"
                         + "\t\t<name>";

        KML_Placemark[1] = "</name>\n"
                         + "\t\t<styleUrl>#default_color_";

        KML_Placemark[2] = "</styleUrl>\n"
                         + "\t\t<LineString>\n"
                         + "\t\t\t<extrude>1</extrude>\n"
                         + "\t\t\t<tessellate>1</tessellate>\n"
                         + "\t\t\t<coordinates>\n"
                         + "\t\t\t\t";

         //%s,%s,%s \n%s,%s,%s \n"


        KML_Placemark[3] = "\t\t\t</coordinates>\n"
                         + "\t\t</LineString>\n"
                         + "\t</Placemark>\n";

    //
    // /////////////////////////////////////
}

private String lfCreateStyleDefs(int liStyles)
{
    StringBuffer sbReturnValue = new StringBuffer();
    String iString = "";

    	//Loop through the Colors, and write out the values!
	for (int i=1; i<liStyles+1; i++)
        {
            iString = Integer.toString(i);
            sbReturnValue.append(KML_Style_Defs[0] + iString + KML_Style_Defs[1] + iString + KML_Style_Defs[2]);
        }

        return sbReturnValue.toString();

}

private String lfCreateStyles(int liStyles)
{
        int colorMax = 255;
        int cGreen = 0;
        int cRed = 0;
        //int cBlue = 0;
        String sGreen = "";
        String sRed = "";
        //String sBlue = ""; //Currently we don't use blue
        
        String KMLColor = "";

        StringBuffer sbReturnValue = new StringBuffer();
        
	//Loop through the Colors, and write out the values!
	for (int i=1; i<liStyles+1; i++)
            {
		int cRatio = ((i*100)/liStyles);

		if (cRatio > 66)
                {
			//Green Is Full
			cGreen = colorMax;
			//Red is Fading In
			cRed = ((100-cRatio)*colorMax)/33; //used to be 17
                }
                else if (cRatio >= 33)
                {
			//Green Is Fading Out
			cGreen = colorMax-(((83-cRatio)*colorMax)/50);
			//Red is Full
			cRed = colorMax;
                }
                else if (cRatio >= 00)
                {
			//Green Is Gone
			cGreen = 0;
                        //Red is Fading Out
			cRed = colorMax-(((33-cRatio)*colorMax)/33);
                }
                
                //Convert to Hex
                    sGreen = Integer.toString(cGreen, 16).toUpperCase();
                    sRed = Integer.toString(cRed, 16).toUpperCase();
                
                //Add Leadin Zeros
                    if (sGreen.length()<2) sGreen = 0 + sGreen;
                    if (sRed.length()<2) sRed = 0 + sRed;
		
		KMLColor = "CC00" + sGreen + sRed; //ABGR
		
                sbReturnValue.append(KML_Style_Elements[0] + Integer.toString(i) + KML_Style_Elements[1] + KMLColor + KML_Style_Elements[2]);
                
		//outf.write(KML_Style_Elements % (i,KMLColor)) #Style Information
            }
        
        return sbReturnValue.toString();
}

private String lfCreateHeader(String s)
{
    String lsReturnValue = "";
    String lsFilename = "";
    
    //Determine the file name from its path
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1)
            {
              lsFilename = s.substring(0, i);
            }

        int j = lsFilename.lastIndexOf("/");
        int k = lsFilename.lastIndexOf("\\");

        if (k>j) j=k; //Takes the greater of the two, we do this for mac or PC file paths

        if (j > 0 &&  j < lsFilename.length() - 1)
            {
              lsFilename = lsFilename.substring(j+1);
            }
        loConversionStatus.setWindowName("Creating " + lsFilename + "...");
    
    //Create the header
        lsReturnValue = KML_Header[0] + lsFilename + KML_Header[1];
        
    //Return the value
        return lsReturnValue;

}

private String lfCreateLines(String lsSource, int liCodeBy, int liStyles)
{
    String lsLines = "";
    String lsFileExtention = "";
    
    //Determine if we're working with a GPX or a CSV
    int i = lsSource.lastIndexOf('.');
    lsFileExtention = lsSource.substring(i+1);
    
    
    if (lsFileExtention.equalsIgnoreCase("GPX"))
    {
        lsLines = lfCreateLinesGPX(lsSource, liCodeBy, liStyles);
    }
    else if (lsFileExtention.equalsIgnoreCase("CSV"))
    {
        //TO ADD, CSV SUPPORT!
        lsLines = lfCreateLinesCSV(lsSource, liCodeBy, liStyles);
    }
    
    return lsLines;
}

private String lfCreateLinesGPX(String lsSource, int liCodeBy, int liStyles)
{
    
//System.out.println(lsSource);
    String lsReturnValue = "";
    StringBuffer sbReturnValue = new StringBuffer();
    ParseXML trk = new ParseXML(lsSource,"gpx");

    loConversionStatus.setVals(true, 0, 0);

    //Determine the Max and Min Speeds
    Float lfCurrValue = Float.parseFloat("0");
    Float lfMaxValue = Float.parseFloat("0");
    Float lfMinValue = Float.parseFloat("0");
    Float lfLastValue = Float.parseFloat("0");
    int liPointCount = 0;

    for(ParseXML trkseg:trk.child("trk").children("trkseg"))
        {
            for(ParseXML trkpt:trkseg.children("trkpt"))
                {
                    switch (liCodeBy)
                        {
                            case 2: //Ele
                                lfCurrValue = Float.parseFloat(trkpt.child("ele").content().toString());
                                break;
                            case 3: //Acc
                                lfCurrValue = Float.parseFloat(trkpt.child("speed").content().toString()) - lfLastValue;
                                lfLastValue = Float.parseFloat(trkpt.child("speed").content().toString());
                                break;
                            case 4: //Time
                                lfCurrValue++;
                                break;
                            default: //Also 1 // Speed
                                lfCurrValue = Float.parseFloat(trkpt.child("speed").content().toString());
                                break;
                        }

                    if (lfCurrValue<lfMinValue) lfMinValue = lfCurrValue;
                    if (lfCurrValue>lfMaxValue) lfMaxValue = lfCurrValue;
                    liPointCount++;
                }
    }

    loConversionStatus.setVals(true, liPointCount, 0);

    //More Floats!
        Float lfLat = Float.parseFloat("0");
        Float lfLon = Float.parseFloat("0");
        Float lfEle = Float.parseFloat("0");
        Float lfSpd = Float.parseFloat("0");
        Float lfVal = Float.parseFloat("0");
        Float lfLastLat = Float.parseFloat("0");
        Float lfLastLon = Float.parseFloat("0");
        Float lfLastEle = Float.parseFloat("0");
        Float lfLastSpd = Float.parseFloat("0");
        String lsDescription = "";
        int liColorLevel = 0;
        int liPointCount2 = 0;
        boolean FirstRun = true;

    //Determine the Lines
    for(ParseXML trkseg:trk.child("trk").children("trkseg"))
        {
            for(ParseXML trkpt:trkseg.children("trkpt"))
                {
                    //System.out.println("lfSpd: " + lfSpd + ", lfMaxSpeed: " + lfMaxValue + ", lfMinSpeed: " + lfMinValue +", tot: " + Math.round(((lfSpd / (lfMaxValue - lfMinValue)) * (liStyles-1)) +1));

                    //lfSpd = Float.parseFloat(trkpt.child("speed").content().toString());
                    lsDescription = trkpt.child("desc").content().toString();
                    lfLat = Float.parseFloat(trkpt.string("lat"));
                    lfLon = Float.parseFloat(trkpt.string("lon"));
                    //lfEle = Float.parseFloat(trkpt.child("ele").content().toString());

                    //Determine the Color
                    switch (liCodeBy)
                        {
                            case 2: //Ele
                                lfEle = Float.parseFloat(trkpt.child("ele").content().toString());
                                lfVal = lfEle;
                                break;
                            case 3: //Acc
                                lfSpd = Float.parseFloat(trkpt.child("speed").content().toString());
                                lfVal = lfSpd - lfLastSpd;
                                lfLastSpd = lfSpd;
                                break;
                            case 4:
                                lfVal++;
                                break;
                            default: //Also 1 // Speed
                                lfSpd = Float.parseFloat(trkpt.child("speed").content().toString());
                                lfVal = lfSpd;
                                break;
                        }

                    //Add the MinValue to everything in the case of Acc
                    //This is to keep the number above 0 in all cases
                    if (liCodeBy == 3)
                    {
                        lfVal += Math.abs(lfMinValue);
                        liColorLevel = Math.round(((lfVal / (lfMaxValue + Math.abs(lfMinValue))) * (liStyles-1)) +1);
                    }
                    else
                    {
                        liColorLevel = Math.round(((lfVal / (lfMaxValue - lfMinValue)) * (liStyles-1)) +1);
                    }

                    //System.out.println("liCodeBy: " + liCodeBy + ", lfVal: " + lfVal + ", lfMaxValue: " + lfMaxValue + ", lfMinValue: " + lfMinValue + ", liColorLevel: " + liColorLevel);

                    if (!FirstRun)
                    {
                        sbReturnValue.append(KML_Placemark[0] + lsDescription +
                                             KML_Placemark[1] + Integer.toString(liColorLevel) +
                                             KML_Placemark[2] +
                                             lfLastLon + "," + lfLastLat + "," + lfLastEle + " \n" + lfLon + "," + lfLat + "," + lfEle + " \n" +
                                             KML_Placemark[3]);
                    }

                        lfLastLat = lfLat;
			lfLastLon = lfLon;
			//lfLastEle = lfEle;
			FirstRun = false;

                    //Set last value for variables only when we're using them
                    switch (liCodeBy)
                        {
                            case 2: //Ele
                                lfLastEle = lfEle;
                                break;
                            case 3: //Acc
                            default: //Also 1 // Speed
                                lfLastSpd = lfSpd;
                                break;
                        }
                        
                        liPointCount2++;
                        loConversionStatus.setVals(true, liPointCount, liPointCount2);
                }
        }

lsReturnValue = sbReturnValue.toString();
return lsReturnValue;
}

private String lfCreateLinesCSV(String lsSource, int liCodeBy, int liStyles)
{

//System.out.println(lsSource);
    String lsReturnValue = "";
    StringBuffer sbReturnValue = new StringBuffer();
    String lsString = "";
    String lsStrings[];
    char lcNull = 0;
    char lsSpace = 20;

    System.out.println("lsSpace = " + lsSpace);

    //loConversionStatus.setVals(true, 0, 0);

    //Determine the Max and Min Speeds
    Float lfCurrValue = Float.parseFloat("0");
    Float lfMaxValue = Float.parseFloat("0");
    Float lfMinValue = Float.parseFloat("0");
    Float lfLastValue = Float.parseFloat("0");
    int liPointCount = 0;

            try
            {
                // Wrap the FileInputStream with a DataInputStream
                    BufferedInputStream DBF_input = new BufferedInputStream (new FileInputStream (lsSource));
                    BufferedReader DBF_reader = new BufferedReader(new InputStreamReader(DBF_input));
                    //DBF_input.close ();


                while ((lsString = DBF_reader.readLine()) != null)
                {
                    //Print the content on the console
                    //System.out.println (lsString);
                    lsString = lsString.replace(lcNull, lsSpace);
                    lsStrings = lsString.split(",");
                    if (!lsStrings[7].trim().equalsIgnoreCase("SPEED")) //Skip First Row!
                    {
                            switch (liCodeBy)
                            {
                                case 2: //Ele
                                    lfCurrValue = Float.parseFloat(lsStrings[6].trim());
                                    break;
                                case 3: //Acc
                                    lfCurrValue = Float.parseFloat(lsStrings[7].trim()) - lfLastValue;
                                    lfLastValue = Float.parseFloat(lsStrings[7].trim());
                                    break;
                                case 4: //Time
                                    lfCurrValue++;
                                    break;
                                default: //Also 1 // Speed
                                    lfCurrValue = Float.parseFloat(lsStrings[7].trim());
                                    break;

                            }

                        if (lfCurrValue<lfMinValue) lfMinValue = lfCurrValue;
                        if (lfCurrValue>lfMaxValue) lfMaxValue = lfCurrValue;
                        liPointCount++;
                        loConversionStatus.setVals(true, liPointCount, 0);
                    }
                 }
                 DBF_input.close ();
            }
        catch  (IOException e)
            {
               System.out.println ( "IO Exception =: " + e );
            }

    //loConversionStatus.setVals(true, liPointCount, 0);

    //More Floats!
        Float lfLat = Float.parseFloat("0");
        Float lfLon = Float.parseFloat("0");
        Float lfEle = Float.parseFloat("0");
        Float lfSpd = Float.parseFloat("0");
        Float lfVal = Float.parseFloat("0");
        Float lfLastLat = Float.parseFloat("0");
        Float lfLastLon = Float.parseFloat("0");
        Float lfLastEle = Float.parseFloat("0");
        Float lfLastSpd = Float.parseFloat("0");
        String lsDescription = "";
        int liColorLevel = 0;
        int liPointCount2 = 0;
        boolean FirstRun = true;

    //Determine the Lines
            try
            {
                // Wrap the FileInputStream with a DataInputStream
                    BufferedInputStream DBF_input = new BufferedInputStream (new FileInputStream (lsSource));
                    BufferedReader DBF_reader = new BufferedReader(new InputStreamReader(DBF_input));
                    //DBF_input.close ();


                while ((lsString = DBF_reader.readLine()) != null)
                {
                    lsString = lsString.replace(lcNull, lsSpace);
                    lsStrings = lsString.split(",");
                    if (!lsStrings[7].trim().equalsIgnoreCase("SPEED")) //Skip First Row!
                    {
                    //System.out.println("lfSpd: " + lfSpd + ", lfMaxSpeed: " + lfMaxValue + ", lfMinSpeed: " + lfMinValue +", tot: " + Math.round(((lfSpd / (lfMaxValue - lfMinValue)) * (liStyles-1)) +1));

                    /*
                     * lat = fields[4].replace("\000", "").rstrip()
			lon = fields[5].replace("\000", "").rstrip()
			elv = fields[6].replace("\000", "").rstrip()
			spd = fields[7].replace("\000", "").rstrip()
			alt = elv
			heading = fields[8].replace("\000", "").rstrip()
                     * */

                    lfSpd = Float.parseFloat(lsStrings[7].trim());
                    lfEle = Float.parseFloat(lsStrings[6].trim());
                    lsDescription = "Spd: " + lfSpd + " mph / Elv: " + lfEle + " meters";
                    lfLat = Float.parseFloat(lsStrings[4].trim().substring(0, lsStrings[4].trim().length()-1));
                    lfLon = Float.parseFloat(lsStrings[5].trim().substring(0, lsStrings[5].trim().length()-1));

                    if (lsStrings[4].trim().substring(lsStrings[4].trim().length()-1).equalsIgnoreCase("S"))
                    {
                        lfLat = lfLat * -1;
                    }
                    if (lsStrings[5].trim().substring(lsStrings[5].trim().length()-1).equalsIgnoreCase("W"))
                    {
                        lfLon = lfLon * -1;
                    }

                    //Determine the Color
                    switch (liCodeBy)
                        {
                            case 2: //Ele
                                lfVal = lfEle;
                                break;
                            case 3: //Acc
                                lfVal = lfSpd - lfLastSpd;
                                lfLastSpd = lfSpd;
                                break;
                            case 4:
                                lfVal++;
                                break;
                            default: //Also 1 // Speed
                                lfVal = lfSpd;
                                break;
                        }

                    //Add the MinValue to everything in the case of Acc
                    //This is to keep the number above 0 in all cases
                    if (liCodeBy == 3)
                    {
                        lfVal += Math.abs(lfMinValue);
                        liColorLevel = Math.round(((lfVal / (lfMaxValue + Math.abs(lfMinValue))) * (liStyles-1)) +1);
                    }
                    else
                    {
                        liColorLevel = Math.round(((lfVal / (lfMaxValue - lfMinValue)) * (liStyles-1)) +1);
                    }

                    //System.out.println("liCodeBy: " + liCodeBy + ", lfVal: " + lfVal + ", lfMaxValue: " + lfMaxValue + ", lfMinValue: " + lfMinValue + ", liColorLevel: " + liColorLevel);

                    if (!FirstRun)
                    {
                        sbReturnValue.append(KML_Placemark[0] + lsDescription +
                                             KML_Placemark[1] + Integer.toString(liColorLevel) +
                                             KML_Placemark[2] +
                                             lfLastLon + "," + lfLastLat + "," + lfLastEle + " \n" + lfLon + "," + lfLat + "," + lfEle + " \n" +
                                             KML_Placemark[3]);
                    }

                        lfLastLat = lfLat;
			lfLastLon = lfLon;
			//lfLastEle = lfEle;
			FirstRun = false;

                    //Set last value for variables only when we're using them
                    switch (liCodeBy)
                        {
                            case 2: //Ele
                                lfLastEle = lfEle;
                                break;
                            case 3: //Acc
                            default: //Also 1 // Speed
                                lfLastSpd = lfSpd;
                                break;
                        }
                    }
                        liPointCount2++;
                        loConversionStatus.setVals(true, liPointCount, liPointCount2);
                }
                 DBF_input.close ();
            }
        catch  (IOException e)
            {
               System.out.println ( "IO Exception =: " + e );
        }

lsReturnValue = sbReturnValue.toString();
return lsReturnValue;
}

public boolean ConvertFiles(String lsSource, String lsDestination, int liCodeBy, int liStyles) throws IOException
{

    boolean lbReturnValue = false;


    //Create the File to write out

    //Define all the Needed Text
        DefineKML();


    //Create the File Parts
        String lsHeader = lfCreateHeader(lsDestination);
        String lsLines = lfCreateLines(lsSource, liCodeBy, liStyles);
        String lsStyleDefs = lfCreateStyleDefs(liStyles);
        String lsStyles = lfCreateStyles(liStyles);

    //Write Stuff Out
        BufferedWriter writer = new BufferedWriter(new FileWriter(lsDestination));
        writer.append(lsHeader);
        writer.append(lsStyleDefs);
        writer.append(lsStyles);
        writer.append(lsLines);
        writer.append(KML_Footer);
        writer.close();  // Close to unlock and flush to disk.*/

        loConversionStatus.setVals(false, 0, 0);

    return lbReturnValue;
}

        boolean lbEmpty = false;
        ConversionStatus loConversionStatus = new ConversionStatus(null, lbEmpty);

}
