package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.SimpleTimeZone;

public class NewTimeActivity extends AppCompatActivity {
    private EditText editTextAddTitle,editTextAddDescription;
    private ListView listViewTime1;
    private Button buttonCancel,buttonOK;
    private List<Message>message=new ArrayList<>();
    private MessageAdapter adapter;
    private int insertPosition;
    private  int Position;
    private int picwhich=0;  //保存周期单选按钮的选择
    private String date;  //定义所选日期，易于传值
    private int down,mYear,mMonth,mDay;  //定义当前时间，计算后的剩余日期
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int position = data.getIntExtra("insert_position", 0);
            String title = data.getStringExtra("message");
            Message mes = message.get(position);
            mes.setMessage(title);
            adapter.notifyDataSetChanged();
        }
    }*/
    private Handler timeHandler=new Handler(){

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_time);

        init();
        editTextAddTitle=(EditText)this.findViewById(R.id.edit_text_add_title);
        editTextAddDescription=(EditText)this.findViewById(R.id.edit_text_add_description);
        listViewTime1=(ListView)this.findViewById(R.id.list_view_itime2);
        buttonCancel=(Button)this.findViewById(R.id.button_cancel);
        buttonOK=(Button)this.findViewById(R.id.button_ok);

        adapter =new MessageAdapter(NewTimeActivity.this,R.layout.list_view_item_time2,message);
        listViewTime1.setAdapter(adapter);
        listViewTime1.setOnItemClickListener(new AdapterView.OnItemClickListener() {    //给每个item添加响应事件
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //给每一个item添加点击事件，position是item在listview中的位置
                final int currentPosition=position;
                switch(currentPosition){
                    case 0:  //添加时间
                        Calendar calendar = Calendar.getInstance();
                        DatePickerDialog datePickerDialog = new DatePickerDialog(NewTimeActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Message message0 = message.get(currentPosition);
                                date=year + "年" + (monthOfYear+1) + "月" + dayOfMonth+"日";
                                message0.setMessage("日期\n"+date);
                                adapter.notifyDataSetChanged();
                                //计算剩余时间
                                Calendar c = Calendar.getInstance();
                                mYear = c.get(Calendar.YEAR); // 获取当前年份
                                mMonth = c.get(Calendar.MONTH) ;// 获取当前月份
                                mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
                                int x=year-mYear;  //年份差
                                int y=monthOfYear-mMonth;  //月份差
                                int z=dayOfMonth-mDay;  //天数差
                                down=x*365+y*30+z;   //剩余天数
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                        break;

                    case 1:  //选择周期
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(NewTimeActivity.this);
                        builder1.setTitle("周期");
                        final String[] cycles = new String[]{"每周", "每月","每年", "自定义"};
                        builder1.setSingleChoiceItems(cycles, 0, new DialogInterface.OnClickListener() {
                            @Override //which：点击位置
                            public void onClick(DialogInterface dialog, int which) {
                               picwhich=which;  //保存按钮选择位置
                            }
                        });
                        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                String  cycle=cycles[picwhich];
                                Message message1 = message.get(currentPosition);
                                message1.setMessage("重复设置\n"+cycle);
                                adapter.notifyDataSetChanged();
                            }
                        });
                        builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                            }
                        });
                        builder1.show();
                        break;

                    case 2:  //选择图片
                        break;

                    case 3:  //添加标签,多选
                        AlertDialog.Builder builder3 = new AlertDialog.Builder(NewTimeActivity.this);
                        builder3.setTitle("标签");
                        final String[] labels = new String[]{"生日", "学习","工作", "节假日"};
                        final boolean[] checks = new boolean[]{false, false, false, false, false};
                        builder3.setMultiChoiceItems(labels, checks, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            }
                        });
                        builder3.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                StringBuffer sb=new StringBuffer();
                                for(int i=0;i<checks.length;i++){
                                    if(checks[i]){
                                        String label=labels[i];
                                        sb.append(label+" ");
                                    }
                                }
                                Message message3 = message.get(currentPosition);
                                message3.setMessage("标签\n"+sb.toString());
                                adapter.notifyDataSetChanged();
                            }
                        });
                        builder3.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                            }
                        });
                        builder3.show();
                        break;
                }
            }
        });

        /*倒计时
        CountDownTimer timer=new CountDownTimer(down*24*60*60*1000,1000) {
            @Override
            public void onTick(long l) {
                down=formatTime(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                down=0;
            }
        };*/


        editTextAddTitle.setText(getIntent().getStringExtra("title"));  //获取修改时传过来的数据
        editTextAddDescription.setText(getIntent().getStringExtra("description"));
        insertPosition=getIntent().getIntExtra("insert_position",0);
        Position=getIntent().getIntExtra("position",0);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("title",editTextAddTitle.getText().toString());
                intent.putExtra("description",editTextAddDescription.getText().toString());
                intent.putExtra("date",date.toString());
                intent.putExtra("countdown",down+"");
                intent.putExtra("insert_position",insertPosition);  //新建位置
                intent.putExtra("position",Position);  //修改位置
                setResult(RESULT_OK,intent);
                NewTimeActivity.this.finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewTimeActivity.this.finish();
            }
        });
    }

    private void init() {
        message.add(new Message("日期",R.drawable.a7));
        message.add(new Message("重复设置",R.drawable.a8));
        message.add(new Message("图片",R.drawable.a9));
        message.add(new Message("标签",R.drawable.a10));
    }

    private class MessageAdapter extends ArrayAdapter<Message> {

        private int source;

        public MessageAdapter(Context context, int resource, List<Message> objects) {
            super(context, resource, objects);
            source = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Message mes = getItem(position);//获取当前项的实例
            View view = LayoutInflater.from(getContext()).inflate(source, parent, false);
            ((ImageView) view.findViewById(R.id.image_view)).setImageResource(mes.getSource());
            ((TextView) view.findViewById(R.id.text_view_description)).setText(mes.getMessage());
            return view;
        }
    }

}
