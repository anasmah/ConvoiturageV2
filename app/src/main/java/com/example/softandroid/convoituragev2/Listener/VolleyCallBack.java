package com.example.softandroid.convoituragev2.Listener;

/**
 * Created by el-habib on 28/10/16.
 */

public interface VolleyCallBack<T> {


    public void sameOpperation();

    public void onSuccess(T result);

    public void errorNetwork();

}
