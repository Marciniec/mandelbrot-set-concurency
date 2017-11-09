import java.io.*;

public class MandelbrotSimulation {
    static final String FILE_NAME = "results.txt";
    File result_file;
    int[] iterrationsArray = {100, 200, 300, 400, 500, 1000, 2000, 5000, 10000};
    int[] threadNumber = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 20, 50, 100, 150};

    public MandelbrotSimulation() {
        this.result_file = new File(FILE_NAME);
    }
    void simulate(){
        for (int iteration :
                iterrationsArray) {
            for (int thread :
                    threadNumber) {
                makeSimulation(thread, iteration);
            }
        }
    }

    void makeSimulation(int threadNumber, int iterations) {
        long start = System.nanoTime();
        new Mandelbrot(iterations, threadNumber).setVisible(false);
        long end = System.nanoTime();
        double time = (end - start) / Math.pow(10, 6);
        try (FileOutputStream fileOutputStream = new FileOutputStream(result_file, true);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream)) {
            outputStreamWriter.write(String.format("Time of computing Mandelbrot set for %d iterations and %d threads in thread pool is %fl ms \n", iterations, threadNumber, time));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
