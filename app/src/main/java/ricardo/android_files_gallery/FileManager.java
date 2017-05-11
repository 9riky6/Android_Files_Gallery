package ricardo.android_files_gallery;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
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
//Afegeix el directori .. pare
        if (!(file.getAbsolutePath().equals("/"))){
            //Fem layout amb taula + inflater
            TableRow row = (TableRow) findViewById(R.id.Item);
            LayoutInflater inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //pasem el layoud la row de la taula.
            TableRow rowLayout = (TableRow) inflater.inflate(R.layout.item_list,row, false);

            //Fem la imatge.
            ImageView imatge = (ImageView) rowLayout.findViewById(R.id.icono);
            imatge.setImageResource(R.drawable.folder);

            //Fem el nom.
            TextView cami = (TextView) rowLayout.findViewById(R.id.textViewChildren);
            cami.setText("Atras (..)");
//            cami.setTextSize(16);

            //Modifiquem el size i el type
            TextView type = (TextView) rowLayout.findViewById(R.id.textViewType);
            TextView size = (TextView) rowLayout.findViewById(R.id.textViewSize);
            size.setText("");
            type.setText("Directori Anterior / pare");

            //Afegim la informació a la taula
            tabla.addView(rowLayout);

            root = false;
        }
        //Fa el ls. També introdueix imatge.
        final File[] children = file.listFiles();

        for(int i=0;i<children.length;i++){

            //Fem layout amb taula + inflater
            TableRow row = (TableRow) findViewById(R.id.Item);
            LayoutInflater inflater =
                    (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            TableRow rowLayout = (TableRow) inflater.inflate(R.layout.item_list,row, false);

            //Fem la imatge.
            ImageView imatge = (ImageView) rowLayout.findViewById(R.id.icono);
            if(children[i].isDirectory()) {
                imatge.setImageResource(R.drawable.folder);
            }else {
                imatge.setImageResource(R.drawable.unknown);
            }

            //Fem el nom.
            TextView cami = (TextView) rowLayout.findViewById(R.id.textViewChildren);
            cami.setText(children[i].getName());
            cami.setTextSize(16);

            //Si es un fitxer, que indiqui la mida i el tipus de fitxer
            //Si es un directori, que indiqui que ho es
            TextView extension = (TextView) rowLayout.findViewById(R.id.textViewType);
            TextView size = (TextView) rowLayout.findViewById(R.id.textViewSize);
            if (children[i].isFile()){
                //tamaño
                long temp = children[i].length();
                if (temp %1024 != 0)
                    temp = temp/1024 + 1;
                else
                    temp = temp/1024;
                size.setText(String.valueOf(temp)+" KB");

                //tipo
                String temp2 = children[i].getName();

                if(temp2.contains("."))
                    extension.setText("Fitxer " + temp2.substring(temp2.lastIndexOf(".")+1));

                else
                    extension.setText("Fitxer");
            }
            else{
                size.setText("");
                extension.setText("Directori");
            }

            //Aquest if es per no sobreescriure el directori ".." en cas de que no estiguem a la root
            if (root)
                tabla.addView(rowLayout);
            else
                tabla.addView(rowLayout, i+1);
        }
        //Listener per navegar
        for(int i=0;i<tabla.getChildCount();i++){
            final boolean finalRoot = root;
            final int finalI = i;
            final int aux;

            //Auxiliar degut a la carpeta ..
            if (!finalRoot)
                aux = -1;
            else
                aux = 0;

            tabla.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    //Exclusiu al ..
                    if(!finalRoot && finalI == 0){
                        String cami = path.getText().toString();

                        //Si acaba amb una barra, la treu
                        if (cami.charAt(cami.length()-1) == '/')
                            cami = cami.substring(0,cami.length()-1);

                        //Agafa tot el path fins la ultima barra, inclosa
                        cami = cami.substring(0,cami.lastIndexOf("/")+1);

                        Intent intent2 = new Intent(getApplicationContext(), FileManager.class);
                        intent2.putExtra("path",cami);
                        startActivity(intent2);
                    }
                    //Suposant que sigui un directori
                    else if(children[finalI+aux].isDirectory()){
                        Intent intent2 = new Intent(getApplicationContext(), FileManager.class);
                        intent2.putExtra("path",children[finalI+aux].getAbsolutePath()+"/");
                        startActivity(intent2);
                    }
                    else{
                        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                        intent2.putExtra("path",children[finalI+aux].getAbsolutePath());
                        startActivity(intent2);
                    }
                }
            });
        }


    }

}
