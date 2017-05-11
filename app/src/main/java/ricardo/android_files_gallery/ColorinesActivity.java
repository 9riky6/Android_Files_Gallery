package ricardo.android_files_gallery;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import ricardo.android_files_gallery.ColorChoser.ColorChooserDialog;
import ricardo.android_files_gallery.ColorChoser.ColorListener;


/**
 * Created by Ricardo on 07/05/2017.
 */

public class ColorinesActivity extends AppCompatActivity {
    Button button;
    Methods methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constant.theme);
        setContentView(R.layout.colories);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_colorines);

        toolbar.setTitle("Colorines");
//        toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), Constant.color));

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
}
