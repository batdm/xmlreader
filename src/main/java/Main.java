import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        File file = new File("in\\src.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Map<Integer, Map<String, Map<String, String>>> index_elem_attr_map = new HashMap<>();

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            NodeList nodeList = document.getElementsByTagName("Field");

            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList childNodes = nodeList.item(i).getChildNodes();
                Map<String, Map<String, String>> elem_attr_map = new HashMap<>();
                Field_elem f_e = new Field_elem();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    String names = childNodes.item(j).getNodeName();
                    String values = childNodes.item(j).getTextContent();
                    if (!names.equals("#text")) {//есть какойто node #text, который выводит знаечние null, избавимся от него
                        if (names.equalsIgnoreCase("type")) {
                            f_e.setType(values);
                        } else {
                            f_e.putt(names, values);
                        }
                    }
                }
                elem_attr_map.put(f_e.getType(), f_e.getMap());
                index_elem_attr_map.put(i, elem_attr_map);
            }
            //create XML file(DOM Parser)
            Document doc = documentBuilder.newDocument();
            Element rootElement = doc.createElement("Data");
            doc.appendChild(rootElement);

            for (Map.Entry<Integer, Map<String, Map<String, String>>> m0 :
                    index_elem_attr_map.entrySet()) {
//                System.out.println("index: " + m0.getKey());
                for (Map.Entry<String, Map<String, String>> m :
                        m0.getValue().entrySet()) {
                    Element element = doc.createElement(m.getKey());
                    rootElement.appendChild(element);
//                    System.out.println(" element: " + m.getKey());
                    for (Map.Entry<String, String> m1 :
                            m.getValue().entrySet()) {
                        Attr attr = doc.createAttribute(m1.getKey());
                        String replace_bool = m1.getValue();
//                        replace_bool = m1.getKey().equals("required") || m1.getKey().equals("readOnly") || m1.getKey().equals("digitOnly") ? replace_bool.replaceAll("1", "true") : replace_bool;
                        if (m1.getKey().equals("required") || m1.getKey().equals("readOnly") || m1.getKey().equals("digitOnly")) {//если аттрибут называется required, readOnly или digitOnly, то необходимо заменить значение аттрибута: 0 или 1 на false или true
                            if (m1.getValue().equals("1")) {
                                replace_bool = "true";
                            } else {
                                replace_bool = "false";
                            }
                        }
                        attr.setValue(replace_bool);
                        element.setAttributeNode(attr);
//                        System.out.println("  attr: " + m1.getKey() + ", value: " + m1.getValue());
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:\\Java\\xmlreader\\out\\Dst.xml"));

            transformer.transform(source, result);
            System.out.println("File saved!");


        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }


    }
}
