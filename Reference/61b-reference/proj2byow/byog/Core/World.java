package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

import static byog.Core.RandomUtils.uniform;

public class World implements Serializable {
    public class Player implements Serializable {
        private static final long serialVersionUID = 20000000311L;
        int x;
        int y;
        TETile t;

        public Player() {
            t = TETile.colorVariant(Tileset.PLAYER, 64, 64, 64, RANDOM);
            int xi = uniform(RANDOM, 0, width);
            int yi = uniform(RANDOM, 0, height);
            while (xi < width && yi < height) {
                if (map[xi][yi].character() == Tileset.FLOOR.character()) {
                    this.x = xi;
                    this.y = yi;
                    break;
                }
                yi++;
                if (yi == height) {
                    xi++;
                    yi = 0;
                }
                if (xi == width) {
                    xi = 0;
                }
            }

            replacedTile = map[this.x][this.y];
            map[this.x][this.y] = t;
        }

        private void move(int dx, int dy) {
            if (x + dx >= width || y + dy >= height || x + dx < 0 || y + dy < 0) {
                return;
            }
            if (map[x + dx][y + dy].character() == Tileset.FLOOR.character()) {
                map[x][y] = replacedTile;
                x = x + dx;
                y = y + dy;
                replacedTile = map[x][y];
                map[x][y] = t;
            } else if (map[x + dx][y + dy].character() == Tileset.LOCKED_DOOR.character()) {
                map[x + dx][y + dy] =
                        TETile.colorVariant(Tileset.UNLOCKED_DOOR, 64, 64, 64, RANDOM);
            } else if (map[x + dx][y + dy].character() == Tileset.UNLOCKED_DOOR.character()) {
                map[x][y] = replacedTile;
                x = x + dx;
                y = y + dy;
                replacedTile = map[x][y];
                map[x][y] = t;
                gameOver = true;
            }
        }
    }

    private static final long serialVersionUID = 1231231231L;
    TETile[][] map;
    private Player player;
    private TETile replacedTile;
    private Random RANDOM;
    private int height;
    private int width;
    boolean gameOver = false;

    public World(TETile[][] map, long seed) {
        this.map = map;
        this.height = map[0].length;
        this.width = map.length;
        RANDOM = new Random(seed);
        player = new Player();
    }

    public void controlPlayer(Character c) {
        switch (c) {
            case 'w':
                player.move(0, 1);
                break;
            case 'a':
                player.move(-1, 0);
                break;
            case 's':
                player.move(0, -1);
                break;
            case 'd':
                player.move(1, 0);
                break;
            default:
        }
    }

}
