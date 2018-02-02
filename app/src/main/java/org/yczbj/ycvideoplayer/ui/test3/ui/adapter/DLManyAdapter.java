package org.yczbj.ycvideoplayer.ui.test3.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BaseApplication;
import org.yczbj.ycvideoplayer.download.TasksManager;
import org.yczbj.ycvideoplayer.listener.OnItemLongClickListener;
import org.yczbj.ycvideoplayer.listener.OnListItemClickListener;
import org.yczbj.ycvideoplayer.ui.test3.download.DLTasksManager;
import org.yczbj.ycvideoplayer.ui.test3.download.DLTasksManagerModel;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DLManyAdapter extends RecyclerView.Adapter<DLManyAdapter.MyViewHolder> {

    private List<DLTasksManagerModel> list;
    private Context context;
    private OnListItemClickListener mItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;


    public DLManyAdapter(List<DLTasksManagerModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    public void setOnItemClickListener(OnListItemClickListener listener) {
        this.mItemClickListener = listener;
    }


    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_down_list_view, parent, false);
        return new MyViewHolder(view, mItemClickListener);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        DLTasksManagerModel model = list.get(position);
        DLTasksManager.getImpl().addTask(model.getPath());
        final String path = DLTasksManager.getImpl().createPath(model.getPath());
        int id = DLTasksManager.getImpl().getId(model.getPath());
        holder.update(id, position);


        //如果是设置tvStartOrPause点击事件，则不要setTag具体值，而是holder，，，为什么？？？
        //holder.tvStartOrPause.setTag(R.string.pause);
        holder.tvStartOrPause.setTag(holder);
        holder.tvStartOrPause.setOnClickListener(taskActionOnClickListener);

        //如果是设置llDownload点击事件，则该如何处理？？？
        //holder.llDownload.setOnClickListener(taskActionOnClickListener);

        //注意这两句话的执行顺序，settag一定要在updateViewHolder之前
        //holder.llDownload.setTag(holder);
        DLTasksManager.getImpl().updateViewHolder(holder.id, holder);

        holder.tvTitle.setText(model.getName());
        holder.tvTime.setVisibility(View.INVISIBLE);
        holder.ivDownload.setBackgroundResource(R.drawable.icon_cache_download);

        //设置类型
        holder.circlePb.setProgressType(CircleProgressbar.ProgressType.COUNT);
        //设置圆形的填充颜色
        holder.circlePb.setInCircleColor(context.getResources().getColor(R.color.colorTransparent));
        //设置外部轮廓的颜色
        holder.circlePb.setOutLineColor(context.getResources().getColor(R.color.gray3));
        //设置进度监听
        holder.circlePb.setCountdownProgressListener(1, null);
        //设置外部轮廓的颜色
        holder.circlePb.setOutLineWidth(1);
        //设置进度条线的宽度
        holder.circlePb.setProgressLineWidth(3);
        //设置进度
        holder.circlePb.setProgress(0);

        DLTasksManager.getImpl().updateViewHolder(holder.id, holder);

        if (DLTasksManager.getImpl().isReady()) {
            final int status = DLTasksManager.getImpl().getStatus(model.getId(), path);
            if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
                    status == FileDownloadStatus.connected) {
                // start task, but file not created yet
                holder.updateDownloading(status, DLTasksManager.getImpl().getSoFar(model.getId())
                        , DLTasksManager.getImpl().getTotal(model.getId()));
            } else if (!new File(path).exists() &&
                    !new File(FileDownloadUtils.getTempPath(path)).exists()) {
                // not exist file
                holder.updateNotDownloaded(status, 0, 0);
            } else if (DLTasksManager.getImpl().isDownloaded(status)) {
                // already downloaded and exist
                holder.updateDownloaded();
            } else if (status == FileDownloadStatus.progress) {
                // downloading
                holder.updateDownloading(status, DLTasksManager.getImpl().getSoFar(model.getId())
                        , DLTasksManager.getImpl().getTotal(model.getId()));
            } else {
                // not start
                holder.updateNotDownloaded(status, DLTasksManager.getImpl().getSoFar(model.getId())
                        , DLTasksManager.getImpl().getTotal(model.getId()));
            }
        } else {
            holder.tvDownloadState.setText(R.string.tasks_manager_demo_status_loading);
            //holder.taskActionBtn.setEnabled(false);
        }
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void addAllData(List<DLTasksManagerModel> data) {
        if(list!=null){
            list.clear();
            list.addAll(data);
            notifyDataSetChanged();
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.pb)
        ProgressBar taskPb;
        @Bind(R.id.iv_download)
        ImageView ivDownload;
        @Bind(R.id.circle_pb)
        CircleProgressbar circlePb;
        @Bind(R.id.tv_download_state)
        TextView tvDownloadState;
        @Bind(R.id.tv_video_size)
        TextView tvVideoSize;
        @Bind(R.id.tv_start_or_pause)
        TextView tvStartOrPause;
        @Bind(R.id.ll_download)
        LinearLayout llDownload;


        /**
         * viewHolder position
         */
        private int position;
        /**
         * download id
         */
        private int id;
        private OnListItemClickListener mListener;
        public void update(final int id, final int position) {
            this.id = id;
            this.position = position;
        }

        public void updateDownloaded() {
            taskPb.setMax(1);
            taskPb.setProgress(1);

            circlePb.setProgress(1);
            circlePb.setVisibility(View.GONE);
            tvStartOrPause.setText(R.string.delete);
            tvDownloadState.setText(R.string.tasks_manager_demo_status_completed);
            ivDownload.setBackgroundResource(R.drawable.icon_cache_delete);
        }


        MyViewHolder(View view, OnListItemClickListener listener) {
            super(view);
            ButterKnife.bind(this, itemView);
            //关于回调接口的两种写法，都行
            this.mListener = listener;
            view.setOnClickListener(this);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onLongClick(v, getAdapterPosition());
                    return true;
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getAdapterPosition());
            }
        }

        /**
         * 当下载队列中，连接中，或者下载中，调用该方法
         */
        public void updateDownloading(final int status, final long sofar, final long total) {
            final float percent = sofar / (float) total;
            circlePb.setProgress((int) (percent * 100));
            taskPb.setMax(100);
            taskPb.setProgress((int) (percent * 100));

            switch (status) {
                case FileDownloadStatus.pending:
                    tvDownloadState.setText(R.string.tasks_manager_demo_status_pending);
                    break;
                case FileDownloadStatus.started:
                    tvDownloadState.setText(R.string.tasks_manager_demo_status_started);
                    break;
                case FileDownloadStatus.connected:
                    tvDownloadState.setText(R.string.tasks_manager_demo_status_connected);
                    break;
                case FileDownloadStatus.progress:
                    tvDownloadState.setText(R.string.tasks_manager_demo_status_progress);
                    break;
                default:
                    tvDownloadState.setText(BaseApplication.getInstance().getString(R.string.tasks_manager_demo_status_downloading, status));
                    break;
            }
            tvStartOrPause.setText(R.string.pause);
            ivDownload.setBackgroundResource(R.drawable.icon_cache_play);
        }

        /**
         * 当暂停或者下载出错时，调用这个方法
         */
        public void updateNotDownloaded(final int status, final long sofar, final long total) {
            if (sofar > 0 && total > 0) {
                final float percent = sofar / (float) total;
                circlePb.setProgress((int) (percent * 100));
                taskPb.setMax(100);
                taskPb.setProgress((int) (percent * 100));
            } else {
                circlePb.setProgress(0);
                taskPb.setMax(1);
                taskPb.setProgress(0);
            }

            switch (status) {
                case FileDownloadStatus.error:
                    tvDownloadState.setText(R.string.tasks_manager_demo_status_error);
                    break;
                case FileDownloadStatus.paused:
                    tvDownloadState.setText(R.string.tasks_manager_demo_status_paused);
                    break;
                default:
                    tvDownloadState.setText(R.string.tasks_manager_demo_status_not_downloaded);
                    break;
            }
            tvStartOrPause.setText(R.string.start);
            ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
        }
    }


    private View.OnClickListener taskActionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //注意这个v对应是点击事件那个控件，比如如果是holder.llDownload.setOnClickListener(taskActionOnClickListener);
            //那么由于llDownload控件没有设置setTag，而是holder.tvStartOrPause.setTag(holder);
            //那么llDownload点击事件，v.getTag便会取空值

            //如果是llDownload控件做点击事件，但是tvStartOrPause设置tag，则需要设置
            if (v.getTag() == null) {
                return;
            }
            MyViewHolder holder = (MyViewHolder) v.getTag();
            CharSequence action = ((TextView) v).getText();
            //String action = (String) holder.tvDownloadState.getTag();

            DLTasksManagerModel downManyBean = list.get(holder.position);
            ToastUtil.showToast(context,action.toString());
            if (action.equals(context.getString(R.string.pause))) {
                ToastUtil.showToast(context,"暂停");
                // to pause
                FileDownloader.getImpl().pause(holder.id);
            } else if (action.equals(context.getString(R.string.start))) {
                ToastUtil.showToast(context,"开始");
                // to start
                // to start
                String path = TasksManager.getImpl().createPath(downManyBean.getPath());
                final BaseDownloadTask task = FileDownloader.getImpl().create(downManyBean.getPath())
                        .setPath(path)
                        .setCallbackProgressTimes(100)
                        .setListener(taskDownloadListener);

                /*final DLTasksManagerModel model = DLTasksManager.getImpl().get(holder.position);
                if(model==null){
                    ToastUtil.showToast(context,"值不能为空");
                    return;
                }
                final BaseDownloadTask task = FileDownloader.getImpl().create(model.getUrl())
                        .setPath(path)
                        .setCallbackProgressTimes(100)
                        .setListener(taskDownloadListener);*/

                DLTasksManager.getImpl().addTaskForViewHolder(task);
                DLTasksManager.getImpl().updateViewHolder(holder.id, holder);
                task.start();
            } else if (action.equals(v.getResources().getString(R.string.delete))) {
                // to delete
                //new File(DLTasksManager.getImpl().get(holder.position).getPath()).delete();
                new File(TasksManager.getImpl().createPath(downManyBean.getPath())).delete();
                //holder.taskActionBtn.setEnabled(true);
                holder.updateNotDownloaded(FileDownloadStatus.INVALID_STATUS, 0, 0);
                DLTasksManager.getImpl().removeDownloaded(downManyBean.getPath());
            }
        }
    };


    private FileDownloadListener taskDownloadListener = new FileDownloadSampleListener() {

        private MyViewHolder checkCurrentHolder(final BaseDownloadTask task) {
            final MyViewHolder tag = (MyViewHolder) task.getTag();
            if (tag.id != task.getId()) {
                return null;
            }
            return tag;
        }

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.pending(task, soFarBytes, totalBytes);
            final MyViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateDownloading(FileDownloadStatus.pending, soFarBytes, totalBytes);
            tag.tvDownloadState.setText(R.string.tasks_manager_demo_status_pending);
        }

        @Override
        protected void started(BaseDownloadTask task) {
            super.started(task);
            final MyViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.tvDownloadState.setText(R.string.tasks_manager_demo_status_started);
        }

        @Override
        protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
            super.connected(task, etag, isContinue, soFarBytes, totalBytes);
            final MyViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateDownloading(FileDownloadStatus.connected, soFarBytes, totalBytes);
            tag.tvDownloadState.setText(R.string.tasks_manager_demo_status_connected);
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.progress(task, soFarBytes, totalBytes);
            final MyViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateDownloading(FileDownloadStatus.progress, soFarBytes, totalBytes);
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            super.completed(task);
            final MyViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateDownloaded();
            tag.tvDownloadState.setText(R.string.tasks_manager_demo_status_completed);
            DLTasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.paused(task, soFarBytes, totalBytes);
            final MyViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes);
            tag.tvDownloadState.setText(R.string.tasks_manager_demo_status_paused);
            DLTasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            super.error(task, e);
            final MyViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.tvDownloadState.setText(R.string.tasks_manager_demo_status_error);
            tag.updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes(), task.getLargeFileTotalBytes());
            DLTasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }

        @Override
        protected void warn(BaseDownloadTask task) {
            super.warn(task);
            final MyViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.tvDownloadState.setText(R.string.tasks_manager_demo_status_warn);
        }
    };

}
