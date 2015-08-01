package io.bxbxbai.zhuanlan.fragment;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import io.bxbxbai.zhuanlan.App;
import io.bxbxbai.zhuanlan.R;

public class PrefsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);
        findPreference("about").setOnPreferenceClickListener(this);

        PackageManager pm = App.getInstance().getPackageManager();

        try {
            if (pm != null) {
                pm.getPackageInfo("com.zhihu.android", PackageManager.GET_ACTIVITIES);
            }
        } catch (PackageManager.NameNotFoundException e) {
            ((PreferenceCategory) findPreference("settings_settings")).removePreference(findPreference("using_client?"));
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals("about")) {
            showApacheLicenseDialog();
            return true;
        }
        return false;
    }

    private void showApacheLicenseDialog() {
        final Dialog apacheLicenseDialog = new Dialog(getActivity());
        apacheLicenseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        apacheLicenseDialog.setCancelable(true);
        apacheLicenseDialog.setContentView(R.layout.dialog_apache_license);

        TextView textView = (TextView) apacheLicenseDialog.findViewById(R.id.dialog_text);

        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.licences_header)).append("\n");

        String[] basedOnProjects = getResources().getStringArray(R.array.apache_licensed_projects);

        for (String str : basedOnProjects) {
            sb.append("• ").append(str).append("\n");
        }

        sb.append("\n").append(getString(R.string.licenses_subheader));
        sb.append("\n\n").append(getString(R.string.apache_license));
        textView.setText(sb.toString());

        apacheLicenseDialog.findViewById(R.id.close_dialog_button)
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        apacheLicenseDialog.dismiss();
                    }
                });

        apacheLicenseDialog.show();
    }
}
