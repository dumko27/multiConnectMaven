package com.multiconnect;

import com.multiconnect.mapping.Entry;
import java.io.*;
import java.util.*;
import org.slf4j.*;

/**
 * Class that read xml file from directory and move this xml file in new
 * directory
 *
 * @author Novikov Dmitry
 */
public class Main {

    static final Logger log = LoggerFactory.getLogger(Main.class);
    List<Entry> listEntry = new ArrayList<>();

    public static void main(String argv[]) throws IOException {
        CheckAndParseXML checkAndParseXML = new CheckAndParseXML();
        //ForkJoinPool pool = new ForkJoinPool();
        checkAndParseXML.runExample();

    }

}
