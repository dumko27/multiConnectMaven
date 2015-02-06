package com.multiconnect;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {
    
    static final Logger log = Logger.getLogger(XMLReader.class);

    public static void main(String argv[]) throws IOException {
        //PropertiesConfigurator is used to configure log from properties file
        PropertyConfigurator.configure("log4j.properties");
        log.info("Log4j appender configuration is successful");
        String patch = "/home/DN270791NDI/NetBeansProjects/MultiConnectMaven/TestXML";
        log.debug("patch=" + patch);
        parseAllFiles(patch);
    }

    public static void parseAllFiles(String parentDirectory) throws IOException {
        File[] filesInDirectory = new File(parentDirectory).listFiles();
        File dir = new File("/home/DN270791NDI/NetBeansProjects/MultiConnectMaven/GoodXML");
        dir.mkdirs();
        log.debug("dir=." + dir);
        for (File file : filesInDirectory) {
            if (file.isDirectory()) {
                log.debug("Name directory=" + file.getName());
                parseAllFiles(file.getAbsolutePath());
            } else if (file.isFile()) {
                log.debug("Name file=" + file.getName());
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
            log.debug("Root element " + doc.getDocumentElement().getNodeName());

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
                        log.debug("Content : " + content);
                    } else {
                        log.debug("Cтрока content длиной больше 1024 символов.");
                    }

                    NodeList dateNmElmntLst = element.getElementsByTagName("creationDate");
                    Element dateNmElmnt = (Element) dateNmElmntLst.item(0);
                    NodeList dateNm = dateNmElmnt.getChildNodes();
                    log.debug("Creation date : " + ((Node) dateNm.item(0)).getNodeValue());
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

}
