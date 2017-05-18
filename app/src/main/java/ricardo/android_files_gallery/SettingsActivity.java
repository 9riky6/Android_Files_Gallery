package ricardo.android_files_gallery;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */

    public static final String TAG = "Settings";

    private static final int FRAGMENT_OPEN = 99;

    private static final String EXTRA_RECREATE = "recreate";
    public static final String KEY_ADVANCED_DEVICES = "advancedDevices";
    public static final String KEY_FILE_SIZE = "fileSize";
    public static final String KEY_FOLDER_SIZE = "folderSize";
    public static final String KEY_FILE_THUMBNAIL = "fileThumbnail";
    public static final String KEY_FILE_HIDDEN = "fileHidden";
    private static final String KEY_PIN = "pin";
    public static final String KEY_PIN_ENABLED = "pin_enable";
    public static final String KEY_PIN_SET = "pin_set";
    public static final String KEY_ROOT_MODE = "rootMode";
    public static final String KEY_PRIMARY_COLOR = "primaryColor";
    public static final String KEY_ACCENT_COLOR = "accentColor";
    public static final String KEY_THEME_STYLE = "themeStyle";
    public static final String KEY_FOLDER_ANIMATIONS = "folderAnimations";
    public static final String KEY_RECENT_MEDIA = "recentMedia";

    private Resources res;
    private int actionBarColor;
    private final Handler handler = new Handler();
    private Drawable oldBackground;
    private boolean mRecreate = false;

    public static boolean getDisplayAdvancedDevices(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_ADVANCED_DEVICES, true);
    }

    public static boolean getDisplayFileSize(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_FILE_SIZE, true);
    }

    public static boolean getDisplayFolderSize(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_FOLDER_SIZE, false);
    }

    public static boolean getDisplayFileThumbnail(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_FILE_THUMBNAIL, true);
    }

    public static boolean getDisplayFileHidden(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_FILE_HIDDEN, false);
    }

    public static boolean getDisplayRecentMedia() {
        return PreferenceManager.getDefaultSharedPreferences(DocumentsApplication.getInstance().getBaseContext())
                .getBoolean(KEY_RECENT_MEDIA, true);
    }

    public static boolean getRootMode(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_ROOT_MODE, true);
    }

    public static int getPrimaryColor(Context context) {
        int newColor = ContextCompat.getColor(context, R.color.defaultColor);
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(KEY_PRIMARY_COLOR, newColor);
    }

    public static int getPrimaryColor() {
        return PreferenceManager.getDefaultSharedPreferences(DocumentsApplication.getInstance().getBaseContext())
                .getInt(KEY_PRIMARY_COLOR, Color.parseColor("#0288D1"));
    }

    public static int getAccentColor() {
        return PreferenceManager.getDefaultSharedPreferences(DocumentsApplication.getInstance().getBaseContext())
                .getInt(KEY_ACCENT_COLOR, Color.parseColor("#EF3A0F"));
    }

    public static void setAccentColor(int color) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(DocumentsApplication.getInstance().getBaseContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_ACCENT_COLOR, color);
        editor.commit();
    }

    public static String getThemeStyle() {
        return PreferenceManager.getDefaultSharedPreferences(DocumentsApplication.getInstance().getBaseContext())
                .getString(KEY_THEME_STYLE, "1");
    }

    public static boolean getFolderAnimation(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_FOLDER_ANIMATIONS, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        changeActionBarColor(0);
        res = getResources();
        actionBarColor = getPrimaryColor(this);
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return true;
    }

    public static final boolean isPinEnabled(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(KEY_PIN_ENABLED, false)
                && isPinProtected(context);
    }

    public static final boolean isPinProtected(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_PIN, "") != "";
    }

    public static void setPin(Context context, String pin) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(KEY_PIN, hashKeyForPIN(pin)).commit();
    }

    public static boolean checkPin(Context context, String pin) {
        pin = hashKeyForPIN(pin);
        String hashed = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_PIN, "");
        if (TextUtils.isEmpty(pin))
            return TextUtils.isEmpty(hashed);
        return pin.equals(hashed);
    }

    private static String hashKeyForPIN(String value) {
        if (TextUtils.isEmpty(value))
            return null;
        try {
            //MessageDigest digester = MessageDigest.getInstance("MD5");
            //return Base64.encodeToString(value.getBytes(), Base64.DEFAULT);
        }
        catch (Exception e) {
            CrashReportingManager.logException(e);
        }
        return value;
    }

/*    public static String hashKeyForPIN(String key) {
        String cacheKey = key;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        cacheKey = Base64.encodeToString(key.getBytes(), Base64.DEFAULT);
        return cacheKey;
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        changeActionBarColor(0);
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivityForResult(intent, FRAGMENT_OPEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FRAGMENT_OPEN){
            if(resultCode == RESULT_FIRST_USER){
                recreate();
            }
        }
    }

    @Override
    public void recreate() {
        mRecreate = true;
        super.recreate();
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EXTRA_RECREATE, true);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if(state.getBoolean(EXTRA_RECREATE)){
            setResult(RESULT_FIRST_USER);
        }
    }

    public void changeActionBarColor(int newColor) {

        int color = newColor != 0 ? newColor : SettingsActivity.getPrimaryColor(this);
        Drawable colorDrawable = new ColorDrawable(color);

        if (oldBackground == null) {
            getSupportActionBar().setBackgroundDrawable(colorDrawable);

        } else {
            TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, colorDrawable });
            getSupportActionBar().setBackgroundDrawable(td);
            td.startTransition(200);
        }

        oldBackground = colorDrawable;
    }

    public static void logSettingEvent(String key){
        AnalyticsManager.logEvent("settings_"+key.toLowerCase());
    }

}