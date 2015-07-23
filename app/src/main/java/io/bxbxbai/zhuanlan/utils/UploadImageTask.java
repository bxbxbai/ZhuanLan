package io.bxbxbai.zhuanlan.utils;

import android.os.AsyncTask;
import android.util.Config;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;

/**
 * Created by xuebin on 15/6/4.
 */
public class UploadImageTask extends AsyncTask<Void, Integer, String> {

    private ProgressBar mProgressBar;
    private TextView mTextView;


    public UploadImageTask(ProgressBar progressBar, TextView textView) {
        mProgressBar = progressBar;
        mTextView = textView;
    }

    @Override
    protected void onPreExecute() {
        mProgressBar.setProgress(0);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        //at last

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(values[0]);
        mTextView.setText(values[0] + "%");
    }

    @Override
    protected String doInBackground(Void... params) {
        return uploadImage();
    }

    private String uploadImage() {
        String responseString = null;

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost("file_upload_url");

        try {
            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new AndroidMultiPartEntity.ProgressListener() {
                @Override
                public void transferred(long num) {
                    publishProgress((int)(num/ 100));
                }
            });

            File file = new File("file");

            entity.addPart("image", new FileBody(file));
            entity.addPart("website", new StringBody("www.google.com"));
            entity.addPart("email", new StringBody("abc@email.com"));
            //totalSize = entity.getContentLength();

            HttpResponse response = httpClient.execute(post);
            HttpEntity httpEntity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                responseString = EntityUtils.toString(httpEntity);
            } else {
                responseString = "Error occurred! Http Status Code : " + statusCode;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;
    }
}
