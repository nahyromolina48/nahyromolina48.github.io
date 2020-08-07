/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scaryhouse;

import java.util.HashMap;
import java.util.Scanner;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Nahyro
 */
public class Parser extends DefaultHandler {
    Player person;
    Room r;

    HashMap<String, Room> rooms = new HashMap<>();
    
    
    @Override
    public void startDocument() throws SAXException {  
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        String name = atts.getValue("name");
        String facts = atts.getValue("description");
        if (qName.equals("room")) {
            r = new Room(name, facts);
            String north = atts.getValue("north");
            String south = atts.getValue("south");
            String east = atts.getValue("east");
            String west = atts.getValue("west");
            if ((north != null) && (rooms.get(north) != null)) {
                r.setNorth(rooms.get(north));
                rooms.get(north).setSouth(r);
            }
            if ((south != null) && (rooms.get(south) != null)) {
                r.setSouth(rooms.get(south));
                rooms.get(south).setNorth(r);
            }
            if ((east != null) && (rooms.get(east) != null)) {
                r.setEast(rooms.get(east));
                rooms.get(east).setWest(r);
            }
            if ((west != null) && (rooms.get(west) != null)) {
                r.setWest(rooms.get(west));
                rooms.get(west).setEast(r);
            }
        }
        if (qName.equals("item")) {
            Item obj = new Item(name,facts);
            String[] list = atts.getValue("actions").split(",");
            for(String act : list){
                act = act.toUpperCase();
                obj.addAction(Item.Actions.valueOf(act));
            }
            r.addItem(obj);
        }
        if (qName.equals("adult")) {
            Adult old = new Adult(name, facts);
            r.addCharacter(old);
            old.setRoom(r);

        }
        if (qName.equals("child")) {
            Child young = new Child(name, facts);
            r.addCharacter(young);
            young.setRoom(r);
        }
        if (qName.equals("player")) {
            person = new Player(name, facts);
            r.addCharacter(person);
            person.setRoom(r);
        }
        rooms.put(name, r);
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        
    }
    
    @Override
    public void endDocument() throws SAXException {
        person.play();
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

    }
}
