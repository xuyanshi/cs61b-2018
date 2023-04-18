package lab14;

import edu.princeton.cs.algs4.StdAudio;
import lab14lib.Generator;

/**
 * @author xuyanshi
 * @date 2023/4/18 13:05
 */
public class SawToothGenerator implements Generator {

    private int period;
    private int state;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    /**
     * Returns a number between -1 and 1
     */
    @Override
    public double next() {
        state = (state + 1);
        return 2.0 * (state % period) / period - 1;
    }
}
