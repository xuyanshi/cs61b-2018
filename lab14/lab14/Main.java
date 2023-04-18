package lab14;

import lab14lib.Generator;
import lab14lib.GeneratorDrawer;
import lab14lib.GeneratorPlayer;

public class Main {
    public static void main(String[] args) {
        /* Your code here. */
        Generator generator = new SineWaveGenerator(200);
        GeneratorDrawer gd = new GeneratorDrawer(generator);
        gd.draw(4096);
    }
} 