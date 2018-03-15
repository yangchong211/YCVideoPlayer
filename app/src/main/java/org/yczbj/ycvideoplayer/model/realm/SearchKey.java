package org.yczbj.ycvideoplayer.model.realm;

import io.realm.RealmObject;

public class SearchKey extends RealmObject {

    public String searchKey;
    //插入时间
    public long insertTime;

    public SearchKey() {}

    public SearchKey(String suggestion, long insertTime) {
        this.searchKey = suggestion;
        this.insertTime = insertTime;
    }

    public String getSearchKey() {
        return searchKey;
    }
}
