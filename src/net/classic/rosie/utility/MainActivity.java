
package net.classic.rosie.utility;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import net.classic.rosie.utility.ShellInterface;

public class MainActivity extends PreferenceActivity implements OnPreferenceChangeListener {
	
    public static final String TAG = "Sense Tweaks";
    
    
    /* launch right and launch left prefs*/
    private static final String PREF_CLICK_RIGHT = "click_right";
    private static final String PREF_CLICK_LEFT = "click_left";  
    private static final String PREF_CLICK_RIGHT_ACTIVITY = "click_right_activity";
    private static final String PREF_CLICK_LEFT_ACTIVITY = "click_left_activity";  
    public static final int SELECT_ACTIVITY = 2;
    //private static final String PREF_LONG_MIDDLE = "long_middle";    
    //private static final String PREF_LONG_RIGHT = "long_right";
    //private static final String PREF_LONG_LEFT = "long_left";
    //private static final String PREF_LONG_MIDDLE_ACTIVITY = "long_middle_activity";    
    //private static final String PREF_LONG_RIGHT_ACTIVITY = "long_right_activity";
    //private static final String PREF_LONG_LEFT_ACTIVITY = "long_left_activity";
    private static final String PREF_HIDE_STATUSBAR = "hide_show";
    
    private static final String PREF_ROMAN_SOURCE = "roman_source";
    private static final String PREF_TT_SOURCE = "tt_source";
      
    //private static final String PREF_ENABLE_SCREENSHOTS = "enable_screenshots";
    //private static final String PREF_ENABLE_UNLOCK_ANIM = "enable_unlock_animation";
    private static final String PREF_NUM_COLUMNS_APP_DRAWER = "app_drawer_app";    
    //private static final String PREF_USE_PAGINATED_APPS = "paginated_apps";
    private static final String PREF_KILL_ROSIE = "kill_rosie";
    //private static final String PREF_SCREEN_ON_MMS = "screen_on_mms";
    //private static final String PREF_TRANSPARENT_APPS = "transparent_apps";
    
    public String LongPress = null;

    CheckBoxPreference mEnableScreenshots;
    CheckBoxPreference mEnableUnlockAnimation;
    ListPreference mEnableColumns;    
    
    CheckBoxPreference mUseClickRight;
    CheckBoxPreference mUseClickLeft;
    Preference mClickRightActivity;
    Preference mClickLeftActivity;    
    
    CheckBoxPreference mUseLongMiddle;
    CheckBoxPreference mUseLongRight;
    CheckBoxPreference mUseLongLeft;
    Preference mLongMiddleActivity;
    Preference mLongRightActivity;
    Preference mLongLeftActivity;
    
    CheckBoxPreference mHideStatusBar;
    CheckBoxPreference mUsePaginatedAppDrawer;
    CheckBoxPreference mScreenOnSms;
    CheckBoxPreference mUseTransparentApps;
    Preference mKillRosie;
    
    Preference mRomanbb;
    Preference mClassic;

