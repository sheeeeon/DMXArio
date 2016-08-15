package com.icaynia.dmxario;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by icaynia on 16. 7. 13..
 */
public class Project {
    /* */private String dirPath = "/sdcard/DMXArio";

    /* 현재 시간 */
    long now = System.currentTimeMillis();
    Date date = new Date(now);

    SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String strCurDate = CurDateFormat.format(date);

    /* 변수 선언과 동시에 초기값 설정 */
    private String name = "Untitled Name";      // 프로젝트 이름
    private String regDate = strCurDate;        // 프로젝트 작성일
    private int editCount = 0;                  // 프로젝트 수정 회수
    private String lastEdit = strCurDate;       // 프로젝트 최종수정일
    private String makerInfo = "-";              // 프로젝트 만든 유저
    private String note = "초기값";               // 프로젝트 주석(설명)
    private int trackCount = 1;                 // 프로젝트 트랙 개수
    private String musicFile = "초기값";          // 프로젝트 참고 음악파일

    ArrayList<Track> mTrack = new ArrayList<Track>();


    public Project() {
    }
    public Project(String _name) {
        this.name = _name;
    }

    public void loadProject(String str) {
        //파일이름과 함께 이 프로젝트가 로드되면 다 바뀜. 다 바껴.
        String[] resources = str.split("&");
        //Log.e("e", resources[1]); --- OK


        String[] ProjectSet = resources[1].split(",");
        int i;
        for (i = 1; i < ProjectSet.length; i++) {
            //Log.e("e", ProjectSet[i] + "\n");

            if (ProjectSet[i] == "&TRACK") break;
            String[] ProjectSetValue = ProjectSet[i].split("=");

            //Log.e("e", ProjectSetValue[0] + " = " + ProjectSetValue[1]);

            switch (ProjectSetValue[0]) {
                case "name":
                    this.setName(ProjectSetValue[1]);
                    break;
                case "regDate":
                    this.regDate = ProjectSetValue[1];
                    break;
                case "editCount":
                    this.editCount = Integer.parseInt(ProjectSetValue[1]) + 1;  // 만약 Save에서 카운트를 추가하는 이 코드를 작성한다면 연속 저장 시 count가 무한정으로 늘어날 수 있음.
                    break;
                case "lastEdit":
                    this.lastEdit = ProjectSetValue[1];
                    break;
                case "makerInfo":
                    this.makerInfo = ProjectSetValue[1];
                    break;
                case "note":
                    this.note = ProjectSetValue[1];
                    break;
                case "trackCount":
                    this.trackCount = Integer.parseInt(ProjectSetValue[1]);
                    break;
                case "musicFile":
                    this.musicFile = ProjectSetValue[1];
                    break;
            }
        }

        int j;
        for (j = i; j < ProjectSet.length; j++) {
            //TRACK 관련.
            if(ProjectSet[j] == "&OBJECT") break;




        }
        /*
        int k;


        for (k = j; k < ProjectSet.length; k++) {
            //OBJECT
        }
        */

        //Log.e("e", ProjectSet[1]);
    }


    public void saveProject(String fileName) {

        File file = new File(dirPath);
        if( !file.exists() ) {
            file.mkdirs();
        }

        File savefile = new File(dirPath+"/"+fileName);
        try{
            FileOutputStream fos = new FileOutputStream(savefile);
            fos.write(varToStr().getBytes());
            fos.close();
        } catch(IOException e){}

    }

    public String varToStr() {
        String Str = "";

        Str += "&PROJECT,\n";

        Str += "name=" + this.name + ",\n";
        Str += "regDate=" + this.regDate + ",\n";
        Str += "editCount=" + this.editCount + ",\n";
        Str += "lastEdit=" + strCurDate + ",\n";
        Str += "makerInfo=" + this.makerInfo + ",\n";
        Str += "note=" + this.note + ",\n";
        Str += "trackCount=" + this.trackCount + ",\n";
        Str += "musicFile=" + this.musicFile + "\n";

        Str += "&TRACK\n";

        for (int i = 0; i < mTrack.size(); i++) {
            Str += "{Track=" + mTrack.get(i).getName()  + ",\n";


            Str += "}; \n";

        }
        Str += "&OBJECT\n";


        return Str;
    }

    public String getName() {
        return this.name;
    }
    public String getRegDate() {
        return this.regDate;
    }
    public int getEditCount() {
        return this.editCount;
    }
    public String getLastEdit() {
        return this.lastEdit;
    }
    public String getMakerInfo() {
        return this.makerInfo;
    }
    public String getNote() {
        return this.note;
    }
    public int getTrackCount() {
        return this.trackCount;
    }
    public String getMusicFile() {
        return this.musicFile;
    }

    public void setName(String _name) {
        this.name = _name;
    }
    public void setMakerInfo(String _makerInfo) {
        this.makerInfo = _makerInfo;
    }
    public void setTrackCount(int _trackCount) {
        this.trackCount = _trackCount;
    }

    //


    public String[] getFileList(String strPath) {
        // 폴더 경로를 지정해서 File 객체 생성
        File fileRoot = new File(strPath);
        // 해당 경로가 폴더가 아니라면 함수 탈출
        if( fileRoot.isDirectory() == false ) return null;
        String[] fileList = fileRoot.list();
        String str = "";
        for (int i = 0; i < fileList.length; i++) {
            fileList[i] = fileList[i]+"";
        }

        return fileList;
    }

    public void getProjectInfo() {
        Log.e("ww", varToStr());
    }

    public void addTrack(int pos){
        mTrack.add(pos,new Track("new Track"));
    }

    class Track {
        private String name = "";
        private String id = "초기값";
        private int startChannel = 1;
        private String light = "moving head";
        private String trackColor = "#FFFFFF";
        private boolean mute = false;
        private boolean solo = false;
        private String tracknote = "초기값";

        public Track(String _name) {
            this.name = _name;
        }

        public void strToVar(String str) {
            Log.e("e", "e");
        }

        public String getName() {
            return this.name;
        }
        public String getId() {
            return this.id;
        }
        public int getStartChannel() {
            return this.startChannel;
        }
        public String getLight() {
            return this.light;
        }
        public String getTrackColor() {
            return this.trackColor;
        }
        public boolean getMute() {
            return this.mute;
        }
        public boolean getSolo() {
            return this.solo;
        }
        public String getTrackNote() {
            return this.tracknote;
        }

        public void setName(String _name) {
            this.name = _name;
        }
        public void setStartChannel(int _startChannel) {
            this.startChannel = _startChannel;
        }
        public void setLight(String _light) {
            this.light = _light;
        }
        public void setTrackColor(String _trackColor) {
            this.trackColor = _trackColor;
        }
        public void setMute(boolean _mute) {
            this.mute = _mute;
        }
        public void setSolo(boolean _solo) {
            this.solo = _solo;
        }
        public void setTrackNote(String _trackNote) {
            this.tracknote = _trackNote;
        }

    }

}



class Object {
    private String name;
    private String langth;
    private val[] data;
}

class val {
    private int channel;
    private int val;
}
