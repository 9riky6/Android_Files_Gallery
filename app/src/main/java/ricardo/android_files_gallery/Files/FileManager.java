package ricardo.android_files_gallery.Files;


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
import android.widget.Toast;

import java.io.File;
import java.util.Arrays;

import ricardo.android_files_gallery.MainActivity;
import ricardo.android_files_gallery.R;


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
        final TableLayout tabla = (TableLayout) findViewById(R.id.Contenido);
        tabla.removeAllViews();

        boolean home = true;

        //Afegeix el directori .. pare
        if (!(file.getAbsolutePath().equals(pathtemp))){
            //Fem layout amb taula + inflater
            TableRow row = (TableRow) findViewById(R.id.Item);
            LayoutInflater inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //pasem el layoud la row de la taula.
            TableRow rowLayout = (TableRow) inflater.inflate(R.layout.item_list,row, false);

            //Fem la imatge.
            ImageView imatge = (ImageView) rowLayout.findViewById(R.id.icono);
            imatge.setImageResource(R.drawable.ic_arrow_back_black_24dp);

            //Fem el nom.
            TextView cami = (TextView) rowLayout.findViewById(R.id.textViewChildren);
            cami.setText(pathtemp);
//            cami.setTextSize(16);

            //Modifiquem el size i el type
            TextView type = (TextView) rowLayout.findViewById(R.id.textViewType);
            TextView size = (TextView) rowLayout.findViewById(R.id.textViewSize);
            size.setText("Cosas magicas 001");
            type.setText("Atras <3");

            //Afegim la informació a la taula
            tabla.addView(rowLayout);

            home = false;
        }
        //Fa el ls. També introdueix imatge.
        final File[] children = file.listFiles();
        Arrays.sort(children);

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
                //PER MILLORAR
                size.setText(TamanyFitxer(temp));

                //tipo
                String temp2 = children[i].getName();

                if(temp2.contains("."))
                    extension.setText("Fitxer " + temp2.substring(temp2.lastIndexOf(".")+1));

                else{
                    extension.setText("Fitxer");
                }
            }
            else{
                //PER MILLORAR
                size.setText(TamanyTotalfitxers(children[i].getAbsolutePath().toString()));
                extension.setText("Directori");
            }

            //Aquest if es per no sobreescriure el directori ".." en cas de que no estiguem a la home
            if (home)
                tabla.addView(rowLayout);
            else
                tabla.addView(rowLayout, i+1);
        }
        //Listener per navegar
        for(int i=0;i<tabla.getChildCount();i++){
            final boolean finalRoot = home;
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
                        //
                        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                        intent2.putExtra("path",children[finalI+aux].getAbsolutePath());
                        startActivity(intent2);
                    }
                }
            });
        }
    for(int i=0; i<tabla.getChildCount();i++){
        final int finalI = i;
        tabla.getChildAt(i).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(FileManager.this,children[finalI-1].getName(), Toast.LENGTH_LONG).show();
               // Toast.makeText(FileManager.this,children[finalI-1].getAbsolutePath(), Toast.LENGTH_LONG).show();//falta la / al final
                return true;
            }
        });
    }

    }
    //PER MILLORAR

    public String TamanyTotalfitxers(String ruta){
        File arxiu = new File (ruta);
        //ESPAI TOTAL
        String numero;//bytes
        long auxNum = arxiu.getTotalSpace()-arxiu.getFreeSpace();//numero temporal per pasar el valors i fer la combercio
        long num2 = arxiu.getTotalSpace()-arxiu.getFreeSpace() ;
        if(num2%1024 != 0){ //kilobyte
            numero = String.valueOf(auxNum)+" B";

        }else if(num2%1024 == 0 && num2/1048576 ==0){

            numero = String.valueOf(auxNum/1024)+" KB";

        }else if(num2/1048576 !=0 && num2/1073741824 ==0){

            numero = String.valueOf(auxNum/1048576)+" MB";

        }else{

            numero = String.valueOf(auxNum/1073741824)+" GB";
        }
        return numero;
    }
    public String  TamanyFitxer(long num){
        int n = (int) num;
        String valor= null;
        if (num %1024 != 0 && num/1024 !=0) {
            valor = String.valueOf(num)+" B";
        }else /*if(n%1024 != 0 && n/1048576==0)*/{

            valor = String.valueOf(num/1024)+" KB";

        }/*else if(n%1048576 != 0 && n/1073741824==0) {

            valor = String.valueOf(num / 1048576) + " MB";
        }else{
            valor = String.valueOf(num/1073741824)+" GB";
        }*/
        return valor;
    }
}
