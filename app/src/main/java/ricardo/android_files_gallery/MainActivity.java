package ricardo.android_files_gallery;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import ricardo.android_files_gallery.Files.FileManager;
import ricardo.android_files_gallery.Fragments.FileManagerFragment;
import ricardo.android_files_gallery.Fragments.Gallery_Fragment;
import ricardo.android_files_gallery.Fragments.Storage_Fragment;
import ricardo.android_files_gallery.Permission.AbsRuntimePermision;
import ricardo.android_files_gallery.Settings.Settings;

/**
 * The type Main activity.
 */
/* conversion
1 Kilobyte = 1,024 Bytes
1 Megabyte = 1,048,576 Bytes
1 Gigabyte = 1,073,741,824 Byte
1 Terabyte = 1,099,511,627,776 Bytes
*/
public class MainActivity extends AbsRuntimePermision
        implements NavigationView.OnNavigationItemSelectedListener,
        Storage_Fragment.OnFragmentInteractionListener,
        Gallery_Fragment.OnFragmentInteractionListener,
        FileManagerFragment.OnFragmentInteractionListener {

    /**
     * Variables
     */
    private static final int REQUEST_PERMISSION = 10;
    private boolean load = true;
    private boolean Permisions = false;
    private Storage_Fragment test;
    private SharedPreferences sharedPreferences;
    private int theme;
    private Intent intent;
    private android.graphics.Bitmap icono;
    private Dialog dialog;
    private Button bt1Dialog;

    /**
     * Metodo onCreate aqui comprobamos el color de la aplicacion y se lo aplicamos a la activity,
     * creamos la toolbar y el drawerLayout en el que aplicaremos todo.
     * <p>
     * Tambien configuraremos el menu lateral y por ultimo realizaremos la peticion de permisos.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Select theme saved by user
        theme();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = Storage_Fragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (Build.VERSION.SDK_INT > 23) {
            requestAppPermissions(new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    R.string.msg,
                    REQUEST_PERMISSION);
        }

    }

    /**
     * @param requestCode
     */
    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    /**
     * Metodo por defecto de las Activitys.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Asignar acciones a cada opcion del menu lateral.
     *
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        test = (Storage_Fragment) getSupportFragmentManager().findFragmentByTag("testID");
        if (id == R.id.nav_home) {
            if (test != null && test.isVisible()) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                // Toast.makeText(this, "NO peta", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        } else if (id == R.id.phone_storage) {
            intent = new Intent(getApplicationContext(), FileManager.class);
            intent.putExtra("path", Storage_Fragment.rutaInterna + "/"); //rutaInterna
            intent.putExtra("root", Storage_Fragment.rutaInterna + "/");
            startActivity(intent);
        } else if (id == R.id.sd_storage) {
            intent = new Intent(getApplicationContext(), FileManager.class);
            intent.putExtra("path", Storage_Fragment.rutaExterna[0] + "/"); //rutaInterna
            intent.putExtra("root", Storage_Fragment.rutaExterna[0] + "/");
            startActivity(intent);
        } else if (id == R.id.nav_galeria) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new Gallery_Fragment()).commit();
        } else if (id == R.id.nav_apariencia) {
            intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
        } else if (id == R.id.about_us) {
            About_us();
//            fragmentClass = FragmentTwo.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Metodo para mostrar el pop- up de About-us.
     */
    private void About_us() {
        dialog = new Dialog(MainActivity.this, R.style.Title);
        dialog.setContentView(R.layout.about_us);
        dialog.setTitle("Android Files Manager");
        bt1Dialog = (Button) dialog.findViewById(R.id.buttonCerrar_about);
        bt1Dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Recogemos la configuracion que tengamos del tema y lanzamos un metodo
     * que comprueba cual es el que tema que tenemos guardado en la configuracion.
     */
    public void theme() {
        sharedPreferences = getSharedPreferences("VALUES", Context.MODE_PRIVATE);
        theme = sharedPreferences.getInt("THEME", 0);
        settingTheme(theme);
    }

    /**
     * Este metodo solo lo ejecutamos porque es obligatorio al implementar fragments.
     *
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * Recogemos el integer y aplicamos el tema segun el integer que le pasamos.
     *
     * @param theme the theme
     */
    public void settingTheme(int theme) {
        switch (theme) {
            case 1:
                setTheme(R.style.AppTheme);
                break;
            case 2:
                setTheme(R.style.AppTheme_maraschino);
                break;
            case 3:
                setTheme(R.style.AppTheme_cayenne);
                break;
            case 4:
                setTheme(R.style.AppTheme_marron);
                break;
            case 5:
                setTheme(R.style.AppTheme_plum);
                break;
            case 6:
                setTheme(R.style.AppTheme_eggplant);
                break;
            case 7:
                setTheme(R.style.AppTheme_grape);
                break;
            case 8:
                setTheme(R.style.AppTheme_orchid);
                break;
            case 9:
                setTheme(R.style.AppTheme_lavender);
                break;
            case 10:
                setTheme(R.style.AppTheme_carnation);
                break;
            case 11:
                setTheme(R.style.AppTheme_strawberry);
                break;
            case 12:
                setTheme(R.style.AppTheme_bubblegum);
                break;
            case 13:
                setTheme(R.style.AppTheme_magenta);
                break;
            case 14:
                setTheme(R.style.AppTheme_salmon);
                break;
            case 15:
                setTheme(R.style.AppTheme_tangerine);
                break;
            case 16:
                setTheme(R.style.AppTheme_cantaloupe);
                break;
            case 17:
                setTheme(R.style.AppTheme_banana);
                break;
            case 18:
                setTheme(R.style.AppTheme_lemon);
                break;
            case 19:
                setTheme(R.style.AppTheme_honeydew);
                break;
            case 20:
                setTheme(R.style.AppTheme_lime);
                break;
            case 21:
                setTheme(R.style.AppTheme_spring);
                break;
            case 22:
                setTheme(R.style.AppTheme_clover);
                break;
            case 23:
                setTheme(R.style.AppTheme_fern);
                break;
            case 24:
                setTheme(R.style.AppTheme_moss);
                break;
            case 25:
                setTheme(R.style.AppTheme_flora);
                break;
            case 26:
                setTheme(R.style.AppTheme_seafoam);
                break;
            case 27:
                setTheme(R.style.AppTheme_spindrift);
                break;
            case 28:
                setTheme(R.style.AppTheme_teal);
                break;
            case 29:
                setTheme(R.style.AppTheme_sky);
                break;
            case 30:
                setTheme(R.style.AppTheme_tuquoise);
                break;
            default:
                setTheme(R.style.AppTheme);
                break;
        }
    }
}



