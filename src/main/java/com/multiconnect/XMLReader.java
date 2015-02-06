package com.multiconnect;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {

    public static void main(String argv[]) throws IOException {
        Logger logger = LoggerFactory.getLogger(XMLReader.class);
        String patch = "/home/DN270791NDI/NetBeansProjects/MultiConnectMaven/TestXML";
        logger.debug("patch={}.", patch);
        logger.info("patch={}.", patch);
        parseAllFiles(patch);
    }

    public static void parseAllFiles(String parentDirectory) throws IOException {
        File[] filesInDirectory = new File(parentDirectory).listFiles();
        File dir = new File("/home/DN270791NDI/NetBeansProjects/MultiConnectMaven/GoodXML");
        dir.mkdirs();
        for (File file : filesInDirectory) {
            if (file.isDirectory()) {
                System.out.println("Name directory=" + file.getName());
                parseAllFiles(file.getAbsolutePath());
            } else if (file.isFile()) {
                System.out.println("Name file=" + file.getName());
                parsingXML(file.getAbsolutePath());
                file.renameTo(new File(dir, file.getName()));
            }
        }
    }

    public static void parsingXML(String nameFile) {
        try {
            File file = new File(nameFile);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println("Root element " + doc.getDocumentElement().getNodeName());

            NodeList nodeLst = doc.getElementsByTagName("Entry");
            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node fstNode = nodeLst.item(s);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) fstNode;

                    NodeList contentNmElmntLst = element.getElementsByTagName("content");
                    Element contentNmElmnt = (Element) contentNmElmntLst.item(0);
                    NodeList contentNm = contentNmElmnt.getChildNodes();
                    String content = ((Node) contentNm.item(0)).getNodeValue();
                    if (content.length() < 1024) {
                        System.out.println("Content : " + content);
                    } else {
                        System.out.println("Cтрока content длиной больше 1024 символов.");
                    }

                    NodeList dateNmElmntLst = element.getElementsByTagName("creationDate");
                    Element dateNmElmnt = (Element) dateNmElmntLst.item(0);
                    NodeList dateNm = dateNmElmnt.getChildNodes();
                    System.out.println("Creation date : " + ((Node) dateNm.item(0)).getNodeValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
