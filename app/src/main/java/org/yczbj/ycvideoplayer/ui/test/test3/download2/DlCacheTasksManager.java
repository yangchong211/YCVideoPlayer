package org.yczbj.ycvideoplayer.ui.test.test3.download2;


import android.text.TextUtils;
import android.util.SparseArray;

import com.blankj.utilcode.util.ToastUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;


import org.yczbj.ycvideoplayer.ui.person.adapter.MeCacheAdapter;

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
 *
 *
 *          思考一下，如果多个地方下载，关于这个引用可以传不同类型，那么task可不可以使用泛型，该如何使用？？？
 * ================================================
 */
public class DlCacheTasksManager {

    /**
     * 数据库控制器
     */
    private DlTasksManagerDBController dbController;
    /**
     * 正在下载的
     */
    private List<DlTasksManagerModel> modelList;
    /**
     * 下载完成的
     */
    private List<DlTasksManagerModel> downloadModelList;
    /**
     * 集合
     */
    private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();
    /**
     * 下载连接监听器
     */
    private FileDownloadConnectListener listener;


    private final static class HolderClass {
        private final static DlCacheTasksManager INSTANCE = new DlCacheTasksManager();
    }

    public static DlCacheTasksManager getImpl() {
        return HolderClass.INSTANCE;
    }

    private DlCacheTasksManager() {
        dbController = new DlTasksManagerDBController();
        modelList = dbController.getAllTasks();
        downloadModelList = dbController.getAllDownloadedList();
    }


    /**---------------------------------task增删改查---------------------------------------------*/

    /**
     * 添加
     * @param task
     */
    public void addTaskForViewHolder(final BaseDownloadTask task) {
        taskSparseArray.put(task.getId(), task);
    }

    /**
     * 移除
     * @param id
     */
    public void removeTaskForViewHolder(final int id) {
        taskSparseArray.remove(id);
    }

    /**
     * 更新
     * @param id
     * @param holder
     */
    public void updateViewHolder(final int id, final MeCacheAdapter.ViewHolder holder) {
        final BaseDownloadTask task = taskSparseArray.get(id);
        if (task == null) {
            return;
        }
        task.setTag(holder);
    }

    /**
     * 清除所有
     * 一般在onDestroy方法执行
     */
    private void releaseTask() {
        taskSparseArray.clear();
    }


    /**---------------------------服务连接监听器注册和注销--------------------------------------*/


    /**
     * 注册服务连接监听器
     * @param weakReference             软引用集合
     */
    private void registerServiceConnectionListener(final WeakReference<MeCacheAdapter> weakReference) {
        if (listener != null) {
            FileDownloader.getImpl().removeServiceConnectListener(listener);
        }

        listener = new FileDownloadConnectListener() {
            /**
             * 连接
             */
            @Override
            public void connected() {
                if (weakReference == null || weakReference.get() == null) {
                    return;
                }
                weakReference.get().postNotifyDataChanged();
            }

            /**
             * 没有连接上
             */
            @Override
            public void disconnected() {
                if (weakReference == null || weakReference.get() == null) {
                    return;
                }
                weakReference.get().postNotifyDataChanged();
            }
        };
        FileDownloader.getImpl().addServiceConnectListener(listener);
    }

    /**
     * 注销服务连接监听器
     * 一般在onDestroy方法执行
     */
    private void unregisterServiceConnectionListener() {
        FileDownloader.getImpl().removeServiceConnectListener(listener);
        listener = null;
    }


    /**---------------------------生命周期方法调用--------------------------------------*/



    /**
     * 创建时执行
     * @param weakReference         软引用集合对象
     */
    public void onCreate(final WeakReference<MeCacheAdapter> weakReference) {
        if (!FileDownloader.getImpl().isServiceConnected()) {
            FileDownloader.getImpl().bindService();
            registerServiceConnectionListener(weakReference);
        }
    }

    /**
     * 销毁时执行
     */
    public void onDestroy() {
        unregisterServiceConnectionListener();
        releaseTask();
    }

    /**---------------------------其他方法--------------------------------------*/

    /**
     * 判断服务是否连接上
     */
    public boolean isReady() {
        return FileDownloader.getImpl().isServiceConnected();
    }

    /**
     * 获取集合中索引为position的数据
     */
    public DlTasksManagerModel get(final int position) {
        if(modelList!=null){
            return modelList.get(position);
        }
        return null;
    }

    /**
     * 获取id，比较model中id和下载id是否相同，如果相同则返回model，如果不同则返回null
     */
    public DlTasksManagerModel getById(final int id) {
        for (DlTasksManagerModel model : modelList) {
            if (model.getId() == id) {
                return model;
            }
        }
        return null;
    }

