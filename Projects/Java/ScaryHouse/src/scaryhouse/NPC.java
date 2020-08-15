/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scaryhouse;
public class NPC extends Characters{
    int health;
    int opponents;
    
    public NPC(String name, String description){
        super(name, description);
        this.health = 100;
        this.opponents = 5;
    }
}
