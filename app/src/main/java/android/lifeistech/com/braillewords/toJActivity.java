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

   //使用フィールド変数
    public final static int FIELD_LEFT = 0;
    public final static int FIELD_RIGHT = 1;

    TextView toJResult; //翻訳結果

    int[] flag1 = new int[6];
    int[] flag2 = new int[6];

    String s = "";      //String型の初期化
    String copy = "";   //String型の初期化

    //重み格納変数
    int temp = 0;

    int field = 0;//使用フィールド

    char[] charArray = new char[256]; //char型配列256用意

    int total=0; //現在の単語数

    LinearLayout linearLayout1; //左使用フィールド
    LinearLayout linearLayout2; //右使用フィールド

    public static Braille[] brailles;

//    private String[] codes = {
//            "100000","101000","110000","111000","011000",   //あ行
//            "100001","101001","110001","111001","011001",   //か行
//            "100101","101101","110101","111101","011101",   //さ行
//            "100110","101110","110110","111110","011110",   //た行
//            "100010","101010","110010","111010","011010",   //な行
//            "100011","101011","110011","111011","011011",   //は行
//            "100111","101111","110111","111111","011111",   //ま行
//            "010010","010011","010110",                     //や行
//            "100100","101100","110100","111100","011100",   //ら行
//            "000010","001010","001110","000110",            //わ行
//            "000111","001000","001100",                     //ん行
//            "001101","000101","000100","001001","001110"    //。、・？！
//    };

        private int[] weights = {
            1,3,9,11,10,        //あ行
            33,35,41,43,42,     //か行
            49,51,57,59,58,     //さ行
            21,23,29,31,30,     //た行
            5,7,13,15,14,       //な行
            37,39,45,47,46,     //は行
            53,55,61,63,62,     //ま行
            12,44,28,           //や行
            17,19,25,27,26,     //ら行
            4,6,64,20,          //わ行("ゑ"のみ別対処を考える．)
            52,2,18,            //ん行
            50,48,16,34,22      //。、・？！
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
        for(int i = 0;i<weights.length;i++){
            Braille braille = new Braille(japaneses[i],weights[i]);
            brailles[i] = braille;
        }
    }

    
    public void button1(View v) {
        //仕様フィールド判定
        if(field == FIELD_RIGHT){
            reset2();
            field = FIELD_LEFT;
        }

        //ボタン押下判定
        if (flag1[0] == 0) {
            this.findViewById(R.id.button1).setActivated(true);
            flag1[0] = 1;
        } else if (flag1[0] == 1) {
            this.findViewById(R.id.button1).setActivated(false);
            flag1[0] = 0;
        }

        //判定
        judge();
    }

    public void button2(View v) {

        if(field == FIELD_RIGHT){
            reset2();
            field = FIELD_LEFT;
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

        if(field == FIELD_RIGHT){
            reset2();
            field = FIELD_LEFT;
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

        if(field == FIELD_RIGHT){
            reset2();
            field = FIELD_LEFT;
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

        if(field == FIELD_RIGHT){
            reset2();
            field = FIELD_LEFT;
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

        if(field == FIELD_RIGHT){
            reset2();
            field = FIELD_LEFT;
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

        if(field == FIELD_LEFT){
            reset1();
            field = FIELD_RIGHT;
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

        if(field == FIELD_LEFT){
            reset1();
            field = FIELD_RIGHT;
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

        if(field == FIELD_LEFT){
            reset1();
            field = FIELD_RIGHT;
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

        if(field == FIELD_LEFT){
            reset1();
            field = FIELD_RIGHT;
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

        if(field == FIELD_LEFT){
            reset1();
            field = FIELD_RIGHT;
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

        if(field == FIELD_LEFT){
            reset1();
            field = FIELD_RIGHT;
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

    public void judge(){ //判定

        //使用フィールド,String sに格納
        if(field == FIELD_LEFT){
            //重み計算
            for(int i=0;i<6;i++){
                //s+= "" + flag1[i];
                temp += Math.pow(2,i) * flag1[i];
            }

//            temp = 1 * flag1[0]
//                    + 2 * flag1[1]
//                    + 4 * flag1[2]
//                    + 8 * flag1[3]
//                    +16 * flag1[4]
//                    +32 * flag1[5];

            //使用フィールドにsetColor
            linearLayout1.setBackgroundColor(Color.parseColor("#BFE0FFFF"));
            linearLayout2.setBackgroundColor(Color.parseColor("#00000000"));

        }else if(field == FIELD_RIGHT){
            for(int i=0;i<6;i++){
                temp += Math.pow(2,i) * flag2[i];
            }
            //使用フィールドにsetColor
            linearLayout1.setBackgroundColor(Color.parseColor("#00000000"));
            linearLayout2.setBackgroundColor(Color.parseColor("#BFE0FFFF"));
        }

        for(int i = 0;i < weights.length;i++){
            System.out.println("temp = " + temp);
            //System.out.println("brailles = " + brailles[i].getCode());

            if(temp == brailles[i].getWeight()){
                charArray[total] = brailles[i].getJapanese();
                System.out.println("brailles = " + brailles[i].getCode());
                break;
            }else{
                charArray[total]='\u0000';
            }
        }

        s = String.valueOf(charArray);

        toJResult.setText(s);

        if(temp==0){
            linearLayout1.setBackgroundColor(Color.parseColor("#00000000"));
            linearLayout2.setBackgroundColor(Color.parseColor("#00000000"));
        }


        System.out.println(charArray.length);

        //初期化
        temp=0;
        s = "";
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

        //移った段階で，total変数をプラス．
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

        //移った段階で，total変数をプラス．
        total++;
    }

    public void del1(View v){ //一文字削除

        s="";

        if(total>=0){
            charArray[total]='\u0000';
            s = String.valueOf(charArray);

            toJResult.setText(s);

            total--;
            if(field==FIELD_LEFT){
                reset1();
            }else if(field==FIELD_RIGHT){
                reset2();
            }

            linearLayout1.setBackgroundColor(Color.parseColor("#00000000"));
            linearLayout2.setBackgroundColor(Color.parseColor("#00000000"));

            //リセットで＋されてしまうため
            total--;
        }

        if(total == 0){
            toJResult.setText("");
        }

        //初期化
        s="";

    }

    public void delAll(View v){ //トータルがマイナスのときのエラー処理

        s="";

        for(int j = 0;j<=total;j++){
            charArray[j]='\u0000';
        }

        s = String.valueOf(charArray);

        if(s.isEmpty()){
            toJResult.setText("");
        }else{
            toJResult.setText(s);
        }

        reset1();
        reset2();
        total=0;

        toJResult.setText("");


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



