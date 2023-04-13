package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;
import huglife.HugLifeUtils;

import java.awt.Color;
import java.util.Map;
import java.util.List;

public class Clorus extends Creature {
    private int r;
    private int g;
    private int b;
    private double repEnergyRetained = 0.5;
    private double repEnergyGiven = 0.5;
    private double moveEnergyDecreased = 0.03;
    private double stayEnergyDecreased = 0.01;

    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    public Color color() {
        return color(r, g, b);
    }

    public void attack(Creature c) {
        energy += c.energy();
    }

    public void move() {
        energy -= moveEnergyDecreased;
    }

    public void stay() {
        energy -= stayEnergyDecreased;
    }

    public Clorus replicate() {
        double babyEnergy = energy * repEnergyGiven;
        energy = energy * repEnergyRetained;
        return new Clorus(babyEnergy);
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        java.util.List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plip = getNeighborsOfType(neighbors, "plip");
        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }
        if (plip.size() > 0) {
            Direction moveDir = HugLifeUtils.randomEntry(plip);
            return new Action(Action.ActionType.ATTACK, moveDir);
        }
        if (energy > 1.0) {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, moveDir);
        } else {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.MOVE, moveDir);
        }
    }
}
