package sb5.cs309.nextgen911;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Encodes, Decodes, and sends images over internet
 */

public class ImageHandler {
    public static String encodeBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String s) {
        byte[] decoded = Base64.decode(s, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
    }
}
