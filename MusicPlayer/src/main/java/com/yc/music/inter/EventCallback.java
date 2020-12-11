package com.yc.music.inter;


public interface EventCallback<T> {
    void onEvent(T t);
}
