package ricardo.android_files_gallery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snappydb.SnappydbException;

import ricardo.android_files_gallery.Database.DBAccess;
import ricardo.android_files_gallery.Database.Database;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean load = true;
    TextView InternalStorage, ExternalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Database database = new Database(this);
        Integer color = null;
        try {
            color = database.getInt(DBAccess.COLOR);
            Log.d("asd", color.toString());
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        if (color == null) {
            try {
                database.putInt(DBAccess.COLOR, Constant.def);
            } catch (SnappydbException e) {
                e.printStackTrace();
            }
            color = Constant.def;
        }

        Methods.setColorTheme(this, color);

        setTheme(Constant.theme);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listRoots();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.Floatingbutton1);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RelativeLayout phoneStorage = (RelativeLayout) findViewById(R.id.phone_storage);
        phoneStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this,
                        "Phone Storage Main", Toast.LENGTH_LONG).show();
                //File manager <3
                Intent intent = new Intent(getApplicationContext(), FileManager.class);
                intent.putExtra("path", "/");
                startActivity(intent);
            }
        });
        RelativeLayout sdStorage = (RelativeLayout) findViewById(R.id.sd_storage);
        sdStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PhoneFragment phoneFrag = new PhoneFragment();
//                FragmentManager manager = getSupportFragmentManager();
//                manager.beginTransaction().replace(
//                        R.id.include,
//                        phoneFrag,
//                        phoneFrag.getTag()
//                ).commit();

                Toast.makeText(MainActivity.this,
                        "SD Storage", Toast.LENGTH_LONG).show();
            }
        });

//        SwitchCompat switchCompat = (SwitchCompat) findViewById(R.id.blanco_switch);
//
//        switchCompat.setChecked(false);
//        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView,
//                                         boolean isChecked) {
//            }
//        });
//
//        //check the current state before we display the screen
//        if (switchCompat.isChecked()) {
//            Toast.makeText(MainActivity.this,
//                    "Blanco", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(MainActivity.this,
//                    "Negro", Toast.LENGTH_LONG).show();
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.phone_storage) {
            Toast.makeText(this, "PHONE Menu", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.sd_storage) {
            Toast.makeText(this, "SD Menu", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_galeria) {
            Toast.makeText(this, "GALERIA", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_apariencia) {
            Intent searchIntent = new Intent(MainActivity.this, ColorinesActivity.class);
            startActivity(searchIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        } else if (id == R.id.about_us) {
            Toast.makeText(this, "Ricardo THE BEST", Toast.LENGTH_LONG).show();
        } else if (id == R.id.blanco_switch) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


//    private void listRoots() {
//        InternalStorage = (TextView) findViewById(R.id.textTituloInter);
//       // ExternalStorage = (TextView) findViewById(R.id.textTituloExt);
//
//        String IntStorage;
//
//        IntStorage = Environment.getExternalStorageDirectory().getAbsolutePath();
//        if(IntStorage.contains("sdcard"))
//        {
//            InternalStorage.setText("Phone Storage <3");
//        }
//Falta posar la SD
//}

    private void listRoots() {
        InternalStorage = (TextView) findViewById(R.id.textTituloInter);
        ExternalStorage = (TextView) findViewById(R.id.textTituloExt);

        String IntStorage, External;
//        String[] Info= new String[30];
//        int i=0;
//        do{
//            i=0;
//            Info[i] = Environment.getExternalStorageDirectory().getAbsolutePath();
//            if(Info[i].contains("storage")){
//                InternalStorage.setText(Info[i].concat("--Mobil--"));
//            }if(Info[i].contains("ext")){
//                ExternalStorage.setText(Info[i].concat("--SD--"));
//            }
//            i++;
//        }while(Info!=null || i==30);
        IntStorage = Environment.getExternalStorageDirectory().getAbsolutePath();
        //External =Environment.getDownloadCacheDirectory().getAbsolutePath();
        //ExternalStorage.setText(External);
        if (IntStorage.contains("storage")) {
            InternalStorage.setText(IntStorage);
        }
    }
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
