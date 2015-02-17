package implementation;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

import framework.Graphics;
import framework.Image;

/**
 * AndroidGraphics.java
 *
 * A class to construct an object of AndroidGraphics and methods to manipulate an
 * AndroidGraphics object. The AndroidGraphics class is used to create images,
 * lines, rectangles and Strings.
 *
 * @author Unknown
 */
public class AndroidGraphics implements Graphics {
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();
    
    /**
     * Constructor to instantiate a new AndroidGraphics object. This contains
     * a frame buffer, paint, a canvas instantiated using the frame buffer
     * and an asset. 
     * 
     * @param assets		A type of music, image or audio. Accesses raw asset
     * 						files.
     * @param frameBuffer	A Bitmap variable containing bitmap configurations.
     */
    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    /**
     * Method used to create a new Image. 
     * 
     * The config value is determined by the given ImageFormat in the variable
     * format. Config is set to either RGB_565 or ARGB_8888. The bitmap is
     * decoded from the asset. The value of format is then determined by the
     * configuration of the bitmap. A new AndroidImage in then created using
     * the bitmap and format variables.
     * 
     * @param fileName	File name of asset
     * @param format	Image format, whether that be a type of RGB or ARGB.
     */
    @Override
    public Image newImage(String fileName, ImageFormat format) {
        Config config = null;
        if (format == ImageFormat.RGB565)
            config = Config.RGB_565;
        else if (format == ImageFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;
        
        
        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = ImageFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = ImageFormat.ARGB4444;
        else
            format = ImageFormat.ARGB8888;

        return new AndroidImage(bitmap, format);
    }

    /**
     * A method to clear the screen to white by setting the RGB values.
     * 
     * @param color	Integer number 
     */
    @Override
    public void clearScreen(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }

    /**
     * A method to draw a line by passing the coordinates of the line and the
     * line colour into the method drawLine().
     * 
     * @param x			x coordinate of line
     * @param y			y coordinate of line
     * @param x2		Second x coordinate of line to be drawn to
     * @param y2		Second y coordinate of line to be drawn to
     * @param color		Colour of the line to be drawn
     */
    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }
    
    /**
     * A method to draw a rectangle by passing the coordinates of the 
     * rectangle, the width, the height, colour and style into the method
     * drawRect().
     * 
     * @param x			x coordinate of rectangle
     * @param y			y coordinate of rectangle
     * @param width		Width of rectangle to be drawn
     * @param height	Height of rectangle to be drawn
     * @param color		Colour of rectangle
     */
    @Override
    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
    }
    
    /**
     * A method to draw ARGB by passing integer values of a, r, g and b into
     * the method drawARGB.
     * 
     * @param a		Integer value of a
     * @param r		Integer value of r
     * @param g		Integer value of g
     * @param b 	Integer value of b
     */
    @Override
    public void drawARGB(int a, int r, int g, int b) {
        paint.setStyle(Style.FILL);
       canvas.drawARGB(a, r, g, b);
    }
    
    /**
     * A method to draw a String by passing the text, coordinates of the String
     * and paint.
     * 
     * @param text		text contained within the String
     * @param x			x coordinate of text 
     * @param y			y coordinate of text
     * @param paint		Contains style and colour information on how to draw
     * 					shapes, bitmap and text.
     */
    @Override
    public void drawString(String text, int x, int y, Paint paint){
        canvas.drawText(text, x, y, paint);
    }
    
    /**
     * A method to draw an image using a bitmap. The source and destination
     * dimensions are calculated and passed into the drawBitmap() method to
     * draw the bitmap.
     * 
     * @param Image			The image to be drawn
     * @param x				The x coordinate of the destination image
     * @param y				The y coordinate of the destination image
     * @param srcX			The x coordinate of the source image
     * @param srcY			The y coordinate of the source image
     * @param srcWidth		The width of the source image
     * @param srcHeight		The height of the source image
     */
    public void drawImage(Image Image, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;
        
        
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth;
        dstRect.bottom = y + srcHeight;

        canvas.drawBitmap(((AndroidImage) Image).bitmap, srcRect, dstRect,
                null);
    }
    
    /**
     * A method to draw image using a bitmap. The image is drawn using x and y
     * coordinates.
     * 
     * @param x		x coordinate of image
     * @param y		y coordinate of image
     */
    @Override
    public void drawImage(Image Image, int x, int y) {
        canvas.drawBitmap(((AndroidImage)Image).bitmap, x, y, null);
    }
    
    /**
     * A method to draw scaled image. The source and destination
     * dimensions are calculated and passed into the drawBitmap() method to
     * draw the bitmap.
     * 
     * @param Image			The image to be drawn
     * @param x				The x coordinate of the destination image
     * @param y				The y coordinate of the destination image
     * @param width			The width of the destination image
     * @param height		The height of the destination image
     * @param srcX			The x coordinate of the source image
     * @param srcY			The y coordinate of the source image
     * @param srcWidth		The width of the source image
     * @param srcHeight		The height of the source image
     */
    public void drawScaledImage(Image Image, int x, int y, int width, int height, int srcX, int srcY, int srcWidth, int srcHeight){
        
        
     srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;
        
        
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + width;
        dstRect.bottom = y + height;
        
   
        
        canvas.drawBitmap(((AndroidImage) Image).bitmap, srcRect, dstRect, null);
        
    }
   
    /**
     * A method to return the width of the frame buffer.
     */
    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }
    
    /**
     * A method to return the height of the frame buffer.
     */
    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }
}
