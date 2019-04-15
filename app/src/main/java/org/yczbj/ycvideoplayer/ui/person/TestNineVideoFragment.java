package org.yczbj.ycvideoplayer.ui.person;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.constant.ConstantVideo;
import org.yczbj.ycvideoplayer.base.mvp1.BaseFragment;
import org.yczbj.ycvideoplayer.ui.test.test2.model.Video;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 如果你需要在播放的时候按下Home键能暂停，回调此Fragment又继续的话，需要继承自CompatHomeKeyFragment
 */
public class TestNineVideoFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    @Override
    public int getContentView() {
        return R.layout.base_recycler_view;
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        List<Video> list = new ArrayList<>();
        for (int a = 0; a< ConstantVideo.VideoPlayerList.length ; a++){
            Video video = new Video(ConstantVideo.VideoPlayerTitle[a],10,"",ConstantVideo.VideoPlayerList[a]);
            list.add(video);
        }
        VideoAdapter adapter = new VideoAdapter(getActivity(), list);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                VideoPlayer niceVideoPlayer = ((VideoAdapter.VideoViewHolder) holder).mVideoPlayer;
                if (niceVideoPlayer == VideoPlayerManager.instance().getCurrentVideoPlayer()) {
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
