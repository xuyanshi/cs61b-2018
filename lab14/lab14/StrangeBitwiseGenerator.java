package lab14;

import lab14lib.Generator;

/**
 * @author xuyanshi
 * @date 2023/4/18 13:23
 */
public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        state = 0;
        this.period = period;
    }

    /**
     * Returns a number between -1 and 1
     */
    @Override
    public double next() {
        state = (state + 1);
        return period * (state % period + 1) / 2.0;
    }
}
