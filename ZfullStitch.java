public class ZfullStitch {
    private int xTiles;
    private int yTiles;
    private String folderName;

    public ZfullStitch(int xT, int yT, String folName) {
        xTiles = xT;
        yTiles = yT;
        folderName = folName;
    }

    public void stitchAcross(int rowName) {
        String f1 = "/Users/olavolsen/Desktop/" + folderName + "/" + Integer.toString(rowName) + "1.png";
        String f2 = "/Users/olavolsen/Desktop/" + folderName + "/" + Integer.toString(rowName) + "2.png";
        stitch s1 = new stitch(f1, f2);
        s1.stitchCalc();
        s1.recombine();
        s1.writeImage("row" + Integer.toString(rowName), folderName);
        for (int i = 2; i < xTiles; i++) {
            String fi = "/Users/olavolsen/Desktop/" + folderName + "/row" + Integer.toString(rowName) + ".png";
            String fj = "/Users/olavolsen/Desktop/" + folderName + "/" + rowName + (i + 1) + ".png";
            stitch sx = new stitch(fi, fj);
            sx.stitchCalc();
            sx.recombine();
            sx.writeImage("row" + Integer.toString(rowName), folderName);
        }
    }

    public void stitchDown() {
        String f1 = "/Users/olavolsen/Desktop/" + folderName + "/row1.png";
        String f2 = "/Users/olavolsen/Desktop/" + folderName + "/row2.png";
        ZhorStitch h1 = new ZhorStitch(f1, f2);
        h1.stitchCalc();
        h1.recombine();
        h1.writeImage("final", folderName);
        for (int i = 2; i < yTiles; i++) {
            String fi = "/Users/olavolsen/Desktop/" + folderName + "/final.png";
            String fj = "/Users/olavolsen/Desktop/" + folderName + "/row" + (i + 1) + ".png";
            horStitch hx = new horStitch(fi, fj);
            hx.stitchCalc();
            hx.recombine();
            hx.writeImage("final", folderName);
        }
    }
}
