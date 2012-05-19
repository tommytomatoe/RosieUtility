package net.classic.rosie.utility;

import java.io.File;
import java.net.URISyntaxException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.widget.Toast;

public class MainActivity extends PreferenceActivity {

    public static final String TAG = "Rosie Utility";

    public static final int SELECT_ACTIVITY = 2;
    private static final String PREF_CUSTOM_ACTIVITY = "pref_rosie_activity";
    private static final String PREF_RESET_ROSIE = "use_custom_rosie_activity";
    private static final String PREF_ROMAN_SOURCE = "roman_source";
    private static final String PREF_TT_SOURCE = "tt_source";

    Preference mRosieActivity;
    CheckBoxPreference mUseRosieCustomActivity;
    
    Preference mRomanbb;
    Preference mClassic;

    @Override
    protected void onCreate(Bundle ofLove) {
        super.onCreate(ofLove);

        addPreferencesFromResource(R.xml.rosie);

        PreferenceScreen prefs = getPreferenceScreen();

        mRosieActivity = prefs.findPreference(PREF_CUSTOM_ACTIVITY);
        mUseRosieCustomActivity = (CheckBoxPreference) prefs.findPreference(PREF_RESET_ROSIE);
        
        mRomanbb = prefs.findPreference(PREF_ROMAN_SOURCE);
        mClassic = prefs.findPreference(PREF_TT_SOURCE);

        String activityName = Settings.System.getString(getContentResolver(),
                "tweaks_rosie_activity_name");
        mRosieActivity.setSummary(activityName == null ? "Browser" : activityName);

        boolean checked = (Settings.System.getInt(getContentResolver(), "tweaks_rosie_remap_personalize",
                0) == 1) ? true : false;
        mUseRosieCustomActivity.setChecked(checked);

        /* check rosie */
        if (!new File("/system/app/Rosie.apk").exists()) {
            prefs.removePreference(prefs.findPreference("sense_tweaks_id"));
            prefs.removePreference(prefs.findPreference("sense_restart_rosie_cat"));
        }

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen screen,
            Preference preference) {
        if (preference == mRosieActivity) {
            // launch native android activity picker
            preference
                    .setSummary("**** List is loading, please be patient!!****");

            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
            pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
            startActivityForResult(pickIntent, SELECT_ACTIVITY);

            return true;

        } else if (preference == mUseRosieCustomActivity) {
            boolean checked = ((CheckBoxPreference) preference).isChecked();

            Settings.System.putInt(getContentResolver(),
                    "tweaks_rosie_remap_personalize", checked ? 1 : 0);
            if (!checked)
                Settings.System.putString(getContentResolver(),
                        "tweaks_rosie_activity_name", "Personalize");
            return true;

        } else if (preference == mRomanbb) {
        	
        	new AlertDialog.Builder(MainActivity.this)
        	.setTitle(R.string.select)
            .setItems(R.array.links_roman, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    String[] items = getResources().getStringArray(R.array.links_roman);
                    if (items[which].equals(getString(R.string.gh_source))) {
                    	Intent githubIntent = new Intent(Intent.ACTION_VIEW,
                            	Uri.parse("https://github.com/romanbb/ROM-Control/"));
                            	startActivity(githubIntent);
                    } else if (items[which].equals(getString(R.string.roman_twitter))) {
                    	Intent twitterIntent = new Intent(Intent.ACTION_VIEW,
                            	Uri.parse("http://twitter.com/#!/romanbb"));
                            	startActivity(twitterIntent);
                    }
                }
            })
            .show();
        	
        	return true;
        } else if (preference == mClassic) {
        	new AlertDialog.Builder(MainActivity.this)
        	.setTitle(R.string.select)
            .setItems(R.array.links_tommy, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    String[] items = getResources().getStringArray(R.array.links_tommy);
                    if (items[which].equals(getString(R.string.gh_source))) {
                    	Intent githubIntent = new Intent(Intent.ACTION_VIEW,
                            	Uri.parse("https://github.com/tommytomatoe/RosieUtility"));
                            	startActivity(githubIntent);
                    } else if (items[which].equals(getString(R.string.home))) {
                    	Intent homeIntent = new Intent(Intent.ACTION_VIEW,
                            	Uri.parse("http://classictomatoe.net"));
                            	startActivity(homeIntent);
                    }
                }
            })
            .show();
        	
        	return true;
        }
        
        return false;
    }

    protected void onResume() {
        super.onResume();
        String activityName = Settings.System.getString(getContentResolver(),
                "tweaks_rosie_activity_name");
        mRosieActivity.setSummary(activityName == null ? "Internet"
                : activityName);
    }

    public void openUserActivity(Context sup) {
        String activityUri = Settings.System.getString(
                sup.getContentResolver(), "tweaks_rosie_activity_intent");

        if (activityUri == null) {
            String packageName = "com.android.browser";
            String className = "com.android.browser.BrowserActivity";
            Intent internetIntent = new Intent(Intent.ACTION_VIEW);
            internetIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            internetIntent.setClassName(packageName, className);
            sup.startActivity(internetIntent);
        } else {
            try {
                sup.startActivity(Intent.getIntent(activityUri));
            } catch (URISyntaxException e) {
                Toast.makeText(sup, "Invalid activity intent",
                        Toast.LENGTH_SHORT);
            }
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_ACTIVITY
                && resultCode != Activity.RESULT_CANCELED) {
            // launch the application that we just picked
            // startActivity(data);

            PackageManager pm = getPackageManager();
            ResolveInfo ac = pm.resolveActivity(data,
                    PackageManager.MATCH_DEFAULT_ONLY);

            String appName = ac.loadLabel(pm).toString();

            String uri = data.toUri(Intent.URI_INTENT_SCHEME);
            // uri = uri.substring(7, uri.length());

            Settings.System.putString(getContentResolver(),
                    "tweaks_rosie_activity_name", appName);
            Settings.System.putString(getContentResolver(),
                    "tweaks_rosie_activity_intent", uri);
        }
    }

}