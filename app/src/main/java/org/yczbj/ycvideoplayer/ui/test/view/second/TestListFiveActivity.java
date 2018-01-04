package org.yczbj.ycvideoplayer.ui.test.view.second;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.pedaily.yc.ycdialoglib.toast.ToastUtil;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BaseActivity;
import org.yczbj.ycvideoplayer.listener.OnListItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;

/**
 * Description:
 * Update:
 * CreatedTime:2017/12/29
 * Author:yc
 */
public class TestListFiveActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private TestListFiveAdapter adapterVideoList;
    private List<TestFiveBean> list;


    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }


    @Override
    public int getContentView() {
        return R.layout.base_recycler_view;
    }

    @Override
    public void initView() {
        list = new ArrayList<>();
        for(int a=0 ; a<10 ; a++){
            if(a==2 || a==5 || a==7 || a==9){
                TestFiveBean bean = new TestFiveBean(1,"five");
                list.add(bean);
            }else {
                TestFiveBean bean = new TestFiveBean(2,"five");
                list.add(bean);
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterVideoList = new TestListFiveAdapter(this, list);
        recyclerView.setAdapter(adapterVideoList);
    }

    @Override
    public void initListener() {
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                JZVideoPlayer jzvd = (JZVideoPlayer)view.findViewById(R.id.videoplayer);
                if (jzvd != null && JZUtils.dataSourceObjectsContainsUri(jzvd.dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
                    JZVideoPlayer.releaseAllVideos();
                }
            }
        });
        adapterVideoList.setOnItemClickListener(new OnListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(list.get(position).getType()==1){
                    ToastUtil.showToast(TestListFiveActivity.this,"这个是视频控件");
                }else if(list.get(position).getType()==2){
                    ToastUtil.showToast(TestListFiveActivity.this,"这个是文本控件");
                }
            }
        });
    }

    @Override
    public void initData() {

    }


}
