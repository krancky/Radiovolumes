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
 * Handles Xml parsing for N templates
 */
public class NUCaseXMLHandler {
    /**
     * The Ctv 56 nu case catalog.
     * Stores all elementary CTV56N cases
     */
    List<CTV56NUCase> CTV56NUCaseCatalog;
    private CTV56NUCase cTV56NUCase;
    private String text;

    /**
     * Instantiates a new Xml pull parser handler.
     */
    public NUCaseXMLHandler() {
            CTV56NUCaseCatalog = new ArrayList<CTV56NUCase>();
        }

    /**
     * Gets ctv 56 nu case.
     *
     * @return the ctv 56 nu case
     */
    public List<CTV56NUCase> getCTV56NUCase() {
            return CTV56NUCaseCatalog;
        }

    /**
     * Parses XmL file provided as a parameter.
     *
     * @param is the XmL file
     * @return the list
     */
    public List<CTV56NUCase> parse(InputStream is) {
            XmlPullParserFactory factory = null;
            XmlPullParser parser = null;
            try {
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
                            if (tagname.equalsIgnoreCase("CTV56NUCase")) {
                                // Creates a new instance of CTV56NUCase
                                cTV56NUCase = new CTV56NUCase();
                                Log.i("hop","New TV56NUCase");
                            }
                            break;

                        case XmlPullParser.TEXT:
                            // Stores in text content between START and END tags
                            text = parser.getText();
                            break;

                        case XmlPullParser.END_TAG:
                            if (tagname.equalsIgnoreCase("CTV56NUCase")) {
                                // add CTV56NUCase object to catalog
                                CTV56NUCaseCatalog.add(cTV56NUCase);
                            } else if (tagname.equalsIgnoreCase("CaseName")) {
                                cTV56NUCase.setCaseName(text);
                            } else if (tagname.equalsIgnoreCase("Identifier")) {
                                cTV56NUCase.setIdentifier(Integer.parseInt(text));
                            } else if (tagname.matches("Spread(.*)")) {
                                // Adds unique spread volume of CTV56NU case.
                                cTV56NUCase.addSVolumeToMap(tagname, Integer.parseInt(text));
                                Log.i("hop","New SVolume added");
                            } else if (tagname.matches("Target(.*)")) {
                                // Adds new target volume to CTV56NU case
                                cTV56NUCase.addTVolumeToMap(tagname, Integer.parseInt(text));
                                Log.i("hop", "New TVolume added");
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
            return CTV56NUCaseCatalog;
        }
    }


