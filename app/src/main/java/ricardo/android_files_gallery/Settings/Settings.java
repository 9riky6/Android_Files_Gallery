package ricardo.android_files_gallery.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import ricardo.android_files_gallery.R;


/**
 * Created by Ricardo on 21/05/2017.
 */

public class Settings extends AppCompatActivity
        implements View.OnClickListener {

    /**
     * Variables
     */
    final Context context = this;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int theme;
    FrameLayout statusBar;
    RelativeLayout relativeLayoutChooseTheme;
    Boolean homeButton = false, themeChanged;

    /**
     * Metodo por derfecto onCreate.
     * Recogeremos lo que tengamos creado por defecto en el super del metodo y nosotros llamaremos a las siguientes funciones.
     * Theme, toolbarStatusBar, navigationBar, settingsButton, fix BooleanDownload y themeChanged
     * Ahora explicaremos cada metodo por sepatado
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theme();
        setContentView(R.layout.activity_settings);
        toolbarStatusBar();
        navigationBarStatusBar();
        settingsButtons();
        fixBooleanDownload();
        themeChanged();
    }

    /**
     * Con este metodo lo que lograremos sera realizar las modificaciones,
     * sin este metodo las modificaciones que hicieramos no se guardarian.
     */
    public void fixBooleanDownload() {

        // Fix download boolean value
        editor = sharedPreferences.edit();
        editor.putBoolean("DOWNLOAD", true);
        editor.apply();
    }

    /**
     * Con este metodo l oque conseguimos es que segun la version de Android
     * del telefono movil apliquemos los colores de una manera o de otra, dado que
     * dependiendo de la version tenemos que tratar la toolbar y la statusbar de una
     * manera u otra.
     */
    private void navigationBarStatusBar() {

        // Fix portrait issues
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Fix issues for KitKat setting Status Bar color primary
            if (Build.VERSION.SDK_INT >= 19) {
                TypedValue typedValue19 = new TypedValue();
                Settings.this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue19, true);
                final int color = typedValue19.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
            }

            // Fix issues for Lollipop, setting Status Bar color primary dark
            if (Build.VERSION.SDK_INT >= 21) {
                TypedValue typedValue21 = new TypedValue();
                Settings.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue21, true);
                final int color = typedValue21.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
                getWindow().setStatusBarColor(color);
            }
        }

        // Fix landscape issues (only Lollipop)
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (Build.VERSION.SDK_INT >= 19) {
                TypedValue typedValue19 = new TypedValue();
                Settings.this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue19, true);
                final int color = typedValue19.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                TypedValue typedValue = new TypedValue();
                Settings.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
                final int color = typedValue.data;
                getWindow().setStatusBarColor(color);
            }
        }
    }

    /**
     * El uso de este metodo es para modificar la toolbar de manera que en el titulo
     * se muestre Settings y aparezca una flecha atras que nos mande al Home.
     */
    private void toolbarStatusBar() {
        // Cast toolbar and status bar
        statusBar = (FrameLayout) findViewById(R.id.statusBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Get support to the toolbar and change its title

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
     * Comprobamos el tema que pasamos por parametros y pedendiendo del integer,
     * segun el valor entraremos en uno de los siguientes caso y modificaremos la configuracion
     * del sharedPreferences.
     *
     * @param theme
     */
    public void setThemeFragment(int theme) {
        switch (theme) {
            case 1:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 1).apply();
                break;
            case 2:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 2).apply();
                break;
            case 3:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 3).apply();
                break;
            case 4:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 4).apply();
                break;
            case 5:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 5).apply();
                break;
            case 6:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 6).apply();
                break;
            case 7:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 7).apply();
                break;
            case 8:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 8).apply();
                break;
            case 9:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 9).apply();
                break;
            case 10:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 10).apply();
                break;
            case 11:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 11).apply();
                break;
            case 12:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 12).apply();
                break;
            case 13:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 13).apply();
                break;
            case 14:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 14).apply();
                break;
            case 15:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 15).apply();
                break;
            case 16:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 16).apply();
                break;
            case 17:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 17).apply();
                break;
            case 18:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 18).apply();
                break;
            case 19:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 19).apply();
                break;
            case 20:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 20).apply();
                break;
            case 21:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 21).apply();
                break;
            case 22:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 22).apply();
                break;
            case 23:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 23).apply();
                break;
            case 24:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 24).apply();
                break;
            case 25:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 25).apply();
                break;
            case 26:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 26).apply();
                break;
            case 27:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 27).apply();
                break;
            case 28:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 28).apply();
                break;
            case 29:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 29).apply();
                break;
            case 30:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 30).apply();
                break;
        }
    }

    /**
     * Recogemos el integer y aplicamos el tema segun el integer que le pasamos.
     *
     * @param theme
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

    /**
     * Comprobamos si es diferente al Main activity para aplicar el boton atras a la toolbar
     */
    private void themeChanged() {
        themeChanged = sharedPreferences.getBoolean("THEMECHANGED", false);
        homeButton = true;
    }

    /**
     * Con este metodo recogemos los indices del layout para poder aplicar cambios.
     */
    private void settingsButtons() {
        relativeLayoutChooseTheme = (RelativeLayout) findViewById(R.id.relativeLayoutChooseTheme);
        relativeLayoutChooseTheme.setOnClickListener(this);
    }

    /**
     * En este metodo preparamos el onClick del boton de cambio de color.
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relativeLayoutChooseTheme:
                FragmentManager fragmentManager = getSupportFragmentManager();
                ColorChooserDialog dialog = new ColorChooserDialog();
                dialog.show(fragmentManager, "fragment_color_chooser");
                break;
        }
    }
}
