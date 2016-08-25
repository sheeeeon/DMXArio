package com.icaynia.dmxario;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by icaynia on 16. 7. 11..
 */
public class SequencerFragment extends Fragment  implements View.OnClickListener {

    int limitTrack = 4;
    GridLayout board;
    GridLayout trackboard;

    Project mProject;
    ListView listview2;
    ArrayAdapter adapter2;

    AlertDialog dialog2;

    TextView trackView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v =  inflater.inflate(R.layout.fragment_sequencer, container, false);

        for (int i = 0; i < limitTrack; i++) {
            trackView = new TextView(getActivity());
        }

        board = (GridLayout) v.findViewById(R.id.board);
        HorizontalScrollView boardLayout = (HorizontalScrollView) v.findViewById(R.id.boardLayout);
        trackboard = (GridLayout) v.findViewById(R.id.ChannelList);
        mProject = new Project();
        mProject.setName("Untitled Name");
        //Log.e("e", mProject.getName());



        ((MainActivity)getActivity()).TabOff();

        ((MainActivity)getActivity()).setTitleText("DMXArio - Sequencer - " + mProject.getName());

        init();


        //mProject.saveProject("untitled project.txt");
        Button saveButton = (Button) v.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        Button projectButton = (Button) v.findViewById(R.id.projectButton);
        projectButton.setOnClickListener(this);

        Button loadButton = (Button) v.findViewById(R.id.loadButton);
        loadButton.setOnClickListener(this);

        Button addTrack = (Button) v.findViewById(R.id.addTrack);
        addTrack.setOnClickListener(this);

        setCommandBlock(1,1);

        setTrack();


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.projectButton:
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView= inflater.inflate(R.layout.dialog_projectset, null);
                //멤버의 세부내역 입력 Dialog 생성 및 보이기
                AlertDialog.Builder buider= new AlertDialog.Builder(getActivity()); //AlertDialog.Builder 객체 생성
                buider.setTitle("프로젝트 설정"); //Dialog 제목
                buider.setIcon(android.R.drawable.ic_menu_add); //제목옆의 아이콘 이미지(원하는 이미지 설정)

                EditText projectName_edit= (EditText) dialogView.findViewById(R.id.projectName_edit);
                projectName_edit.setText(mProject.getName());

                EditText makerInfo_edit= (EditText) dialogView.findViewById(R.id.makerInfo_edit);
                makerInfo_edit.setText(mProject.getMakerInfo());

                EditText trackCount_edit= (EditText) dialogView.findViewById(R.id.trackCount_edit);
                trackCount_edit.setText(mProject.getTrackCount()+"");

