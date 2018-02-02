package org.yczbj.ycvideoplayer.download;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import org.yczbj.ycvideoplayer.ui.home.view.adapter.DownloadVideoAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/1/9
 * 描    述：下载Tasks帮助类
 * 修订历史：
 *          下载框架：https://github.com/lingochamp/FileDownloader
 * ================================================
 */
public class TasksManager {

    /**数据库控制器*/
    private TasksManagerDBController dbController;
    private List<TasksManagerModel> modelList;
    private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();
    /**下载连接监听器*/
    private FileDownloadConnectListener listener;


    private final static class HolderClass {
        private final static TasksManager INSTANCE = new TasksManager();
    }

    public static TasksManager getImpl() {
        return HolderClass.INSTANCE;
    }

    private TasksManager() {
        dbController = new TasksManagerDBController();
        modelList = dbController.getAllTasks();
        initListData();
    }

    /**
     * 这一步很重要，将数据添加到下载队列中
     */
    private void initListData() {

    }

    /**
     * 创建时执行
     * @param activityWeakReference         软引用集合对象
     */
    public void onCreate(final WeakReference<DownloadVideoAdapter> activityWeakReference) {
        if (!FileDownloader.getImpl().isServiceConnected()) {
            FileDownloader.getImpl().bindService();
            registerServiceConnectionListener(activityWeakReference);
        }
    }

    /**
     * 销毁时执行
     */
    public void onDestroy() {
        unregisterServiceConnectionListener();
        releaseTask();
    }


    public void addTaskForViewHolder(final BaseDownloadTask task) {
        taskSparseArray.put(task.getId(), task);
    }

    public void removeTaskForViewHolder(final int id) {
        taskSparseArray.remove(id);
    }

    public void updateViewHolder(final int id, final TaskViewHolderImp holder) {
        final BaseDownloadTask task = taskSparseArray.get(id);
        if (task == null) {
            return;
        }
        task.setTag(holder);
    }

    private void releaseTask() {
        taskSparseArray.clear();
    }


    /**
     * 注册服务连接监听器
     * @param activityWeakReference             软引用集合
     */
    private void registerServiceConnectionListener(final WeakReference<DownloadVideoAdapter> activityWeakReference) {
        if (listener != null) {
            FileDownloader.getImpl().removeServiceConnectListener(listener);
        }

        listener = new FileDownloadConnectListener() {
            @Override
            public void connected() {
                if (activityWeakReference == null
                        || activityWeakReference.get() == null) {
                    return;
                }
                activityWeakReference.get().postNotifyDataChanged();
            }

            @Override
            public void disconnected() {
                if (activityWeakReference == null
                        || activityWeakReference.get() == null) {
                    return;
                }
                activityWeakReference.get().postNotifyDataChanged();
            }
        };
        FileDownloader.getImpl().addServiceConnectListener(listener);
    }

    /**
     * 移除服务连接监听器
     */
    private void unregisterServiceConnectionListener() {
        FileDownloader.getImpl().removeServiceConnectListener(listener);
        listener = null;
    }


    public boolean isReady() {
        return FileDownloader.getImpl().isServiceConnected();
    }


    public TasksManagerModel get(final int position) {
        if(modelList!=null){
            return modelList.get(position);
        }
        return null;
    }


    private TasksManagerModel getById(final int id) {
        for (TasksManagerModel model : modelList) {
            if (model.getId() == id) {
                return model;
            }
        }
        return null;
    }

    public int getId(String url) {
        if (TextUtils.isEmpty(url)){
            return -1 ;
        }
        return FileDownloadUtils.generateId(url, createPath(url));
    }


    /**
     * @param status Download Status
     * @return has already downloaded
     * @see FileDownloadStatus
     */
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

    public TasksManagerModel addTask(final String url) {
        return addTask(url, createPath(url));
    }

    private TasksManagerModel addTask(final String url, final String path) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return null;
        }
        final int id = FileDownloadUtils.generateId(url, path);
        TasksManagerModel model = getById(id);
        if (model != null) {
            return model;
        }
        final TasksManagerModel newModel = dbController.addTask(url, path);
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

}
