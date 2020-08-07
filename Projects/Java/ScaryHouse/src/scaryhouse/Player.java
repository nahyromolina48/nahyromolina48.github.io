/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scaryhouse;

import java.util.Scanner;

/**
 *
 * @author Nahyro
 */
public class Player extends Characters {

    Room current;
    Adult adult;
    Child child;
    Item item;

    public Player(String name, String description) {
        super(name, description);
        this.name = name;
        this.description = description;
    }

    @Override
    public void setRoom(Room rs) {
        current = rs;
    }

    /*public void selection(Room current, enum a) {
     Item item = new Item(this.current.getItem(a));
     if (this.current.contains(possess) && this.i.getAct(Item.ItemAction.POSSESS)) {
     this.i.Possess(Item.ItemAction.POSSESS);
     for (Characters c : this.current.getCharacters()) {
     if (c instanceof Adult) {
     ((Adult) c).reaction(Item.ItemAction.POSSESS);
     }
     if (c instanceof Child) {
     ((Child) c).reaction(Item.ItemAction.POSSESS);
     }
     }
     break;
     } else {
     System.out.println("Invaild Item please try again");
     System.out.println("Which Item would you like to possess? or type Esc to go back");
     possess = kb.nextLine();
     }
     }*/
    public void Help() {
        System.out.println("Controls: \nn -- go North\ns -- go South\ne -- go East\nw -- go West\n"
                + "Actions: \nPossess:[ItemName]\nShake:[ItemName]\nThrow:[ItemName]\n"
                + "Commands: \nLook -- Observe the room you're in\nHelp -- Prints out additional Information"
                + "\nQuit -- Leave the game if you are afraid!!");
    }

    public void Look(Room r) {
        System.out.println(r.toString());
    }

    public void Quit() {
        System.exit(0);
    }

    public void play() {
        Scanner kb = new Scanner(System.in);
        this.Help();
        System.out.println("\nWelcome to my Haunted house, beware of the haunting and the creepy visitor!!\nWhat is your Command");
        String command = kb.nextLine();
        switch (command) {
            case "Help":
                Help();
                break;
            case "Look":
                Look(current);
                break;
            case "Quit":
                System.out.println("Bye and Don't come back!!");
                Quit();
                break;
            case "n":
                if (current.getNorth() != null) {
                    current.removeCharacter(this);
                    current.getNorth().addCharacter(this);
                    this.setRoom(this.current.getNorth());
                    System.out.println("You moved north to the " + this.current.getName() + " room");
                } else {
                    System.out.println("No room exist in that direction");
                }
                break;
            case "s":
                if (current.getSouth() != null) {
                    current.removeCharacter(this);
                    current.getSouth().addCharacter(this);
                    setRoom(current.getSouth());
                    System.out.println("You moved south to the " + current.getName());
                } else {
                    System.out.println("No room exist in that direction");
                }
                break;
            case "e":
                if (current.getEast() != null) {
                    current.removeCharacter(this);
                    current.getEast().addCharacter(this);
                    setRoom(current.getEast());
                    System.out.println("You moved east to the " + current.getName());
                } else {
                    System.out.println("No room exist in that direction");
                }
                break;
            case "w":
                if (current.getWest() != null) {
                    current.removeCharacter(this);
                    current.getWest().addCharacter(this);
                    this.setRoom(current.getWest());
                    System.out.println("You moved west to the " + current.getName());
                } else {
                    System.out.println("No room exist in that direction");
                }
                break;
        }

    }

    @Override
    public String toString() {
        return "Name: " + name + " Description: " + description;
    }

}
