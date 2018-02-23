package org.yczbj.ycvideoplayer.ui.test3.download;

import android.text.TextUtils;
import android.util.SparseArray;

import com.blankj.utilcode.util.ToastUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import org.yczbj.ycvideoplayer.api.constant.ConstantVideo;
import org.yczbj.ycvideoplayer.ui.test3.ui.activity.DLManyTestActivity;
import org.yczbj.ycvideoplayer.ui.test3.ui.adapter.DLManyAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by yc on 2018/1/17.
 */

public class DLTasksManager {

    private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();
    private DLTasksManagerDBController dbController;
    private List<DLTasksManagerModel> modelList;

    private final static class HolderClass {
        private final static DLTasksManager INSTANCE = new DLTasksManager();
    }

    public static DLTasksManager getImpl() {
        return HolderClass.INSTANCE;
    }



    private DLTasksManager() {
        dbController = new DLTasksManagerDBController();
        modelList = dbController.getAllTasks();
    }

    private void initDemo() {
        if (modelList.size() <= 0) {
            final int demoSize = ConstantVideo.VideoPlayerList.length;
            for (int i = 0; i < demoSize; i++) {
                final String url = ConstantVideo.VideoPlayerList[i];
                addTask(url);
            }
        }
    }

    public void addTaskForViewHolder(final BaseDownloadTask task) {
        taskSparseArray.put(task.getId(), task);
    }

    public void removeTaskForViewHolder(final int id) {
        taskSparseArray.remove(id);
    }

    public void updateViewHolder(final int id, final DLManyAdapter.MyViewHolder holder) {
        final BaseDownloadTask task = taskSparseArray.get(id);
        if (task == null) {
            return;
        }
        task.setTag(holder);
    }

    public void releaseTask() {
        taskSparseArray.clear();
    }

    private FileDownloadConnectListener listener;

    private void registerServiceConnectionListener(final WeakReference<DLManyTestActivity> activityWeakReference) {
        if (listener != null) {
            FileDownloader.getImpl().removeServiceConnectListener(listener);
        }

        listener = new FileDownloadConnectListener() {

            @Override
            public void connected() {
                if (activityWeakReference == null || activityWeakReference.get() == null) {
                    return;
                }
                activityWeakReference.get().postNotifyDataChanged();
            }

            @Override
            public void disconnected() {
                if (activityWeakReference == null || activityWeakReference.get() == null) {
                    return;
                }
                activityWeakReference.get().postNotifyDataChanged();
            }
        };

        FileDownloader.getImpl().addServiceConnectListener(listener);
    }

    private void unregisterServiceConnectionListener() {
        FileDownloader.getImpl().removeServiceConnectListener(listener);
        listener = null;
    }

    public void onCreate(final WeakReference<DLManyTestActivity> activityWeakReference) {
        if (!FileDownloader.getImpl().isServiceConnected()) {
            FileDownloader.getImpl().bindService();
            registerServiceConnectionListener(activityWeakReference);
        }
    }

    public void onDestroy() {
        unregisterServiceConnectionListener();
        releaseTask();
    }

    public boolean isReady() {
        return FileDownloader.getImpl().isServiceConnected();
    }

    public DLTasksManagerModel get(final int position) {
        if(modelList!=null && modelList.size()>=position){
            return modelList.get(position);
        }
        return null;
    }

    public DLTasksManagerModel getById(final int id) {
        for (DLTasksManagerModel model : modelList) {
            if (model.getId() == id) {
                return model;
            }
        }

        return null;
    }

    public boolean isDownloaded(final int status) {
        return status == FileDownloadStatus.completed;
    }

    public int getStatus(final int id, String path) {
        return FileDownloader.getImpl().getStatus(id, path);
    }

    public long getTotal(final int id) {
        return FileDownloader.getImpl().getTotal(id);
    }

    public long getSoFar(final int id) {
        return FileDownloader.getImpl().getSoFar(id);
    }

    public int getTaskCounts() {
        return modelList.size();
    }

    public DLTasksManagerModel addTask(final String url) {
        return addTask(url, createPath(url));
    }

    public DLTasksManagerModel addTask(final String url, final String path) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return null;
        }

        final int id = FileDownloadUtils.generateId(url, path);
        DLTasksManagerModel model = getById(id);
        if (model != null) {
            return model;
        }
        final DLTasksManagerModel newModel = dbController.addTask(url, path);
        if (newModel != null) {
            modelList.add(newModel);
        }

        return newModel;
    }

    public String createPath(final String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return FileDownloadUtils.getDefaultSaveFilePath(url);
    }

    public int getId(String url) {
        if (TextUtils.isEmpty(url)){
            return -1 ;
        }
        return FileDownloadUtils.generateId(url, createPath(url));
    }


    public void removeDownloaded(String videoUrl){
        if(modelList==null || modelList.size()==0){
            ToastUtils.showShort("删除失败");
            return;
        }
        for (int i = 0; i < modelList.size(); i++) {
            DLTasksManagerModel model = modelList.get(i);
            if (model.getUrl().equals(videoUrl)){
                modelList.remove(i);
                break;
            }
        }
        boolean b = dbController.removeDownloaded(videoUrl);
        if(b){
            ToastUtils.showShort("删除成功");
        }else {
            ToastUtils.showShort("删除失败");
        }
    }


}
