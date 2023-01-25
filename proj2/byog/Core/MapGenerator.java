package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class MapGenerator implements Serializable {
    private int WIDTH = 0;
    private int HEIGHT = 0;
    private Random RANDOM;
    private long SEED;
    private static final int TIMES = 100;
    private static TETile[][] world;
    private static TETile[][] positionOfRoom;
    private static ArrayList<Room> Rooms;

    public MapGenerator(int width, int height, long seed) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.SEED = seed;
        this.RANDOM = new Random(seed);

        boolean[][] isPassed = new boolean[width][height];
        
    }

    public static void main(String[] args) {
        System.out.println("MapGenerator");
    }
}
