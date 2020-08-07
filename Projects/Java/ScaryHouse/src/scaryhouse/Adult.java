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
    
    @Override
    public String toString(){
        return "Name: " + name + " Description: " + description;
    }
    
}
