package com.chen.concise.example01.http.callback;

import com.chen.concise.example01.http.HttpManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by chenyongan
 */
public abstract class FileCallBack extends AbCallBack<File> {
    /**
     * The target file storage folder path
     */
    private String destFileDir;
    /**
     * The target file storage file name
     */
    private String destFileName;


    public FileCallBack(String destFileDir, String destFileName)
    {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    @Override
    public File parseNetworkResponse(Response response, int id) throws Exception {
        return saveFile(response,id);
    }

    @Override
    public abstract void inProgress(float progress, long total, int id);

    /**
     * save file
     * param response
     * param id
     * return
     * @throws IOException
     */
    public File saveFile(Response response,final int id) throws IOException
    {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try
        {
            is = response.body().byteStream();
            final long total = response.body().contentLength();

            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            if(!file.exists()){
                file.delete();
            }
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1)
            {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                HttpManager.getInstance().getDelivery().execute(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        inProgress(finalSum * 1.0f / total,total,id);
                    }
                });
            }
            fos.flush();

            return file;

        } finally
        {
            try
            {
                response.body().close();
                if (is != null) is.close();
            } catch (IOException e)
            {
            }
            try
            {
                if (fos != null) fos.close();
            } catch (IOException e)
            {
            }

        }
    }


}
