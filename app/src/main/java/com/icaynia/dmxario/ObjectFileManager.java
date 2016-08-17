package com.icaynia.dmxario;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created by icaynia on 16/08/18.
 */
public class ObjectFileManager {
    private static final String FILE_NAME = "MEMO.obj";

    Context mContext = null;

    public ObjectFileManager(Context _context) {
        mContext = _context;
    }

    public void save(HashMap<String, String> objData) {
        if (objData == null || objData.isEmpty()) {
            return;

        }

        ObjectOutputStream oos = null;
        FileOutputStream fos = null;

        try{
            fos = mContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);

            oos = new ObjectOutputStream(fos);
            oos.writeObject(objData);
            oos.close();

        } catch (Exception e) { e.printStackTrace(); }

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
