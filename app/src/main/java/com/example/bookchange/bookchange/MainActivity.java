package com.example.bookchange.bookchange;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BookchangeAccount account; // we don't need this after integration with firebase
    private ArrayList<Fragment> frags;
    private final int HOME_INDEX = 0;
    private final int SUB_INDEX = 1;
    private final int LISTINGS_INDEX = 2;
    private final int ACC_INDEX = 3;
    private final int DEP_INDEX = 4;

    // firebaseDB variables
    private String mUserId;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        frags = new ArrayList<>();
        frags.add(new HomeFragment());
//       frags.add(new SubscriptionsFragment());
        frags.add(new ManageSubscriptionsFragment());
        frags.add(new YourListingsFragment());
        frags.add(new AccountDisplayFragment());
        frags.add(new DeptSelectFragment());

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        account = new BookchangeAccount("chrisloughlin","crlough18@gmail.com");
        TextView navText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_textView);
        navText.setText("Hello, "+mUser.getDisplayName());
        if(savedInstanceState==null){
            HomeFragment fragment = (HomeFragment)frags.get(HOME_INDEX);
            android.app.FragmentManager fragmentManager = getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frag , fragment);
            fragmentTransaction.commit();
        }
    }

    public BookchangeAccount getAccount(){return  account;}

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        getFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (id == R.id.nav_home) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            HomeFragment fragment = (HomeFragment)frags.get(HOME_INDEX);
            fragmentTransaction.replace(R.id.frag, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_department) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DeptSelectFragment fragment = (DeptSelectFragment) frags.get(DEP_INDEX);
            fragmentTransaction.replace(R.id.frag, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_subscriptions) {
            // launch the subscription fragment (Preferences fragment?)
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ManageSubscriptionsFragment fragment = (ManageSubscriptionsFragment) frags.get(SUB_INDEX);
            fragmentTransaction.replace(R.id.frag, fragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_listings){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            YourListingsFragment fragment = (YourListingsFragment)frags.get(LISTINGS_INDEX);
            fragmentTransaction.replace(R.id.frag, fragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this,LoginActivity.class);
            this.startActivity(intent);
            finish();
        }
        else if (id == R.id.nav_account){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            AccountDisplayFragment fragment = (AccountDisplayFragment) frags.get(ACC_INDEX);
            fragmentTransaction.replace(R.id.frag, fragment);
            fragmentTransaction.commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setSelected(false);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onAddSubscriptionClicked(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DeptSelectFragment fragment = (DeptSelectFragment) frags.get(DEP_INDEX);
        fragmentTransaction.add(fragment,"detail").addToBackStack(null).replace(R.id.frag, fragment);
        fragmentTransaction.commit();
    }

    public void onCreateListingClicked(View view){
        Intent intent = new Intent(this,CreateListingActivity.class);
        // no longer used
//        intent.putExtra("account_name",account.getAccountName());
        // get display name from the mUser
        intent.putExtra("account_name", mUser.getDisplayName());
        startActivity(intent);
    }



//    public void onUnsubscribeClicked(View view){
//        mDatabase.child("users").child(mUserId).child("subscriptions").;
//    }

    public void addSubToAccount(String className){
        account.addSubscription(className);
    }

    public void removeSubFromAccount(String className){
        account.removeSubscription(className);
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
                    CourseSelectFragment fragment = new CourseSelectFragment();
                    bundle.putInt("department_int", which);
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag, fragment);
                    fragmentTransaction.commit();
                }
            });

            return builder.create();
        }
    }
}
