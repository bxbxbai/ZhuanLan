package io.bxbxbai.zhuanlan.adapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by baia on 14-9-18.
 */
public class TestCallable {

    public void testCall() {
        Map<String, Callable> operationMap = new HashMap<String, Callable>();

        operationMap.put("A", new ACallable());
        operationMap.put("B", new BCallable());

        String[] targets = {"A", "B"};

        for(String s: targets) {
            operationMap.get(s).call("I'm " + s);
        }
    }
}
