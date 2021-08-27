package com.yc.audioplayer;

/**
 * 链表结果的播放数据
 */
public class PlayData {

    public enum TtsPriority {

        NORMAL_PRIORITY,

        MIDDLE_PRIORITY,

        HIGH_PRIORITY
    }

    TtsPriority mPriority = TtsPriority.NORMAL_PRIORITY;

    boolean mPlayTts = true;

    private String mTts;

    private int mRawId;

    private PlayData mCurrent;

    private PlayData mNext;

    PlayData() {
        mCurrent = this;
        mCurrent.mNext = mNext;
    }

    PlayData(int mRawId) {
        this();
        this.mRawId = mRawId;
        this.mPlayTts = false;
    }

    PlayData(String mTts) {
        this();
        this.mTts = mTts;
        this.mPlayTts = true;
    }

    public String getTts() {
        return mTts;
    }

    public int getRawId() {
        return mRawId;
    }

    public PlayData getNext() {
        return mNext;
    }

    public static class Builder {

        private PlayData mHeaderPlayData;

        private PlayData mCurrentPlayData;
        private TtsPriority mPriority;

        public Builder(TtsPriority priority) {
            this.mPriority = priority;
        }

        public Builder() {
        }

        public Builder tts(String string) {
            PlayData data = new PlayData(string);
            if (mHeaderPlayData == null) {
                mHeaderPlayData = data;
                mCurrentPlayData = data;
            } else {
                mCurrentPlayData.mNext = data;
                mCurrentPlayData = data;
            }
            if (mPriority != null) {
                data.mPriority = mPriority;
            }
            return this;
        }

        public Builder rawId(int rawId) {
            PlayData data = new PlayData(rawId);
            if (mHeaderPlayData == null) {
                if (mPriority != null) {
                    data.mPriority = mPriority;
                }
                mHeaderPlayData = data;
                mCurrentPlayData = data;
            } else {
                mCurrentPlayData.mNext = data;
                mCurrentPlayData = data;
            }
            return this;
        }

        public PlayData build() {
            return mHeaderPlayData;
        }

    }

    @Override
    public String toString() {
        return "PlayData{" +
            "priority=" + mPriority +
            ", mTts='" + mTts + '\'' +
            ", mRawId=" + mRawId +
            ", mNext=" + mNext +
            '}';
    }

}
