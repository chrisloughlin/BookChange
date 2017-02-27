package com.example.bookchange.bookchange;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BookchangeAccount account;
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        account = new BookchangeAccount("chrisloughlin","crlough18@gmail.com");

        if(savedInstanceState==null){
            Fragment fragment = new HomeFragment();
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

        if (id == R.id.nav_home) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            HomeFragment fragment = new HomeFragment();
            fragmentTransaction.replace(R.id.frag, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_department) {
            DialogFragment newFrag = new DepartmentsFrag();
            newFrag.show(getSupportFragmentManager(), "departmentsDialogFragment");
        } else if (id == R.id.nav_subscriptions) {
            // launch the subscription fragment (Preferences fragment?)
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SubscriptionsFragment fragment = new SubscriptionsFragment();
            fragmentTransaction.replace(R.id.frag, fragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_listings){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            YourListings fragment = new YourListings();
            fragmentTransaction.replace(R.id.frag, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setSelected(false);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onAddSubscriptionClicked(View view){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SubscriptionsFragment fragment = new SubscriptionsFragment();
        fragmentTransaction.replace(R.id.frag, fragment);
        fragmentTransaction.commit();
    }

    public void onCreateListingClicked(View view){
        Intent intent = new Intent(this,CreateListingActivity.class);
        intent.putExtra("account_name",account.getAccountName());
        startActivity(intent);
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
                    CourseSelect fragment = new CourseSelect();
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