                buider.setView(dialogView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)
                buider.setPositiveButton("변경", new DialogInterface.OnClickListener() {
                    //Dialog에 "Complite"라는 타이틀의 버튼을 설정
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        EditText projectName_edit = (EditText)dialogView.findViewById(R.id.projectName_edit);
                        EditText makerInfo_edit   = (EditText)dialogView.findViewById(R.id.makerInfo_edit);
                        EditText trackCount_edit  = (EditText)dialogView.findViewById(R.id.trackCount_edit);

                        String projectNameText  = projectName_edit.getText().toString();
                        String makerInfoText    = makerInfo_edit  .getText().toString();
                        int    TrackCount       = Integer.parseInt(trackCount_edit.getText().toString());

                        mProject.setName(projectNameText);
                        mProject.setMakerInfo(makerInfoText);
                        mProject.setTrackCount(TrackCount);

                        ((MainActivity)getActivity()).setTitleText("DMXArio - Sequencer - " + mProject.getName());
                        Toast.makeText(getActivity(), "프로젝트 설정을 변경하였습니다", Toast.LENGTH_SHORT).show();
                    }
                });
                buider.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getActivity(), "변경 취소", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog=buider.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                break;

            case R.id.saveButton:
                mProject.saveProject(mProject.getName()+".txt");
                Toast.makeText(getActivity(), "저장 완료", Toast.LENGTH_SHORT).show();
                break;

            case R.id.loadButton:

                LayoutInflater inflater2 = getActivity().getLayoutInflater();
                final View dialogView2= inflater2.inflate(R.layout.dialog_projectload, null);
                //멤버의 세부내역 입력 Dialog 생성 및 보이기
                AlertDialog.Builder buider2= new AlertDialog.Builder(getActivity()); //AlertDialog.Builder 객체 생성
                buider2.setTitle("프로젝트 불러오기"); //Dialog 제목
                buider2.setIcon(android.R.drawable.ic_menu_upload); //제목옆의 아이콘 이미지(원하는 이미지 설정)
                // Android에서 제공하는 string 문자열 하나를 출력 가능한 layout으로 어댑터 생성

                final ArrayList<String> items2 = new ArrayList<String>() ;
                // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
                 adapter2 = new ArrayAdapter(dialogView2.getContext(), android.R.layout.simple_list_item_1, items2) ;

                // listview 생성 및 adapter 지정.
                listview2 = (ListView) dialogView2.findViewById(R.id.fileListView);
                listview2.setAdapter(adapter2) ;

                listview2.setOnItemClickListener(listener);
                String[] fileList = mProject.getFileList("/sdcard/DMXArio");
                for (int i = 0; i < fileList.length; i++) {
                    adapter2.add(fileList[i].toString());

                }
                buider2.setView(dialogView2); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)
                buider2.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getActivity(), "취소", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog2=buider2.create();
                dialog2.setCanceledOnTouchOutside(false);
                dialog2.show();



                break;

            case R.id.addTrack:

                if (mProject.mTrack.size() < limitTrack) {
                        addTrack(mProject.mTrack.size());
                } else {
                    //Toast.makeText(getActivity(), "4개 이상의 트랙을 생성할 수 없습니다.", Toast.LENGTH_SHORT);
                }
                break;

        }
    }

    AdapterView.OnItemClickListener listener= new AdapterView.OnItemClickListener() {

        //ListView의 아이템 중 하나가 클릭될 때 호출되는 메소드
        //첫번째 파라미터 : 클릭된 아이템을 보여주고 있는 AdapterView 객체(여기서는 ListView객체)
        //두번째 파라미터 : 클릭된 아이템 뷰
        //세번째 파라미터 : 클릭된 아이템의 위치(ListView이 첫번째 아이템(가장위쪽)부터 차례대로 0,1,2,3.....)
        //네번재 파리미터 : 클릭된 아이템의 아이디(특별한 설정이 없다면 세번째 파라이터인 position과 같은 값)
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO Auto-generated method stub

            //클릭된 아이템의 위치를 이용하여 데이터인 문자열을 Toast로 출력
            //Toast.makeText(getContext(), adapter2.getItem(position)+"", Toast.LENGTH_SHORT).show();

            FileManager mFilemanger = new FileManager();
            String str = mFilemanger.loadFile(adapter2.getItem(position)+"");
            mProject = new Project();
            mProject.loadProject(str);
            dialog2.cancel();
            Refresh();

            //mProject.getProjectInfo();
        }
    };

    public void init() {
        //! 보드 길이 설정
        int t = 1;
        for (int i = 1; i < 101; i++) {
            TextView lengthView = new TextView(getActivity());
            lengthView.setText(i + "");
            lengthView.setTextColor(getResources().getColor(R.color.sequencer_progress_textcolor));
            lengthView.setBackgroundColor(getResources().getColor(R.color.sequencer_progress_backgroundcolor));
            lengthView.setPadding(5, 5, 5, 5);
            this.board.addView(lengthView, 80, 30);
        }


    }

    public void setCommandBlock(final int row, final int column) {

        TextView commandView = new TextView(getActivity());

        commandView.setText("cmd");
        commandView.setTextColor(getResources().getColor(R.color.sequencer_commandbox_textcolor));
        commandView.setBackgroundColor(getResources().getColor(R.color.sequencer_commandbox_backgroundcolor));
        commandView.setPadding(5, 5, 5, 5);
        commandView.setWidth(80);
        commandView.setHeight(60);
        commandView.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("e", row+""+column);
            }
        });
        this.board.addView(commandView, new GridLayout.LayoutParams(
                GridLayout.spec(column, 1),
                GridLayout.spec(row,1)
        ));

    }



    public void Refresh() {

        String _Name = mProject.getName();
        setTitle(_Name);
        for (int i = 0; i < mProject.mTrack.size(); i++) {

        }

    }

    public void addTrack(final int pos) {
        final TextView trackView = new TextView(getActivity());
        mProject.addTrack(pos);

        trackView.setText("New Track " + pos);
        mProject.mTrack.get(pos).setName("New Track " + pos);
        trackView.setTextColor(getResources().getColor(R.color.sequencer_commandbox_textcolor));
        trackView.setBackgroundColor(getResources().getColor(R.color.sequencer_trackbox_backgroundcolor));
        trackView.setPadding(5, 5, 5, 5);
        trackView.setWidth(170);
        trackView.setHeight(60);


        trackView.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("e", ""+pos);

                LayoutInflater inflater2 = getActivity().getLayoutInflater();
                final View dialogView3= inflater2.inflate(R.layout.dialog_trackset, null);
                //멤버의 세부내역 입력 Dialog 생성 및 보이기
                AlertDialog.Builder buider2= new AlertDialog.Builder(getActivity()); //AlertDialog.Builder 객체 생성
                buider2.setTitle("트랙 편집 - "+mProject.mTrack.get(pos).getName()); //Dialog 제목
                buider2.setIcon(android.R.drawable.ic_menu_edit); //제목옆의 아이콘 이미지(원하는 이미지 설정)


                Button deleteButton = (Button) dialogView3.findViewById(R.id.deleteTrack);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProject.mTrack.remove(pos);
                        trackView.setVisibility(View.GONE);
                        dialog2.cancel();

                    }
                });

                EditText editTrackName = (EditText) dialogView3.findViewById(R.id.trackName_edit);
                editTrackName.setText(mProject.mTrack.get(pos).getName());
                buider2.setView(dialogView3); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)

                buider2.setPositiveButton("변경", new DialogInterface.OnClickListener() {
                    //Dialog에 "Complite"라는 타이틀의 버튼을 설정
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub


                        EditText TrackName_edit = (EditText)dialogView3.findViewById(R.id.trackName_edit);

                        String TrackNameText  = TrackName_edit.getText().toString();
                        mProject.mTrack.get(pos).setName(TrackNameText);

                        trackView.setText(TrackNameText);

                        //((MainActivity)getActivity()).setTitleText("DMXArio - Sequencer - " + mProject.getName());
                        Toast.makeText(getActivity(), "트랙 설정을 변경하였습니다", Toast.LENGTH_SHORT).show();
                    }
                });

                buider2.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getActivity(), "취소", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog2=buider2.create();
                dialog2.setCanceledOnTouchOutside(false);
                dialog2.show();
            }
        });


        this.trackboard.addView(trackView, new GridLayout.LayoutParams(
                GridLayout.spec(pos,1),
                GridLayout.spec(0,1)
        ));
    }
    public void setTitle(String str) {
        ((MainActivity)getActivity()).setTitleText("DMXArio - Sequencer - " + str);  //프로젝트 이름 갱신
    }


    public void setTrack() {
        for (int i = 0; i < limitTrack; i++) {
            trackView.setText("New Track ");
        }
    }





}


