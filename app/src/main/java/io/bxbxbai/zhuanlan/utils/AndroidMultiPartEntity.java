package io.bxbxbai.zhuanlan.utils;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Created by xuebin on 15/6/4.
 *
 * @author bxbxbai
 */
@SuppressWarnings("deprecation")
public class AndroidMultiPartEntity extends MultipartEntity{


    private ProgressListener mProgressListener;

    public AndroidMultiPartEntity(ProgressListener progressListener) {
        super();
        mProgressListener = progressListener;
    }

    public AndroidMultiPartEntity(HttpMultipartMode mode, ProgressListener progressListener) {
        super(mode);
        mProgressListener = progressListener;
    }

    public AndroidMultiPartEntity(HttpMultipartMode mode, String boundary, Charset charset, ProgressListener progressListener) {
        super(mode, boundary, charset);
        mProgressListener = progressListener;
    }

    @Override
    public void writeTo(OutputStream outstream) throws IOException {
        super.writeTo(new CountingOutputStream(outstream, mProgressListener));
    }


    /**
     *
     */
    public interface ProgressListener {
        public void transferred(long num);
    }

    /**
     *
     */
    public static class CountingOutputStream extends FilterOutputStream {

        private final ProgressListener mListener;

        private long transferred;

        /**
         * Constructs a new {@code FilterOutputStream} with {@code out} as its
         * target stream.
         *
         * @param out the target stream that this stream writes to.
         */
        public CountingOutputStream(OutputStream out, final ProgressListener listener) {
            super(out);
            mListener = listener;
        }

        @Override
        public void write(byte[] buffer, int offset, int length) throws IOException {
            super.write(buffer, offset, length);
            transferred += length;
            mListener.transferred(transferred);
        }

        @Override
        public void write(int oneByte) throws IOException {
            super.write(oneByte);
            transferred++;
            mListener.transferred(transferred);
        }
    }



}
