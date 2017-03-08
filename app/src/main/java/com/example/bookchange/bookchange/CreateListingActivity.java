package com.example.bookchange.bookchange;


import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by christopher on 2/27/17.
 */

/*
Allows the user to post a listing to the database
 */

public class CreateListingActivity extends AppCompatActivity {
    private String userDisplayName;
    // firebaseDB variables
    private DatabaseReference mDatabase;
    private String mUserId;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private static final String[] PERMISSION = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"};
    public static final int CROP_CODE = Crop.REQUEST_CROP;
    public static final int CAMERA_CODE = 0, GALLERY_CODE = 1, REQUEST_IMAGE_CAPTURE = 111;
    public static final String CROP_KEY = "cropKey";
    Boolean fromCamera;
    private Uri takenUri, croppedUri;
    private ImageView bookPic;
//    ImageButton picBtn;

    String encodedImage = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_listing_activity);
        userDisplayName = getIntent().getStringExtra("account_name");
        ((TextView)findViewById(R.id.accountNameText)).setText(userDisplayName);

        // Initialize mAuth, mUser, and mDatabase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // make sure the user is logged in
        if (mUser == null) {
            // Not logged in, launch LoginActivity
            Intent intent = new Intent(this,LoginActivity.class);
            this.startActivity(intent);
            finish();
        } else {
            mUserId = mUser.getUid(); // get the Uid
        }

//        picBtn = (ImageButton) findViewById(R.id.picBtn);
//        initializeImgView();
        bookPic = (ImageView) findViewById(R.id.bookPic);
        bookPic.setImageResource(R.drawable.ic_add_a_photo_black_36dp);
        initializeImgView();
        getCameraPermission();
        if(savedInstanceState != null) croppedUri = savedInstanceState.getParcelable(CROP_KEY);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedState){
        super.onSaveInstanceState(savedState);
        savedState.putParcelable(CROP_KEY, croppedUri);
    }

    public void onSaveListingClicked(View view){
        String bookTitle = ((EditText)findViewById(R.id.bookTitleEditText)).getText().toString();
        String price = ((EditText)findViewById(R.id.priceEditText)).getText().toString();
        String courseName = ((TextView)findViewById(R.id.course_select_textView)).getText().toString();
        if (bookTitle.equals("")){
            Toast toast = Toast.makeText(this,"Add Book Title",Toast.LENGTH_SHORT);
            toast.show();
            ((EditText)findViewById(R.id.bookTitleEditText)).setError("Required");
        }
        else  if (price.equals("")){
            Toast toast = Toast.makeText(this,"Add Price",Toast.LENGTH_SHORT);
            toast.show();
            ((EditText)findViewById(R.id.priceEditText)).setError("Required");
        }
        else if (courseName.equals("None")){
           Toast toast = Toast.makeText(this,"Add Courses Name",Toast.LENGTH_SHORT);
            toast.show();
            ((TextView)findViewById(R.id.course_select_textView)).setError("Required");
        }
        else {
            BookListing bookListing = new BookListing(userDisplayName, Double.parseDouble(price), bookTitle, courseName,mUser.getEmail());
            bookListing.setPic(encodedImage);
            // insert a new listing in the firebaseDB under courses/coursename/listings
            DatabaseReference pushedListingsRef = mDatabase.child("courses").child(courseName).child("listings").push();
            bookListing.setId(pushedListingsRef.getKey());
            pushedListingsRef.setValue(bookListing);
            // insert a listing under users/mUserID/listings
            mDatabase.child("users").child(mUserId).child("listings").child(pushedListingsRef.getKey()).setValue(bookListing);
            finish();
        }
    }

    public void onCancelListingClicked(View view){
        finish();
    }

    public void onSelectCourseClicked(View view){
        DialogFragment dialogFragment = new DepartmentsFrag();
        dialogFragment.show(getSupportFragmentManager(),"departmentsDialogFrag");
    }

    public static class DepartmentsFrag extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Departments");

            builder.setItems(R.array.departments_array, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // The 'which' argument contains the index position
                    // launch an activity with the department's courses on click
                    Bundle bundle = new Bundle();
                    DialogFragment fragment = new CourseFrag();
                    bundle.putInt("department_int", which);
                    fragment.setArguments(bundle);
                    fragment.show(getFragmentManager(),"courseDialogFrag");
                }
            });

            return builder.create();
        }
    }

    public static class CourseFrag extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Departments");
            Resources res = getResources();
            int departmentInt = getArguments().getInt("department_int");
            final String[][] courseArrays = {res.getStringArray(R.array.AAASCourses),
                    res.getStringArray(R.array.ANTHCourses),
                    res.getStringArray(R.array.ARTHCourses),
                    res.getStringArray(R.array.AMELCourses),
                    res.getStringArray(R.array.AMESCourses),
                    res.getStringArray(R.array.BIOLCourses),
                    res.getStringArray(R.array.CHEMCourses),
                    res.getStringArray(R.array.CLSTCourses),
                    res.getStringArray(R.array.COGSCourses),
                    res.getStringArray(R.array.COCOCourses),
                    res.getStringArray(R.array.COLTCourses),
                    res.getStringArray(R.array.COSCCourses),
                    res.getStringArray(R.array.EARSCourses),
                    res.getStringArray(R.array.ECONCourses),
                    res.getStringArray(R.array.EDUCCourses),
                    res.getStringArray(R.array.ENGSCourses),
                    res.getStringArray(R.array.ENGLCourses),
                    res.getStringArray(R.array.ENVSCourses),
                    res.getStringArray(R.array.FILMCourses),
                    res.getStringArray(R.array.FRENCourses),
                    res.getStringArray(R.array.ITALCourses),
                    res.getStringArray(R.array.GEOGCourses),
                    res.getStringArray(R.array.GERMCourses),
                    res.getStringArray(R.array.GOVTCourses),
                    res.getStringArray(R.array.HISTCourses),
                    res.getStringArray(R.array.HUMCourses),
                    res.getStringArray(R.array.JWSTCourses),
                    res.getStringArray(R.array.LACSCourses),
                    res.getStringArray(R.array.LATSCourses),
                    res.getStringArray(R.array.LINGCourses),
                    res.getStringArray(R.array.MATHCourses),
                    res.getStringArray(R.array.MUSCourses),
                    res.getStringArray(R.array.NASCourses),
                    res.getStringArray(R.array.PHILCourses),
                    res.getStringArray(R.array.ASTRCourses),
                    res.getStringArray(R.array.PHYSCourses),
                    res.getStringArray(R.array.PSYCCourses),
                    res.getStringArray(R.array.RELCourses),
                    res.getStringArray(R.array.RUSSCourses),
                    res.getStringArray(R.array.SSOCCourses),
                    res.getStringArray(R.array.SOCYCourses),
                    res.getStringArray(R.array.SPANCourses),
                    res.getStringArray(R.array.PORTCourses),
                    res.getStringArray(R.array.SARTCourses),
                    res.getStringArray(R.array.THEACourses),
                    res.getStringArray(R.array.WGSSCourses)};

            final String[] courses = courseArrays[departmentInt];

            builder.setItems(courses, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // The 'which' argument contains the index position
                    // launch an activity with the department's courses on click
                    CreateListingActivity createListingActivity = (CreateListingActivity) getActivity();
                    createListingActivity.setCourseText(courses[which]);

                }
            });

            return builder.create();
        }
    }

    public void setCourseText(String text){
        TextView textView = (TextView) findViewById(R.id.course_select_textView);
        textView.setText(text);

    }

    private void initializeImgView() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), "android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED) {
            getCameraPermission();
        } else {
            bookPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateListingActivity.this);
                    builder.setTitle("Add Images of Your Book");
                    final CharSequence[] options = {"Take New Picture", "Select from Gallery"};
