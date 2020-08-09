/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scaryhouse;

import java.util.ArrayList;

/**
 *
 * @author Nahyro
 */
public class Room {

    String name, description;
    Room n, s, e, w;
    ArrayList<Item> stuff = new ArrayList<>();
    ArrayList<Characters> people = new ArrayList<>();

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Room getNorth() {
        return n;
    }

    public Room getSouth() {
        return s;
    }

    public Room getEast() {
        return e;
    }

    public Room getWest() {
        return w;
    }

    public void setNorth(Room n) {
        this.n = n;
    }

    public void setSouth(Room s) {
        this.s = s;
    }

    public void setEast(Room e) {
        this.e = e;
    }

    public void setWest(Room w) {
        this.w = w;
    }

    public void addCharacter(Characters c) {
        people.add(c);
        c.setRoom(this);
    }

    public void removeCharacter(Characters c) {
        people.remove(c);
    }

    public void addItem(Item e) {
        stuff.add(e);
    }

    public void removeItem(Item e) {
        stuff.remove(e);
    }

    public ArrayList<Item> getItemList() {
        return stuff;
    }

    public void printItemList() {
        System.out.println("\nItems in this Room:");
        stuff.stream().forEach((product) -> {
            System.out.println(product.name);
        });
    }

    @Override
    public String toString() {
        String info = "\nRoom: " + name + " Details: " + description + "\n\n"
                + "Adjacent Rooms: \nNorth: " + this.getNorth().getName() + ""
                + "\nSouth: " + this.getSouth().getName();

        return info;
    }

}
