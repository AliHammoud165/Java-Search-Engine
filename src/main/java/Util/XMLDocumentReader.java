package Util;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The {@code XMLDocumentReader} class reads text content from XML documents.
 */
public class XMLDocumentReader implements DocumentReader {

    /**
     * Reads the text content of an XML document from the specified file path.
     *
     * @param filePath the file path of the XML document to read
     * @return the text content of the XML document
     * @throws IOException if an I/O error occurs while reading the document
     */
    @Override
    public String readDocument(String filePath) throws IOException {
        File xmlFile = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        StringBuilder sb = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(xmlFile)) {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(fis));
            doc.getDocumentElement().normalize();
            // Recursively traverse the document from the root node
            traverseXML(doc.getDocumentElement(), sb);
            return sb.toString();
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("Error parsing XML document", e);
        }
    }

    /**
     * Recursively traverses the XML document and appends the text content of each node to the StringBuilder.
     *
     * @param node the current node being processed
     * @param sb   the StringBuilder to append the text content
     */
    private void traverseXML(Node node, StringBuilder sb) {
        // Handle the node content here, for example:
        sb.append(node.getNodeName()).append(": ").append(node.getTextContent().trim()).append("\n");

        if (node.hasChildNodes()) {
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node currentNode = nodeList.item(i);
                if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                    traverseXML(currentNode, sb);
                }
            }
        }
    }
}
