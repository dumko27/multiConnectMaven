package com.multiconnect;

import com.multiconnect.mapping.Entry;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
public class Main {

    static final Logger log = LoggerFactory.getLogger(Main.class);
    private final String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
    List<Entry> listEntry = new ArrayList<>();

    public static void main(String argv[]) throws IOException {
        Main xMLReader = new Main();
        xMLReader.runExample();
    }

    private void runExample() throws IOException {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        File file = new File(System.getProperty("user.dir"));
        String parentDirectory = file.getAbsolutePath() + "/src/test/resources/TestXML",
                newDirectory = file.getAbsolutePath() + "/src/test/resources/GoodXML";
        log.debug("parentDirectory={}, \n newDirectory={}", parentDirectory, newDirectory);
        parseAllFiles(parentDirectory, newDirectory);
        log.debug("END listEntry: {}.", listEntry);

    }

    /**
     * Перемещение файлов в каталог обработанных файлов
     * 
     * @param parentDirectory Директория в которой находятся файлы для мониторинга.
     * @param newDirectory Каталог обработанных файлов.
     * @throws IOException 
     */
    private void parseAllFiles(String parentDirectory, String newDirectory) throws IOException {
        File[] filesInDirectory = new File(parentDirectory).listFiles();
        File dir = new File(newDirectory);
        dir.mkdirs();
        log.debug("dir={}", dir);
        for (File file : filesInDirectory) {
            if (file.isDirectory()) {
                log.debug("Name directory={}", file.getName());
                parseAllFiles(file.getAbsolutePath(), newDirectory);
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

    private boolean parsingXML(String nameFile) {
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
                flag = checkAndParseXML(doc, flag);
            }
        } catch (Exception e) {
            log.error("Error parsingXML: {}.", e.toString());
            flag = true;
        }
        return flag;
    }

    private boolean checkAndParseXML(Document doc, boolean flag) {
        try {
            NodeList nodeLst = doc.getElementsByTagName("Entry");
            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node fstNode = nodeLst.item(s);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) fstNode;
                    String content = getBody(element, "content");
                    flag = checkContent(content);
                    String creationDate = null;
                    if (!flag) {
                        creationDate = getBody(element, "creationDate");
                        flag = checkCreationDate(creationDate);
                    } else {
                        break;
                    }
                    if (!flag) {
                        Entry entry = new Entry(content, new SimpleDateFormat(FORMAT_DATE).parse(creationDate));
                        log.debug("entry: {}.", entry);
                        
                        Factory.getInstance().getEntryDAO().addEnties(entry);
                        
                        listEntry.add(entry);
                        log.debug("listEntry: {}.", listEntry);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error checkAndParseXML: {}.", e.toString());
            flag = true;
        }
        return flag;
    }

    /**
     * Получение значения тела передаваемого тега
     *
     * @param element
     * @param tagName
     * @return
     */
    private String getBody(Element element, String tagName) {
        NodeList nmElmntLst = element.getElementsByTagName(tagName);
        Element nmElmnt = (Element) nmElmntLst.item(0);
        NodeList contentNm = nmElmnt.getChildNodes();
        String content = ((Node) contentNm.item(0)).getNodeValue();
        return content;
    }

    /**
     * Проверка тела тега <content>
     *
     * @param content
     * @return
     */
    private boolean checkContent(String content) {
        boolean flag = false;
        log.debug("Content : {}", content);
        if (content.length() < 1024) {
            log.debug("Content length < 1024");
        } else {
            log.debug("Cтрока content длиной больше 1024 символов!");
            flag = true;
        }
        return flag;
    }

    /**
     * Проверка тела тега <creationDate>
     *
     * @param creationDate
     * @return
     */
    private boolean checkCreationDate(String creationDate) {
        boolean flag = false;
        log.debug("Creation date : {}", creationDate);
        DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);
        dateFormat.setLenient(false);
        try {
            log.debug("Date VALID is: {}", dateFormat.format(dateFormat.parse(creationDate)));
        } catch (Exception e) {
            log.debug("Неккоректный формат даты создания записи!");
            flag = true;
        }
        return flag;
    }

}
