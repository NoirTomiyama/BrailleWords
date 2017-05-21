package android.lifeistech.com.braillewords;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class toJActivity extends AppCompatActivity {

    TextView toJResult; //翻訳結果

    int[] flag1 = new int[6];
    int[] flag2 = new int[6];
    String s = "";      //String型の初期化
    String copy = "";   //String型の初期化

    int field = 0;//使用フィールド

    char[] charArray = new char[256]; //char型配列256用意

    int total=0; //現在の単語数

    LinearLayout linearLayout1; //左使用フィールド
    LinearLayout linearLayout2; //右使用フィールド

    public static Braille[] brailles;
    private String[] codes = {
            "100000","101000","110000","111000","011000",   //あ行
            "100001","101001","110001","111001","011001",   //か行
            "100101","101101","110101","111101","011101",   //さ行
            "100110","101110","110110","111110","011110",   //た行
            "100010","101010","110010","111010","011010",   //な行
            "100011","101011","110011","111011","011011",   //は行
            "100111","101111","110111","111111","011111",   //ま行
            "010010","010011","010110",                     //や行
            "100100","101100","110100","111100","011100",   //ら行
            "000010","001010","001110","000110",            //わ行
            "000111","001000","001100",                     //ん行
            "001101","000101","000100","001001","001110"    //。、・？！
    };

    private char[] japaneses = {
            'あ','い','う','え','お',
            'か','き','く','け','こ',
            'さ','し','す','せ','そ',
            'た','ち','つ','て','と',
            'な','に','ぬ','ね','の',
            'は','ひ','ふ','へ','ほ',
            'ま','み','む','め','も',
            'や','ゆ','よ',
            'ら','り','る','れ','ろ',
            'わ','ゐ','ゑ','を',
            'ん','っ','ー',
            '。','、','・','？','！'
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_j);

        toJResult=(TextView)findViewById(R.id.toJResult);
        linearLayout1=(LinearLayout)findViewById(R.id.linearLayout1);
        linearLayout2=(LinearLayout)findViewById(R.id.linearLayout2);

        //flag1,2の初期化
        for (int i = 0; i < 6; i++) {
            flag1[i] = 0;
            flag2[i] = 0;
        }

        //braillesの初期化
        brailles = new Braille[55];
        for(int i = 0;i<codes.length;i++){
            Braille braille = new Braille(codes[i],japaneses[i]);
            brailles[i] = braille;
        }
    }

    
    public void button1(View v) {
        //仕様フィールド判定
        if(field==1){
            reset2();
            field=0;
        }

        //ボタン押下判定
        if (flag1[0] == 0) {
            this.findViewById(R.id.button1).setActivated(true);
            flag1[0] = 1;
        } else if (flag1[0] == 1) {
            this.findViewById(R.id.button1).setActivated(false);
            flag1[0] = 0;
        }

        judge();
    }

    public void button2(View v) {
        if(field==1){
            reset2();
            field=0;
        }

        if (flag1[1] == 0) {
            this.findViewById(R.id.button2).setActivated(true);
            flag1[1] = 1;
        } else if (flag1[1] == 1) {
            this.findViewById(R.id.button2).setActivated(false);
            flag1[1] = 0;
        }

        judge();
    }

    public void button3(View v) {
        if(field==1){
            reset2();
            field=0;
        }

        if (flag1[2] == 0) {
            this.findViewById(R.id.button3).setActivated(true);
            flag1[2] = 1;
        } else if (flag1[2] == 1) {
            this.findViewById(R.id.button3).setActivated(false);
            flag1[2] = 0;
        }
        judge();
    }

    public void button4(View v) {
        if(field==1){
            reset2();
            field=0;
        }

        if (flag1[3] == 0) {
            this.findViewById(R.id.button4).setActivated(true);
            flag1[3] = 1;
        } else if (flag1[3] == 1) {
            this.findViewById(R.id.button4).setActivated(false);
            flag1[3] = 0;
        }
        judge();
    }

    public void button5(View v) {
        if(field==1){
            reset2();
            field=0;
        }

        if (flag1[4] == 0) {
            this.findViewById(R.id.button5).setActivated(true);
            flag1[4] = 1;
        } else if (flag1[4] == 1) {
            this.findViewById(R.id.button5).setActivated(false);
            flag1[4] = 0;
        }
        judge();
    }

    public void button6(View v) {
        if(field==1){
            reset2();
            field=0;
        }

        if (flag1[5] == 0) {
            this.findViewById(R.id.button6).setActivated(true);
            flag1[5] = 1;
        } else if (flag1[5] == 1) {
            this.findViewById(R.id.button6).setActivated(false);
            flag1[5] = 0;
        }
        judge();
    }

    public void button7(View v) {
        if(field==0){
            reset1();
            field=1;
        }

        if (flag2[0] == 0) {
            this.findViewById(R.id.button7).setActivated(true);
            flag2[0] = 1;
        } else if (flag2[0] == 1) {
            this.findViewById(R.id.button7).setActivated(false);
            flag2[0] = 0;
        }

        judge();
    }

    public void button8(View v) {
        if(field==0){
            reset1();
            field=1;
        }

        if (flag2[1] == 0) {
            this.findViewById(R.id.button8).setActivated(true);
            flag2[1] = 1;
        } else if (flag2[1] == 1) {
            this.findViewById(R.id.button8).setActivated(false);
            flag2[1] = 0;
        }

        judge();
    }

    public void button9(View v) {
        if(field==0){
            reset1();
            field=1;
        }

        if (flag2[2] == 0) {
            this.findViewById(R.id.button9).setActivated(true);
            flag2[2] = 1;
        } else if (flag2[2] == 1) {
            this.findViewById(R.id.button9).setActivated(false);
            flag2[2] = 0;
        }

        judge();
    }

    public void button10(View v) {
        if(field==0){
            reset1();
            field=1;
        }

        if (flag2[3] == 0) {
            this.findViewById(R.id.button10).setActivated(true);
            flag2[3] = 1;
        } else if (flag2[3] == 1) {
            this.findViewById(R.id.button10).setActivated(false);
            flag2[3] = 0;
        }

        judge();
    }

    public void button11(View v) {
        if(field==0){
            reset1();
            field=1;
        }

        if (flag2[4] == 0) {
            this.findViewById(R.id.button11).setActivated(true);
            flag2[4] = 1;
        } else if (flag2[4] == 1) {
            this.findViewById(R.id.button11).setActivated(false);
            flag2[4] = 0;
        }

        judge();

    }

    public void button12(View v) {
        if(field==0){
            reset1();
            field=1;
        }

        if (flag2[5] == 0) {
            this.findViewById(R.id.button12).setActivated(true);
            flag2[5] = 1;
        } else if (flag2[5] == 1) {
            this.findViewById(R.id.button12).setActivated(false);
            flag2[5] = 0;
        }

        judge();
    }

    public void judge(){ //50音判定

        //仕様フィールド,String sに格納
        if(field==0){
            for(int i=0;i<6;i++){
                s+= "" + flag1[i];
            }
            linearLayout1.setBackgroundColor(Color.parseColor("#BFE0FFFF"));
            linearLayout2.setBackgroundColor(Color.parseColor("#00000000"));
        }else if(field==1){
            for(int i=0;i<6;i++){
                s+= "" + flag2[i];
            }
            linearLayout1.setBackgroundColor(Color.parseColor("#00000000"));
            linearLayout2.setBackgroundColor(Color.parseColor("#BFE0FFFF"));
        }

        for(int i = 0;i<codes.length;i++){
            System.out.println("s = " + s);
            //System.out.println("brailles = " + brailles[i].getCode());

            if(s.equals(brailles[i].getCode())){
                charArray[total]=brailles[i].getJapanese();
                System.out.println("brailles = " + brailles[i].getCode());
                break;
            }else{
                charArray[total]='\u0000';
            }
        }

        toJResult.setText(String.valueOf(charArray));

        System.out.println(charArray.length);

        s="";//初期化
    }

    public void reset1(){ //Table1削除用
        for(int j=0;j<6;j++){
            flag1[j]=0;
        }
        this.findViewById(R.id.button1).setActivated(false);
        this.findViewById(R.id.button2).setActivated(false);
        this.findViewById(R.id.button3).setActivated(false);
        this.findViewById(R.id.button4).setActivated(false);
        this.findViewById(R.id.button5).setActivated(false);
        this.findViewById(R.id.button6).setActivated(false);

        total++;
    }

    public void reset2(){ //Table2削除用
        for(int j=0;j<6;j++){
            flag2[j]=0;
        }
        this.findViewById(R.id.button7).setActivated(false);
        this.findViewById(R.id.button8).setActivated(false);
        this.findViewById(R.id.button9).setActivated(false);
        this.findViewById(R.id.button10).setActivated(false);
        this.findViewById(R.id.button11).setActivated(false);
        this.findViewById(R.id.button12).setActivated(false);

        total++;
    }

    public void del1(View v){ //一文字削除

        if(total>=0){
            charArray[total]='\u0000';
            toJResult.setText(String.valueOf(charArray));
            total--;
            if(field==0){
                reset1();
                field=1;
            }else if(field==1){
                reset2();
                field=0;
            }
            total--;//リセットで＋されてしまうため
        }

    }

    public void delAll(View v){ //トータルがマイナスのときのエラー処理
        for(int j = 0;j<=total;j++){
            charArray[j]='\u0000';
        }
        reset1();
        reset2();
        total=0;
        toJResult.setText(String.valueOf(charArray));
    }

    public void copy(View v){

        copy = String.valueOf(charArray); //今

        //クリップボードに格納するItemを作成
        ClipData.Item item = new ClipData.Item(copy);

        //MIMETYPEの作成
        String[] mimeType = new String[1];
        mimeType[0] = ClipDescription.MIMETYPE_TEXT_PLAIN;

        //クリップボードに格納するClipDataオブジェクトの作成
        ClipData cd = new ClipData(new ClipDescription("text_data", mimeType), item);

        //クリップボードにデータを格納
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cm.setPrimaryClip(cd);

        Toast.makeText(this,"クリップボードにコピーしました.", Toast.LENGTH_LONG).show();
    }



}



