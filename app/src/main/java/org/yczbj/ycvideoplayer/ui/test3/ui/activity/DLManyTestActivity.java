package org.yczbj.ycvideoplayer.ui.test3.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.liulishuo.filedownloader.FileDownloader;

import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.ConstantVideo;
import org.yczbj.ycvideoplayer.base.BaseActivity;
import org.yczbj.ycvideoplayer.download.TasksManager;
import org.yczbj.ycvideoplayer.listener.OnItemLongClickListener;
import org.yczbj.ycvideoplayer.listener.OnListItemClickListener;
import org.yczbj.ycvideoplayer.ui.test3.download.DLTasksManager;
import org.yczbj.ycvideoplayer.ui.test3.download.DLTasksManagerModel;
import org.yczbj.ycvideoplayer.ui.test3.ui.adapter.DLManyAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by yc on 2018/1/12.
 */

public class DLManyTestActivity extends BaseActivity {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private DLManyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DLTasksManager.getImpl().onCreate(new WeakReference<>(this));
    }

    @Override
    public int getContentView() {
        return R.layout.base_recycler_view;
    }

    @Override
    public void initView() {
        initRecyclerView();
    }

    @Override
    public void initListener() {
        adapter.setOnItemClickListener(new OnListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onLongClick(View view, int position) {

            }
        });
    }

    @Override
    public void initData() {
        getData();
    }

    private void initRecyclerView() {
        List<DLTasksManagerModel> list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RecycleViewItemLine line = new RecycleViewItemLine(this, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);
        adapter = new DLManyAdapter(list,this);
        recyclerView.setAdapter(adapter);
    }



    public void postNotifyDataChanged() {
        if (adapter != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private void getData() {
        final List<DLTasksManagerModel> list = new ArrayList<>();
        for(int a = 0; a< ConstantVideo.VideoPlayerList.length; a++){
            DLTasksManagerModel bean = new DLTasksManagerModel(0,"logo",
                    ConstantVideo.VideoPlayerTitle[a], "url",ConstantVideo.VideoPlayerList[a]);
            list.add(bean);
        }
        adapter.addAllData(list);
    }

}
