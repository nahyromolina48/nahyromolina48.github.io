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
public class Characters {
    String name;
    String description;
    Room r;
    
    public Characters(String name, String description){
        this.name = name;
        this.description = description;
    }
    
    public void setRoom(Room r){
        this.r = r;
    }
    
}
