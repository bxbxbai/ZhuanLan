package io.bxbxbai.zhuanlan.utils;

import com.android.volley.DefaultRetryPolicy;

/**
 *
 * @author bxbxbai
 */
public class ZhuanLanRetryPolicy extends DefaultRetryPolicy {

    public static final int RETRY_COUNT = 3;

    @Override
    public int getCurrentRetryCount() {
        return RETRY_COUNT;
    }
}
