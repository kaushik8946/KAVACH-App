package com.example.location;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.view.View;

import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    TextView textView;
   // CheckBox cb;

    String GeoLocs = "";


    //ArrayList<contact> contactsList = new ArrayList<>();
    ArrayList<String> numberList = new ArrayList<>();
    final int for_speak = 100;
    final int for_nums = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv1);


        //cb = findViewById(R.id.checkBox);
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
          //      && ActivityCompat.checkSelfPermission(getApplicationContext(),
               // android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.SEND_SMS,
                    Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS}, 101);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationEnabled();
        getLocation();
    }


    public void numberActivity(View view){
        Intent intent0 = new Intent(this,NumbersActivity.class);
        intent0.putExtra("CURRENT_NUMBER_LIST",numberList);
        startActivityForResult(intent0,for_nums);
//        Bundle args = new Bundle();
//        args.putSerializable("CURRENT_NUMBER_LIST",numberList);
//        intent.putExtra("BUNDLE",args);
//        startActivityForResult(intent,1234);
    }
    public void speak(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking");
        startActivityForResult(intent,for_speak);
    }
    private void forSms(){
        String message = "HELP!!!" + GeoLocs;
        for (String x:
                numberList) {
            SmsManager mySmsManager = SmsManager.getDefault();
            mySmsManager.sendTextMessage(x,null, message, null, null);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == for_speak && resultCode == RESULT_OK){
            if(data!=null) {
                textView.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
                if (textView.getText().toString().toLowerCase().contains("kavach")) {
                    forSms();
//                 System.out.println(numberList);
//                System.out.println(GeoLocs);
                }
            }
        }
        if(requestCode == for_nums && resultCode == RESULT_OK){
            if (data!=null) {
                //System.out.println("REQUEST PROCESSED");
                //Intent intent2 = getIntent();
                //Bundle args3 = intent1.getBundleExtra("BUNDLE1");
                numberList = data.getStringArrayListExtra("MODIFIED_NUMBER_LIST");
                System.out.println(numberList);
            }
        }

    }
    private void locationEnabled() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Enable GPS Service")
                    .setMessage("We need your GPS location to show Near Places around you.")
                    .setCancelable(false)
                    .setPositiveButton("Enable", (paramDialogInterface, paramInt) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
           GeoLocs=  "\nLatitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude() +//"\nCity : " + addresses.get(0).getLocality() + //"\nState : " + addresses.get(0).getAdminArea() +//
                   /*"\nCountry : " + addresses.get(0).getCountryName()  "\nPin : " + addresses.get(0).getPostalCode() +*/
                    "\nLocality : " + addresses.get(0).getAddressLine(0);

        } catch (Exception e) {
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}