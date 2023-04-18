package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.Map;

/**
 * @author xuyanshi
 * @date 2023/4/18 14:02
 */
public class Clorus extends Creature {
    /**
     * Creates a creature with the name N. The intention is that this
     * name should be shared between all creatures of the same type.
     *
     * @param n
     */
    public Clorus(String n) {
        super(n);
    }

    /**
     * Called when this creature moves.
     */
    @Override
    public void move() {

    }

    /**
     * Called when this creature attacks C.
     *
     * @param c
     */
    @Override
    public void attack(Creature c) {

    }

    /**
     * Called when this creature chooses replicate.
     * Must return a creature of the same type.
     */
    @Override
    public Creature replicate() {
        return null;
    }

    /**
     * Called when this creature chooses stay.
     */
    @Override
    public void stay() {

    }

    /**
     * Returns an action. The creature is provided information about its
     * immediate NEIGHBORS with which to make a decision.
     *
     * @param neighbors
     */
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        return null;
    }

    /**
     * Required method that returns a color.
     */
    @Override
    public Color color() {
        return null;
    }
}
