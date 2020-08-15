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
public class Item {

    String name, description;

    enum Actions {
        POSSESS, SHAKE, THROW
    };
    
    ArrayList<Actions> abilities = new ArrayList<>();

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
//This first checks if an action can be done with this item then adds action to an item
    public String getItemName(){
        return name;
    }
    
    public String getItemDetail(){
        return description;
    }

    public Item getItem(){
        return this;
    }
    
    public void addAction(Actions a) {
        if(!abilities.contains(a)){
            abilities.add(a);
        }
    }
    
    
    
    @Override
    public String toString(){
        return "\nItem name: " + name + " Item Description: " + description + " Actions: " + abilities.toString();
    }

}
