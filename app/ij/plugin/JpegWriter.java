package ij.plugin;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

/**
 * The File->Save As->Jpeg command (FileSaver.saveAsJpeg()) uses this plugin to
 * save images in JPEG format when running Java 2. The path where the image is
 * to be saved is passed to the run method.
 */
public class JpegWriter implements PlugIn {

    public static final int DEFAULT_QUALITY = 75;
    private static int quality;

    static {
        setQuality(ij.Prefs.getInt(ij.Prefs.JPEG, DEFAULT_QUALITY));
    }

    public void run(String arg) {
        ImagePlus imp = WindowManager.getCurrentImage();
        if (imp == null)
            return;
        imp.startTiming();
        saveAsJpeg(imp, arg);
        IJ.showTime(imp, imp.getStartTime(), "JpegWriter: ");
    }

    void saveAsJpeg(ImagePlus imp, String path) {
        // IJ.log("saveAsJpeg: "+path);
        int width = imp.getWidth();
        int height = imp.getHeight();
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        try {
            FileOutputStream f = new FileOutputStream(path);
            Graphics g = bi.createGraphics();
            g.drawImage(imp.getImage(), 0, 0, null);
            g.dispose();

            ImageIO.write(bi, "JPEG", f);
            f.close();
        } catch (Exception e) {
            IJ.error("Jpeg Writer", "" + e);
        }
    }

    /**
     * Specifies the image quality (0-100). 0 is poorest image quality, highest
     * compression, and 100 is best image quality, lowest compression.
     */
    public static void setQuality(int jpegQuality) {
        quality = jpegQuality;
        if (quality < 0)
            quality = 0;
        if (quality > 100)
            quality = 100;
    }

    public static int getQuality() {
        return quality;
    }

}
