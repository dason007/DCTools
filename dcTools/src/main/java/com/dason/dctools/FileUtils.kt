package com.dason.dctools

import android.content.Context
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.Base64
import androidx.core.content.FileProvider
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.io.RandomAccessFile
import java.math.BigInteger
import java.nio.channels.FileChannel
import java.security.MessageDigest
import java.text.DecimalFormat

object FileUtils {
    //定义GB的计算常量
    const val GB: Long = (1024 * 1024 * 1024).toLong()

    //定义MB的计算常量
    const val MB: Long = (1024 * 1024).toLong()

    //定义KB的计算常量
    const val KB: Long = 1024

    @JvmStatic
    fun isFileExist(filePath: String?): Boolean {
        if (TextUtils.isEmpty(filePath)) {
            return false
        }
        return File(filePath!!).exists()
    }

    @JvmStatic
    fun createFolder(filePath: String?): Boolean {
        if (TextUtils.isEmpty(filePath)) {
            return false
        }
        val folder = File(filePath!!)
        return if (folder.exists()) {
            true
        } else {
            folder.mkdirs()
        }
    }

    /**
     * 根据文件路径获取文件名
     */
    @JvmStatic
    fun getFileName(filePath: String): String? {
        if (TextUtils.isEmpty(filePath)) {
            return null
        }
        val filePosition = filePath.lastIndexOf(File.separator)
        return if ((filePosition == -1)) filePath else filePath.substring(filePosition + 1)
    }

