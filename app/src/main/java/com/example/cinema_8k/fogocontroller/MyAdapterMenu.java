package com.example.cinema_8k.fogocontroller;

/**
 * Created by CINEMA-8K on 22/02/2016.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Milton on 06/01/2016.
 */
public class MyAdapterMenu extends BaseAdapter {
    private Context context;
    private ArrayList<Computer> computers = new ArrayList<>();
    private ArrayList<Computer> arrayDevices = new ArrayList<>();

    public MyAdapterMenu(Context context, ArrayList<Computer> arrayDevices) {
        this.context = context;
        this.arrayDevices = arrayDevices;
    }

    public ArrayList<Computer> getArrayDevices() {
        return arrayDevices;
    }

    public void setArrayDevices(ArrayList<Computer> arrayDevices) {
        this.arrayDevices = arrayDevices;
    }

    public ArrayList<Computer> getComputers() {
        return computers;
    }

    public void setComputers(ArrayList<Computer> computers) {
        this.computers = computers;
    }

    @Override
    public int getCount() {
        return arrayDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_modelo, parent, false);
        } else {
            view = convertView;
        }
        final TextView textView = (TextView) view.findViewById(R.id.textName);
        //textView.setText(itens[position][0]);
        textView.setText(arrayDevices.get(position).getName());
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkboxIP);
        //checkBox.setText(itens[position][1]);
        checkBox.setText(arrayDevices.get(position).getIp());


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Computer computer = new Computer(textView.getText().toString(), checkBox.getText().toString(), isChecked);
                    computers.add(computer);
                    /*CHANGE param checked of the ArrayDevices*/
                    for (int x = 0; x < arrayDevices.size(); x++) {
                        if (arrayDevices.get(x).getName() == textView.getText().toString()) {
                            arrayDevices.get(x).setChecked(isChecked);
                        }
                    }
                    Log.e("Situation:", String.valueOf(isChecked));
                    Log.e("Size Array:", String.valueOf(computers.size()));
                    Log.e("Name: ", computer.getName());
                } else {
                    for (int x = 0; x < computers.size(); x++) {
                        if (computers.get(x).getIp() == checkBox.getText().toString()) {
                            Log.e("CheckBox: ", checkBox.getText().toString());
                            Log.e("Name: ", computers.get(x).getName());
                            computers.remove(x);
                        }
                    }
                    /*CHANGE param checked of the ArrayDevices*/
                    for (int x = 0; x < arrayDevices.size(); x++) {
                        if (arrayDevices.get(x).getName() == textView.getText().toString()) {
                            arrayDevices.get(x).setChecked(isChecked);
                        }
                    }
                    Log.e("Situation:", String.valueOf(isChecked));
                    Log.e("Size Array:", String.valueOf(computers.size()));
                }
            }
        });
        return view;
    }
}

