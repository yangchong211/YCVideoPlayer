package org.yczbj.ycvideoplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.yczbj.ycvideoplayerlib.old.other.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.old.player.OldVideoPlayer;

import java.util.ArrayList;
import java.util.List;


/**
 * 如果你需要在播放的时候按下Home键能暂停，回调此Fragment又继续的话，需要继承自CompatHomeKeyFragment
 */
public class TestListFragment extends Fragment {

    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(),container,false);
        initView(view);
        return view;
    }

    public int getContentView() {
        return R.layout.base_recycler_view;
    }

    public void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        List<Video> list = new ArrayList<>();
        for (int a = 0; a< ConstantVideo.VideoPlayerList.length ; a++){
            Video video = new Video(ConstantVideo.VideoPlayerTitle[a],ConstantVideo.VideoPlayerList[a]);
            list.add(video);
        }
        VideoAdapter adapter = new VideoAdapter(getActivity(), list);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                OldVideoPlayer niceVideoPlayer = ((VideoAdapter.VideoViewHolder) holder).mVideoPlayer;
                if (niceVideoPlayer == VideoPlayerManager.instance().getCurrentVideoPlayer()) {
                    VideoPlayerManager.instance().releaseVideoPlayer();
                }
            }
        });
    }



}