    @JvmStatic
    fun getFileNameWithoutExtension(filePath: String): String? {
        if (TextUtils.isEmpty(filePath)) {
            return null
        }
        val extenPosi = filePath.lastIndexOf(".")
        val filePosi = filePath.lastIndexOf(File.separator)
        if (filePosi == -1) {
            return (if (extenPosi == -1) filePath else filePath.substring(0, extenPosi))
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1)
        }
        return (if (filePosi < extenPosi) filePath.substring(
            filePosi + 1,
            extenPosi
        ) else filePath.substring(filePosi + 1))
    }

    @JvmStatic
    fun getFileExtension(filePath: String): String? {
        if (TextUtils.isEmpty(filePath)) {
            return null
        }
        val extenPosi = filePath.lastIndexOf(".")
        val filePosi = filePath.lastIndexOf(File.separator)
        if (extenPosi == -1) {
            return ""
        }
        return if ((filePosi >= extenPosi)) "" else filePath.substring(extenPosi + 1)
    }

    @JvmStatic
    fun getFileParentDirectory(filepath: String?): String? {
        return if (TextUtils.isEmpty(filepath)) {
            null
        } else {
            getFileParentDirectory(File(filepath!!))
        }
    }

    @JvmStatic
    fun getFileParentDirectory(file: File): String? {
        if (EmptyUtils.isEmpty(file)) {
            return null
        } else {
            val parent = file.parentFile
            return if (EmptyUtils.isEmpty(parent)) {
                null
            } else {
                parent?.name
            }
        }
    }

    @JvmStatic
    fun getFileSize(filepath: String?): Long {
        if (TextUtils.isEmpty(filepath)) {
            return 0
        }
        return getFileSize(File(filepath!!))
    }

    @JvmStatic
    fun getFileSize(file: File?): Long {
        if (null == file) {
            return 0
        }
        return (if ((file.exists() && file.isFile)) file.length() else 0)
    }

    @JvmStatic
    fun formatFileSize(fileS: Long): String { // 转换文件大小
        val df = DecimalFormat("#0.00")
        var unit = "B"
        var unitLength = fileS.toDouble()
        if (fileS / GB >= 1) { //如果当前Byte的值大于等于1GB
            unit = "GB"
            unitLength = 1.0 * fileS / GB
        } else if (fileS / MB >= 1) { //如果当前Byte的值大于等于1MB
            if (fileS / MB >= 1000) {
                unit = "GB"
                unitLength = 1.0 * fileS / GB
            } else {
                unit = "MB"
                unitLength = 1.0 * fileS / MB
            }
        } else if (fileS / KB >= 1) { //如果当前Byte的值大于等于1KB
            if (fileS / KB >= 1000) {
                unit = "MB"
                unitLength = 1.0 * fileS / MB
            } else {
                unit = "KB"
                unitLength = (1.0f * fileS / KB).toDouble()
            }
        } else {
            if (fileS >= 1000) {
                unit = "KB"
                unitLength = (1.0f * fileS / KB).toDouble()
            }
        }
        return (df.format(unitLength) + unit)
    }

    @JvmStatic
    fun renameFile(filePath: String?, newName: String?): Boolean {
        if (EmptyUtils.isEmpty(filePath) || EmptyUtils.isEmpty(newName)) {
            return false
        }
        val file = File(filePath!!)
        return file.exists() && file.renameTo(File(newName!!))
    }

    @JvmStatic
    fun deleteFile(filename: String?) {
        if (!TextUtils.isEmpty(filename)) {
            deleteFile(File(filename!!))
        }
    }

    @JvmStatic
    fun deleteFile(file: File?) {
        if (null != file && file.exists()) {
            file.delete()
        }
    }

    @JvmStatic
    fun clearDirectory(path: String?) {
        if (TextUtils.isEmpty(path)) {
            return
        }
        clearDirectory(File(path!!))
    }

    @JvmStatic
    fun clearDirectory(file: File) {
        if (file.exists() && file.isDirectory) {
            val files = file.listFiles()
            if (EmptyUtils.isNotEmpty(files)) {
                for (item in files!!) {
                    deleteFile(item)
                }
            }
        }
    }

    @JvmStatic
    fun deleteDirectory(path: String?): Boolean {
        if (TextUtils.isEmpty(path)) {
            return false
        }
        val directory = File(path!!)
        clearDirectory(directory)
        return directory.delete()
    }

    @JvmStatic
    fun writeFile(path: String?, bytes: ByteArray?, length: Int): Boolean {
        var raf: RandomAccessFile? = null
        try {
            val file = File(path)
            raf = RandomAccessFile(file, "rwd")
            raf.seek(file.length())
            raf.write(bytes, 0, length)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            if (null != raf) {
                try {
                    raf.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    @JvmStatic
    fun insertFile(path: String?, bytes: ByteArray?, pos: Int, length: Int): Boolean {
        var raf: RandomAccessFile? = null
        try {
            val file = File(path)
            raf = RandomAccessFile(file, "rwd")
            raf.seek(pos.toLong())
            raf.write(bytes, 0, length)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            if (null != raf) {
                try {
                    raf.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    @JvmStatic
    fun saveFile(content: String?, path: String?): File? {
        var fwriter: FileWriter? = null
        try {
            fwriter = FileWriter(path)
            fwriter.write(content)
        } catch (ex: IOException) {
            ex.printStackTrace()
        } finally {
            try {
                fwriter!!.flush()
                fwriter.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
        return null
    }

    @JvmStatic
    fun readFile(path: String?): String? {
        var bf: BufferedReader? = null
        try {
            val file = File(path)
            bf = BufferedReader(FileReader(file))
            var content: String? = ""
            val sb = StringBuilder()
            while (content != null) {
                content = bf.readLine()
                if (content == null) {
                    break
                }
                sb.append(content.trim { it <= ' ' })
            }
            return sb.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (bf != null) {
                try {
                    bf.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }

    @JvmStatic
    fun copyFile(sourcePath: String?, destPath: String?): Boolean {
        if (TextUtils.isEmpty(sourcePath) || TextUtils.isEmpty(destPath)) {
            return false
        }
        return copyFile(File(sourcePath!!), File(destPath!!))
    }

    @JvmStatic
    fun copyFile(source: File?, dest: File?): Boolean {
        var inputChannel: FileChannel? = null
        var outputChannel: FileChannel? = null
        var inputStream: FileInputStream? = null
        var outputStream: FileOutputStream? = null
        try {
            inputStream = FileInputStream(source)
            outputStream = FileOutputStream(dest)
            inputChannel = inputStream.channel
            outputChannel = outputStream.channel
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size())
            outputStream.fd.sync()
            outputStream.flush()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            try {
                inputChannel?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                outputChannel?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                inputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                outputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @JvmStatic
    fun file2Base64(filepath: String?, from: Long, length: Long): String? {
        if (TextUtils.isEmpty(filepath)) {
            return ""
        }
        return file2Base64(File(filepath!!), from, length)
    }

    @JvmStatic
    fun file2Base64(file: File?, from: Long, length: Long): String? {
        var le = length
        if (null == file || !file.exists() || !file.isFile || file.length() == 0L) {
            return null
        }
        var inputFile: FileInputStream? = null
        try {
            inputFile = FileInputStream(file)
            inputFile.skip(from)
            if (length == 0L) {
                le = file.length() - from
            }
            val buffer = ByteArray(le.toInt())
            inputFile.read(buffer)
            return Base64.encodeToString(buffer, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputFile?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }
    @JvmStatic
    fun file2MD5(filepath: String?): String? {
        if (TextUtils.isEmpty(filepath)) {
            return ""
        }
        return file2MD5(File(filepath!!))
    }
    @JvmStatic
    fun file2MD5(file: File?): String? {
        if (null == file || !file.exists() || !file.isFile || file.length() == 0L) {
            return null
        }
        var len: Int
        var `in`: FileInputStream? = null
        val buffer = ByteArray(2048)
        try {
            val digest = MessageDigest.getInstance("MD5")
            `in` = FileInputStream(file)
            while ((`in`.read(buffer).also { len = it }) != -1) {
                digest.update(buffer, 0, len)
            }
            val bigInt = BigInteger(1, digest.digest())
            return bigInt.toString(16)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            try {
                `in`!!.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 获取文件uri
    fun getFileUri(context: Context, path: String): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(context, context.packageName + ".provider", File(path))
        } else {
            Uri.fromFile(File(path))
        }
    }
}