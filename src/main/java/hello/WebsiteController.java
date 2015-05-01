/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.util.locale.StringTokenIterator;

/**
 *
 * @author Stephen R. Williams
 * See Application.java for assignment text.
 */
@RestController
public class WebsiteController {

    //change this filepath to reflect your documents folder
    private final String filepath = "E:\\Users\\home\\Documents\\NetBeansProjects\\" + "INEW2438_Proj2.csv";
    private final AtomicLong counter = new AtomicLong();

    //handles all RequestMethod types
    @RequestMapping(value = "/website/test")//, method = RequestMethod.GET
    //simple method to test Website object creation
    public Website website(
            @RequestParam(value = "cat", defaultValue = "defaultCategory") String category,
            @RequestParam(value = "url", defaultValue = "http://www.example.com") String url
    ) {
        return new Website(counter.incrementAndGet(), category, url);
    }

    //store the given website object into a file
    //http://localhost:8080/store?cat=categoryHere&url=http://www.example.com
    @RequestMapping(value = "/store")//, method = RequestMethod.GET
    public Website store(
            @RequestParam(value = "cat", defaultValue = "defaultCategory") String category,
            @RequestParam(value = "url", defaultValue = "http://www.example.com") String url
    ) throws IOException {
        //no control for unique id 
        Website website = new Website(counter.incrementAndGet(), category, url);
        storeWebsite(website);
        return website;//g ok
    }

    //file handling submethod. appends new website object to file.
    private void storeWebsite(Website website) throws IOException {
        String record = website.toString();
        File file = new File(filepath);
        boolean newFile = false;
        if (!(file.exists())) {
            file.createNewFile();//create new file if non-existent
            newFile = true;
        }
        FileWriter fw = new FileWriter(file, true);//true for append mode
        BufferedWriter bw = new BufferedWriter(fw);
        if (!(newFile)) {
            bw.newLine();//add new line before next record if this is not a new file
        }
        bw.append(record);
        bw.close();
    }

    //retrieve website object by id field. grabs the first matching id without looking for duplicates
    //http://localhost:8080/grab?id=1
    @RequestMapping(value = "/grab")//, method = RequestMethod.GET
    public Website grab(
            @RequestParam(value = "id", defaultValue = "1") String id_s
    ) throws IOException {
        long id = Long.parseLong(id_s);
        Website website = grabWebsite(id);
        if (website == null) {
            return new Website(-404, "NOT FOUND", "NOT FOUND");
        }
        return website;
    }

    //file handling submethod for finding the website by id
    private Website grabWebsite(long id) throws FileNotFoundException, IOException {
        Website website = null;
        FileReader fr = new FileReader(filepath);
        BufferedReader br = new BufferedReader(fr);
        String thisLine = "";
        while ((thisLine = br.readLine()) != null) {
            website = parseLine(thisLine);
            if (website.getId() == id) {//check if this is the correct id
                br.close();
                return website;//found one, stop searching.
            }
        }
        br.close();
        return null;//nothing found
    }

    //show all the websites report
    //http://localhost:8080/list
    @RequestMapping(value = "/list")//, method = RequestMethod.GET
    public String list() throws IOException {
        String output = "List of websites: \n";
        output += grabList().toString();//nothing fancy yet, just return the toString of the list.
        return output;
    }

    //returned with newline delim between records. view source in browser if it does not display line breaks
    private List<Website> grabList() throws FileNotFoundException, IOException {
        List<Website> list = new ArrayList();
        Website website = null;
        FileReader fr = new FileReader(filepath);
        BufferedReader br = new BufferedReader(fr);
        String thisLine = "", output = "";
        while ((thisLine = br.readLine()) != null) {
            //output += thisLine + "\n";
            website = parseLine(thisLine);
            list.add(website);            
        }
        return list;
    }

    private Website parseLine(String thisLine) {
        //System.out.println("Parsing: "+thisLine);
        StringTokenizer st = new StringTokenizer(thisLine, ",");
        long id = Long.parseLong(st.nextToken());
        String cat = st.nextToken();
        String url = st.nextToken();
        return new Website(id,cat,url);
    }

}
