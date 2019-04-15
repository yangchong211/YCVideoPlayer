package org.yczbj.ycvideoplayer.ui.person;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.constant.ConstantVideo;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.ui.test.test2.base.HomeKeyWatcher;
import org.yczbj.ycvideoplayer.ui.test.test2.model.Video;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author yc
 */
public class TestEightVideoActivity extends BaseActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private boolean pressedHome;
    private HomeKeyWatcher mHomeKeyWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeKeyWatcher = new HomeKeyWatcher(this);
        mHomeKeyWatcher.setOnHomePressedListener(new HomeKeyWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                pressedHome = true;
            }
        });
        pressedHome = false;
        mHomeKeyWatcher.startWatch();
    }

    @Override
    protected void onStop() {
        // 在OnStop中是release还是suspend播放器，需要看是不是因为按了Home键
        if (pressedHome) {
            VideoPlayerManager.instance().suspendVideoPlayer();
        } else {
            VideoPlayerManager.instance().releaseVideoPlayer();
        }
        super.onStop();
        mHomeKeyWatcher.stopWatch();
    }

    @Override
    protected void onRestart() {
        mHomeKeyWatcher.startWatch();
        pressedHome = false;
        super.onRestart();
        VideoPlayerManager.instance().resumeVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public int getContentView() {
        return R.layout.base_recycler_view;
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        VideoAdapter adapter;
        List<Video> list = new ArrayList<>();
        for (int a=0 ; a< ConstantVideo.VideoPlayerList.length ; a++){
            Video video = new Video(ConstantVideo.VideoPlayerTitle[a],10,"",ConstantVideo.VideoPlayerList[a]);
            list.add(video);
        }
        adapter = new VideoAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                VideoPlayer VideoPlayer = ((VideoAdapter.VideoViewHolder) holder).mVideoPlayer;
                if (VideoPlayer == VideoPlayerManager.instance().getCurrentVideoPlayer()) {
                    VideoPlayerManager.instance().releaseVideoPlayer();
                }
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

}
