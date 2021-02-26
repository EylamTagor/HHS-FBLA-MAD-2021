package com.hhsfbla.hhs_fbla_mad_2021.util;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import androidx.exifinterface.media.ExifInterface;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a utility class that helps with rotating images with uneven resolutions
 */
public class ImageRotator {

    private Context context;

    /**
     * Creates a new image rotator object with the context of the activity or fragment calling it
     *
     * @param context the context of the activity or fragment that is calling this class
     */
    public ImageRotator(Context context) {
        this.context = context;
    }

    /**
     * Gets the image bitmap from the uri, checks how much it is rotated, and returns a new bitmap with the proper orientation
     *
     * @param uri the uri of the image
     * @return the rotated bitmap
     */
    public Bitmap getImageBitmap(Uri uri) {
        Bitmap rotatedBitmap = null;

        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            InputStream imageStream = context.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream, null, options);
            InputStream input = context.getContentResolver().openInputStream(uri);
            ExifInterface ei = new ExifInterface(input);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rotatedBitmap;
    }

    /**
     * Loads the given url into the given imageview in a way that the image won't be restricted by the dimensions of the imageview
     *
     * @param context   where the method is called from
     * @param imageView the image view to load the url into
     * @param url       the url of the image to load
     */
    public static void loadImageWrapContent(Context context, final ImageView imageView, String url) {
        Picasso.get().load(url).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = imageView.getWidth();
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                if (targetHeight <= 0 || targetWidth <= 0) return source;
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return "key";
            }
        }).into(imageView);
    }

    /**
     * Loads the given url into the given imageview in a way that the image won't be restricted by the dimensions of the imageview
     *
     * @param context   where the method is called from
     * @param imageView the image view to load the url into
     * @param url       the url of the image to load
     * @param b         the rotated bitmap to load into the imageview
     */
    public static void loadImageWrapContent(Context context, final ImageView imageView, String url, final Bitmap b) {
        Picasso.get().load(url).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = imageView.getWidth();
                double aspectRatio = (double) b.getHeight() / (double) b.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                if (targetHeight <= 0 || targetWidth <= 0) return b;
                Bitmap result = Bitmap.createScaledBitmap(b, targetWidth, targetHeight, false);
                if (result != b) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return "key";
            }
        }).into(imageView);
    }

    /**
     * Gets the file type of the given image uri: jpg, png, bmp, etc
     *
     * @param uri The uri of the image
     * @return the file type extension
     */
    private String getFileExtension(Uri uri) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    /**
     * Rotates the bitmap a certain amount of angles
     *
     * @param source the bitmap to rotate
     * @param angle  the number of degrees to rotate
     * @return the newly rotated bitmap
     */
    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    /**
     * Converts a bitmap to a byte array for database upload
     *
     * @param bitmap the bitmap to upload
     * @return the byte array of the bitmap
     */
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }
}
