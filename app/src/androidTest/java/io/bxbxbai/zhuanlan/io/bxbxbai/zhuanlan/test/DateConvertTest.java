package io.bxbxbai.zhuanlan.io.bxbxbai.zhuanlan.test;

import android.test.AndroidTestCase;
import io.bxbxbai.common.StopWatch;
import io.bxbxbai.zhuanlan.utils.Utils;

/**
 * @author bxbxbai
 */
public class DateConvertTest extends AndroidTestCase {


    public void testDateConvert() {

//        <td><code>"yyyy-MM-dd'T'HH:mm:ssXXX"</code>
//        *         <td><code>2001-07-04T12:08:56.235-07:00</code>
        String str = "2015-03-18T14:43:50+08:00";

        StopWatch.log(Utils.convertPublishTime(str));

    }

}
