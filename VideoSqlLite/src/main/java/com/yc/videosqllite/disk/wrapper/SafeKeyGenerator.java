package com.yc.videosqllite.disk.wrapper;

import com.yc.videosqllite.cache.VideoLruCache;


public class SafeKeyGenerator {

  private final VideoLruCache<Key, String> loadIdToSafeHash = new VideoLruCache<>(1000);

  public String getSafeKey(Key key) {
    String safeKey;
    synchronized (loadIdToSafeHash) {
      safeKey = loadIdToSafeHash.get(key);
    }
    synchronized (loadIdToSafeHash) {
      loadIdToSafeHash.put(key, safeKey);
    }
    return safeKey;
  }



}
