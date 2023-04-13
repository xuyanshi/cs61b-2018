package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
    }

    public double next() {
        state = (state + 1) % (period);
        return normalize(state);
    }

    private double normalize(int curState) {
        if (state == 0) {
            period = (int) Math.floor(period * factor);
        }
        return -1.0 + (double) curState / period * 2.0;
    }
}
