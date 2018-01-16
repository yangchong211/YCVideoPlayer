package org.yczbj.ycvideoplayer.download;


/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/1/9
 * 描    述：下载Tasks的ViewHolder接口
 * 修订历史：
 *          下载框架：https://github.com/lingochamp/FileDownloader
 * ================================================
 */
public interface TaskViewHolderImp {

    void update(final int id, final int position) ;
    void updateDownloaded();
    void updateNotDownloaded(final int status, final long sofar, final long total) ;
    void updateDownloading(final int status, final long sofar, final long total);

}
