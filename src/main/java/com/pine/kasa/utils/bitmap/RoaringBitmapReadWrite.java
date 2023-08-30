package com.pine.kasa.utils.bitmap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.roaringbitmap.RoaringBitmap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pine
 * @date 2020-06-03 18:19.
 * @description TODO --do sth
 */
public class RoaringBitmapReadWrite {
    private static final Logger logger = LoggerFactory.getLogger(RoaringBitmapReadWrite.class);

    public RoaringBitmap read(String path) throws Exception{

        RoaringBitmap r = new RoaringBitmap();
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(path));
            r.deserialize(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("bitmap file fail for read from ={}", path);
            throw e;
        }
        return r;
    }

    public void write(RoaringBitmap r, String path) {

        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(path));
            r.runOptimize();
            r.serialize(out);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("bitmap file fail to write ={}", path);
        }
    }

    public static void main(String[] args) {
//        int size = roaringBitmap.serializedSizeInBytes();
//        ByteBuffer buffer = ByteBuffer.allocate(size);
//        roaringBitmap.serialize(buffer);
//        // 将RoaringBitmap的数据转成字节数组,这样就可以直接存入数据库了,数据库字段类型BLOB
//        byte[] bitmapData = buffer.array();
    }

}
