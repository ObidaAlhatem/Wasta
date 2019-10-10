package com.devneoxmy.wasta;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OffersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ListView offerList;
    ServiceAdapter mAdapter;
    public ArrayList<Offer> offers = new ArrayList<Offer>();
    Button addServiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        ///////////////////////////////////////////////////////////

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ///////////////////////////////////////////////////////////

        offers.add(new Offer("service 1", "service 1 details", 5));
        offers.add(new Offer("service 2", "service 2 details", 3));
        offers.add(new Offer("service 3", "service 3 details", 4));
        offers.add(new Offer("service 4", "service 4 details", 5));
        offers.add(new Offer("service 5", "service 5 details", 3));
        offers.add(new Offer("service 6", "service 6 details", 4));
        offers.add(new Offer("service 7", "service 7 details", 5));


        addServiceButton = findViewById(R.id.add_offer);

        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsIntent = new Intent(OffersActivity.this, AddService.class);
                startActivity(detailsIntent);
            }
        });

        mAdapter = new ServiceAdapter(this, offers);
        offerList = findViewById(R.id.list_view);
        offerList.setAdapter(mAdapter);

        View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.footerview, null, false);
        offerList.addFooterView(footerView);

        offerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Offer currentOffer = offers.get(position);
                String offer = (currentOffer.getServiceName());
                Intent offerIntent = new Intent(OffersActivity.this, ServiceDetails.class.getComponentType());
                startActivity(offerIntent);
            }
        });
    }

    // menu click listener
    public boolean onNavigationItemSelected(MenuItem item) {
        String itemName = (String) item.getTitle();

        closeDrawer();

        switch (item.getItemId()) {
            case R.id.exit:
                AlertDialog.Builder buld = new AlertDialog.Builder(this);
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
                String tshear = "wasta app";
                String shearlink = "https://play.google.com/store/apps/details?id=com.powerdev.wasta1";
                Intent sher = new Intent();
                sher.setAction(Intent.ACTION_SEND);
                sher.setType("text/plain");
                sher.putExtra(Intent.EXTRA_TEXT, tshear + "       " + shearlink);
                startActivity(sher);

                break;
            case R.id.rate_us:
                Intent rartUs = new Intent(Intent.ACTION_VIEW);
                rartUs.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.powerdev.wasta1"));
                startActivity(rartUs);
                break;
            case R.id.contact:
                String txt = "السلام عليكم ورحمة الله وبركاته \n اقتراحي هو ";
                Intent senemail = new Intent(Intent.ACTION_SEND);
                senemail.setData(Uri.parse("mailto:"));
                senemail.setType("masseg/rfc822");
                senemail.putExtra(Intent.EXTRA_EMAIL, new String[]{"yasser2010_eg@yahoo.com"});
                senemail.putExtra(Intent.EXTRA_SUBJECT, "تطبيق وسطة");
                senemail.putExtra(Intent.EXTRA_TEXT, txt);
                startActivity(senemail);
                break;
        }
        return true;
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}
