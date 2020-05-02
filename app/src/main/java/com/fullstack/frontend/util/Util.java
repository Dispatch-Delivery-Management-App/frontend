package com.fullstack.frontend.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    private Util() {

    }

    public static boolean isValidEmail(@Nullable String email) {
        Matcher matcher = PATTERN.matcher(email);
        return matcher.matches();
    }

    public static boolean isSameString(@Nullable String s1, @Nullable String s2) {
        return Objects.equals(s1, s2);
    }

    @Nullable
    public static LatLng convertLocation(Location location) {
        if (location == null) {
            return null;
        }
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    public static double distanceBetweenTwoLocations(LatLng currentLat, LatLng destinationLatLng) {
        Location currentLocation = new Location(LocationManager.GPS_PROVIDER);
        currentLocation.setLatitude(currentLat.latitude);
        currentLocation.setLongitude(currentLat.longitude);
        Location destLocation = new Location(LocationManager.GPS_PROVIDER);
        destLocation.setLatitude(destinationLatLng.latitude);
        destLocation.setLongitude(destinationLatLng.longitude);
        double distance = currentLocation.distanceTo(destLocation);

        double inches = (39.370078 * distance);
        return Double.parseDouble(String.format(Locale.getDefault(), "%.3f", inches / 63360));
    }

    public static BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public static String timeTransformer(long millis) {
        long currenttime = System.currentTimeMillis();
        long diff = currenttime - millis;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        long hours = TimeUnit.MILLISECONDS.toHours(diff);
        long days = TimeUnit.MILLISECONDS.toDays(diff);

        if (seconds < 60) {
            return seconds + " Seconds Ago";
        } else if (minutes < 60) {
            return minutes + " Minutes Ago";
        } else if (hours < 24) {
            return hours + " Hours ago";
        } else {
            return days + " Days ago";
        }
    }

    public static Toast showToast(Context context, String msg) {
        return Toast.makeText(context, msg, Toast.LENGTH_SHORT);
    }

}
