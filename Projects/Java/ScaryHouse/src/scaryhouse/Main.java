/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scaryhouse;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author Nahyro
 */
public class Main {

    /**
     * @param args the command line arguments
     */
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
