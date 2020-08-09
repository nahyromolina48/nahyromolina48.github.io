package scaryhouse;
import java.util.Scanner;
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
        String command;
        Scanner kb = new Scanner(System.in);
        Help();
        System.out.println("\nWelcome to my Haunted house, beware of the haunting and the creepy visitor!!");
        while (true) {
            System.out.println("What is your command");
            command = kb.nextLine();
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
                    try {
                        current.removeCharacter(this);
                        current.getNorth().addCharacter(this);
                        this.setRoom(current);
                        System.out.println(this.name + " moved north to the " + current.getName() + " room");
                    } catch (NullPointerException e) {
                        System.out.println("No room exist in that direction please try again");
                    }
                    break;
                case "s":
                    try{
                        current.removeCharacter(this);
                        current.getSouth().addCharacter(this);
                        this.setRoom(current);
                        System.out.println(this.name + " moved south to the " + current.getName());
                    }catch (NullPointerException e) {
                        System.out.println("No room exist in that direction please try again");
                    }
                    break;
                case "e":
                    try {
                        current.removeCharacter(this);
                        current.getEast().addCharacter(this);
                        this.setRoom(current);
                        System.out.println(this.name + " moved east to the " + current.getName());
                    }catch (NullPointerException e) {
                        System.out.println("No room exist in that direction please try again");
                    }
                    break;
                case "w":
                    try {
                        current.removeCharacter(this);
                        current.getWest().addCharacter(this);
                        this.setRoom(current);
                        System.out.println(this.name + " moved west to the " + current.getName());
                    }catch (NullPointerException e) {
                        System.out.println("No room exist in that direction please try again");
                    }
                    break;
                case "scare":
                    current.printItemList();
                    break;
                default:
                    System.out.println("Error!!!");
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return "Name: " + name + " Description: " + description;
    }

}
