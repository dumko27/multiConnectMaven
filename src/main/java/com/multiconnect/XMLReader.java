package com.multiconnect;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class that read xml file from directory and move this xml file in new
 * directory
 *
 * @author Novikov Dmitry
 */
public class XMLReader {

    static final Logger log = LoggerFactory.getLogger(XMLReader.class);

    public static void main(String argv[]) throws IOException {
        PropertyConfigurator.configure("log4j.properties");
        String patch = "/home/DN270791NDI/NetBeansProjects/MultiConnectMaven/TestXML";
        log.debug("patch=" + patch);
        parseAllFiles(patch);
    }

    public static void parseAllFiles(String parentDirectory) throws IOException {
        File[] filesInDirectory = new File(parentDirectory).listFiles();
        File dir = new File("/home/DN270791NDI/NetBeansProjects/MultiConnectMaven/GoodXML");
        dir.mkdirs();
        log.debug("dir={}", dir);
        for (File file : filesInDirectory) {
            if (file.isDirectory()) {
                log.debug("Name directory={}", file.getName());
                parseAllFiles(file.getAbsolutePath());
            } else if (file.isFile()) {
                log.debug("Name file={}", file.getName());
                if (!parsingXML(file.getAbsolutePath())) {
                    file.renameTo(new File(dir, file.getName()));
                    log.debug("File is moved");
                } else {
                    log.debug("File is NOT moved");
                }
            }
        }
    }

    public static boolean parsingXML(String nameFile) {
        boolean flag = false;
        try {
            File file = new File(nameFile);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            String rootElement = doc.getDocumentElement().getNodeName();
            log.debug("Root element: {}", rootElement);
            
            flag = rootElement.equals("Entry") ? flag : true;
            
            if (!flag) {
                log.debug("flag={}", flag);
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
                            log.debug("Content : {}", content);
                        } else {
                            log.debug("Cтрока content длиной больше 1024 символов!");
                            flag = true;
                            break;
                        }

                        NodeList dateNmElmntLst = element.getElementsByTagName("creationDate");
                        Element dateNmElmnt = (Element) dateNmElmntLst.item(0);
                        NodeList dateNm = dateNmElmnt.getChildNodes();
                        String creationDate = ((Node) dateNm.item(0)).getNodeValue();
                        log.debug("Creation date : {}", creationDate);
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        dateFormat.setLenient(false);
                        try {
                            log.debug("Date VALID is: {}", dateFormat.format(dateFormat.parse(creationDate)));
                        } catch (Exception e) {
                            log.debug("Неккоректный формат даты создания записи!");
                            flag = true;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error: {}.", e.toString());
            flag = true;
        }
        return flag;
    }

}
