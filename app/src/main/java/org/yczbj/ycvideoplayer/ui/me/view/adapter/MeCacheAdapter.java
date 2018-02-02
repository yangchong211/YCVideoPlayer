package org.yczbj.ycvideoplayer.ui.me.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.ns.yc.ycprogresslib.CircleProgressbar;
import com.pedaily.yc.ycdialoglib.toast.ToastUtil;

import org.yczbj.ycrefreshviewlib.swipeMenu.OnSwipeMenuListener;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.download.TasksManager;
import org.yczbj.ycvideoplayer.listener.OnListItemClickListener;
import org.yczbj.ycvideoplayer.ui.test3.download2.DlCacheTasksManager;
import org.yczbj.ycvideoplayer.ui.test3.download2.DlTasksManagerModel;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MeCacheAdapter extends RecyclerView.Adapter<MeCacheAdapter.ViewHolder> {


    private LayoutInflater inflater;
    private List<DlTasksManagerModel> mList;
    private Context mContext;
    private OnListItemClickListener onItemClickListener;

    public MeCacheAdapter(Context mContext, List<DlTasksManagerModel> mList) {
        this.mList = mList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        //1.一定要添加这一步
        DlCacheTasksManager.getImpl().onCreate(new WeakReference<>(this));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_me_cache_data, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(mList==null || mList.size()<=0){
            return;
        }
        DlTasksManagerModel model = mList.get(position);
        //获取下载链接
        String url = model.getUrl();
        //通过下载链接创建下载路径
        final String path = DlCacheTasksManager.getImpl().createPath(url);
        //通过下载链接创建下载id
        int id = DlCacheTasksManager.getImpl().getId(url);
        holder.update(id, position, model);
        DlCacheTasksManager.getImpl().updateViewHolder(holder.id, holder);


        holder.ivImage.setTag(holder);
        holder.ivDownload.setTag(R.drawable.ic_player_start);
        holder.ivImage.setOnClickListener(taskActionOnClickListener);

        holder.tvName.setText(model.getName());
        holder.ivDownload.setBackgroundResource(R.drawable.ic_player_start);


        //条目点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, position);
                }
            }
        });

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_del:
                        if (null != listener) {
                            listener.toDelete(position);
                        }
                        break;
                    case R.id.btn_top:
                        if (null != listener) {
                            listener.toTop(position);
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        holder.btnDel.setOnClickListener(clickListener);
        holder.btnTop.setOnClickListener(clickListener);


        if (DlCacheTasksManager.getImpl().isReady()) {
            final int status = DlCacheTasksManager.getImpl().getStatus(id, path);
            if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started || status == FileDownloadStatus.connected) {
                // start task, but file not created yet
                //当下载在排队中，开始下载，或者链接下载时，则开始调用更新下载进度和状态的方法
                long soFar = DlCacheTasksManager.getImpl().getSoFar(id);
                //使用{code id}获取任务目标文件的总字节。
                long total = DlCacheTasksManager.getImpl().getTotal(id);
                holder.updateDownloading(status, soFar, total);
            } else if (!new File(path).exists() && !new File(FileDownloadUtils.getTempPath(path)).exists()) {
                // not exist file
                //当下载文件夹不存在，并且下载路径不存在时，直接调用下载错误方法
                holder.updateNotDownloaded(status, 0, 0);
            } else if (DlCacheTasksManager.getImpl().isDownloaded(status)) {
                // already downloaded and exist
                //获取下载的状态是否下载完成，如果完成，则调用该方法
                holder.updateDownloaded();
            } else if (status == FileDownloadStatus.progress) {
                //当下载在排队中，开始下载，或者链接下载时，则开始调用更新下载进度和状态的方法
                long soFar = DlCacheTasksManager.getImpl().getSoFar(id);
                //使用{code id}获取任务目标文件的总字节。
                long total = DlCacheTasksManager.getImpl().getTotal(id);
                // downloading
                holder.updateDownloading(status, soFar, total);
            } else {
                // not start
                //当下载在排队中，开始下载，或者链接下载时，则开始调用更新下载进度和状态的方法
                long soFar = DlCacheTasksManager.getImpl().getSoFar(id);
                //使用{code id}获取任务目标文件的总字节。
                long total = DlCacheTasksManager.getImpl().getTotal(id);
                holder.updateNotDownloaded(status, soFar, total);
            }
        } else {
            //状态: 加载中...
        }
    }


    @Override
    public int getItemCount() {
        return mList==null ? 0 : mList.size();
    }


    public void postNotifyDataChanged() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
        }
    }

    public void addAll(List<DlTasksManagerModel> downloadingList) {
        if (mList != null) {
            mList.addAll(downloadingList);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.iv_download)
        ImageView ivDownload;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_state)
        TextView tvState;
        @Bind(R.id.pb)
        ProgressBar pb;
        @Bind(R.id.btn_del)
        Button btnDel;
        @Bind(R.id.btn_top)
        Button btnTop;
        /**
         * viewHolder position
         */
        private int position;
        /**
         * download id
         */
        private int id;
        /**
         * 数据
         */
        private DlTasksManagerModel model;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void update(final int id, final int position, DlTasksManagerModel model) {
            this.id = id;
            this.position = position;
            this.model = model;
        }

        void updateDownloaded() {
            //当下载完成后，隐藏圆环控件，显示删除图标
            ivDownload.setBackgroundResource(R.drawable.icon_cache_delete);
            tvState.setText("下载完成");
            ivDownload.setTag(R.drawable.icon_cache_delete);

            pb.setMax(1);
            pb.setProgress(1);

            //TODO 当下载完成以后，就把下载完的数据保存起来
            DlCacheTasksManager.getImpl().deleteTasksManagerModel(model.getUrl());
            if (!DlCacheTasksManager.getImpl().isExistDownloadFile(model.getUrl())) {
                DlCacheTasksManager.getImpl().addDownloaded(model);
                ToastUtil.showToast(mContext, "添加下载完成数据成功");
            }
        }

        @SuppressLint("DefaultLocale")
        void updateNotDownloaded(int status, long sofar, long total) {
            if (sofar > 0 && total > 0) {
                final float percent = sofar / (float) total;
                pb.setMax(100);
                pb.setProgress((int) (percent * 100));
            } else {
                pb.setMax(1);
                pb.setProgress(0);
            }
            switch (status) {
                case FileDownloadStatus.error:
                    ivDownload.setBackgroundResource(R.drawable.ic_player_start);
                    tvState.setText("错误");
                    break;
                case FileDownloadStatus.paused:
                    //tvVideoSize.setText(String.format("%dMB", total / 1024 / 1024));
                    ivDownload.setBackgroundResource(R.drawable.ic_player_start);
                    tvState.setText("暂停");
                    break;
                default:
                    ivDownload.setBackgroundResource(R.drawable.ic_player_start);
                    break;
            }
            ivDownload.setTag(R.drawable.ic_player_start);
        }


        void updateDownloading(int status, long sofar, long total) {
            final float percent = sofar / (float) total;
            switch (status) {
                case FileDownloadStatus.pending:
                    tvState.setText("排队中");
                    ivDownload.setBackgroundResource(R.drawable.ic_player_start);
                    break;
                case FileDownloadStatus.started:
                    tvState.setText("开始下载");
                    ivDownload.setBackgroundResource(R.drawable.ic_player_start);
                    break;
                case FileDownloadStatus.connected:
                    tvState.setText("链接中");
                    ivDownload.setBackgroundResource(R.drawable.ic_player_start);
                    break;
                case FileDownloadStatus.progress:
                    tvState.setText("下载中");
                    ivDownload.setBackgroundResource(R.drawable.ic_player_pause);
                    break;
                default:
                    ivDownload.setBackgroundResource(R.drawable.ic_player_pause);
                    break;
            }
            pb.setMax(100);
            pb.setProgress((int) (percent * 100));
            ivDownload.setTag(R.drawable.ic_player_pause);
        }
    }


    /**
     * 处理点击事件
     */
    private View.OnClickListener taskActionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() == null) {
                return;
            }

            ViewHolder holder = (ViewHolder) v.getTag();
            int res = (int) holder.ivDownload.getTag();
            int play = R.drawable.ic_player_pause;
            int delete = R.drawable.icon_cache_delete;
            int download = R.drawable.ic_player_start;

            DlTasksManagerModel model = mList.get(holder.position);
            if (res == play) {
                //暂停
                ToastUtil.showToast(mContext, "暂停");
                FileDownloader.getImpl().pause(holder.id);
            } else if (res == download) {
                //下载
                ToastUtil.showToast(mContext, "下载");
                //获取下载链接
                String url = model.getUrl();
                //获取路径
                String path = DlCacheTasksManager.getImpl().createPath(url);
                final BaseDownloadTask task = FileDownloader.getImpl().create(url)
                        .setPath(path)
                        .setCallbackProgressTimes(500)
                        .setListener(taskListener);
                DlCacheTasksManager.getImpl().addTaskForViewHolder(task);
                //将下载中的任务添加到缓冲数据库中，方便其他地方调用
                DlCacheTasksManager.getImpl().addTask(model.getUrl());
                DlCacheTasksManager.getImpl().updateViewHolder(holder.id, holder);
                task.start();
            } else if (res == delete) {
                //删除
                ToastUtil.showToast(mContext, "删除");
                String path = DlCacheTasksManager.getImpl().createPath(model.getUrl());
                if (path != null) {
                    //删除
                    //noinspection ResultOfMethodCallIgnored
                    new File(path).delete();
                } else {
                    ToastUtil.showToast(mContext, "删除路径不能为空");
                }
                holder.updateNotDownloaded(FileDownloadStatus.INVALID_STATUS, 0, 0);
                //不仅要删除文件，还要移除已经完成下载数据中的数据
                DlCacheTasksManager.getImpl().removeDownloaded(model.getUrl());
            }
        }
    };


    /**
     * 这个是用来监听下载的情况[没问题]
     * 1.下载队列中
     * 2.开始下载
     * 3.连接中
     * 4.下载中
     * 5.下载错误
     * 6.暂停
     * 7.完成
     */
    private FileDownloadListener taskDownloadListener = new FileDownloadSampleListener() {

        private ViewHolder checkCurrentHolder(final BaseDownloadTask task) {
            final ViewHolder tag = (ViewHolder) task.getTag();
            if (tag.id == 0) {
                ToastUtil.showToast(mContext, "id值不对");
                return null;
            }
            if (tag.id != task.getId()) {
                return null;
            }
            return tag;
        }

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.pending(task, soFarBytes, totalBytes);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateDownloading(FileDownloadStatus.pending, soFarBytes, totalBytes);
            tag.tvState.setText("队列中");
        }

        @Override
        protected void started(BaseDownloadTask task) {
            super.started(task);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.tvState.setText("状态: 开始下载");
        }

        @Override
        protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
            super.connected(task, etag, isContinue, soFarBytes, totalBytes);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateDownloading(FileDownloadStatus.connected, soFarBytes, totalBytes);
            tag.tvState.setText("状态: 连接中");
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.progress(task, soFarBytes, totalBytes);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateDownloading(FileDownloadStatus.progress, soFarBytes, totalBytes);
            tag.tvState.setText("状态: 下载中");
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            super.error(task, e);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes()
                    , task.getLargeFileTotalBytes());
            tag.tvState.setText("状态: 错误");
            DlCacheTasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.paused(task, soFarBytes, totalBytes);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes);
            tag.tvState.setText("状态: 暂停");
            DlCacheTasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            super.completed(task);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateDownloaded();
            tag.tvState.setText("状态: 完成");
            DlCacheTasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }
    };


    /**
     * 原生demo案例代码
     */
    private FileDownloadListener taskListener = new FileDownloadSampleListener() {
        private ViewHolder checkCurrentHolder(final BaseDownloadTask task) {
            final ViewHolder tag = (ViewHolder) task.getTag();
            /*if (tag.id != task.getId()) {
                return null;
            }*/
            return tag;
        }

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.pending(task, soFarBytes, totalBytes);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateDownloading(FileDownloadStatus.pending, soFarBytes, totalBytes);
            tag.tvState.setText(R.string.tasks_manager_demo_status_pending);
        }

        @Override
        protected void started(BaseDownloadTask task) {
            super.started(task);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.tvState.setText(R.string.tasks_manager_demo_status_started);
        }

        @Override
        protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
            super.connected(task, etag, isContinue, soFarBytes, totalBytes);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateDownloading(FileDownloadStatus.connected, soFarBytes, totalBytes);
            tag.tvState.setText(R.string.tasks_manager_demo_status_connected);
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.progress(task, soFarBytes, totalBytes);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateDownloading(FileDownloadStatus.progress, soFarBytes, totalBytes);
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            super.error(task, e);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes()
                    , task.getLargeFileTotalBytes());
            TasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.paused(task, soFarBytes, totalBytes);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes);
            tag.tvState.setText(R.string.tasks_manager_demo_status_paused);
            TasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            super.completed(task);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateDownloaded();
            TasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }
    };


    private CircleProgressbar.OnCountdownProgressListener progressListener
            = new CircleProgressbar.OnCountdownProgressListener() {
        @Override
        public void onProgress(int what, int progress) {
            if (what == 1) {

            }
        }
    };


    public void setOnItemClickListener(OnListItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnSwipeMenuListener listener;

    public void setOnSwipeMenuListener(OnSwipeMenuListener listener) {
        this.listener = listener;
    }
}
