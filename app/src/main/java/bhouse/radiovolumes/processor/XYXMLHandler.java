package bhouse.radiovolumes.processor;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kranck on 9/28/2017.
 */

public class XYXMLHandler {
    private HashMap<String, HashMap<String, Pair<String,String>>> xyValues;
    private String text;
    /**
     * Instantiates a new Xml pull parser handler.
     */
    public XYXMLHandler() {
        this.xyValues = new HashMap<String, HashMap<String, Pair<String, String>>>();
    }



    public HashMap<String, HashMap<String, Pair<String, String>>> parse(InputStream is) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            // Creates parser
            HashMap<String, Pair<String, String>> singleOrganXY = new HashMap<String, Pair<String, String>>();
            String oName = new String();
            Pair<String, String> xyPair = new Pair<>(null,null);
            String ymin = new String();
            String z = new String();
            String xmin = new String();
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
                            singleOrganXY = new HashMap<String, Pair<String, String>>();
                        }
                        if (tagname.equalsIgnoreCase("slice")) {
                            xyPair = new Pair<>(null,null);
                        }

                    case XmlPullParser.TEXT:
                        // Stores in text content between START and END tags
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("Organ")) {
                            // add CTV56NUCase object to catalog
                            this.xyValues.put(oName, singleOrganXY);
                        } else if (tagname.equalsIgnoreCase("Name")) {
                            oName = text;
                            //singleOrganXY.add(text);
                        } else if (tagname.equalsIgnoreCase("z")) {
                            z = text;
                            //singleOrganXY.add(text);
                        } else if (tagname.equalsIgnoreCase("xmin")) {
                            xmin = text;
                        } else if (tagname.equalsIgnoreCase("ymin")) {
                            ymin = text;
                        } else if (tagname.equalsIgnoreCase("slice")) {
                            xyPair.setFirst(xmin);
                            xyPair.setSecond(ymin);
                            singleOrganXY.put(z,xyPair);
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
        return this.xyValues;
    }

    public HashMap<String, HashMap<String, Pair<String,String>>> getxyValues() {
        return xyValues;
    }
}

