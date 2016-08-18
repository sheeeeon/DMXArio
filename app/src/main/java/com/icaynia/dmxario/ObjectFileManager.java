package com.icaynia.dmxario;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created by icaynia on 16/08/18.
 */
public class ObjectFileManager {

    String dirPath = "/sdcard/DMXArio";
    File file = new File(dirPath);

    private static final String FILE_NAME = "MEMO.obj";

    Context mContext = null;

    public ObjectFileManager(Context _context) {
        mContext = _context;
    }

    public void save(HashMap<String, String> objData) {
        if( !file.exists() ) {
            file.mkdirs();
        }

        if (objData == null || objData.isEmpty()) {
            return;

        }
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("/sdcard/DMXArio/test.txt"));
            oos.writeObject(objData);
            oos.close();
        }catch (Exception e) {}

        Log.e("ObjectFileManager", "완료");

    }

    @SuppressWarnings("unchecked")
    public HashMap<String, String> load() {
        try
        {
            FileInputStream fis = mContext.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);

            HashMap<String, String> memoData = null;
            memoData = (HashMap<String,String >)ois.readObject();

            ois.close();

            return memoData;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete() {
        mContext.deleteFile(FILE_NAME);
    }

}