    @Override
    protected void onCreate(Bundle ofLove) {
        super.onCreate(ofLove);

        addPreferencesFromResource(R.xml.rosie);

        PreferenceScreen prefs = getPreferenceScreen();
        
        mUseClickRight = (CheckBoxPreference) prefs.findPreference(PREF_CLICK_RIGHT); 
        mClickRightActivity = prefs.findPreference(PREF_CLICK_RIGHT_ACTIVITY);
        String activityNameCR = Settings.System.getString(getContentResolver(),
                "activity_name_click_right");
        mClickRightActivity.setSummary(activityNameCR == null ? "Personalize" : activityNameCR);        
        boolean checked = (Settings.System.getInt(getContentResolver(), "use_click_right",
                0) == 1) ? true : false;
        mUseClickRight.setChecked(checked);
        
        mUseClickLeft = (CheckBoxPreference) prefs.findPreference(PREF_CLICK_LEFT);
        mClickLeftActivity = prefs.findPreference(PREF_CLICK_LEFT_ACTIVITY);
        String activityNameCL = Settings.System.getString(getContentResolver(),
                "activity_name_click_left");
        mClickLeftActivity.setSummary(activityNameCL == null ? "Dialer" : activityNameCL);
        checked = (Settings.System.getInt(getContentResolver(), "use_click_left", 0) == 1) ? true : false;
        mUseClickLeft.setChecked(checked);
                
        /*mUseLongMiddle = (CheckBoxPreference) prefs.findPreference(PREF_LONG_MIDDLE);
        mLongMiddleActivity = prefs.findPreference(PREF_LONG_MIDDLE_ACTIVITY);
        String activityNameM = Settings.System.getString(getContentResolver(),
                "activity_name_rosie_middle");
        mLongMiddleActivity.setSummary(activityNameM == null ? "Disabled" : activityNameM);
        checked = (Settings.System.getInt(getContentResolver(), "use_long_middle", 0) == 1) ? true : false;
        mUseLongMiddle.setChecked(checked);
        mUseLongMiddle.setSummary(checked ? "Enabled" : "Disabled");
        
        mUseLongRight = (CheckBoxPreference) prefs.findPreference(PREF_LONG_RIGHT);
        mLongRightActivity = prefs.findPreference(PREF_LONG_RIGHT_ACTIVITY);
        String activityNameR = Settings.System.getString(getContentResolver(),
                "activity_name_rosie_right");
        mLongRightActivity.setSummary(activityNameR == null ? "Default: Rosie Options" : activityNameR);
        checked = (Settings.System.getInt(getContentResolver(), "use_long_right", 0) == 1) ? true : false;
        mUseLongRight.setChecked(checked);
        mUseLongRight.setSummary(checked ? "Enabled" : "Disabled");
        
        mUseLongLeft = (CheckBoxPreference) prefs.findPreference(PREF_LONG_LEFT);
        mLongLeftActivity = prefs.findPreference(PREF_LONG_LEFT_ACTIVITY);
        String activityNameL = Settings.System.getString(getContentResolver(),
                "activity_name_rosie_left");
        mLongLeftActivity.setSummary(activityNameL == null ? "Disabled" : activityNameL);
        checked = (Settings.System.getInt(getContentResolver(), "use_long_left", 0) == 1) ? true : false;
        mUseLongLeft.setChecked(checked);
        mUseLongLeft.setSummary(checked ? "Enabled" : "Disabled");*/
        
        mKillRosie = prefs.findPreference(PREF_KILL_ROSIE);
        //mUseRosieCustomActivity = (CheckBoxPreference) prefs.findPreference(PREF_RESET_ROSIE);        
        //mEnableScreenshots = (CheckBoxPreference) prefs.findPreference(PREF_ENABLE_SCREENSHOTS);        
        //mEnableUnlockAnimation = (CheckBoxPreference) prefs.findPreference(PREF_ENABLE_UNLOCK_ANIM);
        
        mEnableColumns = (ListPreference) prefs.findPreference(PREF_NUM_COLUMNS_APP_DRAWER); 
        int numAppColumns = Settings.System.getInt(getContentResolver(),
                "tweaks_rosie_app_drawer_columns", 4);
        mEnableColumns.setValue(String.valueOf(numAppColumns));
        mEnableColumns.setOnPreferenceChangeListener(this);
        
        
        /*mLockscreenStylePref = (ListPreference) prefSet.findPreference(LOCKSCREEN_STYLE_PREF);
        int lockScreenStyle = Settings.System.getInt(getContentResolver(),
                "tweaks_lockscreen_style", 0);
        // mLockscreenStylePref.setValueIndex(lockScreenStyle);
        mLockscreenStylePref.setOnPreferenceChangeListener(this);*/
        
        //mUsePaginatedAppDrawer = (CheckBoxPreference) prefs.findPreference(PREF_USE_PAGINATED_APPS);
        //mScreenOnSms = (CheckBoxPreference) prefs.findPreference(PREF_SCREEN_ON_MMS);
        //mUseTransparentApps = (CheckBoxPreference) prefs.findPreference(PREF_TRANSPARENT_APPS);

        //String activityName = Settings.System.getString(getContentResolver(), "tweaks_rosie_activity_name");
        //mRosieActivity.setSummary(activityName == null ? "Browser" : activityName);
        
        //checked = (Settings.System.getInt(getContentResolver(), "tweaks_rosie_transparent", 0) == 1);
        //mUseTransparentApps.setChecked(true);
        
        mHideStatusBar = (CheckBoxPreference) prefs.findPreference(PREF_HIDE_STATUSBAR);
        checked = (Settings.System.getInt(getContentResolver(), "tweaks_show_hide_statusbar", 0) == 1) ? true : false;
        mHideStatusBar.setChecked(checked);

        //checked = (Settings.System.getInt(getContentResolver(), "tweaks_enable_screenshot", 1) == 1) ? true : false;
        //mEnableScreenshots.setChecked(checked);

        /* unlock animation */
        //checked = (Settings.System.getInt(getContentResolver(), "tweaks_rosie_skip_unlock_animation", 0) == 0);
        //mEnableUnlockAnimation.setChecked(checked);

        /* columns 
        checked = (Settings.System.getInt(getContentResolver(), "tweaks_rosie_app_drawer_columns", 4) == 5);
        mEnableFiveColumns.setChecked(checked); */

        /* page drawer */
        //checked = (Settings.System.getInt(getContentResolver(), "tweaks_rosie_app_scroll", 0) == 0);
        //mUsePaginatedAppDrawer.setChecked(checked);

        /* sms screen on */
        //checked = (Settings.System.getInt(getContentResolver(), "tweaks_sms_screen_on", 0) == 1);
        //mScreenOnSms.setChecked(checked);

        /* transparent app drawer */
        
        mRomanbb = prefs.findPreference(PREF_ROMAN_SOURCE);
        mClassic = prefs.findPreference(PREF_TT_SOURCE);

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen screen,
            Preference preference) {
    	
    	if (preference == mUseClickRight) {
            boolean checked = ((CheckBoxPreference) preference).isChecked();

            Settings.System.putInt(getContentResolver(),
                    "use_click_right", checked ? 1 : 0);
            if (!checked)
                Settings.System.putString(getContentResolver(),
                        "activity_name_click_right", null);
            return true;

        } else if (preference == mClickRightActivity) {
            // launch native android activity picker
        	LongPress = "right_click";
        	
            //preference
            	//.setSummary("**** List is loading, please be patient!!****");

            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
            pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
            startActivityForResult(pickIntent, SELECT_ACTIVITY);
            
            return true;

        } else if (preference == mUseClickLeft) {
            boolean checked = ((CheckBoxPreference) preference).isChecked();

            Settings.System.putInt(getContentResolver(),
                    "use_click_left", checked ? 1 : 0);
            if (!checked)
                Settings.System.putString(getContentResolver(),
                        "activity_name_click_left", null);
            return true;

        } else if (preference == mClickLeftActivity) {
            // launch native android activity picker
        	LongPress = "left_click";
        	
            //preference
            	//.setSummary("**** List is loading, please be patient!!****");

            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
            pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
            startActivityForResult(pickIntent, SELECT_ACTIVITY);
            
            return true;

        } else if (preference == mKillRosie) {
            preference.setSummary("Successfully killed");

            if (ShellInterface.isSuAvailable())
                ShellInterface.runCommand("busybox pkill -TERM -f com.htc.launcher");

            return true;
        } else if (preference == mUseTransparentApps) {
            boolean checked = ((CheckBoxPreference) preference).isChecked();

            Settings.System.putInt(getContentResolver(),
                    "tweaks_rosie_transparent", checked ? 1 : 0);
            return true;

        } else if (preference == mUseLongMiddle) {
            boolean checked = ((CheckBoxPreference) preference).isChecked();

            Settings.System.putInt(getContentResolver(),
                    "use_long_middle", checked ? 1 : 0);
            preference.setSummary(checked ? "Enabled" : "Disabled");
            if (!checked)
                Settings.System.putString(getContentResolver(),
                        "activity_name_rosie_middle", null);
            return true;

        } else if (preference == mLongMiddleActivity) {
            // launch native android activity picker
        	LongPress = "middle";
        	
            //preference
            	//.setSummary("**** List is loading, please be patient!!****");

            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
            pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
            startActivityForResult(pickIntent, SELECT_ACTIVITY);
            
            return true;

        } else if (preference == mUseLongRight) {
            boolean checked = ((CheckBoxPreference) preference).isChecked();

            Settings.System.putInt(getContentResolver(),
                    "use_long_right", checked ? 1 : 0);
            preference.setSummary(checked ? "Enabled" : "Disabled");
            if (!checked)
                Settings.System.putString(getContentResolver(),
                        "activity_name_rosie_right", null);
            return true;

        } else if (preference == mLongRightActivity) {
            // launch native android activity picker
        	LongPress = "right";
            
            //preference
        		//.setSummary("**** List is loading, please be patient!!****");

            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
            pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
            startActivityForResult(pickIntent, SELECT_ACTIVITY);
        
            return true;

        } else if (preference == mUseLongLeft) {
            boolean checked = ((CheckBoxPreference) preference).isChecked();

            Settings.System.putInt(getContentResolver(),
                    "use_long_left", checked ? 1 : 0);
            preference.setSummary(checked ? "Enabled" : "Disabled");
            if (!checked)
                Settings.System.putString(getContentResolver(),
                        "activity_name_rosie_left", null);
            return true;

        } else if (preference == mLongLeftActivity) {
            // launch native android activity picker
        	LongPress = "left";

        	//preference
        	//.setSummary("**** List is loading, please be patient!!****");

            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
            pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
            startActivityForResult(pickIntent, SELECT_ACTIVITY);
        
            return true;

        } else if (preference == mHideStatusBar) {
        	boolean checked = ((CheckBoxPreference) preference).isChecked();

            Settings.System.putInt(getContentResolver(),
                    "tweaks_show_hide_statusbar", checked ? 1 : 0);
            
            if (ShellInterface.isSuAvailable())
                ShellInterface.runCommand("busybox pkill -TERM -f com.htc.launcher");
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
    	
    	/*else if (preference == mUseLongMiddle) {
        }
            boolean checked = ((CheckBoxPreference) preference).isChecked();

            Settings.System.putInt(getContentResolver(),
                    "use_long_middle", checked ? 1 : 0);
            if (!checked)
                Settings.System.putString(getContentResolver(),
                        "use_long_middle", null);
            return true;

        } /*else if (preference == mScreenOnSms) {
            boolean checked = ((CheckBoxPreference) preference).isChecked();

            Settings.System.putInt(getContentResolver(),
                    "tweaks_sms_screen_on", checked ? 1 : 0);
            return true;
        } else if (preference == mEnableScreenshots) {
            boolean checked = ((CheckBoxPreference) preference).isChecked();

            Settings.System.putInt(getContentResolver(),
                    "tweaks_enable_screenshot", checked ? 1 : 0);
            return true;

        } else if (preference == mEnableUnlockAnimation) {
            boolean checked = ((CheckBoxPreference) preference).isChecked();

            Settings.System.putInt(getContentResolver(),
                    "tweaks_rosie_skip_unlock_animation", !checked ? 1 : 0);
            return true;

        } else if (preference == mUsePaginatedAppDrawer) {
            boolean checked = ((CheckBoxPreference) preference).isChecked();

            Settings.System.putInt(getContentResolver(),
                    "tweaks_rosie_app_scroll", checked ? 0 : 1);
            return true;

        } else if (preference == mEnableColumns) {
        	boolean checked = ((CheckBoxPreference) preference).isChecked();

        	Settings.System.putInt(getContentResolver(),
                	"tweaks_rosie_app_drawer_columns", checked ? 5 : 4);
        	return true;   
    	} else if (preference == mRosieActivity) {
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

        } else*/

        return false;
    }
    
    
    public boolean onPreferenceChange(Preference preference, Object newValue) {
    	if (preference == mEnableColumns) {
            int numColumns = Integer.valueOf((String) newValue);
            Settings.System.putInt(getContentResolver(), "tweaks_rosie_app_drawer_columns", numColumns);
            return true;
        } else {
        	//do something
        }

        return false;
    }
    
    protected void onResume() {
        super.onResume();
        
        String activityNameCR = Settings.System.getString(getContentResolver(),
                "activity_name_click_right");
        mClickRightActivity.setSummary(activityNameCR == null ? "Personlize"
                : activityNameCR);
        
        String activityNameCL = Settings.System.getString(getContentResolver(),
                "activity_name_click_left");
        mClickLeftActivity.setSummary(activityNameCL == null ? "Dialer"
                : activityNameCL);
        
        /*String activityNameM = Settings.System.getString(getContentResolver(),
                "activity_name_rosie_middle");
        mLongMiddleActivity.setSummary(activityNameM == null ? "Select app on long-press"
                : activityNameM);
        
        String activityNameR = Settings.System.getString(getContentResolver(),
                "activity_name_rosie_right");
        mLongRightActivity.setSummary(activityNameR == null ? "Default: Open Rosie Options"
                : activityNameR);
        
        String activityNameL = Settings.System.getString(getContentResolver(),
                "activity_name_rosie_left");
        mLongLeftActivity.setSummary(activityNameL == null ? "Select app on long-press"
                : activityNameL);*/
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == SELECT_ACTIVITY
                && resultCode != Activity.RESULT_CANCELED
                && LongPress.equals("right_click")) {
            // launch the application that we just picked
            // startActivity(data);

            PackageManager pm = getPackageManager();
            ResolveInfo ac = pm.resolveActivity(data,
                    PackageManager.MATCH_DEFAULT_ONLY);

            String appName = ac.loadLabel(pm).toString();

            String uri = data.toUri(Intent.URI_INTENT_SCHEME);
            // uri = uri.substring(7, uri.length());

            Settings.System.putString(getContentResolver(),
                    "activity_name_click_right", appName);
            Settings.System.putString(getContentResolver(),
                    "intent_click_app_rosie_right", uri);
        } else if (requestCode == SELECT_ACTIVITY
                && resultCode != Activity.RESULT_CANCELED
                && LongPress.equals("left_click")) {
            // launch the application that we just picked
            // startActivity(data);

            PackageManager pm = getPackageManager();
            ResolveInfo ac = pm.resolveActivity(data,
                    PackageManager.MATCH_DEFAULT_ONLY);

            String appName = ac.loadLabel(pm).toString();

            String uri = data.toUri(Intent.URI_INTENT_SCHEME);
            // uri = uri.substring(7, uri.length());

            Settings.System.putString(getContentResolver(),
                    "activity_name_click_left", appName);
            Settings.System.putString(getContentResolver(),
                    "intent_click_app_rosie_left", uri);
        } else if (requestCode == SELECT_ACTIVITY
                && resultCode != Activity.RESULT_CANCELED
                && LongPress.equals("middle")) {
            // launch the application that we just picked
            // startActivity(data);

            PackageManager pm = getPackageManager();
            ResolveInfo ac = pm.resolveActivity(data,
                    PackageManager.MATCH_DEFAULT_ONLY);

            String appName = ac.loadLabel(pm).toString();

            String uri = data.toUri(Intent.URI_INTENT_SCHEME);
            // uri = uri.substring(7, uri.length());

            Settings.System.putString(getContentResolver(),
                    "activity_name_rosie_middle", appName);
            Settings.System.putString(getContentResolver(),
                    "intent_long_app_rosie_middle", uri);
        } else if (requestCode == SELECT_ACTIVITY
                && resultCode != Activity.RESULT_CANCELED
                && LongPress.equals("right")) {
            // launch the application that we just picked
            // startActivity(data);

            PackageManager pm = getPackageManager();
            ResolveInfo ac = pm.resolveActivity(data,
                    PackageManager.MATCH_DEFAULT_ONLY);

            String appName = ac.loadLabel(pm).toString();

            String uri = data.toUri(Intent.URI_INTENT_SCHEME);
            // uri = uri.substring(7, uri.length());

            Settings.System.putString(getContentResolver(),
                    "activity_name_rosie_right", appName);
            Settings.System.putString(getContentResolver(),
                    "intent_long_app_rosie_right", uri);
        } else if (requestCode == SELECT_ACTIVITY
                && resultCode != Activity.RESULT_CANCELED
                && LongPress.equals("left")) {
            // launch the application that we just picked
            // startActivity(data);

            PackageManager pm = getPackageManager();
            ResolveInfo ac = pm.resolveActivity(data,
                    PackageManager.MATCH_DEFAULT_ONLY);

            String appName = ac.loadLabel(pm).toString();

            String uri = data.toUri(Intent.URI_INTENT_SCHEME);
            // uri = uri.substring(7, uri.length());

            Settings.System.putString(getContentResolver(),
                    "activity_name_rosie_left", appName);
            Settings.System.putString(getContentResolver(),
                    "intent_long_app_rosie_left", uri);
        }
    }
    
}