package org.yczbj.ycvideoplayer.weight;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.widget.TextView;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BaseDialog;
import org.yczbj.ycvideoplayer.util.binding.BindView;

import butterknife.OnClick;


/**
 * AboutMeDialog
 * Created by yc on 2016/12/29.
 */

public class AboutMeDialog extends BaseDialog {


    @BindView(R.id.tv_affirm)
    TextView mTvAffirm;
    @BindView(R.id.tv_author_github)
    TextView mTvAuthorGithub;
    @BindView(R.id.tv_author_weibo)
    TextView getmTvAuthorWeibo;
    @BindView(R.id.tv_open_address)
    TextView mTvOpenAddress;
    @BindView(R.id.tv_thanks)
    TextView mTvThanks;
    @BindView(R.id.tv_gankio)
    TextView mTvGankio;

    private int mThemeColor;
    private Activity mContext;


    public AboutMeDialog(Activity context) {
        super(context, R.layout.dialog_about_me, R.style.BaseDialog);
    }

    public AboutMeDialog(Activity context, int color) {
        this(context);
        mContext = context;
        mThemeColor = color;
        initView();
    }


    private void initView() {
        mTvAffirm.setTextColor(mThemeColor);
        mTvAuthorGithub.setTextColor(mThemeColor);
        getmTvAuthorWeibo.setTextColor(mThemeColor);
        mTvOpenAddress.setTextColor(mThemeColor);
        mTvThanks.setTextColor(mThemeColor);
        mTvGankio.setTextColor(mThemeColor);

        setUnderline(mTvOpenAddress);
        setUnderline(mTvGankio);
        setUnderline(mTvAuthorGithub);
        setUnderline(getmTvAuthorWeibo);
    }

    private void setUnderline(TextView textView) {
        //下划线
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        //抗锯齿
        textView.getPaint().setAntiAlias(true);
    }

    @OnClick(R.id.tv_affirm)
    void affirm() {
        cancel();
    }

    @OnClick(R.id.tv_thanks)
    void thanks() {
        viewIntent("https://github.com/yangchong211");
    }

    @OnClick(R.id.tv_open_address)
    void openAddress() {
        viewIntent("https://github.com/yangchong211");
    }

    @OnClick(R.id.tv_gankio)
    void gankio() {
        viewIntent("https://github.com/yangchong211");
    }

    @OnClick(R.id.tv_author_github)
    public void github() {
        viewIntent("https://github.com/yangchong211");
    }

    @OnClick(R.id.tv_author_weibo)
    public void weibo() {
        viewIntent("https://github.com/yangchong211");
    }

    private void viewIntent(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(intent);
    }

}
