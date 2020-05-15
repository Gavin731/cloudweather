package com.lnkj.weather.utils

import android.content.Context
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import java.io.File


/**
 * @ClassName: AndroidUtil
 * @Description:下载工具类
 * @Author: Pekon
 * @CreateDate: 2020/5/13 17:08
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/5/13 17:08
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class DownloadUtil(context: Context) : FileDownloadListener() {

    companion object {
        const val DOWNLOAD_SUCCESS = 0
        const val DOWNLOAD_ERROR = 1
    }

    private val context: Context = context
    private var listener: DownloadFileListener? = null

    /**
     * 保存apk路径
     */
    fun getApkCachePath(): String {
        return context.externalCacheDir.toString() + "/apk/"
    }

    fun setDownloadListener(downloadListener: DownloadFileListener) {
        this.listener = downloadListener
    }

    fun download(url: String?, localPath: String?) {
        val file = File(localPath)
        if (file.exists()) {
            this.listener!!.downloadCompleted();
            return
        }
        FileDownloader.setup(context)
        FileDownloader.getImpl().create(url).setPath(localPath)
            .setForceReDownload(true)
            .setListener(this).start()
    }

    /**
     * 结果处理
     *
     * @param state 状态
     * @param e
     */
    private fun returnState(state: Int, e: Throwable?) {
        if (listener == null) {
            return
        }
        if (state == DOWNLOAD_SUCCESS) {
            listener!!.downloadCompleted()
        } else {
            listener!!.downloadError(e)
        }
    }

    override fun warn(task: BaseDownloadTask?) {
    }

    override fun completed(task: BaseDownloadTask?) {
        returnState(DOWNLOAD_SUCCESS, null);
    }

    override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
    }

    override fun error(task: BaseDownloadTask?, e: Throwable?) {
        returnState(DOWNLOAD_ERROR, e);
    }

    override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
    }

    override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
    }

    interface DownloadFileListener {
        fun downloadCompleted()
        fun downloadError(e: Throwable?)
    }

}