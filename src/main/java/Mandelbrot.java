import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {

    private final int MAX_ITER;
    private final double ZOOM = 150;
    private int noOfThreads;
    private BufferedImage I;

    public Mandelbrot(int max_iter, int noOfThreads) {
        super("Mandelbrot Set");
        MAX_ITER =max_iter;
        this.noOfThreads = noOfThreads;
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        List<Future<MandelbrotCoordinates>> mandelbrotCoordinatesList = new ArrayList<>();
        ExecutorService sequenceExecutor = Executors.newFixedThreadPool(noOfThreads);
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Callable<MandelbrotCoordinates> coordinatesCallable = new MandelbrotIterationCounter(MAX_ITER, ZOOM, x, y);
                mandelbrotCoordinatesList.add(sequenceExecutor.submit(coordinatesCallable));
            }
        }
        mandelbrotCoordinatesList.forEach(
                mandelbrotCoordinatesFuture -> {
                    try {
                        MandelbrotCoordinates mandelbrotCoordinates = mandelbrotCoordinatesFuture.get();

                        I.setRGB(mandelbrotCoordinates.getX(), mandelbrotCoordinates.getY(),
                                mandelbrotCoordinates.getIterations()
                                | mandelbrotCoordinates.getIterations() << 8);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
        );

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

}