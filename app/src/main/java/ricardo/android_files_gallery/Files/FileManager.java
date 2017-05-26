package ricardo.android_files_gallery.Files;


import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ricardo.android_files_gallery.MainActivity;
import ricardo.android_files_gallery.R;


public class FileManager extends AppCompatActivity {
    ArrayList<String> ElementEliminar = new ArrayList<String>(100);
    ImageButton bCrearCarpeta ;
    ImageButton bBorrarCarpeta;
    ImageButton bCopiarCarpeta;
    ImageButton selecteItemRemove;
    Intent intent = null;
    String pathPare,pathtemp;
    File[] children;
    Context context;
    private String NomCarpeta;

    private SharedPreferences sharedPreferences;
    private int theme;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Select theme saved by user
        theme();
        setContentView(R.layout.file_manager);
       final ArrayList<String> ElementEliminar = new ArrayList<String>(100);
        ImageButton bCrearCarpeta = (ImageButton)findViewById(R.id.imageButton_crear_carpeta);
        final ImageButton bBorrarCarpeta = (ImageButton)findViewById(R.id.imageButton_borrar_carpeta);
        ImageButton bCopiarCarpeta = (ImageButton)findViewById(R.id.imageButton_copiar_carpeta);
        final ImageButton selecteItemRemove = (ImageButton)findViewById(R.id.remove);
        Intent intent = getIntent();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        //Afegeix el directori pare
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

//            //Modifiquem el size i el type
            TextView type = (TextView) rowLayout.findViewById(R.id.textViewType);
            TextView size = (TextView) rowLayout.findViewById(R.id.textViewSize);
            size.setVisibility(View.GONE);
            type.setVisibility(View.GONE);


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
               // Log.d("nombre ", temp2);
                if (temp2.contains(".")) {
                    extension.setText("Extensio " + temp2.substring(temp2.lastIndexOf(".") + 1));
                    getImatge(temp2, imatge);
                } else {
                    extension.setText("Arxiu");
                }
            } else { //Carpeta
                //PER MILLORAR
                String temp1 = children[i].getAbsolutePath();
                size.setText(getSize(temp1));
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

            //Auxiliar degut a la carpeta pare
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
                        //intent esplicit per cada arxiu.e
                        //TODO: POSAR INTENTS IMPLICITS
                        File send = new File(children[finalI-1].getAbsolutePath());
                        String send1 = children[finalI-1].getAbsolutePath();
                        IntenstsImplicits(send,send1);

                    }
                }
                private void IntenstsImplicits(File send, String send1) {
                    Intent intent1 = new Intent();
                    //intent1.setData(Uri.parse(children[finalI-1].getAbsolutePath()));
                    //Log.d("Root",children[finalI-1].getAbsolutePath());
                    intent1.setType("image/*");
                    intent1.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    intent1.addCategory(Intent.CATEGORY_OPENABLE);
                    intent1.putExtra(intent1.getAction(),send1);
                    //comprovacio
                    PackageManager pm = getPackageManager();
                    List<ResolveInfo> activities = pm.queryIntentActivities(intent1,PackageManager.MATCH_DEFAULT_ONLY);
                    List<LabeledIntent> otherAppIntentList = new ArrayList<LabeledIntent>();
                    for(int i=0;i<activities.size();i++){
                        ResolveInfo ri = activities.get(i);
                        String packaName = ri.activityInfo.packageName;
                        Intent intentToAdd = new Intent();
                            intentToAdd.setComponent(new ComponentName(packaName,ri.activityInfo.name));
                            intentToAdd.setAction(intent1.getAction());
                            intentToAdd.setType("image/*");
                            intentToAdd.setPackage(packaName);
                            intentToAdd.putExtra(intent1.getAction(),send1);
                        otherAppIntentList.add(new LabeledIntent(intentToAdd,packaName,ri.loadLabel(pm),ri.icon));
                    }
                    //combertim intentList to array
                    LabeledIntent[] extraIntents = otherAppIntentList.toArray(new LabeledIntent[otherAppIntentList.size()]);
                    // create chooser y poner todos los intents al chooser
                    Intent ver = Intent.createChooser(intent1,"Choose app: ");
                   // Toast.makeText(FileManager.this,"Activitys "+String.valueOf(activities.size()),Toast.LENGTH_LONG).show();
                    startActivity(ver);
                }
            });
        }
        for (int i = 1; i <tabla.getChildCount(); i++) {
            final int finalI = i;
            tabla.getChildAt(i).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ElementEliminar.add(children[finalI-1].getAbsolutePath());
                  //  Toast.makeText(FileManager.this,children[finalI-1].getAbsolutePath(),Toast.LENGTH_LONG).show();
                    tabla.getChildAt(finalI).setBackgroundResource(R.drawable.Selected_item);
                    selecteItemRemove.setVisibility(View.VISIBLE);
                    bBorrarCarpeta.setVisibility(View.VISIBLE);
                    return true;
                }
            });
        }
        final String finalPathtemp1 = pathtemp;
        bCrearCarpeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] NomCarpeta = new String[1];
                final Dialog dialog = new Dialog(FileManager.this);
                dialog.setContentView(R.layout.input);
                dialog.setTitle("Nombre Carpeta nueva:");
                final EditText ed1 = (EditText)dialog.findViewById(R.id.editText1);
                Button bt1 =(Button)dialog.findViewById(R.id.button1);
                bt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NomCarpeta[0] = ed1.getText().toString();
                        Boolean b = false;
                        b= CrearCarpeta(NomCarpeta[0], finalPathtemp1);
                        if(b){
                            Toast.makeText(FileManager.this,"La Carpeta se ha creado correctamente",Toast.LENGTH_LONG).show();
                            Intent intent2 = new Intent(getApplicationContext(), FileManager.class);
                            intent2.putExtra("path", finalPathtemp1);
                            intent2.putExtra("root",pathPare);
                            startActivity(intent2);
                            dialog.dismiss();
                        }else{
                            Toast.makeText(FileManager.this,"La Carpeta no se ha creado correctamente",Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });
        bBorrarCarpeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Boolean[] bool = {false};
                if(ElementEliminar!=null) {
                    for (int i = 0; i < ElementEliminar.size(); i++) {
                        final File f = new File(ElementEliminar.get(i));
                        if (f.isDirectory()) {
                            if (f.listFiles().length != 0) {
                                Toast.makeText(FileManager.this, "La carpeta " + f.getName() + " te contingut", Toast.LENGTH_LONG).show();
                                BorrarRecursivo(f);
                                if(f.delete()) Toast.makeText(FileManager.this,"EL directori es borrat",Toast.LENGTH_LONG).show();
                                else Toast.makeText(FileManager.this,"EL directori  no es borrat",Toast.LENGTH_LONG).show();
                                Intent intent2 = new Intent(getApplicationContext(), FileManager.class);
                                intent2.putExtra("path", finalPathtemp1);
                                intent2.putExtra("root",pathPare);
                                startActivity(intent2);
                            }else{
                                bool[0]=f.delete();
                                Intent intent2 = new Intent(getApplicationContext(), FileManager.class);
                                intent2.putExtra("path", finalPathtemp1);
                                intent2.putExtra("root",pathPare);
                                startActivity(intent2);
                            }
                        } else {
                            bool[0] = f.delete();
                            Intent intent2 = new Intent(getApplicationContext(), FileManager.class);
                            intent2.putExtra("path", finalPathtemp1);
                            intent2.putExtra("root",pathPare);
                            startActivity(intent2);
                        }
                    }
                }else{
                    Toast.makeText(FileManager.this, "SELECCIONI UN ELEMENT", Toast.LENGTH_LONG).show();
                }
            }
        });
        selecteItemRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0;i<tabla.getChildCount();i++){
                    tabla.getChildAt(i).setBackgroundResource(R.drawable.transparent);
                    ElementEliminar.clear();
                    Intent intent2 = new Intent(getApplicationContext(), FileManager.class);
                    intent2.putExtra("path", finalPathtemp1);
                    startActivity(intent2);
                }
            }
        });
    }
    public void theme() {
        sharedPreferences = getSharedPreferences("VALUES", Context.MODE_PRIVATE);
        theme = sharedPreferences.getInt("THEME", 0);
        settingTheme(theme);
    }
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
    private void getImatge(String name, ImageView imatge) {
        String extencionFile = name.substring(name.lastIndexOf(".") + 1);
        //Log.d("Contenido extencionFile", extencionFile);

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
        File f = new File(ruta);
        DecimalFormat df = new DecimalFormat("##.##");
        String Sumatotal = null;//bytes
        File[] files=null;
        float suma = 0;
       // Log.d("Root","Entro");
        if(f.isDirectory()){
            files = f.listFiles();
            if(files.length!=0) {
                for (int i = 0; i < files.length; i++) {
                    if(files[i].isDirectory()){
                        getSize(files[i].getAbsolutePath());
                    }
                    float auxNum = files[i].length();
                    suma = suma + auxNum;
                }
                int aux= (int) suma;

                if (aux <=1024) {
                    Sumatotal = df.format(suma) + " B ";
                } else if (aux >1024 && aux <=1048576 ) {
                    Sumatotal = df.format(suma / 1024) + " KB ";
                } else if (aux > 1048576  && aux <= 1073741824) {
                    Sumatotal = df.format(suma / 1048576) + " MB ";
                } else if (aux > 1073741824) {
                    Sumatotal = df.format(suma / 1073741824) + " GB ";
                } else if (aux == 0) {
                    Sumatotal = "Vacio";
                }
               // Log.d("Root", String.valueOf(aux));
            }else{
                Sumatotal ="Vacio";
            }
        }else{
            Sumatotal="Vacio";
        }
        return Sumatotal;
    }
    public String getSizefile(long num) {//arxius
        DecimalFormat df = new DecimalFormat("##.##");
        float n = (float) num;
        String valor;
        if (num % 1024 != 0 && num / 1024 == 0) {
            valor = String.valueOf(n) + " B ";
        } else if (num / 1024 != 0) {

            valor = df.format(n / 1024) + " KB ";

        } else if (num % 1048576 != 0) {

            valor = df.format(n / 1048576) + " MB ";
        } else {

            valor = df.format(n / 1073741824) + " GB ";
        }
        return valor;
    }
    private Boolean CrearCarpeta(String nombreCarpeta, String finalPathtemp) {
        File file = null;
        boolean bool= false;
        try {
            String rutaMesNom= finalPathtemp+nombreCarpeta;
            file = new File(rutaMesNom);
            bool=file.mkdir();

        }catch (Exception e){
            e.printStackTrace();
        }
        return bool;
    }
    //borrado recursivo del contenido de la carpeta padre
    private void BorrarRecursivo(File f){
        boolean bool = false;
        File[] fichero = f.listFiles();
        for (int x=0;x<fichero.length;x++) {
            if (fichero[x].isDirectory()) {
                    BorrarRecursivo(fichero[x]);
            }
                bool = fichero[x].delete();
//            Log.d("Root", "Entro al borrat");
//            Log.d("Root", "borrat? " + bool);
        }
            }
}
