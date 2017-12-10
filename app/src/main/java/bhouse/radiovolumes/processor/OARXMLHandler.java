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
public class OARXMLHandler {
    /**
     * The Ctv 56 nu case catalog.
     * Stores all elementary CTV56N cases
     */
    ArrayList<OARTemplate> OARTemplateList;
        private OARTemplate OARTemplate;
        private String text;

    /**
     * Instantiates a new Xml pull parser handler.
     */
    public OARXMLHandler() {
            OARTemplateList = new ArrayList<OARTemplate>();
        }

    /**
     * Gets ctv 56 nu case.
     *
     * @return the ctv 56 nu case
     */
    public List<OARTemplate> getNodeAreaTemplateList() {
            return OARTemplateList;
        }

    /**
     * Parses XmL file provided as a parameter.
     *
     * @param is the XmL file
     * @return the list
     */
    public ArrayList<OARTemplate> parse(InputStream is) {
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
                            if (tagname.equalsIgnoreCase("OAR")) {
                                // Creates a new instance of CTV56NUCase
                                OARTemplate = new OARTemplate();
                            }
                            break;

                        case XmlPullParser.TEXT:
                            // Stores in text content between START and END tags
                            text = parser.getText();
                            break;

                        case XmlPullParser.END_TAG:
                            if (tagname.equalsIgnoreCase("OAR")) {
                                // add CTV56NUCase object to catalog

                                OARTemplate.setLeftContent("0");
                                OARTemplate.setRightContent("0");
                                OARTemplateList.add(OARTemplate);
                            }
                            else if (tagname.equalsIgnoreCase("Name")) {
                                OARTemplate.setLocation(text.replaceAll("\\s+", "").toLowerCase());
                            }
                            else if (tagname.equalsIgnoreCase("RisqueComplications")) {
                                OARTemplate.setComplications(text);
                            }
                            else if (tagname.equalsIgnoreCase("ContraintesPlanification")) {
                                OARTemplate.setConstraints(text);
                            }
                            else if (tagname.equalsIgnoreCase("AutresContraintes")) {
                                OARTemplate.setOtherconstraints(text);
                            }
                            else if (tagname.equalsIgnoreCase("Lateralized")) {
                                OARTemplate.setLateralized(text);
                            }
                            else if (tagname.equalsIgnoreCase("Comment")) {
                                OARTemplate.setComment(text);
                            }
                            else if (tagname.equalsIgnoreCase("Cranial")) {
                                OARTemplate.setCranial(text);
                            }
                            else if (tagname.equalsIgnoreCase("Caudal")) {
                                OARTemplate.setCaudal(text);
                            }
                            else if (tagname.equalsIgnoreCase("Anterior")) {
                                OARTemplate.setAnterior(text);
                            }
                            else if (tagname.equalsIgnoreCase("Posterior")) {
                                OARTemplate.setPosterior(text);
                            }
                            else if (tagname.equalsIgnoreCase("Lateral")) {
                                OARTemplate.setLateral(text);
                            }
                            else if (tagname.equalsIgnoreCase("Medial")) {
                                OARTemplate.setMedial(text);
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
            return OARTemplateList;
        }
    }


