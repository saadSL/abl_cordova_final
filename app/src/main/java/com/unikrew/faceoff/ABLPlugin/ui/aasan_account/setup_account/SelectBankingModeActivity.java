package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivitySelectBankingModeBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode.BranchListItemModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode.BranchesModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode.GetBranchPostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode.SuggestBranchListItemModel;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;
import java.util.List;

public class SelectBankingModeActivity extends BaseActivity implements OnMapReadyCallback, OnItemSelectedListener, GoogleMap.OnMarkerClickListener {
    private ActivitySelectBankingModeBinding bankingModeBinding;
    private FusedLocationProviderClient mFusedLocationClient;
    private SelectBankingModeViewModel selectBankingModeViewModel;
    private GoogleMap mMap;
    private List<SuggestBranchListItemModel> suggestBranchListItemModelArrayList = new ArrayList();
    private int BANKING_MODE = 0;
    private String selectedBranchTitle = "";
    private LatLng USER_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setViewModel();
        getLatLng();
        setObservers();
        clicks();
    }

    private void clicks() {

        bankingModeBinding.llConventional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectConventionalBanking();
            }
        });
        bankingModeBinding.llIslamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectIslamicBanking();
            }
        });


        bankingModeBinding.ivBackBankingMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bankingModeBinding.btCancelBankingMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bankingModeBinding.btnNextBankingMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidations();
            }
        });
    }

    private void selectIslamicBanking() {
        bankingModeBinding.llConventional.setBackground(getDrawable(R.drawable.rounded_corner_white));
        bankingModeBinding.llIslamic.setBackground(getDrawable(R.drawable.rounded_corner_selected));
        BANKING_MODE = Config.ISLAMIC_BANKING;
    }

    private void selectConventionalBanking() {
        bankingModeBinding.llConventional.setBackground(getDrawable(R.drawable.rounded_corner_selected));
        bankingModeBinding.llIslamic.setBackground(getDrawable(R.drawable.rounded_corner_white));
        BANKING_MODE = Config.CONVENTIONAL_BANKING;
    }

    private void checkValidations() {
        if (BANKING_MODE == 0) {
            showAlert(Config.errorType, "Please select a banking method to continue.");
        } else if (selectedBranchTitle.equals("")) {
            showAlert(Config.errorType, "Please select a branch from suggested branches on the map or select any other branch from the drop down.");
        } else {
            Log.d("selectedBranchTitle", "your selected branch is: " + selectedBranchTitle);
            showAlert(Config.successType, "You can proceed.");
        }
    }


    private void setMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setObservers() {
        selectBankingModeViewModel.branchesLiveData.observe(this, new Observer<BranchesModel>() {
            @Override
            public void onChanged(BranchesModel branchesModel) {
                Log.d("branchesResponse", "onChanged: " + branchesModel);
                dismissLoading();
                setMap();
                setMarkersOnMap(branchesModel);
                setSpinner(branchesModel);
            }
        });

        selectBankingModeViewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                dismissLoading();
            }
        });

    }

    private void setSpinner(BranchesModel branchesModel) {
        List<BranchListItemModel> allBranchesList = branchesModel.getData().getBranchList();
        List<String> _allBranches = new ArrayList<>();


        if (allBranchesList.size() > 0) {
            // Spinner click listener
            bankingModeBinding.spinnerAllBranches.setOnItemSelectedListener(this);
            _allBranches.add(getString(R.string.choose_prefered_branch));
            // Spinner Drop down elements
            for (int i = 0; i < allBranchesList.size(); i++) {
                _allBranches.add(allBranchesList.get(i).getBranchName());
            }

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, _allBranches);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            bankingModeBinding.spinnerAllBranches.setAdapter(dataAdapter);
        }

    }

    private void setMarkersOnMap(BranchesModel branchesModel) {
        suggestBranchListItemModelArrayList = branchesModel.getData().getSuggestBranchList();
    }

    private void setViewModel() {
        selectBankingModeViewModel = new ViewModelProvider(this).get(SelectBankingModeViewModel.class);
    }

    private void getLatLng() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // method to get the location
        getLastLocation();
    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            getBranchesFromNetwork(30.18817, 71.4358);
                            Log.d("latAndLng", "onComplete latitude is: " + location.getLatitude() + " longitude is " + location.getLongitude());
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(100);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            getBranchesFromNetwork(30.18817, 71.4358);
            Log.d("latAndLng", "latitude is: " + mLastLocation.getLatitude() + " longitude is " + mLastLocation.getLongitude());
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, Config.LOCATION_PERMISSION_CODE);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Config.LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    private void getBranchesFromNetwork(double latitude, double longitude) {
        showLoading();
        USER_LOCATION = new LatLng(latitude, longitude);
        GetBranchPostModel getBranchPostModel = new GetBranchPostModel();
        getBranchPostModel.getData().setBranchName("");
        getBranchPostModel.getData().setCategoryType("C");
        getBranchPostModel.getData().setLatitude(String.valueOf(latitude));
        getBranchPostModel.getData().setLongitude(String.valueOf(longitude));
        getBranchPostModel.getData().setDistance("2");
        selectBankingModeViewModel.getBranches(getBranchPostModel);
    }

    private void setBinding() {
        bankingModeBinding = ActivitySelectBankingModeBinding.inflate(getLayoutInflater());
        setContentView(bankingModeBinding.getRoot());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        // inside on map ready method
        // we will be displaying all our markers.
        // for adding markers we are running for loop and
        // inside that we are drawing marker on our map.

        if (suggestBranchListItemModelArrayList.size() > 0) {
            for (int i = 0; i < suggestBranchListItemModelArrayList.size(); i++) {
                LatLng latLng = new LatLng(suggestBranchListItemModelArrayList.get(i).getLatitude(), suggestBranchListItemModelArrayList.get(i).getLongitude());
                // below line is use to add marker to each location of our array list.
                mMap.addMarker(new MarkerOptions().position(latLng).title(suggestBranchListItemModelArrayList.get(i).getBranchName()));
            }
            moveCameraToUserLocation();
        }

    }

    private void moveCameraToUserLocation() {
        // below lin is use to zoom our camera on map.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));

        // below line is use to move our camera to the specific location.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(USER_LOCATION, 12));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, bankingModeBinding.spinnerAllBranches.getSelectedItemPosition() + " selected", Toast.LENGTH_SHORT).show();
        if (bankingModeBinding.spinnerAllBranches.getSelectedItemPosition() == 0) {
            selectedBranchTitle = "";
        } else {
            selectedBranchTitle = bankingModeBinding.spinnerAllBranches.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
        selectedBranchTitle = marker.getTitle();
        return false;
    }
}