package lab14;

import lab14lib.Generator;

/**
 * @author xuyanshi
 * @date 2023/4/18 13:21
 */
public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private final double factor;
    private int state;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
    }

    /**
     * Returns a number between -1 and 1
     */
    @Override
    public double next() {
        state = (state + 1) % period;
        double output = 2.0 * state / period - 1;
        if (state % period == 0) {
            period = (int) Math.round(period * factor);
        }
        return output;
    }
}
