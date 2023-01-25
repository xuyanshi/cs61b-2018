package byog.Core;

import byog.TileEngine.TETile;

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {
    private final int WIDTH = 0;
    private final int HEIGHT = 0;
    private Random RANDOM;
    private long SEED;
    private static final int TIMES = 100;
    private static TETile[][] world;
    private static TETile[][] positionOfRoom;
    private static ArrayList<Room> Rooms;
    

    public static void main(String[] args) {
        System.out.println("MapGenerator");
    }
}
