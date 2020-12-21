package com.app.fr.fruiteefy.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.ConnectivityManager;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.EditText;

import com.app.fr.fruiteefy.R;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Validation {

    private FusedLocationProviderClient fusedLocationClient;
    private Location loc;


    public boolean edttxtvalidation(EditText editText, Context context) {
        if (editText.getText().toString().equals("")) {
            editText.setError(context.getResources().getString(R.string.fill_field));
            return false;
        } else {
            return true;
        }
    }

    public boolean emailvalidation(EditText editText, Context context) {

        String email_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


        if (!editText.getText().toString().matches(email_pattern)) {
            editText.setError(context.getResources().getString(R.string.email_error));
            return false;
        } else {
            return true;
        }
    }


    public boolean mobnovalidation(EditText editText, Context context) {

        // String mob_pattern="^(\\+?\\d{1,4}[\\s-])?(?!0+\\s+,?$)\\d{10}\\s*,?$";

//        if(!editText.getText().toString().matches(mob_pattern)){
//            editText.setError(context.getResources().getString(R.string.mob_no_error));
//            return false;
//        }
        //    else {
        return true;
        //  }
    }

    public boolean passvalidation(EditText editText, Context context) {

        if (editText.getText().length() < 6) {
            editText.setError(context.getResources().getString(R.string.pass_error));
            return false;
        } else {
            return true;
        }
    }

    public boolean conpassvalidation(EditText editText, EditText editTextcon, Context context) {

        if (!editText.getText().toString().equals(editTextcon.getText().toString())) {
            editTextcon.setError(context.getResources().getString(R.string.con_pass_error));
            return false;
        } else {
            return true;
        }
    }




    public void requestPermission(Activity activity,int reqcode) {

        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        },reqcode);

    }


    public boolean checkPermissions(Activity activity) {
        int result = (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION));
        int result2 = (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION));


        if (result == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED

        ) {
            return true;
        } else {
            return false;
        }
    }


    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public Location getFusedLocation(Activity activity) {


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);


            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Log.d("hjhjhj", String.valueOf(location.getLatitude()));
                                loc = location;

                            }
                        }
                    });
            return loc;


    }


    public void GPSenableDialog(final Activity activity) {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(activity);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(activity, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
            }
        });

        task.addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(activity,
                                5);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });


//
//        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context).addApi(LocationServices.API).build();
//
//
//
//
//        builder.setAlwaysShow(true);
//
//        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
//        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//            @Override
//            public void onResult(LocationSettingsResult result) {
//                final Status status = result.getStatus();
//                switch (status.getStatusCode()) {
//                    case LocationSettingsStatusCodes.SUCCESS:
//                        break;
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        try {
//                            status.startResolutionForResult((Activity) context, 1);
//
//                        } catch (IntentSender.SendIntentException e) {
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        break;
//                }
//            }
//        });

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean neverAskAgainSelected(final Activity activity, final String permission, String name) {
        final boolean prevShouldShowStatus = getRatinaleDisplayStatus(activity,permission,name);
        final boolean currShouldShowStatus = activity.shouldShowRequestPermissionRationale(permission);
        return prevShouldShowStatus != currShouldShowStatus;
    }


    public static boolean getRatinaleDisplayStatus(final Context context, final String permission,String name) {
        SharedPreferences genPrefs = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return genPrefs.getBoolean(permission, false);
    }


    public static void setShouldShowStatus(final Context context, final String permission,String name) {
        SharedPreferences genPrefs = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = genPrefs.edit();
        editor.putBoolean(permission, true);
        editor.commit();
    }

    public Bitmap setPic(String currentPhotoPath) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW/12000, photoH/12000);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        return bitmap;
    }

    public void displayNeverAskAgainDialog(String permission, final Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(permission.equals("camera")) {
            builder.setMessage(context.getResources().getString(R.string.camera_permission));
        }
        if(permission.equals("storage")) {
            builder.setMessage(context.getResources().getString(R.string.storage_permission));
        }

        builder.setPositiveButton(context.getResources().getString(R.string.permitmanually), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });

        builder.show();
    }

}
