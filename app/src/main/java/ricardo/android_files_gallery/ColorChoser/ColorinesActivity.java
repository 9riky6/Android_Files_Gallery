package ricardo.android_files_gallery.ColorChoser;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ricardo.android_files_gallery.Files.FileManager;
import ricardo.android_files_gallery.GalleryActivity;
import ricardo.android_files_gallery.MainActivity;
import ricardo.android_files_gallery.R;


/**
 * Created by Ricardo on 07/05/2017.
 */

public class ColorinesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button button;
    Methods methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constant.theme);
        setContentView(R.layout.colories);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        methods = new Methods();
        button = (Button) findViewById(R.id.colorines_boton);

        colorize();

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ColorChooserDialog dialog = new ColorChooserDialog(ColorinesActivity.this);
                dialog.setTitle("Escoje");
                dialog.setColorListener(new ColorListener() {
                    @Override
                    public void OnColorClick(View v, int color) {
                        colorize();
                        Constant.color = color;
                        methods.setColorTheme(ColorinesActivity.this, color);

                        Intent intent = new Intent(ColorinesActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void colorize() {
        ShapeDrawable d = new ShapeDrawable(new OvalShape());
        d.setBounds(32, 32, 32, 32);

        d.getPaint().setStyle(Paint.Style.FILL);
        d.getPaint().setColor(ContextCompat.getColor(getApplicationContext(), Constant.color));

        button.setBackground(d);
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
            Intent searchIntent = new Intent(ColorinesActivity.this, ColorinesActivity.class);
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
