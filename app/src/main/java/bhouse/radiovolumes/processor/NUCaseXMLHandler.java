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
                LRNodeTargetVolume lrNodeTargetVolume = new LRNodeTargetVolume();
                NodeAreaTemplate nodeAreaTemplate =new NodeAreaTemplate();
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
                            if (tagname.equalsIgnoreCase("SVolume")) {
                                // Creates a new instance of the two strings defining Target LN Volume
                                nodeAreaTemplate =new NodeAreaTemplate();
                                lrNodeTargetVolume =new LRNodeTargetVolume();
                            }
                            if (tagname.equalsIgnoreCase("TVolume")) {
                                // Creates a new instance of the two strings defining Target LN Volume
                                lrNodeTargetVolume =new LRNodeTargetVolume();
                            }
                            break;

                        case XmlPullParser.TEXT:
                            // Stores in text content between START and END tags
                            text = parser.getText();
                            break;

                        case XmlPullParser.END_TAG:
                            if (tagname.equalsIgnoreCase("CTV56NUCase")) {
                                CTV56NUCaseCatalog.add(cTV56NUCase);
                            } else if (tagname.equalsIgnoreCase("Location")) {
                                cTV56NUCase.setLocation(text);
                            } else if (tagname.equalsIgnoreCase("Modifier")) {
                                cTV56NUCase.setModifier(text);
                            } else if (tagname.equalsIgnoreCase("Identifier")) {
                                cTV56NUCase.setIdentifier(Integer.parseInt(text));
                            } else if (tagname.equalsIgnoreCase("Side")) {
                                cTV56NUCase.setSide(text);
                            } else if (tagname.equalsIgnoreCase("SLN")) {
                                nodeAreaTemplate.setNodeLocation(text);
                                cTV56NUCase.setSpreadLocation(text);
                            } else if (tagname.equalsIgnoreCase("SLNSide")) {
                                if (text.equals("Gauche")){
                                    nodeAreaTemplate.setLeftContent("1");
                                    nodeAreaTemplate.setRightContent("0");

                                }
                                else {
                                    nodeAreaTemplate.setRightContent("1");
                                    nodeAreaTemplate.setLeftContent("0");
                                }
                                cTV56NUCase.setSpreadSide(text);
                            } else if (tagname.equalsIgnoreCase("SVolume")) {
                                cTV56NUCase.addSVolumeToMap(nodeAreaTemplate);
                            } else if (tagname.equalsIgnoreCase("TLN")) {
                                lrNodeTargetVolume.setLocation(text);
                            } else if (tagname.equalsIgnoreCase("TLNSIDE")) {
                                lrNodeTargetVolume.setSide(text);
                            } else if (tagname.equalsIgnoreCase("TVolume")) {
                                cTV56NUCase.addTVolumeToMap(lrNodeTargetVolume);
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


