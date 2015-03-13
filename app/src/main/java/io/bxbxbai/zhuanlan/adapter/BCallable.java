package io.bxbxbai.zhuanlan.adapter;

/**
 * Created by baia on 14-9-18.
 */
public class BCallable implements Callable {

    @Override
    public void call(String msg) {
        System.out.print("B : " + msg);
    }
}
