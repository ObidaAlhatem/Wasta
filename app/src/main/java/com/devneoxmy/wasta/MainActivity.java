package com.devneoxmy.wasta;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //menu
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView=findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,
                toolbar,(R.string.drawer_open),(R.string.drawer_close));
        // تغير لون
        DrawerArrowDrawable drawerArrowDrawable = new DrawerArrowDrawable(this);
        drawerArrowDrawable.setColor(getResources().getColor(R.color.colorToggl));
        drawerToggle.setDrawerArrowDrawable(drawerArrowDrawable);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<GroupServiceModel> groupService = new ArrayList<>();

        ArrayList<ChildServiceModel> carsService=new ArrayList<>();
        carsService.add(new ChildServiceModel("service 1"));
        carsService.add(new ChildServiceModel("service 2"));
        carsService.add(new ChildServiceModel("service 3"));
        carsService.add(new ChildServiceModel("service 4"));

        GroupServiceModel cars = new GroupServiceModel("خدمات سيارات",carsService);
        groupService.add(cars);

        ArrayList<ChildServiceModel> homeService = new ArrayList<>();
        homeService.add(new ChildServiceModel("service 1"));
        homeService.add(new ChildServiceModel("service 2"));

        GroupServiceModel homesServ = new GroupServiceModel("خدمات منزلية",homeService);
        groupService.add(homesServ);

        ArrayList<ChildServiceModel> MaintenanceElectricalDevices = new ArrayList<>();
        MaintenanceElectricalDevices.add(new ChildServiceModel("service 1"));
        MaintenanceElectricalDevices.add(new ChildServiceModel("service 2"));
        MaintenanceElectricalDevices.add(new ChildServiceModel("service 3"));
        MaintenanceElectricalDevices.add(new ChildServiceModel("service 4"));

        GroupServiceModel elctDevice = new GroupServiceModel("صيانة اجهزة كهربائية",MaintenanceElectricalDevices);
        groupService.add(elctDevice);

        ChildAdapter adapter = new ChildAdapter(groupService);
        recyclerView.setAdapter(adapter);
    }
    // menu click listener

    public boolean onNavigationItemSelected( MenuItem item) {
        String itemName = (String) item.getTitle();

        closeDrawer();

        switch (item.getItemId()){
            case R.id.exit:
                AlertDialog.Builder buld=new AlertDialog.Builder(this);
                buld.setMessage("هل تريد الخروج")
                        .setTitle("انهاء")
                        .setIcon(R.drawable.ic_exit)
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                break;

            case R.id.apps:
                Toast.makeText(this, "more apps", Toast.LENGTH_LONG).show();
                Intent moreapp = new Intent(Intent.ACTION_VIEW);
                moreapp.setData(Uri.parse("https://play.google.com/store/apps/........."));
                startActivity(moreapp);
                break;

            case R.id.nav_share:
                String tshear ="wasta app";
                String shearlink="https://play.google.com/store/apps/details?id=com.powerdev.wasta1";
                Intent sher=new Intent();
                sher.setAction(Intent.ACTION_SEND);
                sher.setType("text/plain");
                sher.putExtra(Intent.EXTRA_TEXT,tshear+"       "+shearlink);
                startActivity(sher);
                break;

            case R.id.rate_us:
                Intent rartUs=new Intent(Intent.ACTION_VIEW);
                rartUs.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.powerdev.wasta1"));
                startActivity(rartUs);
                break;

            case R.id.contact:
                String txt= "السلام عليكم ورحمة الله وبركاته \n اقتراحي هو " ;
                Intent senemail =new Intent(Intent.ACTION_SEND);
                senemail.setData(Uri.parse("mailto:"));
                senemail.setType("masseg/rfc822");
                senemail.putExtra(Intent.EXTRA_EMAIL,new String[]{ "yasser2010_eg@yahoo.com"});
                senemail.putExtra(Intent.EXTRA_SUBJECT,"تطبيق وسطة");
                senemail.putExtra(Intent.EXTRA_TEXT,txt);
                startActivity(senemail);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        }
    }
    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private  void  openDrawer(){
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}
