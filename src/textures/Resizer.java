package textures;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/* source from http://stackoverflow.com/users/72673/maurice-perry */
/* link: http://stackoverflow.com/questions/4756268/how-to-resize-the-buffered-image-n-graphics-2d-in-java */

/**
 */
public enum Resizer {
    NEAREST_NEIGHBOR {
        public BufferedImage resize(BufferedImage source,
                int width, int height) {
            return commonResize(source, width, height,
                    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        }
    },
    BILINEAR {
        public BufferedImage resize(BufferedImage source,
                int width, int height) {
            return commonResize(source, width, height,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        }
    },
    BICUBIC {
        public BufferedImage resize(BufferedImage source,
                int width, int height) {
            return commonResize(source, width, height,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        }
    },
    PROGRESSIVE_BILINEAR {
        public BufferedImage resize(BufferedImage source,
                int width, int height) {
            return progressiveResize(source, width, height,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        }
    },
    PROGRESSIVE_BICUBIC {
        public BufferedImage resize(BufferedImage source,
                int width, int height) {
            return progressiveResize(source, width, height,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        }
    },
    AVERAGE {
        public BufferedImage resize(BufferedImage source,
                int width, int height) {
            Image img2 = source.getScaledInstance(width, height,
                    Image.SCALE_AREA_AVERAGING);
            BufferedImage img = new BufferedImage(width, height,
                    source.getType());
            Graphics2D g = img.createGraphics();
            try {
                g.drawImage(img2, 0, 0, width, height, null);
            } finally {
                g.dispose();
            }
            return img;
        }
    };
    /**
     * Method resize.
     * @param source BufferedImage
     * @param width int
     * @param height int
     * @return BufferedImage
     */
    public abstract BufferedImage resize(BufferedImage source,
            int width, int height);
    /**
     * Method progressiveResize.
     * @param source BufferedImage
     * @param width int
     * @param height int
     * @param hint Object
     * @return BufferedImage
     */
    private static BufferedImage progressiveResize(BufferedImage source,
            int width, int height, Object hint) {
        int w = Math.max(source.getWidth()/2, width);
        int h = Math.max(source.getHeight()/2, height);
        BufferedImage img = commonResize(source, w, h, hint);
        while (w != width || h != height) {
            BufferedImage prev = img;
            w = Math.max(w/2, width);
            h = Math.max(h/2, height);
            img = commonResize(prev, w, h, hint);
            prev.flush();
        }
        return img;
    }
    /**
     * Method commonResize.
     * @param source BufferedImage
     * @param width int
     * @param height int
     * @param hint Object
     * @return BufferedImage
     */
    private static BufferedImage commonResize(BufferedImage source,
            int width, int height, Object hint) {
        BufferedImage img = new BufferedImage(width, height,
                source.getType());
        Graphics2D g = img.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g.drawImage(source, 0, 0, width, height, null);
        } finally {
            g.dispose();
        }
        return img;
    }
};