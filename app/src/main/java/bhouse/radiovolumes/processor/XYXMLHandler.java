package bhouse.radiovolumes.processor;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by kranck on 9/28/2017.
 */

public class XYXMLHandler {
    private LinkedHashMap<String, ArrayList<String>> OLimits;
    private String text;
    /**
     * Instantiates a new Xml pull parser handler.
     */
    public XYXMLHandler() {
        this.OLimits = new LinkedHashMap<String, ArrayList<String>>();
    }



    public LinkedHashMap<String, ArrayList<String>> parse(InputStream is) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            // Creates parser
            ArrayList<String> singleOrganLimits = new ArrayList<>();
            String oName = new String();
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
                        if (tagname.equalsIgnoreCase("Organ")) {
                            // Creates a new instance of CTV56NUCase
                            oName = new String();
                            singleOrganLimits = new ArrayList<>();
                        }
                        if (tagname.equalsIgnoreCase("TVolume")) {
                        }
                        break;

                    case XmlPullParser.TEXT:
                        // Stores in text content between START and END tags
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("Organ")) {
                            // add CTV56NUCase object to catalog
                            this.OLimits.put(oName, singleOrganLimits);
                        } else if (tagname.equalsIgnoreCase("Name")) {
                            oName = text;
                            singleOrganLimits.add(text);
                        } else if (tagname.equalsIgnoreCase("Area")) {
                            singleOrganLimits.add(text);
                        } else if (tagname.equalsIgnoreCase("CranialL")) {
                            singleOrganLimits.add(text);
                        } else if (tagname.equalsIgnoreCase("CaudalL")) {
                            singleOrganLimits.add(text);
                        } else if (tagname.equalsIgnoreCase("AnteriorL")) {
                            singleOrganLimits.add(text);
                        } else if (tagname.equalsIgnoreCase("PosteriorL")) {
                            singleOrganLimits.add(text);
                        } else if (tagname.equalsIgnoreCase("MedialL")) {
                            singleOrganLimits.add(text);;
                        } else if (tagname.equalsIgnoreCase("LateralL")) {
                            // Adds unique target volume of CTV56NU cases
                            singleOrganLimits.add(text);
                        } else if (tagname.equalsIgnoreCase("Comment")) {
                            // Adds unique target volume of CTV56NU cases
                            singleOrganLimits.add(text);
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
        return this.OLimits;
    }

    public LinkedHashMap<String, ArrayList<String>> getOLimits() {
        return OLimits;
    }
}

