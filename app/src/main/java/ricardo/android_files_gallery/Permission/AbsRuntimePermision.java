package ricardo.android_files_gallery.Permission;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by Ricardo on 18/05/2017.
 */

public abstract class AbsRuntimePermision extends AppCompatActivity {
    private SparseArray mErrorString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mErrorString = new SparseArray();
    }

    /**
     * Metodo abstracto para para controlar si se han aceptado los permisos.
     *
     * @param requestCode
     */
    public abstract void onPermissionsGranted(int requestCode);

    /**
     * Metodo publico para la peticion de los permisos, en este metodo pasaremos los permisos que queremos,
     * un mensaje que es el que se mostrara en el Snackbar en caso de no aceptar los permisos y
     * un valor que assignaremos en caso de que falle algo de los permissos.
     *
     * @param requestedPermission
     * @param stringId
     * @param requestCode
     */
    public void requestAppPermissions(final String[] requestedPermission, final int stringId, final int requestCode) {
        mErrorString.put(requestCode, stringId);

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean showRquestPermissions = false;
        for (String permission : requestedPermission) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this, permission);
            showRquestPermissions = showRquestPermissions || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
        }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (showRquestPermissions) {
                Snackbar.make(findViewById(android.R.id.content), stringId, Snackbar.LENGTH_INDEFINITE).setAction("GRANT", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(AbsRuntimePermision.this, requestedPermission, requestCode);
                    }
                }).show();
            } else {
                ActivityCompat.requestPermissions(this, requestedPermission, requestCode);
            }
        } else {
            onPermissionsGranted(requestCode);
        }
    }

    /**
     * Metodo de comprobacion si se han solicitado los permisos y si se han aceptado.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }

        if (grantResults.length > 0 && PackageManager.PERMISSION_GRANTED == permissionCheck) {
            onPermissionsGranted(requestCode);
        } else {
            Snackbar.make(findViewById(android.R.id.content), (Integer) mErrorString.get(requestCode),
                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.setData(Uri.parse("package:" + getPackageName()));
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(i);
                }
            }).show();
        }
    }
}
