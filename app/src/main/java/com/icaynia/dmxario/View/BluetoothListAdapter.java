package com.icaynia.dmxario.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.icaynia.dmxario.Model.Bluetooth;
import com.icaynia.dmxario.R;

import java.util.ArrayList;

/**
 * Created by icaynia on 01/02/2017.
 */

public class BluetoothListAdapter extends BaseAdapter
{
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<Bluetooth> listViewItemList = new ArrayList<Bluetooth>() ;

    // ListViewAdapter의 생성자
    public BluetoothListAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_bluetoothpicker, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.name);
        TextView descTextView = (TextView) convertView.findViewById(R.id.address);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Bluetooth listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(listViewItem.name);
        descTextView.setText(listViewItem.address);

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Bluetooth getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String name, String address) {
        Bluetooth item = new Bluetooth();

        item.name = name;
        item.address = address;

        listViewItemList.add(item);
    }
}
