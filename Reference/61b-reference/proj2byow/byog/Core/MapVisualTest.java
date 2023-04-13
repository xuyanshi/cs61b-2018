package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class MapVisualTest {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    private static final long SEED = 33;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        MapGenerator mg = new MapGenerator(WIDTH, HEIGHT, SEED);
        TETile[][] map = mg.generate();
        ter.renderFrame(map);
    }
}
