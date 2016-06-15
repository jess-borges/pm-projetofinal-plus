package br.ufmg.dcc052.oncebook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by xavier on 6/6/16.
 */
public class BitmapUtils {

  public static byte[] getBytes(Bitmap bitmap) {
    if (bitmap != null) {
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
      return stream.toByteArray();
    } else {
      return null;
    }
  }

  // convert from byte array to bitmap
  public static Bitmap getBitmap(byte[] image) {
    if (image != null) {
      return BitmapFactory.decodeByteArray(image, 0, image.length);
    } else {
      return null;
    }
  }
}
