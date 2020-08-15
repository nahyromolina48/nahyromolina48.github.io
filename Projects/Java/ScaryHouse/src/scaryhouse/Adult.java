/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scaryhouse;

/**
 *
 * @author Nahyro
 */
public class Adult extends NPC {
    Room r;
    
    public Adult(String name, String description){
        super(name, description);
    }
    
    public String getName(){
        return name;
    }
    
    public String getDescription(){
        return description;
    }
    
    public int getScaredLevel(){
        return health;
    }
    
    @Override
    public void setRoom(Room r){
        this.r = r;
    }
    
    public int reaction(Item.Actions s){
        switch(s){
            case POSSESS:
                health = health - 15;
                if(health > 0 && health < 50){
                    //run away
                    System.out.println(name + " was frightened");
                }
                if(health < 0){
                    //remove person from the house(game)
                    System.out.println("That's it Im leaving");
                }
                break;
            case SHAKE:
                health = health - 12;
                break;
            case THROW:
                health = health - 9;
                break;
            default:
                break;
        }
        return 0;
    }
    
    @Override
    public String toString(){
        return "\nName: " + name + " Description: " + description;
    }
    
}
