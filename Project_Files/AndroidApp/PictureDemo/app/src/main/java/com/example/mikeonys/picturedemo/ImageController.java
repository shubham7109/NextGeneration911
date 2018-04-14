package com.example.mikeonys.picturedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageController {

    /**
     * Provide a 64bit encoding of a bitmap image
     *
     * @param image Image to be encoded
     * @return 64-bit string representation of image
     */
    public static String encodeImage(Bitmap image, int quality) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /**
     * Reverse the encoding process to extract an image from a string
     *
     * @param input String to be unencoded
     * @return Bitmap representation of input
     * @throws IllegalArgumentException
     */
    public static Bitmap decodeImage(String input) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                input.substring(input.indexOf(",") + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
