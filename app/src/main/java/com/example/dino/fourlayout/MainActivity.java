package com.example.dino.fourlayout;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    View layout1, layout2,layout3,layout4,draw,sleepmode;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private TextView t1, t2, t3,t4;// 頁卡頭標
    private Button connectbt,data1button,returnbutton,returnbutton2,data2button,data3button,data4button,seedb,deldb,datebt;
    private GraphView mGraphView;
    private DrawView mDrawView;
    private TextView hi,data1,data2,data3,data4,mTextView,dbtext,drawwhat,txtdateoutput,recdata,state,deeptxt,shallowtxt;
    private EditText txtdate;
    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;
    private InputStream inStream;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    private ListView lv;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private MyDBHelper dbHelper;
    private SQLiteDatabase db;
    private static String DATABASE_TABLE = "titles";
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    int flag=1;
    int ifdataclk=0;
    int firstclk2=0;
    int firstclk3=0;
    int interval=0;
    float deep=0;
    float shallow=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater inflater = LayoutInflater.from(this);
        layout1 = inflater.inflate(R.layout.activity_main, null);
        layout2 = inflater.inflate(R.layout.lay2, null);
        layout3 = inflater.inflate(R.layout.lay3, null);
        layout4 = inflater.inflate(R.layout.lay4, null);
        draw = inflater.inflate(R.layout.draw, null);
        sleepmode = inflater.inflate(R.layout.sleepmode, null);

        Init1();
        Initdb();
    }
    private void Initdb(){
        dbHelper = new MyDBHelper(MainActivity.this);
        db=dbHelper.getWritableDatabase();

        hi.setText("if is db open: " + db.isOpen() +
                "\ndb version: " + db.getVersion());

    }
    private void Init1() {
        t1 = (TextView) findViewById(R.id.text1);
        t2 = (TextView) findViewById(R.id.text2);
        t3 = (TextView) findViewById(R.id.text3);
        t4 = (TextView) findViewById(R.id.text4);

        t1.setOnClickListener(new MyOnClickListener(0));
        t2.setOnClickListener(new MyOnClickListener(1));
        t3.setOnClickListener(new MyOnClickListener(2));
        t4.setOnClickListener(new MyOnClickListener(3));

        lv = (ListView)findViewById(R.id.listView1);
        hi = (TextView)findViewById(R.id.hi);
        BA = BluetoothAdapter.getDefaultAdapter();
        clicklist();
        btAdapter = BluetoothAdapter.getDefaultAdapter();

    }
    private void Init2() {
        t1 = (TextView) findViewById(R.id.text1);
        t2 = (TextView) findViewById(R.id.text2);
        t3 = (TextView) findViewById(R.id.text3);
        t4 = (TextView) findViewById(R.id.text4);

        data1button=(Button)findViewById(R.id.data1button);
        data2button=(Button)findViewById(R.id.data2button);
        data3button=(Button)findViewById(R.id.data3button);


        t1.setOnClickListener(new MyOnClickListener(0));
        t2.setOnClickListener(new MyOnClickListener(1));
        t3.setOnClickListener(new MyOnClickListener(2));
        t4.setOnClickListener(new MyOnClickListener(3));

        data1button.setOnClickListener(new MyOnClickListener(4));
        data2button.setOnClickListener(new MyOnClickListener(5));
        data3button.setOnClickListener(new MyOnClickListener(6));


        data1 = (TextView)findViewById(R.id.data1);
        data2 = (TextView)findViewById(R.id.data2);
        data3 = (TextView)findViewById(R.id.data3);

        flag=1;
      //  if(firstclk2==0) {
            Thread t = new thread();
            t.start();
    //    }

    }
    private void Init3() {
        t1 = (TextView) findViewById(R.id.text1);
        t2 = (TextView) findViewById(R.id.text2);
        t3 = (TextView) findViewById(R.id.text3);
        t4 = (TextView) findViewById(R.id.text4);
        state = (TextView) findViewById(R.id.state);

        t1.setOnClickListener(new MyOnClickListener(0));
        t2.setOnClickListener(new MyOnClickListener(1));
        t3.setOnClickListener(new MyOnClickListener(2));
        t4.setOnClickListener(new MyOnClickListener(3));
        flag=0;
       // if(firstclk3==0)
      //  {
        Thread t3 = new thread3();
        t3.start();
       // }
    }
    private void Init4() {
        t1 = (TextView) findViewById(R.id.text1);
        t2 = (TextView) findViewById(R.id.text2);
        t3 = (TextView) findViewById(R.id.text3);
        t4 = (TextView) findViewById(R.id.text4);
        dbtext = (TextView) findViewById(R.id.dbtext);
        txtdateoutput = (TextView) findViewById(R.id.txtdateoutput);

        txtdate = (EditText) findViewById(R.id.txtdate);


        seedb=(Button)findViewById(R.id.seedb);
        seedb.setOnClickListener(new MyOnClickListener(9));
        deldb=(Button)findViewById(R.id.deldb);
        deldb.setOnClickListener(new MyOnClickListener(10));
        datebt=(Button)findViewById(R.id.datebt);
        datebt.setOnClickListener(new MyOnClickListener(11));


        t1.setOnClickListener(new MyOnClickListener(0));
        t2.setOnClickListener(new MyOnClickListener(1));
        t3.setOnClickListener(new MyOnClickListener(2));
        t4.setOnClickListener(new MyOnClickListener(3));

    }
    private void Initdraw() {
        returnbutton=(Button) findViewById(R.id.button);
        returnbutton.setOnClickListener(new MyOnClickListener(8));
        mGraphView = (GraphView) findViewById(R.id.graph);
        mGraphView.setMaxValue(200); //設定圖形數字最大值
        mTextView = (TextView) findViewById(R.id.value);
        drawwhat = (TextView) findViewById(R.id.drawwhat);


    }
    private void Initdrawsleep() {
        Cursor cursor = db.rawQuery(
                "select data5  from titles where data1=?",
                new String[]{String.valueOf(txtdate.getText())});

        txtdateoutput.setText("" + cursor.getCount());

        returnbutton2 = (Button) findViewById(R.id.button2);
        returnbutton2.setOnClickListener(new MyOnClickListener(12));
        mDrawView = (DrawView) findViewById(R.id.drawview);
        recdata = (TextView) findViewById(R.id.recdata);
        deeptxt = (TextView) findViewById(R.id.deeptxt);
        shallowtxt = (TextView) findViewById(R.id.shallowtxt);
        recdata.setText(txtdateoutput.getText());
        interval=cursor.getCount()/1500;
        if(interval==0)
            interval=1;
        if (cursor.getCount() != 0) {
            setdatalendth(cursor.getCount()/interval);

            cursor.moveToFirst();
            float flo;
            for (float reading = 0; reading < cursor.getCount(); reading=reading+interval) {
                flo = cursor.getInt(0);
                drawdata(flo);
                if(flo==2)
                    shallow++;
                else if(flo==3)
                    deep++;
                //  setText(Float.toString((reading))); // 顯示TextView值
                cursor.move(interval);
              // cursor.move(1);
            }
            float sleepcount=(deep/((deep+shallow)/100));
            float sleepcount2=(shallow/((deep+shallow)/100));
            int intsleep1= ((int) sleepcount);
            int intsleep2= ((int) sleepcount2);
            if(intsleep1>intsleep2)
                intsleep2++;
            else
                intsleep1++;

            deeptxt.setText("深睡"+intsleep1+"%");
            shallowtxt.setText("淺睡"+intsleep2+"%");
            Cursor cursor2 = db.rawQuery(
                    "select data2,data3,data4  from titles where data1=?",
                    new String[]{String.valueOf(txtdate.getText())});
            cursor2.moveToFirst();

            String str = "";
            str = str + cursor2.getString(0) + ":" + cursor2.getString(1) + ":" + cursor2.getString(2);
            drawstr(5, 750, str);
            cursor2.moveToPosition(cursor2.getCount() / 2);
            String str3 = "";
            str3 = str3 + cursor2.getString(0) + ":" + cursor2.getString(1) + ":" + cursor2.getString(2);
            drawstr(330, 750, str3);
            cursor2.moveToLast();
            String str2 = "";
            str2 = str2 + cursor2.getString(0) + ":" + cursor2.getString(1) + ":" + cursor2.getString(2);
            drawstr(650, 750, str2);
            deep=0;
            shallow=0;
        }
    }
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            switch(index) {
                case 0:
                    setContentView(layout1);
                    Init1();
                    break;
                case 1:
                    setContentView(layout2);
                    Init2();
                    break;
                case 2:
                    setContentView(layout3);
                    Init3();
                    break;
                case 3:
                    setContentView(layout4);
                    Init4();
                    break;
                case 4:
                    setContentView(draw);
                    ifdataclk=1;
                    Initdraw();
                    break;
                case 5:
                    setContentView(draw);
                    ifdataclk=2;
                    Initdraw();
                    break;
                case 6:
                    setContentView(draw);
                    ifdataclk=3;
                    Initdraw();
                    break;
                case 7:
                    setContentView(draw);
                    ifdataclk=4;
                    Initdraw();
                    break;
                case 8:
                    ifdataclk=0;
                    setContentView(layout2);
                    Init2();
                    break;
                case 9:
                    seedata();
                    break;
                case 10:
                    deldata();
                    break;
                case 11:
                    //getdata();
                    setContentView(sleepmode);
                    Initdrawsleep();
                    break;
                case 12:
                    setContentView(layout4);
                    Init4();
                    erase();
                    break;

            }
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void list(View view){
        pairedDevices = BA.getBondedDevices();

        ArrayList list = new ArrayList();
        for(BluetoothDevice bt : pairedDevices) {
            list.add(bt.getAddress());

        }
        Toast.makeText(getApplicationContext(), "Showing Paired Devices",
                Toast.LENGTH_SHORT).show();
        final ArrayAdapter adapter = new ArrayAdapter
                (this,android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

    }
    private void clicklist(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textview = (TextView) viewClicked;

                Toast.makeText(getApplicationContext(), "Showing Paired Devices" + textview.getText().toString(),
                        Toast.LENGTH_SHORT).show();
                BluetoothDevice device = btAdapter.getRemoteDevice(textview.getText().toString());
                try {
                    btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);

                    btSocket.connect();

                    hi.setText("\nCONNECT");

                } catch (IOException e) {

                }

            }
        });


    }

    class thread extends Thread {
        public void run() {
            try {

                firstclk2=1;
                OutputStream outStream = btSocket.getOutputStream();
                InputStream inStream = btSocket.getInputStream();

              /*  String str = "android client string";
                byte[] sendstr = new byte[21];
                System.arraycopy(str.getBytes(), 0, sendstr, 0, str.length());
                outStream.write(sendstr);*/


                while(flag==1) {
                    outStream.write(0x32);
                    byte[] rebyte = new byte[1];
                    inStream.read(rebyte);
                    while(rebyte[0]==-1) {
                        byte[] data = new byte[5];
                        inStream.read(data);
                        if(data[4]==-2) {
                            Bundle recvstr = new Bundle();
                            recvstr.putByteArray("data", data);

                            Message msg = new Message();
                            msg.setData(recvstr);
                            mHandler.sendMessage(msg);
                            sleep(100); // 暫停100ms
                            if(ifdataclk!=0)
                            {

                                addPoint(data[ifdataclk-1]); // 顯示心跳線
                                setText(Float.toString((data[0]& 0xff))); // 顯示TextView值

                            }
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class thread3 extends Thread {
        public void run() {
            try {

                firstclk3=1;
                OutputStream outStream = btSocket.getOutputStream();
                InputStream inStream = btSocket.getInputStream();

              /*  String str = "android client string";
                byte[] sendstr = new byte[21];
                System.arraycopy(str.getBytes(), 0, sendstr, 0, str.length());
                outStream.write(sendstr);*/


                while(flag==0) {
                    outStream.write(0x31);
                    byte[] rebyte = new byte[1];
                    inStream.read(rebyte);
                    while(rebyte[0]==-1) {
                        byte[] data = new byte[6];
                        inStream.read(data);
                        if(data[5]==-2) {

                            Bundle recvstr = new Bundle();
                           recvstr.putByteArray("data", data);

                            Message msg = new Message();
                            msg.setData(recvstr);
                            mHandler3.sendMessage(msg);

                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();

                            values.put("data1",""+data[0]);
                            values.put("data2",""+data[1]);
                            values.put("data3", "" + data[2]);
                            values.put("data4", "" + data[3]);
                            values.put("data5", "" + data[4]);

                            db.insert(DATABASE_TABLE, null, values);


                            sleep(100); // 暫停100ms

                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private Handler mHandler3 = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            byte[] rebyte2 = new byte[6];
            rebyte2 = (msg.getData().getByteArray("data"));
            state.setText("" + (rebyte2[4] & 0xff));

        }
    };

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            byte[] rebyte2 = new byte[5];
            rebyte2 = (msg.getData().getByteArray("data"));
            data1.setText("" + (rebyte2[0] & 0xff));
            data2.setText("" + (rebyte2[1] & 0xff));
            data3.setText("" + (rebyte2[2] & 0xff));

        }
    };

    private void addPoint(final float point) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mGraphView.addDataPoint(point);
            }
        });
    }

    private void drawdata(final float point) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDrawView.drawData(point);
            }
        });
    }
    private void drawstr(final int x,final int y,final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDrawView.drawstr(x,y,str);
            }
        });
    }
    private void setdatalendth(final float point) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDrawView.setdatalength(point);
            }
        });
    }
    private void erase() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDrawView.erase();
            }
        });
    }

    // 顯示TextView值
    private void setText(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText(str);
            }
        });
    }
    private void getdata(){

    /*    Cursor cursor = db.rawQuery(
                "select *  from titles where data1=?",
                new String[]{String.valueOf(txtdate.getText())});*/
     /*   Cursor cursor = db.rawQuery(
                "select *  from titles where data1=?",new String[]{""+10});*/
        Cursor cursor = db.rawQuery(
                "select data5  from titles where data1=?",
                new String[]{String.valueOf(txtdate.getText())});
        txtdateoutput.setText("" + cursor.getCount());



    }
    private void deldata(){
        db.execSQL("DROP TABLE IF EXISTS titles");
        db.execSQL("CREATE TABLE titles (_id " +
                "integer primary key autoincrement, " +
                "data1 text ," +
                "data2 text ," +
                "data3 text ," +
                "data4 text," +
                "data5 text," +
                "data6 text," +
                "data7 text," +
                "data8 text," +
                "data9 text," +
                "data10 text," +
                "data11 text  )");


    }
    private void seedata(){
        String[] colNames=new String[]{"_id","data1","data2","data3","data4","data5","data6","data7","data8","data9","data10","data11"};
        String str = "";
        Cursor c = db.query(DATABASE_TABLE, colNames,null, null, null, null,null);
        // 顯示欄位名稱
        for (int i = 0; i < colNames.length; i++)
            str += colNames[i] + "\t";
        str += "\n";
        c.moveToFirst();  // 第1筆
        // 顯示欄位值
        for (int i = 0; i < c.getCount(); i++) {
            str += c.getString(c.getColumnIndex(colNames[0])) + "\t";
            str += c.getString(1) + "\t";
            str += c.getString(2) + "\t";
            str += c.getString(3) + "\t";
            str += c.getString(4) + "\t";
            str += c.getString(5) + "\t";
            str += c.getString(6) + "\t";
            str += c.getString(7) + "\t";
            str += c.getString(8) + "\t";
            str += c.getString(9) + "\t";
            str += c.getString(10) + "\t";
            str += c.getString(11) + "\n";
            c.moveToNext();  // 下一筆
        }

        dbtext.setText(str.toString());

    }

}
