package com.yipl.phostory.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yipl.phostory.R;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Random;

/**
 * Created by rakeeb on 12/16/14.
 */
public class Utils {

    public static class Image {

        public static Bitmap getScaledDownBitmap(Resources resources, int inSampleSize, int imgId) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = inSampleSize;
            return BitmapFactory.decodeResource(resources, imgId, options);
        }
    }

    public static class Views {

        public static void setEmptyMessage(View view, int imgRes, String emptyText) {
            ((ImageView) view.findViewById(R.id.emptyImage)).setImageResource(imgRes);
            ((TextView) view.findViewById(R.id.emptyText)).setText(emptyText);
        }

        public static int getDp(int dimension, Resources resources) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimension, resources.getDisplayMetrics());
        }
    }

    public static Spannable highlight(String search, String originalText) {
        // ignore case and accents
        // the same thing should have been done for the search text
        String normalizedText = Normalizer
                .normalize(originalText, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase(Locale.getDefault());

        int start = normalizedText.indexOf(search);

        if (start < 0) {
            // not found, nothing to to
            return new SpannableString(originalText);
        } else {
            // highlight each appearance in the original text
            // while searching in normalized text
            Spannable highlighted = new SpannableString(originalText);
            while (start >= 0) {
                int spanStart = Math.min(start, originalText.length());
                int spanEnd = Math.min(start + search.length(),
                        originalText.length());
                highlighted.setSpan(
                        new ForegroundColorSpan(Color.parseColor("#3190ff")),
                        spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                highlighted.setSpan(new StyleSpan(
                                android.graphics.Typeface.BOLD), spanStart, spanEnd,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                start = normalizedText.indexOf(search, spanEnd);
            }

            return highlighted;
        }
    }

    public static int randInt(int min, int max) {
        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    // TODO null pointer checks
    public static void getColors(Palette p) {
        Palette.Swatch vibrantSwatch = p.getVibrantSwatch();
        Palette.Swatch darkVibrantSwatch = p.getDarkVibrantSwatch();
        Palette.Swatch lightVibrantSwatch = p.getLightVibrantSwatch();
        Palette.Swatch mutedSwatch = p.getMutedSwatch();
        Palette.Swatch darkMutedSwatch = p.getDarkMutedSwatch();
        Palette.Swatch lightMutedSwatch = p.getLightMutedSwatch();

//        int vRGB = vibrantSwatch.getRgb();
//        int dvRGB = darkVibrantSwatch.getRgb();
//        int lvRGB = lightVibrantSwatch.getRgb();

//        int mRGB = mutedSwatch.getRgb();
//        int dmRGB = 0;
//        if (darkMutedSwatch != null) {
//            dmRGB = darkMutedSwatch.getRgb();
//        }
//        int lmRGB = lightMutedSwatch.getRgb();

//        Log.e("VIBRANT", Integer.toHexString(vRGB) + "");
//        Log.e("DARK VIBRANT", Integer.toHexString(dvRGB) + "");
//        Log.e("LIGHT VIBRANT", Integer.toHexString(lvRGB) + "");
//        Log.e("MUTED", Integer.toHexString(mRGB) + "");
//        Log.e("DARK MUTED", Integer.toHexString(dmRGB) + "");
//        Log.e("LIGHT MUTED", Integer.toHexString(lmRGB) + "");

//        int vtText = vibrantSwatch.getTitleTextColor();
//        int vbText = vibrantSwatch.getBodyTextColor();
//
//        int dvtText = darkVibrantSwatch.getTitleTextColor();
//        int dvbText = darkVibrantSwatch.getBodyTextColor();
//
//        int lvtText = lightVibrantSwatch.getTitleTextColor();
//        int lvbText = lightVibrantSwatch.getBodyTextColor();
//
//        int mtText = mutedSwatch.getTitleTextColor();
//        int mbText = mutedSwatch.getBodyTextColor();

//        int dmtText = 0;
//        int dmbText = 0;
//        if (darkMutedSwatch != null) {
//            dmtText = darkMutedSwatch.getTitleTextColor();
//            dmbText = darkMutedSwatch.getBodyTextColor();
//        }

//        int lmtText = lightMutedSwatch.getTitleTextColor();
//        int lmbText = lightMutedSwatch.getBodyTextColor();

//        Log.e("VIBRANT TEXT", "T: " + Integer.toHexString(vtText) + "; B: " + Integer.toHexString(vbText));
//        Log.e("DARK VIBRANT TEXT", "T: " + Integer.toHexString(dvtText) + "; B: " + Integer.toHexString(dvbText));
//        Log.e("LIGHT VIBRANT TEXT", "T: " + Integer.toHexString(lvtText) + "; B: " + Integer.toHexString(lvbText));
//
//        Log.e("MUTED TEXT", "T: " + Integer.toHexString(mtText) + "; B: " + Integer.toHexString(mbText));
//        Log.e("DARK MUTED TEXT", "T: " + Integer.toHexString(dmtText) + "; B: " + Integer.toHexString(dmbText));
//        Log.e("LIGHT MUTED TEXT", "T: " + Integer.toHexString(lmtText) + "; B: " + Integer.toHexString(lmbText));
    }

}
