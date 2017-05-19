package ricardo.android_files_gallery.Files;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;

import ricardo.android_files_gallery.MainActivity;
import ricardo.android_files_gallery.R;


public class FileManager extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_manager);

        Intent intent = getIntent();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Aixo fa que sempre deixi la ultima barra
        String pathtemp = intent.getStringExtra("path");//actual
        final String pathPare = intent.getStringExtra("root");//pare

        pathtemp = pathtemp.substring(0, pathtemp.lastIndexOf("/") + 1);
        getSupportActionBar().setTitle(pathtemp);

        File file = new File(pathtemp);
        final TableLayout tabla = (TableLayout) findViewById(R.id.Contenido);
        tabla.removeAllViews();

        boolean home = true;

        //TODO: AÑADIR LAYOUT SOLO PARA EL ATRAS. IMG AND TEXT
        //Afegeix el directori .. pare
        if (!(file.getAbsolutePath().equals(pathtemp))) {
            //Fem layout amb taula + inflater
            TableRow row = (TableRow) findViewById(R.id.Item);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //pasem el layoud la row de la taula.
            TableRow rowLayout = (TableRow) inflater.inflate(R.layout.item_list, row, false);

            //Fem la imatge.
            ImageView imatge = (ImageView) rowLayout.findViewById(R.id.icono);
            imatge.setImageResource(R.drawable.ic_arrow_back_black_24dp);

            //Fem el nom.
            TextView cami = (TextView) rowLayout.findViewById(R.id.textViewChildren);
            cami.setText(pathtemp);

            //Modifiquem el size i el type
            TextView type = (TextView) rowLayout.findViewById(R.id.textViewType);
            TextView size = (TextView) rowLayout.findViewById(R.id.textViewSize);
            size.setText("");
            type.setText("<3");

            //Afegim la informació a la taula
            tabla.addView(rowLayout);

            home = false;
        }

        //Fa el ls. També introdueix imatge.
        final File[] children = file.listFiles();
        Arrays.sort(children);

        for (int i = 0; i < children.length; i++) {

            //Fem layout amb taula + inflater
            TableRow row = (TableRow) findViewById(R.id.Item);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            TableRow rowLayout = (TableRow) inflater.inflate(R.layout.item_list, row, false);

            //Fem la imatge.
            ImageView imatge = (ImageView) rowLayout.findViewById(R.id.icono);
            if (children[i].isDirectory()) {
                imatge.setImageResource(R.drawable.folder);
            } else {
                imatge.setImageResource(R.drawable.unknown);
            }

            //Fem el nom.
            TextView cami = (TextView) rowLayout.findViewById(R.id.textViewChildren);
            cami.setText(children[i].getName());


            //Si es un fitxer, que indiqui la mida i el tipus de fitxer
            //Si es un directori, que indiqui que ho es i el pesa.
            TextView extension = (TextView) rowLayout.findViewById(R.id.textViewType);
            TextView size = (TextView) rowLayout.findViewById(R.id.textViewSize);

            if (children[i].isFile()) {//arxiu
                //tamaño
                long temp = children[i].length();
                //PER MILLORAR
                size.setText(getSizefile(temp));

                //tipo
                String temp2 = children[i].getName();
                Log.d("nombre ", temp2);
                if (temp2.contains(".")) {
                    extension.setText("Fitxer " + temp2.substring(temp2.lastIndexOf(".") + 1));
                    getImatge(temp2, imatge);
                } else {
                    extension.setText("Fitxer");
                }
            } else { //directori / capeta
                //PER MILLORAR
                size.setText(getSize(children[i].getAbsolutePath()));
                extension.setText("Directori");
            }

            //Aquest if es per no sobreescriure el directori ".." en cas de que no estiguem a la home
            if (home)
                tabla.addView(rowLayout);
            else
                tabla.addView(rowLayout, i + 1);
        }
        //Listener per navegar
        for (int i = 0; i < tabla.getChildCount(); i++) {
            final boolean finalRoot = home;
            final int finalI = i;
            final int aux;

            //Auxiliar degut a la carpeta ..
            if (!finalRoot)
                aux = -1;
            else
                aux = 0;

            final String finalPathtemp = pathtemp;
            tabla.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Exclusiu Atras
                    if (!finalRoot && finalI == 0) {//cliquem atras.
                        String cami = finalPathtemp;
                        if (cami.equalsIgnoreCase(pathPare)) {
                            Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                            intent2.putExtra("path", cami);
                            intent2.putExtra("root", pathPare);
                            startActivity(intent2);

                        } else if (cami.charAt(cami.length() - 1) == '/') {//Si acaba amb una barra, la treu
                            cami = cami.substring(0, cami.length() - 1);

                            //Agafa tot el path fins la ultima barra, inclosa
                            cami = cami.substring(0, cami.lastIndexOf("/") + 1);

                            Intent intent2 = new Intent(getApplicationContext(), FileManager.class);
                            intent2.putExtra("root", pathPare);
                            intent2.putExtra("path", cami);
                            startActivity(intent2);
                        }

                    }
                    //Suposant que sigui un directori
                    else if (children[finalI + aux].isDirectory()) {
                        Intent intent2 = new Intent(getApplicationContext(), FileManager.class);
                        intent2.putExtra("path", children[finalI + aux].getAbsolutePath() + "/");
                        intent2.putExtra("root", pathPare);
                        startActivity(intent2);
                    } else {
                        //intent esplicit per cada arxiu.
                        //TODO: POSAR INTENTS IMPLICITS!!!
                        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                        intent2.putExtra("path", children[finalI + aux].getAbsolutePath());
                        intent2.putExtra("root", pathPare);
                        startActivity(intent2);
                    }
                }
            });
        }
        for (int i = 0; i < tabla.getChildCount(); i++) {
            final int finalI = i;
            tabla.getChildAt(i).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(FileManager.this, children[finalI - 1].getName(), Toast.LENGTH_LONG).show();
                    // Toast.makeText(FileManager.this,children[finalI-1].getAbsolutePath(), Toast.LENGTH_LONG).show();//falta la / al final
                    //Posar drawable amb el color del selectet.
                    tabla.getChildAt(finalI).setBackgroundResource(R.drawable.clicked_gray);
                    return true;
                }
            });
        }

    }

    private void getImatge(String name, ImageView imatge) {
        String extencionFile = name.substring(name.lastIndexOf(".") + 1);
        Log.d("Contenido extencionFile", extencionFile);

        if (extencionFile.equalsIgnoreCase("jpg")) {
            imatge.setImageResource(R.drawable.jpg);

        } else if (extencionFile.equalsIgnoreCase(("ai"))) {
            imatge.setImageResource(R.drawable.ai);

        } else if (extencionFile.equalsIgnoreCase(("apk"))) {
            imatge.setImageResource(R.drawable.apk);

        } else if (extencionFile.equalsIgnoreCase(("avi"))) {
            imatge.setImageResource(R.drawable.avi);

        } else if (extencionFile.equalsIgnoreCase(("bmp"))) {
            imatge.setImageResource(R.drawable.bmp);

        } else if (extencionFile.equalsIgnoreCase(("cad"))) {
            imatge.setImageResource(R.drawable.cad);

        } else if (extencionFile.equalsIgnoreCase(("cdr"))) {
            imatge.setImageResource(R.drawable.cdr);

        } else if (extencionFile.equalsIgnoreCase(("css"))) {
            imatge.setImageResource(R.drawable.css);

        } else if (extencionFile.equalsIgnoreCase(("dat"))) {
            imatge.setImageResource(R.drawable.dat);

        } else if (extencionFile.equalsIgnoreCase(("dll"))) {
            imatge.setImageResource(R.drawable.dll);

        } else if (extencionFile.equalsIgnoreCase(("dmg"))) {
            imatge.setImageResource(R.drawable.dmg);

        } else if (extencionFile.equalsIgnoreCase(("doc"))) {
            imatge.setImageResource(R.drawable.doc);

        } else if (extencionFile.equalsIgnoreCase(("eps"))) {
            imatge.setImageResource(R.drawable.eps);

        } else if (extencionFile.equalsIgnoreCase(("fla"))) {
            imatge.setImageResource(R.drawable.fla);

        } else if (extencionFile.equalsIgnoreCase(("flv"))) {
            imatge.setImageResource(R.drawable.flv);

        } else if (extencionFile.equalsIgnoreCase(("gif"))) {
            imatge.setImageResource(R.drawable.gif);

        } else if (extencionFile.equalsIgnoreCase(("html"))) {
            imatge.setImageResource(R.drawable.html);

        } else if (extencionFile.equalsIgnoreCase(("indd"))) {
            imatge.setImageResource(R.drawable.indd);

        } else if (extencionFile.equalsIgnoreCase(("iso"))) {
            imatge.setImageResource(R.drawable.iso);

        } else if (extencionFile.equalsIgnoreCase(("js"))) {
            imatge.setImageResource(R.drawable.js);

        } else if (extencionFile.equalsIgnoreCase(("midi"))) {
            imatge.setImageResource(R.drawable.midi);

        } else if (extencionFile.equalsIgnoreCase(("mkv"))) {
            imatge.setImageResource(R.drawable.mkv);

        } else if (extencionFile.equalsIgnoreCase(("mov"))) {
            imatge.setImageResource(R.drawable.mov);

        } else if (extencionFile.equalsIgnoreCase(("mp3"))) {
            imatge.setImageResource(R.drawable.mp3);

        } else if (extencionFile.equalsIgnoreCase(("mpg"))) {
            imatge.setImageResource(R.drawable.mpg);

        } else if (extencionFile.equalsIgnoreCase(("php"))) {
            imatge.setImageResource(R.drawable.php);

        } else if (extencionFile.equalsIgnoreCase(("pdf"))) {
            imatge.setImageResource(R.drawable.pdf);

        } else if (extencionFile.equalsIgnoreCase(("png"))) {
            imatge.setImageResource(R.drawable.png);

        } else if (extencionFile.equalsIgnoreCase(("ppt"))) {
            imatge.setImageResource(R.drawable.ppt);

        } else if (extencionFile.equalsIgnoreCase(("ps"))) {
            imatge.setImageResource(R.drawable.ps);

        } else if (extencionFile.equalsIgnoreCase(("psd"))) {
            imatge.setImageResource(R.drawable.psd);

        } else if (extencionFile.equalsIgnoreCase(("rar"))) {
            imatge.setImageResource(R.drawable.rar);

        } else if (extencionFile.equalsIgnoreCase(("raw"))) {
            imatge.setImageResource(R.drawable.raw);

        } else if (extencionFile.equalsIgnoreCase(("sql"))) {
            imatge.setImageResource(R.drawable.sql);

        } else if (extencionFile.equalsIgnoreCase(("svg"))) {
            imatge.setImageResource(R.drawable.svg);

        } else if (extencionFile.equalsIgnoreCase(("tif"))) {
            imatge.setImageResource(R.drawable.tif);

        } else if (extencionFile.equalsIgnoreCase(("txt"))) {
            imatge.setImageResource(R.drawable.txt);

        } else if (extencionFile.equalsIgnoreCase(("wmv"))) {
            imatge.setImageResource(R.drawable.wmv);

        } else if (extencionFile.equalsIgnoreCase(("xls"))) {
            imatge.setImageResource(R.drawable.xls);

        } else if (extencionFile.equalsIgnoreCase(("xml"))) {
            imatge.setImageResource(R.drawable.xml);

        } else if (extencionFile.equalsIgnoreCase(("zip"))) {
            imatge.setImageResource(R.drawable.zip);

        } else if (extencionFile.equalsIgnoreCase(("aac"))) {
            imatge.setImageResource(R.drawable.aac);

        } else if (extencionFile.equalsIgnoreCase(("docx"))) {

            imatge.setImageResource(R.drawable.docx);
        } else if (extencionFile.equalsIgnoreCase(("odt"))) {

            imatge.setImageResource(R.drawable.odt);
        } else if (extencionFile.equalsIgnoreCase(("m4a"))) {

            imatge.setImageResource(R.drawable.m4a);
        } else if (extencionFile.equalsIgnoreCase(("jar"))) {

            imatge.setImageResource(R.drawable.jar);
        } else if (extencionFile.equalsIgnoreCase(("jad"))) {

            imatge.setImageResource(R.drawable.jad);
        } else if (extencionFile.equalsIgnoreCase(("vcf"))) {

            imatge.setImageResource(R.drawable.vcf);
        } else if (extencionFile.equalsIgnoreCase(("mp4"))) {

            imatge.setImageResource(R.drawable.mp4);
        } else if (extencionFile.equalsIgnoreCase(("jpeg"))) {

            imatge.setImageResource(R.drawable.jpeg);
        } else if (extencionFile.equalsIgnoreCase(("opus"))) {

            imatge.setImageResource(R.drawable.opus);
        } else {
            imatge.setImageResource(R.drawable.unknown);
        }
    }

    //PER MILLORAR mirar lo de les carpetes.
    public String getSize(String ruta) {//directori/carpeta
        DecimalFormat df = new DecimalFormat("####");
        File carpeta = new File(ruta);
        //ESPAI TOTAL
        String numero;//bytes
        float auxNum = (float) carpeta.getTotalSpace() - carpeta.getFreeSpace();//numero temporal per pasar el valors i fer la combercio
        float num2 = (float) carpeta.getTotalSpace() - carpeta.getFreeSpace();
        if (num2 % 1024 != 0) {

            numero = String.valueOf(df.format(Math.ceil(auxNum / 1024))) + " KB Carpeta";

        } else if (num2 / 1048576 != 0) {

            numero = String.valueOf(df.format(Math.ceil(auxNum / 1048576))) + " MB Carpeta";

        } else {

            numero = String.valueOf(df.format(Math.ceil(auxNum / 1073741824))) + " GB Carpeta";
        }
        return numero;
    }

    public String getSizefile(long num) {//arxius
        DecimalFormat df = new DecimalFormat("###0.#");
        float n = (float) num;
        String valor;
        if (num % 1024 != 0 && num / 1024 == 0) {
            valor = String.valueOf(n) + " B";
        } else if (num / 1024 != 0) {

            valor = df.format(n / 1024) + " KB";

        } else if (num % 1048576 != 0) {

            valor = df.format(n / 1048576) + " MB";
        } else {

            valor = df.format(n / 1073741824) + " GB";
        }
        return valor;
    }
}
