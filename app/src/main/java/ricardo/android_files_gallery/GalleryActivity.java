package ricardo.android_files_gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import ricardo.android_files_gallery.ColorChoser.ColorinesActivity;
import ricardo.android_files_gallery.Files.FileManager;

public class GalleryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.phone_storage) {
            Intent intent = new Intent(getApplicationContext(), FileManager.class);
            intent.putExtra("path", MainActivity.rutaInterna + "/"); //rutaInterna
            startActivity(intent);
        } else if (id == R.id.sd_storage) {
            Intent intent = new Intent(getApplicationContext(), FileManager.class);
            intent.putExtra("path", MainActivity.rutaExterna[0] + "/"); //rutaInterna
            startActivity(intent);
        } else if (id == R.id.nav_galeria) {
            Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_apariencia) {
            Intent searchIntent = new Intent(GalleryActivity.this, ColorinesActivity.class);
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
}
