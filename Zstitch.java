import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Zstitch {
    private String fN1, fN2;
    private arrayCompare arr1;
    private photoReader lPhoto;
    private photoReader rPhoto;
    private int[][] pixels;

    public Zstitch(String fName1, String fName2) {
        fN1 = fName1;
        fN2 = fName2;
        lPhoto = new photoReader(fN1);
        rPhoto = new photoReader(fN2);
        lPhoto.lPixArr();
        rPhoto.rPixArr();
        lPhoto.normLPArr();
        rPhoto.normRPArr();
        arr1 = new arrayCompare(lPhoto.normLEPix, rPhoto.normREPix);
    }

    public String stitchCalc() {
        return (arr1.findBestShift());
    }

    public int getNewWidth() {
        return (lPhoto.getWidthh() + rPhoto.getWidthh() - arr1.getOverlap());
    }

    public int getNewHeight() {
        return ((lPhoto.getHeightt() - Math.abs(arr1.getShift())));
    }

    public String newDimensions() {
        return ("height: " + getNewHeight() + " width: " + getNewWidth());
    }

    public int[][] recombine() {
        pixels = new int[getNewHeight()][getNewWidth()];
        if (arr1.getShift() >= 0) {
            for (int i = 0; i < getNewHeight(); i++) {
                for (int j = 0; j < lPhoto.getWidthh(); j++) {
                    pixels[i][j] = lPhoto.getPix(j, i);
                }
            }
            for (int i = 0; i < getNewHeight(); i++) {
                for (int j = lPhoto.getWidthh(); j < getNewWidth(); j++) {
                    pixels[i][j] = rPhoto.getPix(j - lPhoto.getWidthh() + arr1.getOverlap(), i + arr1.getShift());
                }
            }
        } else {
            for (int i = 0; i < getNewHeight(); i++) {
                for (int j = 0; j < lPhoto.getWidthh(); j++) {
                    pixels[i][j] = lPhoto.getPix(j, i - arr1.getShift());
                }
            }
            for (int i = 0; i < getNewHeight(); i++) {
                for (int j = lPhoto.getWidthh(); j < getNewWidth(); j++) {
                    pixels[i][j] = rPhoto.getPix(j - lPhoto.getWidthh() + arr1.getOverlap(), i);
                }
            }
        }
        return pixels;
    }

    public void writeImage(String Name, String folName) {
        String path = "/Users/olavolsen/Desktop/" + folName + "/" + Name + ".png";
        BufferedImage image = new BufferedImage(getNewWidth(), getNewHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < getNewHeight(); x++) {
            for (int y = 0; y < getNewWidth(); y++) {
                image.setRGB(y, x, pixels[x][y]);
            }
        }

        File ImageFile = new File(path);
        
        try {
            ImageIO.write(image, "png", ImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
