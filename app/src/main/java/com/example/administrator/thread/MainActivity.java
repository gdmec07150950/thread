package com.example.administrator.thread;



        import android.app.Activity;
        import android.content.Context;
        import android.graphics.BitmapFactory;
        import android.os.AsyncTask;
        import android.os.Handler;
        import android.os.Message;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.TextView;


        import java.util.Date;
        import java.util.Random;
        import java.util.Timer;
        import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView tv1;
    private int seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tv1);
        Date theLastDay = new Date(117, 5, 23);
        Date toDay = new Date();
        seconds = (int) ((theLastDay.getTime() - toDay.getTime()) / 1000);

    }

    public void anr(View v) {
        for (int i = 0; i < 100000; i++) {
            BitmapFactory.decodeResource(getResources(), R.drawable.a);

        }
    }

    class ThreadSample extends Thread {
        private Random rm;

        public ThreadSample(String name) {
            super(name);
            rm = new Random();
        }

        @Override
        public void run() {
            super.run();
            for (int i = 0; i < 10; i++) {
                System.out.println(getName() + ":" + i);
                try {
                    sleep(rm.nextInt(1000));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println(getName() + "完成");
        }
    }

    public void threadclass(View v) {

        ThreadSample t1 = new ThreadSample("线程1");
        ThreadSample t2 = new ThreadSample("线程2");
        t1.start();
        t2.start();
    }

    class RunnableSample implements Runnable {
        Random rm;
        String name;

        public RunnableSample(String name) {
            this.name = name;
            rm = new Random();
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(name + ":" + i);
                try {
                    Thread.sleep(rm.nextInt(1000));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println(name + "完成");
        }
    }

    public void runnableinterface(View v) {

        Thread t1 = new Thread(new RunnableSample("线程1"));
        Thread t2 = new Thread(new RunnableSample("线程2"));
        t1.start();
        t2.start();
    }

    public void timertask(View v) {
        class Mytask extends TimerTask {
            Random rm;
            String name;

            public Mytask(String name) {
                this.name = name;
                rm = new Random();
            }

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(name + ":" + i);
                    try {
                        Thread.sleep(rm.nextInt(1000));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(name + "完成");
            }
        }
        Timer timer1 = new Timer();
        Timer timer2 = new Timer();
        Mytask m1 = new Mytask("线程1");
        Mytask m2 = new Mytask("线程2");
        timer1.schedule(m1, 1000);
        timer2.schedule(m2, 1000);
    }

    final Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    showmsg(String.valueOf(msg.arg1 + msg.getData().get("attach").toString()));
            }
        }
    };

    class MyTask extends TimerTask {
        int countdown;
        double achievement1 = 1, achievement2 = 1;

        public MyTask(int seconds) {
            this.countdown = seconds;
        }

        @Override
        public void run() {
            Message msg = Message.obtain();
            msg.what = 1;
            msg.arg1 = countdown;
            achievement1 = achievement1 * 1.01;
            achievement2 = achievement2 * 1.02;

            Bundle bundle = new Bundle();
            bundle.putString("attach", "\n多努力1%：" + achievement1 + "\n多努力2%:" + achievement2);
            msg.setData(bundle);
            myHandler.sendMessage(msg);
        }
    }

    public void handlermessage(View v) {

        Timer timer = new Timer();
        timer.schedule(new MyTask(seconds), 1, 1000);
    }

    public void showmsg(String msg) {
        tv1.setText(msg);
    }

    class LearHard extends AsyncTask<Long, String, String> {
        private Context context;
        final int duration = 10;
        int count = 0;

        public LearHard(Activity context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Long... params) {
            long num = params[0].longValue();
            while (count < duration) {
                num--;
                count++;
                String status = "离毕业还有" + num + "秒，努力学习" + count + "秒。";
                publishProgress(status);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "这" + duration + "秒有收获，没虚度。";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            ((MainActivity) context).tv1.setText(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            showmsg(s);
            super.onPostExecute(s);
        }
    }

    public void asynctask(View v) {

        LearHard learHard = new LearHard(this);
        learHard.execute((long) seconds);
    }

}
