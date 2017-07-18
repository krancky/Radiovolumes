package bhouse.travellist.processor;


import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Xml pull parser handler.
 * Handles Xml parsing for Tumor uCases
 */
public class TUCaseXMLHandler {
    /**
     * The Ctv 56 nu case catalog.
     * Stores all elementary CTV56N cases
     */
    List<CTV56TUCase> ctv56TUCaseList;
    private CTV56TUCase ctv56TUCase;
    private String text;

    /**
     * Instantiates a new Xml pull parser handler.
     */
    public TUCaseXMLHandler() {
            ctv56TUCaseList = new ArrayList<CTV56TUCase>();
        }

    /**
     * Gets ctv 56 nu case.
     *
     * @return the ctv 56 nu case
     */
    public List<CTV56TUCase> getCTV56TUCase() {
            return ctv56TUCaseList;
        }

    /**
     * Parses XmL file provided as a parameter.
     *
     * @param is the XmL file
     * @return the list
     */
    public List<CTV56TUCase> parse(InputStream is) {
            XmlPullParserFactory factory = null;
            XmlPullParser parser = null;
            try {
                String tLocation ="0";
                String tArea = "0";
                String tSide = "0";
                // Creates parser
                factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                parser = factory.newPullParser();

                parser.setInput(is, null);

                int eventType = parser.getEventType();
                // Parses until end of document
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagname = parser.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if (tagname.equalsIgnoreCase("LRTCTVUCase")) {
                                // Creates a new instance of CTV56NUCase
                                ctv56TUCase = new CTV56TUCase();
                                Log.i("hop","New TV56NUCase");
                            }
                            break;

                        case XmlPullParser.TEXT:
                            // Stores in text content between START and END tags
                            text = parser.getText();
                            break;

                        case XmlPullParser.END_TAG:
                            if (tagname.equalsIgnoreCase("LRTCTVUCase")) {
                                // add CTV56NUCase object to catalog
                                ctv56TUCaseList.add(ctv56TUCase);
                            } else if (tagname.equalsIgnoreCase("Location")) {
                                ctv56TUCase.setLocation(text);
                            } else if (tagname.equalsIgnoreCase("Side")) {
                                ctv56TUCase.setSide(text);
                            } else if (tagname.equalsIgnoreCase("Identifier")) {
                                ctv56TUCase.setIdentifier(Integer.parseInt(text));
                            } else if (tagname.equalsIgnoreCase("TLocation")) {
                                tLocation = text;
                            } else if (tagname.equalsIgnoreCase("TArea")) {
                                tArea = text;
                            } else if (tagname.equalsIgnoreCase("TSide")) {
                                tSide = text;
                            } else if (tagname.equalsIgnoreCase("TVolume")) {
                                // Adds unique spread volume of CTV56NU cases
                                List<String> tList = new ArrayList<String>();
                                tList.add(tSide);
                                tList.add(tArea);
                                if (!ctv56TUCase.getuCaseTVolumes().containsKey(tLocation)){
                                    ctv56TUCase.addTVolumeToMap(tLocation, tList);
                                }
                                else{
                                    tList = ctv56TUCase.getuCaseTVolumes().get(tLocation);
                                    tList.add(tSide);
                                    ctv56TUCase.addTVolumeToMap(tLocation, tList);
                                }

                                Log.i("hop","New TarVolume added");
                            }
                            break;

                        default:
                            break;
                    }
                    // parsing next group of letters
                    eventType = parser.next();
                }

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // returns a catalog of elementary CTV56N objects
            return ctv56TUCaseList;
        }
    }


