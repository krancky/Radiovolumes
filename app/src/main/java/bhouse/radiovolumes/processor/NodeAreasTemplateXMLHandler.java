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
 * Handles Xml parsing for lymph nodes
 */
public class NodeAreasTemplateXMLHandler {
    /**
     * The Ctv 56 nu case catalog.
     * Stores all elementary CTV56N cases
     */
    List<NodeAreaTemplate> NodeAreaTemplateList;
    private NodeAreaTemplate nodeAreaTemplate;
    private String text;

    /**
     * Instantiates a new Xml pull parser handler.
     */
    public NodeAreasTemplateXMLHandler() {
            NodeAreaTemplateList = new ArrayList<NodeAreaTemplate>();
        }

    /**
     * Gets ctv 56 nu case.
     *
     * @return the ctv 56 nu case
     */
    public List<NodeAreaTemplate> getNodeAreaTemplateList() {
            return NodeAreaTemplateList;
        }

    /**
     * Parses XmL file provided as a parameter.
     *
     * @param is the XmL file
     * @return the list
     */
    public List<NodeAreaTemplate> parse(InputStream is) {
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
                                nodeAreaTemplate = new NodeAreaTemplate();
                            }
                            break;

                        case XmlPullParser.TEXT:
                            // Stores in text content between START and END tags
                            text = parser.getText();
                            break;

                        case XmlPullParser.END_TAG:
                            if (tagname.equalsIgnoreCase("Volume")) {
                                // add CTV56NUCase object to catalog
                                NodeAreaTemplateList.add(nodeAreaTemplate);
                            } else if (tagname.equalsIgnoreCase("Short")) {
                                // reads the node name in roman numbers
                                nodeAreaTemplate.setNodeLocation(text);
                                // initializes the content
                                nodeAreaTemplate.setLeftContent("0");
                                nodeAreaTemplate.setRightContent("0");
                            }   else if (tagname.equalsIgnoreCase("Long")) {
                                // reads the name of the node
                                nodeAreaTemplate.setCompleteName(text);
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
            return NodeAreaTemplateList;
        }
    }


