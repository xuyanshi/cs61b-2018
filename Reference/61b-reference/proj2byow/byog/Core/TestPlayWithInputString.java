package byog.Core;

import byog.TileEngine.TETile;

public class TestPlayWithInputString {
    public static void main(String[] args) {
        Game game = new Game();
//        TETile[][] w1 = game.playWithInputString("N123SDDDWWWDDD");
        TETile[][] w2 = game.playWithInputString("N123SDDD:Q");
        TETile[][] w3 = game.playWithInputString("LWWW:Q");
        TETile[][] w4 = game.playWithInputString("LDDD:Q");
        System.out.println(TETile.toString(w4));
    }
}
