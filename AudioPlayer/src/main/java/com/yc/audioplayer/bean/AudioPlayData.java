package com.yc.audioplayer.bean;

/**
 * 链表结果的播放数据
 */
public class AudioPlayData {

    public AudioTtsPriority mPriority = AudioTtsPriority.NORMAL_PRIORITY;

    public boolean mPlayTts = true;

    private String mTts;

    private int mRawId;

    private AudioPlayData mCurrent;

    private AudioPlayData mNext;

    private AudioPlayData() {
        mCurrent = this;
        mCurrent.mNext = mNext;
    }

    private AudioPlayData(int mRawId) {
        this();
        this.mRawId = mRawId;
        this.mPlayTts = false;
    }

    private AudioPlayData(String mTts) {
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

    public AudioPlayData getNext() {
        return mNext;
    }

    public static class Builder {

        private AudioPlayData mHeaderPlayData;
        private AudioPlayData mCurrentPlayData;
        private AudioTtsPriority mPriority;

        public Builder(AudioTtsPriority priority) {
            this.mPriority = priority;
        }

        public Builder() {
        }

        public Builder tts(String string) {
            AudioPlayData data = new AudioPlayData(string);
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
            AudioPlayData data = new AudioPlayData(rawId);
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

        public AudioPlayData build() {
            return mHeaderPlayData;
        }

    }

    @Override
    public String toString() {
        return "AudioPlayData{" +
            "priority=" + mPriority +
            ", mTts='" + mTts + '\'' +
            ", mRawId=" + mRawId +
            ", mNext=" + mNext +
            '}';
    }

}
