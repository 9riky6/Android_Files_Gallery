package ricardo.android_files_gallery;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class app_loading extends AppCompatActivity {

    private ProgressBar spinner;
    Button bt1 ;
    carregan loading1;
    private int TIME_SLEEP= 15;//en segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.app_loading_main);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        loading1 = new carregan();
        loading1.execute();
    }

private void conexion(){
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {

    }
}
    private class carregan extends AsyncTask<Void,Integer,Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i = 0; i < TIME_SLEEP; i++) {
                conexion();
                publishProgress(i*10);
                if (isCancelled()) {
                    break;
                }
            }
            return true;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                Toast.makeText(app_loading.this, "Conexion finalizada!",Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                spinner.setVisibility(View.INVISIBLE);
                //activity loading
                setResult(RESULT_OK,intent);
                finish();
            }
        }
        @Override
        protected void onCancelled() {
            Toast.makeText(app_loading.this, "CONEXION TIME OUT!",Toast.LENGTH_SHORT).show();
            spinner.setVisibility(View.INVISIBLE);
        }
    }
}
