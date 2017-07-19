package com.example.amit.assignment161;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button delete,add;
    TextView textView;
    EditText editText;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textcontent);
        editText=(EditText)findViewById(R.id.Textedit);
        delete=(Button)findViewById(R.id.btndelete);
        add=(Button)findViewById(R.id.button);
        file=new File(Environment.getExternalStorageDirectory()+"/FileData");
        if(!file.exists())
        {
            file.mkdir();
        }
        final File new_file=new File(file,"data.txt");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value=editText.getText().toString();
                editText.setText(" ");
                MyTask myTask=new MyTask(getApplicationContext(),new_file);
                myTask.execute(value);

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new_file.delete()){
                    textView.setText(" ");
                    Toast.makeText(getApplicationContext(),"File Successfully Deleted",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    delete.setEnabled(true);
                }



            }
        });
    }
    public class MyTask extends AsyncTask<String,Void,String>{
        Context context;
        File file;

        public MyTask(Context context, File file) {
            this.context = context;
            this.file = file;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... str) {
            String enter="\n";
            FileWriter fileWriter=null;
            try {
                fileWriter=new FileWriter(file,true);
                fileWriter.append(str[0].toString());
                fileWriter.append(enter);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "File Successfully Read & Write";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            FileReader fileReader=null;
            String name=" ";
            StringBuilder sb=new StringBuilder();
            try {
                fileReader=new FileReader(file);
                BufferedReader br=new BufferedReader(fileReader);
                while ((name=br.readLine())!=null)
                {
                    sb.append(name);
                }
                br.close();
                fileReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
            textView.setText(sb.toString());

        }
    }
}