//                    final int[] options = {R.drawable.ic_photo_camera_black_24dp, R.drawable.ic_photo_library_black_24dp};
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (options[item].equals("Take New Picture")) {
//                            if(options[item].equals(R.drawable.ic_photo_camera_black_24dp)){
                                takePic();
                                Integer i = 0;
                                int j = 1;
                            } else {
                                choosePic();
                            }
                        }
                    });
                    builder.show();
                }
            });
        }
    }

    private void takePic(){
        fromCamera = true;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues values = new ContentValues(1);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");

        takenUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, takenUri);
        startActivityForResult(intent, CAMERA_CODE);
    }

    private void choosePic(){
        fromCamera = false;
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK){
//            Toast.makeText(this, "Error while cropping!", Toast.LENGTH_SHORT).show();
            return;
        }

        switch(requestCode) {
            case CAMERA_CODE:
                startCrop(takenUri);
                break;

            case GALLERY_CODE:
                Uri galleryImg = data.getData();
                startCrop(galleryImg);
                break;

            case CROP_CODE:
                if(data != null)
                    endCrop(resultCode, Crop.getOutput(data));
                if(fromCamera){
                    File f = new File(takenUri.getPath());
                    if(f.exists()) f.delete();
                    break;
                }
        }
    }

    public void encodeBitmap(Uri uri) {
        try{
            InputStream imageStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void startCrop(Uri start){
        Uri end = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(start, end).asSquare().start(this);
    }

    private void endCrop(int result, Uri uri){
        croppedUri = uri;
        bookPic.setImageURI(croppedUri);
//        bookPic.setVisibility(View.VISIBLE);
        encodeBitmap(croppedUri);

    }

    private void getCameraPermission(){
        if (Build.VERSION.SDK_INT < 23) return;

        else if( (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission("android.permission.CAMERA") == PackageManager.PERMISSION_GRANTED) ) {
            return;
        }

        if ( (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) ||
                (checkSelfPermission("android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED) ){
            requestPermissions(PERMISSION, 0);
        }

        else {
            requestPermissions(PERMISSION, 0);
        }
    }
}
