package org.yczbj.ycvideoplayer.ui.person;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.pedaily.yc.ycdialoglib.toast.ToastUtil;

import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycrefreshviewlib.swipeMenu.OnSwipeMenuListener;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.ui.person.adapter.MeCacheAdapter;
import org.yczbj.ycvideoplayer.test.test3.download2.DlTasksManager;
import org.yczbj.ycvideoplayer.test.test3.download2.DlTasksManagerModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by yc on 2018/1/18.
 */

public class MeCacheActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.btn_1)
    Button btn1;
    @Bind(R.id.btn_2)
    Button btn2;
    @Bind(R.id.btn_3)
    Button btn3;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    List<DlTasksManagerModel> mList = new ArrayList<>();

    /**
     * 正在下载
     */
    private List<DlTasksManagerModel> downloadingList = new ArrayList<>();
    /**
     * 已经下载完成的数据
     */
    private List<DlTasksManagerModel> downloadedList = new ArrayList<>();
    private MeCacheAdapter adapter;

    @Override
    public int getContentView() {
        return R.layout.activity_me_cache;
    }

    @Override
    public void initView() {
        initRecyclerView();
    }

    @Override
    public void initListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        adapter.setOnSwipeMenuListener(new OnSwipeMenuListener() {
            @Override
            public void toDelete(int position) {
                ToastUtil.showToast(MeCacheActivity.this,"删除");
            }

            @Override
            public void toTop(int position) {
                ToastUtil.showToast(MeCacheActivity.this,"置顶");
            }
        });
    }

    @Override
    public void initData() {
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:

                break;
            case R.id.btn_2:

                break;
            case R.id.btn_3:

                break;
            default:
                break;
        }
    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RecycleViewItemLine line = new RecycleViewItemLine(this, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);
        adapter = new MeCacheAdapter(this,mList);
        recyclerView.setAdapter(adapter);
    }


    private void getData() {
        downloadingList = DlTasksManager.getImpl().getModelList();
        downloadedList = DlTasksManager.getImpl().getDownloadedlList();
        //正在下载的数据
        if(downloadingList.size()>0){
            adapter.clear();
            adapter.addAll(downloadingList);
        }else {
            ToastUtil.showToast(this,"没有正在缓冲数据");
        }
        //已经下载完成的数据
        if(downloadedList.size()>0){
            adapter.addAll(downloadedList);
        }
        adapter.notifyDataSetChanged();
    }



}
