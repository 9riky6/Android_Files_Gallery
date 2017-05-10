package ricardo.android_files_gallery;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.File;


public class FileManager extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_manager);

        Intent intent = getIntent();
        final TextView path = (TextView) findViewById(R.id.path);
        //Aixo fa que sempre deixi la ultima barra
        String pathtemp = intent.getStringExtra("path");
        pathtemp = pathtemp.substring(0,pathtemp.lastIndexOf("/")+1);

        path.setText(pathtemp);
        File file = new File(path.getText().toString());

        TableLayout tabla = (TableLayout) findViewById(R.id.Contenido);
        tabla.removeAllViews();

        boolean root = true;
    }
}
