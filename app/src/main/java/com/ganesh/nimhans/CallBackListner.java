package com.ganesh.nimhans;

import java.util.HashMap;

/**
 * Created by Ganesh on 11/12/15.
 */
public interface CallBackListner {
    default void onButtonClickListner(int position, int tag){

    }

    default void onButtonClickListner(String id) {

    }

    default void onButtonClickListner(HashMap<String, Integer> hashMap) {

    }
}
