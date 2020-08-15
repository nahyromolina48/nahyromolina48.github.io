package scaryhouse;

import java.util.ArrayList;
public class Room {

    String name, description;
    Room north, south, east,west;
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
        return north;
    }

    public Room getSouth() {
        return south;
    }

    public Room getEast() {
        return east;
    }

    public Room getWest() {
        return west;
    }

    public void setNorth(Room n) {
        this.north = n;
    }

    public void setSouth(Room s) {
        this.south = s;
    }

    public void setEast(Room e) {
        this.east = e;
    }

    public void setWest(Room w) {
        this.west = w;
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
    
    public Boolean isPresent(String s,String a){
        for(Item i : stuff){
            if(i.name.equals(s)){
                return i.abilities.contains(Item.Actions.valueOf(a));
            }
        }
        return false;
    }
    
    public String printItemList(){
        String info = "\n\nItems in Room:";
        for(Item i : stuff){
            info = info + i.toString();
        }
        return info;
    }


    @Override
    public String toString() {
        String roomInfo = "\nRoom: " + name + " Details: " + description;
        roomInfo = roomInfo + "\n\nItems in Room:";
        for(Item i : stuff){
            roomInfo = roomInfo + i.toString();
        }
        roomInfo = roomInfo + "\n\nPeople in Room:";
        for(Characters c : people){
            roomInfo = roomInfo + c.toString();
        }
        roomInfo = roomInfo + "\n\nNearby Rooms:";
        if (north != null) {
            roomInfo = roomInfo + "\nnorth: " + north.getName();
        }
        if (south != null) {
            roomInfo = roomInfo + "\nsouth: " + south.getName();
        }
        if (east != null) {
            roomInfo = roomInfo + "\neast: " + east.getName();
        }
        if (west != null) {
            roomInfo = roomInfo + "\nwest: " + west.getName();
        }
        return roomInfo;
    }

}
