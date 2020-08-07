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
public class Child extends NPC {

    Item stuff;
    Room r;

    public Child(String name, String description) {
        super(name, description);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getScaredLevel() {
        return health;
    }

    public int reaction(Item.Actions a) {
        
        if (health < 100 && health > 0) {
            switch (a) {
                case POSSESS:
                    break;
                case SHAKE:
                    break;
                case THROW:
                    break;
                default:
                    break;

            }
        }else{
            
        }

        return health;
    }

    @Override
    public void setRoom(Room r) {
        this.r = r;
    }

}
