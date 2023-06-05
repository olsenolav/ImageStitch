import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.LinkedList;

public class ZphotoReader {
    private BufferedImage photo;
    int[] lEdgePix;
    int[][] rEdgePix;
    int[] tEdgePix;
    int[][] bEdgePix;
    int[][] fixedBPix;
    double[] normLEPix;
    double[][] normREPix;
    double[] normTEPix;
    double[][] normBEPix;
    double[][] normFixedP;
    private int difference = 0;

    public ZphotoReader(String fileName) {
        File file = new File(fileName);
        try {
            photo = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println(e);
        }
        lEdgePix = new int[photo.getHeight() - 10];
        tEdgePix = new int[photo.getWidth() - 10];
        bEdgePix = new int[(int) (photo.getHeight() * 0.15)][photo.getWidth()];
        normLEPix = new double[photo.getHeight() - 10];
        normTEPix = new double[photo.getWidth() - 10];
        normBEPix = new double[(int) (photo.getHeight() * 0.15)][photo.getWidth()];
    }

    // find red value of a pixel
    public int getRedP(int pix) {
        int red = (pix >> 16) & 0xff;
        /*
         * int alpha = (pix >> 24) & 0xff;
         * int green = (pix >> 8) & 0xff;
         * int blue = (pix) & 0xff;
         */
        return red;
    }

    // create array of red pixel values for left side
    public void lPixArr() {
        for (int i = 5; i < photo.getHeight() - 5; i++) {
            int pixel = photo.getRGB(photo.getWidth() - 1, i);
            lEdgePix[i - 5] = getRedP(pixel);
        }
    }

    public void normLPArr() {
        int reddest = -1;
        int leastRed = 256;
        for (int i = 0; i < lEdgePix.length; i++) {
            if (lEdgePix[i] < leastRed)
                leastRed = lEdgePix[i];
            if (lEdgePix[i] > reddest)
                reddest = lEdgePix[i];
        }
        for (int i = 0; i < lEdgePix.length; i++) {
            normLEPix[i] = ((double) (lEdgePix[i] - leastRed)) / (reddest - leastRed) * 100;
        }
    }

    // create 2d of red pixel values for right side
    public void rPixArr() {
        rEdgePix = new int[(int) (photo.getWidth() * 0.15)][photo.getHeight()];
        for (int i = 0; i < (int) (photo.getWidth() * 0.15); i++) {
            for (int j = 0; j < photo.getHeight(); j++) {
                int pixel = photo.getRGB(i, j);
                rEdgePix[i][j] = getRedP(pixel);
            }
        }
    }

    public void normRPArr() {
        normREPix = new double[(int) (photo.getWidth() * 0.15)][photo.getHeight()];
        for (int j = 0; j < (int) (photo.getWidth() * 0.15); j++) {
            int reddest = -1;
            int leastRed = 256;
            for (int i = 0; i < rEdgePix[0].length; i++) {
                if (rEdgePix[j][i] < leastRed)
                    leastRed = rEdgePix[j][i];
                if (rEdgePix[j][i] > reddest)
                    reddest = rEdgePix[j][i];
            }
            for (int i = 0; i < rEdgePix[j].length; i++) {
                normREPix[j][i] = ((double) (rEdgePix[j][i] - leastRed)) / (reddest - leastRed) * 100;
            }
        }
    }

    public void tPixArr() {
        for (int i = 5; i < photo.getWidth() - 5; i++) {
            int pixel = photo.getRGB(i, photo.getHeight() - 1);
            tEdgePix[i - 5] = getRedP(pixel);
        }
    }

    public void normTPArr() {
        int reddest = -1;
        int leastRed = 256;
        for (int i = 0; i < tEdgePix.length; i++) {
            if (tEdgePix[i] < leastRed)
                leastRed = tEdgePix[i];
            if (tEdgePix[i] > reddest)
                reddest = tEdgePix[i];
        }
        for (int i = 0; i < tEdgePix.length; i++) {
            normTEPix[i] = ((double) (tEdgePix[i] - leastRed)) / (reddest - leastRed) * 100;
        }
    }

    public void bPixArr() {
        for (int i = 0; i < (int) (photo.getWidth()); i++) {
            for (int j = 0; j < (int) (photo.getHeight() * 0.15); j++) {
                int pixel = photo.getRGB(i, j);
                bEdgePix[j][i] = getRedP(pixel);
            }
        }
    }

    public void normBPArr() {
        for (int j = 0; j < bEdgePix.length; j++) {
            int reddest = -1;
            int leastRed = 256;
            for (int i = 0; i < bEdgePix[0].length; i++) {
                if (bEdgePix[j][i] < leastRed)
                    leastRed = bEdgePix[j][i];
                if (bEdgePix[j][i] > reddest)
                    reddest = bEdgePix[j][i];
            }
            for (int i = 0; i < bEdgePix[j].length; i++) {
                normBEPix[j][i] = ((double) (bEdgePix[j][i] - leastRed)) / (reddest - leastRed) * 100;
            }
        }
    }

    public void fixSize(int diff) {
        fixedBPix = new int[(int) (photo.getHeight() * 0.15) - diff][photo.getWidth()];
        difference = diff;
        if (diff < 0) {
            for (int j = 0; j < diff; j++) {
                for (int i = 0; i < fixedBPix[0].length; i++) {
                    fixedBPix[j][i] = Integer.MAX_VALUE;
                }
            }
            for (int k = 0; k < bEdgePix.length; k++) {
                for (int l = 0; l < bEdgePix[0].length; l++) {
                    fixedBPix[k + diff][l] = bEdgePix[k][l];
                }
            }
        } else {
            // might need to be diff -1
            for (int m = Math.abs(diff); m < fixedBPix.length; m++) {
                for (int n = 0; n < fixedBPix[0].length; n++) {
                    fixedBPix[m][n] = bEdgePix[m][n];
                }
            }
        }
    }

    public void normFixed() {
        normFixedP = new double[fixedBPix.length][fixedBPix[0].length];
        for (int j = 0; j < (int) (fixedBPix.length); j++) {
            int reddest = -1;
            int leastRed = 256;
            for (int i = 0; i < fixedBPix[0].length; i++) {
                if (fixedBPix[j][i] < leastRed)
                    leastRed = fixedBPix[j][i];
                if (fixedBPix[j][i] > reddest)
                    reddest = fixedBPix[j][i];
            }
            for (int i = 0; i < fixedBPix[j].length; i++) {
                normFixedP[j][i] = ((double) (fixedBPix[j][i] - leastRed)) / (reddest - leastRed) * 100;
            }
        }
    }

    public int getPix(int i, int j) {
        return (photo.getRGB(i, j));
    }

    public int getLength() {
        return lEdgePix.length;
    }

    public int getHeightt() {
        return photo.getHeight();
    }

    public int getWidthh() {
        return photo.getWidth();
    }

    public int getDifference() {
        return difference;
    }
}
