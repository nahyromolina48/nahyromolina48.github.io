package scaryhouse;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.println("Input a file name");
        String file = kb.nextLine();
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
            InputStream input = new FileInputStream(file);
            SAXParser saxParser = spf.newSAXParser();
            Parser p = new Parser();
            saxParser.parse(input, p);
        } catch (SAXException | ParserConfigurationException | java.io.IOException e) {
            System.out.println(e);
            System.out.println("Game is now corrupted, please restart game or go to gamestop");
        }
    }
}
