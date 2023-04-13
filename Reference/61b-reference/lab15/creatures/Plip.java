package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;
import huglife.HugLifeUtils;

import java.awt.Color;
import java.util.Map;
import java.util.List;

/**
 * An implementation of a motile pacifist photosynthesizer.
 *
 * @author Josh Hug
 */
public class Plip extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;
    private double moveProbability = 0.5;
    private double repEnergyRetained = 0.5;
    private double repEnergyGiven = 0.5;
    private double moveEnergyDecreased = 0.15;
    private double stayEnergyIncreased = 0.2;
    /**
     * creates plip with energy equal to E.
     */
    public Plip(double e) {
        super("plip");
        r = 99;
        g = 63;
        b = 76;
        energy = e;
    }

    /**
     * creates a plip with energy equal to 1.
     */
    public Plip() {
        this(1);
    }

    /**
     * Should return a color with red = 99, blue = 76, and green that varies
     * linearly based on the energy of the Plip. If the plip has zero energy,
     * it should have a green value of 63. If it has max energy, it should
     * have a green value of 255. The green value should vary with energy
     * linearly in between these two extremes. It's not absolutely vital
     * that you get this exactly correct.
     */
    public Color color() {
        g = (int) (63 + 192 * energy / 2);
        return color(r, g, b);
    }

    /**
     * Do nothing with C, Plips are pacifists.
     */
    public void attack(Creature c) {
    }

    /**
     * Plips should lose 0.15 units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    public void move() {
        energy -= moveEnergyDecreased;
    }


    /**
     * Plips gain 0.2 energy when staying due to photosynthesis.
     */
    public void stay() {
        energy += stayEnergyIncreased;
        if (energy > 2) {
            energy = 2;
        }
    }

    /**
     * Plips and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Plip.
     */
    public Plip replicate() {
        double babyEnergy = energy * repEnergyGiven;
        energy = energy * repEnergyRetained;
        return new Plip(babyEnergy);
    }

    /**
     * Plips take exactly the following actions based on NEIGHBORS:
     * 1. If no empty adjacent spaces, STAY.
     * 2. Otherwise, if energy >= 1, REPLICATE.
     * 3. Otherwise, if any Cloruses, MOVE with 50% probability.
     * 4. Otherwise, if nothing else, STAY
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> clorus = getNeighborsOfType(neighbors, "clorus");
        if (energy > 1.0 && empties.size() != 0) {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, moveDir);
        }
        if (clorus.size() > 0 && empties.size() != 0 && energy >= moveEnergyDecreased) {
            if (HugLifeUtils.random() < moveProbability) {
                Direction moveDir = HugLifeUtils.randomEntry(empties);
                return new Action(Action.ActionType.MOVE, moveDir);
            }
        }
        return new Action(Action.ActionType.STAY);
    }

}
