import java.util.concurrent.Callable;

public class MandelbrotIterationCounter implements Callable {

    private final int MAX_ITER;
    private final double ZOOM;
    private int x;
    private int y;
    private double zx, zy, cX, cY, tmp;

    public MandelbrotIterationCounter(int max_iter, double zoom, int x, int y) {
        MAX_ITER = max_iter;
        ZOOM = zoom;
        this.x = x;
        this.y = y;
    }

    @Override
    public Object call() throws Exception {
        zx = zy = 0;
        cX = (x - 400) / ZOOM;
        cY = (y - 300) / ZOOM;
        int iter = MAX_ITER;
        while (zx * zx + zy * zy < 4 && iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        return new MandelbrotCoordinates(x, y, iter);
    }
}
