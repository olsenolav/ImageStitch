import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class horStitch {
    private String fN1, fN2;
    private arrayCompare arr1;
    private photoReader tPhoto;
    private photoReader bPhoto;
    private int[][] pixels;

    public horStitch(String fName1, String fName2) {
        fN1 = fName1;
        fN2 = fName2;
        tPhoto = new photoReader(fN1);
        bPhoto = new photoReader(fN2);
        tPhoto.tPixArr();
        tPhoto.normTPArr();
        if (tPhoto.getWidthh() != bPhoto.getWidthh()) {
            bPhoto.fixSize(bPhoto.getWidthh() - tPhoto.getWidthh());
            bPhoto.normFixed();
            arr1 = new arrayCompare(tPhoto.normTEPix, bPhoto.normFixedP);
        } else {
            bPhoto.bPixArr();
            bPhoto.normBPArr();
            arr1 = new arrayCompare(tPhoto.normTEPix, bPhoto.normBEPix);
        }
    }

    public String stitchCalc() {
        return (arr1.findBestHorShift());
    }

    public int getNewWidth() {
        return ((tPhoto.getWidthh() - Math.abs(arr1.getHorShift())));
    }

    public int getNewHeight() {
        return (tPhoto.getHeightt() + bPhoto.getHeightt() - arr1.getHorOverlap());
    }

    public String newDimensions() {
        return ("height: " + getNewHeight() + " width: " + getNewWidth());
    }

    public int[][] recombine() {
        pixels = new int[getNewHeight()][getNewWidth()];
        if (arr1.getShift() >= 0) {
for (int i = 0; i < tPhoto.getHeightt(); i++) {
                for (int j = 0; j < getNewWidth(); j++) {
                    pixels[i][j] = tPhoto.getPix(j, i);
                }
            }
            for (int i = tPhoto.getHeightt(); i < getNewHeight(); i++) {
                for (int j = 0; j < getNewWidth(); j++) {
                    pixels[i][j] = bPhoto.getPix(j + arr1.getHorShift(),
                            i + arr1.getHorOverlap() - tPhoto.getHeightt());
                }
            }
        } else {
            for (int i = 0; i < tPhoto.getHeightt(); i++) {
                for (int j = 0; j < getNewWidth(); j++) {
                    pixels[i][j] = tPhoto.getPix(j - arr1.getHorShift(), i);
                }
            }
            for (int i = tPhoto.getHeightt(); i < getNewHeight(); i++) {
                for (int j = 0; j < getNewWidth(); j++) {
                    pixels[i][j] = bPhoto.getPix(j, i + arr1.getHorOverlap() - tPhoto.getHeightt());
                }
            }
        }
        return pixels;
    }

    public void writeImage(String Name, String folName) {
        String path = "/Users/OAO/Desktop/" + folName + "/" + Name + ".png";
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
