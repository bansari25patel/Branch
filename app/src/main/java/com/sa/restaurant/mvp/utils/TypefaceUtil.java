package com.sa.restaurant.mvp.utils;

import android.content.Context;
import android.graphics.Typeface;
import java.lang.reflect.Field;



public class TypefaceUtil
{
//    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
//        try {
//            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);
//
//            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
//            defaultFontTypefaceField.setAccessible(true);
//            defaultFontTypefaceField.set(null, customFontTypeface);
//        } catch (Exception e) {
//
//        }
//    }
    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