    /**
     * 获取下载的状态是否下载完成
     */
    public boolean isDownloaded(final int status) {
        return status == FileDownloadStatus.completed;
    }

    /**
     * 获取下载的状态
     */
    public int getStatus(final int id, String path) {
        return FileDownloader.getImpl().getStatus(id, path);
    }

    /**
     * 使用{code id}获取任务目标文件的总字节。
     */
    public long getTotal(final int id) {
        return FileDownloader.getImpl().getTotal(id);
    }

    /**
     * 下载到目前为止下载的字节。。
     */
    public long getSoFar(final int id) {
        return FileDownloader.getImpl().getSoFar(id);
    }

    /**
     * 获取下载任务的总数量
     */
    public int getTaskCounts() {
        return modelList.size();
    }

    /**
     * 添加task
     * @param url               注意这个是下载链接地址
     */
    public DlTasksManagerModel addTask(final String url) {
        return addTask(url, createPath(url));
    }

    /**
     * 获取创建下载文件路劲
     * @param url           下载地址
     * @return              下载路径
     */
    public String createPath(final String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return FileDownloadUtils.getDefaultSaveFilePath(url);
    }


    /**
     * 添加task
     * @param url               注意这个是下载链接地址
     * @param path              注意这个是下载路径
     */
    private DlTasksManagerModel addTask(final String url, final String path) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return null;
        }
        final int id = FileDownloadUtils.generateId(url, path);
        DlTasksManagerModel model = getById(id);
        if (model != null) {
            return model;
        }
        final DlTasksManagerModel newModel = dbController.addTask(url, path);
        if (newModel != null) {
            modelList.add(newModel);
        }
        return newModel;
    }


    /**--------------------------------------------------------------------------------------**/
    /**----------------------------------下面是自己添加----------------------------------------**/



    /**
     * 获取下载文件的id
     * @param url
     * @return
     */
    public int getId(String url) {
        if (TextUtils.isEmpty(url)){
            return -1 ;
        }
        return FileDownloadUtils.generateId(url, createPath(url));
    }


    /**
     * 获取正在下载的内容
     * @return
     */
    public List<DlTasksManagerModel> getModelList(){
        return modelList;
    }


    /**
     * 获取下载完成的内容
     * @return
     */
    public List<DlTasksManagerModel> getDownloadModelList(){
        return downloadModelList;
    }


    /**
     * 删除，移除正在下载数据库中的数据
     * @param url
     */
    public void deleteTasksManagerModel(String url) {
        if (TextUtils.isEmpty(url)) {
            return ;
        }
        if(modelList.size()<=0){
            return;
        }
        for (int i = 0; i < modelList.size(); i++) {
            //如果删除的url和model中存储的url相同，则移除
            if (url.equals(modelList.get(i).getUrl())){
                modelList.remove(i);
                break;
            }
        }
        boolean b = dbController.removeTasks(url);
        if(b){
            ToastUtils.showShort("移除正在下载的数据成功");
        }
    }


    /**
     * 将下载完成的添加到已经完成数据库
     * 思考：如何避免重复的添加数据，一定要去重数据
     * 比较id是否相同，如果相同，则去重
     */
    public void addDownloaded(DlTasksManagerModel model){
        String url = model.getPath();
        String path = DlCacheTasksManager.getImpl().createPath(url);
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return ;
        }
        final int id = FileDownloadUtils.generateId(url, path);
        /*if(dlId==id){
            ToastUtils.showShort("已经添加了该下载完成的数据");
            return;
        }*/
        DlTasksManagerModel bean = new DlTasksManagerModel();
        bean.setId(id);
        bean.setName(url.substring(url.lastIndexOf("/")+1));
        bean.setUrl(url);
        bean.setPath(path);
        bean.setType(2);
        downloadModelList.add(model);
        dbController.addDownloaded(model);
    }


    /**
     * 从已经下载的数据库中移除某一条数据
     * @param url
     */
    public void removeDownloaded(String  url){
        for (int i = 0; i < downloadModelList.size(); i++) {
            DlTasksManagerModel model = downloadModelList.get(i);
            if (model.getUrl().equals(url)){
                downloadModelList.remove(i);
                break;
            }
        }
        dbController.removeDownloaded(url);
    }


    /**
     * 判断是否有下载文件
     * @param url       下载链接
     * @return
     */
    public boolean isExistDownloadFile(String url){
        return dbController.isExistDownloadFile(url);
    }

}
