package io.bxbxbai.zhuanlan.utils;

/**
 *
 *
 * @author bxbxbai
 */
public class Utils {


    public static String getAuthorAvatarUrl(String origin, String userId, String size) {
        origin = origin.replace(ZhuanLanApi.TEMPLATE_ID, userId);
        return origin.replace(ZhuanLanApi.TEMPLATE_SIZE, size);
    }

}
