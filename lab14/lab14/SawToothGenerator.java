package lab14;

import edu.princeton.cs.algs4.StdAudio;
import lab14lib.Generator;

/**
 * @author xuyanshi
 * @date 2023/4/18 13:05
 */
public class SawToothGenerator implements Generator {

    private double frequency;
    private int state;

    public SawToothGenerator(double frequency) {
        state = 0;
        this.frequency = frequency;
    }

    /**
     * Returns a number between -1 and 1
     */
    @Override
    public double next() {
        state = (state + 1);
        double period = StdAudio.SAMPLE_RATE / frequency;
        return Math.sin(state * 2 * Math.PI / period);
    }
}
