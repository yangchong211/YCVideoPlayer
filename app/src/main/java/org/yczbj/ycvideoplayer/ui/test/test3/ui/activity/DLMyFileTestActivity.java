package org.yczbj.ycvideoplayer.ui.test.test3.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.pedaily.yc.ycdialoglib.bottomLayout.BottomDialogFragment;

import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.constant.ConstantVideo;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.listener.OnListItemClickListener;
import org.yczbj.ycvideoplayer.ui.person.MeCacheActivity;
import org.yczbj.ycvideoplayer.ui.test.test3.download2.DlTasksManagerModel;
import org.yczbj.ycvideoplayer.ui.test.test3.ui.adapter.DlMyFileAdapter;
import org.yczbj.ycvideoplayer.util.AppUtil;
import org.yczbj.ycvideoplayerlib.VideoPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class DLMyFileTestActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.btn_1)
    Button btn1;
    @Bind(R.id.btn_2)
    Button btn2;
    @Bind(R.id.tv_1)
    TextView tv1;
    @Bind(R.id.tv_2)
    TextView tv2;
    @Bind(R.id.video_player)
    VideoPlayer videoPlayer;

    @Override
    public int getContentView() {
        return R.layout.activity_test_dl_my;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                showBottomDialog();
                break;
            case R.id.btn_2:
                startActivity(MeCacheActivity.class);
                break;
            default:
                break;
        }
    }


    private void showBottomDialog() {
        final List<DlTasksManagerModel> list = new ArrayList<>();
        for(int a = 0; a< ConstantVideo.VideoPlayerList.length; a++){
            DlTasksManagerModel bean = new DlTasksManagerModel(0,"logo",
                    ConstantVideo.VideoPlayerTitle[a], ConstantVideo.VideoPlayerList[a],"path",0);
            list.add(bean);
        }
        final BottomDialogFragment dialog = new BottomDialogFragment();
        dialog.setFragmentManager(getSupportFragmentManager());
        dialog.setViewListener(new BottomDialogFragment.ViewListener() {
            @Override
            public void bindView(View v) {
                RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
                ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                ImageView ivDownload = (ImageView) v.findViewById(R.id.iv_download);

                recyclerView.setLayoutManager(new LinearLayoutManager(DLMyFileTestActivity.this));
                DlMyFileAdapter mAdapter = new DlMyFileAdapter(DLMyFileTestActivity.this, list);
                recyclerView.setAdapter(mAdapter);
                final RecycleViewItemLine line = new RecycleViewItemLine(
                        DLMyFileTestActivity.this, LinearLayout.HORIZONTAL,
                        SizeUtils.dp2px(1),
                        DLMyFileTestActivity.this.getResources().getColor(R.color.grayLine));
                recyclerView.addItemDecoration(line);
                mAdapter.setOnItemClickListener(new OnListItemClickListener() {
                    @Override
                    public void onItemClick(View view , int position) {

                    }
                });
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.iv_cancel:
                                dialog.dismissDialogFragment();
                                break;
                            case R.id.iv_download:

                                break;
                            default:
                                break;
                        }
                    }
                };
                ivCancel.setOnClickListener(listener);
                ivDownload.setOnClickListener(listener);
            }
        });
        dialog.setLayoutRes(R.layout.dialog_bottom_list_view);
        dialog.setDimAmount(0.5f);
        dialog.setTag("BottomDialogFragment");
        dialog.setCancelOutside(true);
        //这个高度可以自己设置，十分灵活
        dialog.setHeight(ScreenUtils.getScreenHeight()-videoPlayer.getHeight()
                - AppUtil.getStatusBarHeight(this));
        dialog.show();
    }

}
