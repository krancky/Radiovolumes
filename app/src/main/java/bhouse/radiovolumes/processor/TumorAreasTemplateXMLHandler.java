package bhouse.radiovolumes.processor;


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
 * Handles Xml parsing for Tumor template
 */
public class TumorAreasTemplateXMLHandler {
    /**
     * The Ctv 56 nu case catalog.
     * Stores all elementary CTV56N cases
     */
    List<TumorAreaTemplate> tumorAreaTemplateList;
        private TumorAreaTemplate tumorAreaTemplate;
        private String text;

    /**
     * Instantiates a new Xml pull parser handler.
     */
    public TumorAreasTemplateXMLHandler() {
            tumorAreaTemplateList = new ArrayList<TumorAreaTemplate>();
        }

    /**
     * Gets ctv 56 nu case.
     *
     * @return the ctv 56 nu case
     */
    public List<TumorAreaTemplate> getNodeAreaTemplateList() {
            return tumorAreaTemplateList;
        }

    /**
     * Parses XmL file provided as a parameter.
     *
     * @param is the XmL file
     * @return the list
     */
    public List<TumorAreaTemplate> parse(InputStream is) {
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
                            if (tagname.equalsIgnoreCase("Volume")) {
                                // Creates a new instance of CTV56NUCase
                                tumorAreaTemplate = new TumorAreaTemplate();
                                Log.i("hop","New Template Volume");
                            }
                            break;

                        case XmlPullParser.TEXT:
                            // Stores in text content between START and END tags
                            text = parser.getText();
                            break;

                        case XmlPullParser.END_TAG:
                            if (tagname.equalsIgnoreCase("Volume")) {
                                // add CTV56NUCase object to catalog

                                tumorAreaTemplate.setLeftContent("0");
                                tumorAreaTemplate.setRightContent("0");
                                tumorAreaTemplateList.add(tumorAreaTemplate);
                            }
                            else if (tagname.equalsIgnoreCase("Location")) {
                                tumorAreaTemplate.setLocation(text);
                            }
                            else if (tagname.equalsIgnoreCase("LocationLocale")) {
                                tumorAreaTemplate.setLocationLocale(text);
                            }
                            else if (tagname.equalsIgnoreCase("AreaLocale")) {
                                tumorAreaTemplate.setAreaLocale(text);
                            }
                            else if (tagname.equalsIgnoreCase("Area")) {
                                tumorAreaTemplate.setArea(text);
                            }
                            else if (tagname.equalsIgnoreCase("SubLocation")) {
                                tumorAreaTemplate.setSubLocation(text);
                            }

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
            return tumorAreaTemplateList;
        }
    }


