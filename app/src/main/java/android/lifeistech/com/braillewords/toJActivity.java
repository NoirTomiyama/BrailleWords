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

    //数字入力モード
    public final static boolean NUMMODE_ON = true;
    public final static boolean NUMMODE_OFF = false;

    //翻訳結果(今までの保持する)
    TextView toJResult;

    //数字入力中テキスト
    TextView inputNum;

    //押下判定
    int[] flag1 = new int[6];
    int[] flag2 = new int[6];

    //現在の翻訳テキスト
    String translatedText = "";

    //追加テキスト
    String addText = "";

    String copy = "";   //String型の初期化

    //重み格納変数
    int weight = 0;

    //使用フィールド
    int field = 0;

    //数字入力モード判定
    boolean numMode;

//    //char型配列256用意
//    char[] charArray = new char[256];

//    int total=0; //現在の単語数

    LinearLayout linearLayout_left; //左使用フィールド
    LinearLayout linearLayout_right; //右使用フィールド

    //50音が主に入る．
    public static Braille[] brailles1;
    //数字系がおもに入る．
    public static Braille[] brailles2;

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

    private int[] weights1 = {
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

    //weight = 60のとき，数字入力モード

    private String[] japaneses = {
            "あ","い","う","え","お",
            "か","き","く","け","こ",
            "さ","し","す","せ","そ",
            "た","ち","つ","て","と",
            "な","に","ぬ","ね","の",
            "は","ひ","ふ","へ","ほ",
            "ま","み","む","め","も",
            "や","ゆ","よ",
            "ら","り","る","れ","ろ",
            "わ","ゐ","ゑ","を",
            "ん","っ","ー",
            "。","、","・","？","！"
    };

    private String [] numbers = {
            "0","1","2","3","4","5","6","7","8","9",".",","
    };

    private int[] weights2 = {
            26,1,3,9,25,17,11,27,19,10,2,4
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_j);

        toJResult=(TextView)findViewById(R.id.toJResult);
        linearLayout_left=(LinearLayout)findViewById(R.id.linearLayout1);
        linearLayout_right=(LinearLayout)findViewById(R.id.linearLayout2);

        //flag1,2の初期化
        both_flags_reset();
        both_background_reset();

        //braillesの初期化
        brailles1 = new Braille[64];
        for(int i = 0;i<weights1.length;i++){
            Braille braille1 = new Braille(japaneses[i],weights1[i]);
            brailles1[i] = braille1;
        }

        brailles2 = new Braille[64];
        for(int i = 0;i<weights2.length;i++){
            Braille braille2 = new Braille(weights2[i],numbers[i]);
            brailles2[i] = braille2;
        }

        numMode = false;
        inputNum = (TextView)findViewById(R.id.inputNum);
        inputNum.setVisibility(View.INVISIBLE);

    }

    public void both_flags_reset() {
        for (int i = 0; i < 6; i++) {
            flag1[i] = 0;
            flag2[i] = 0;
        }

        this.findViewById(R.id.button1).setActivated(false);
        this.findViewById(R.id.button2).setActivated(false);
        this.findViewById(R.id.button3).setActivated(false);
        this.findViewById(R.id.button4).setActivated(false);
        this.findViewById(R.id.button5).setActivated(false);
        this.findViewById(R.id.button6).setActivated(false);
        this.findViewById(R.id.button7).setActivated(false);
        this.findViewById(R.id.button8).setActivated(false);
        this.findViewById(R.id.button9).setActivated(false);
        this.findViewById(R.id.button10).setActivated(false);
        this.findViewById(R.id.button11).setActivated(false);
        this.findViewById(R.id.button12).setActivated(false);

    }

    public void both_background_reset(){
        linearLayout_left.setBackgroundColor(Color.parseColor("#00000000"));
        linearLayout_right.setBackgroundColor(Color.parseColor("#00000000"));
    }

    public void left_background_reset(){
        linearLayout_left.setBackgroundColor(Color.parseColor("#00000000"));
        linearLayout_right.setBackgroundColor(Color.parseColor("#BFE0FFFF"));
    }

    public void right_background_reset(){
        linearLayout_left.setBackgroundColor(Color.parseColor("#BFE0FFFF"));
        linearLayout_right.setBackgroundColor(Color.parseColor("#00000000"));
    }

    public void button1(View v) {

        right_background_reset();

        //使用フィールド判定
        if(field == FIELD_RIGHT){
            reset_right();

            //使用フィールド変更の際に加える．
            translatedText += addText;

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

        //翻訳判定
        judge();
    }

    public void button2(View v) {

        right_background_reset();

        if(field == FIELD_RIGHT){
            reset_right();

            translatedText += addText;

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

        right_background_reset();

        if(field == FIELD_RIGHT){
            reset_right();

            translatedText += addText;

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

        right_background_reset();

        if(field == FIELD_RIGHT){
            reset_right();

            translatedText += addText;

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

        right_background_reset();

        if(field == FIELD_RIGHT){
            reset_right();

            translatedText += addText;

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

        right_background_reset();

        if(field == FIELD_RIGHT){
            reset_right();

            translatedText += addText;

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

        left_background_reset();

        if(field == FIELD_LEFT){
            reset_left();

            translatedText += addText;

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

        left_background_reset();

        if(field == FIELD_LEFT){
            reset_left();

            translatedText += addText;

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

        left_background_reset();

        if(field == FIELD_LEFT){
            reset_left();

            translatedText += addText;

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

        left_background_reset();

        if(field == FIELD_LEFT){
            reset_left();

            translatedText += addText;

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

        left_background_reset();

        if(field == FIELD_LEFT){
            reset_left();

            translatedText += addText;

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

        left_background_reset();

        if(field == FIELD_LEFT){
            reset_left();
            translatedText += addText;
            field = FIELD_RIGHT;
        }

        if (flag2[5] == 0) {
            this.findViewById(R.id.button12).setActivated(true);
            flag2[5] = 1;
        } else if (flag2[5] == 1) {
            this.findViewById(R.id.button12).setActivated(false);
            flag2[5] = 0;
        }

        if(numMode == NUMMODE_OFF){
            judge();
        }else if(numMode == NUMMODE_ON){
            judgeNum();
        }

    }

    public void weight_calc(){

        weight = 0;

        //重み計算
        if(field == FIELD_LEFT){

            for(int i=0;i<6;i++){
                //s+= "" + flag1[i];
                weight += Math.pow(2,i) * flag1[i];
            }
//            temp = 1 * flag1[0]
//                    + 2 * flag1[1]
//                    + 4 * flag1[2]
//                    + 8 * flag1[3]
//                    +16 * flag1[4]
//                    +32 * flag1[5];

        }else if(field == FIELD_RIGHT){
            for(int i=0;i<6;i++){
                weight += Math.pow(2,i) * flag2[i];
            }
        }

    }

    public void judgeNum() {
        addText = "";

        //数符 + 数符のエラー処理


    }


    public void judge(){ //翻訳判定

        addText = "";

        //重み計算
        weight_calc();

        //数字モードかどうか判定

        if(weight == 60){
            numMode = NUMMODE_ON;
            inputNum.setVisibility(View.VISIBLE);
            addText = "";
            toJResult.setText(translatedText);
        }else{
            numMode = NUMMODE_OFF;
            inputNum.setVisibility(View.INVISIBLE);
        }

        if(numMode == NUMMODE_OFF){
            for(int i = 0;i < weights1.length;i++){
                System.out.println("weight = " + weight);
                //System.out.println("brailles = " + brailles[i].getCode());

                if(weight == brailles1[i].getWeight()){
                    addText = brailles1[i].getS_japanese();
                    System.out.println("brailles = " + brailles1[i].getS_japanese());
                    break;
                }else{
                    addText="";
                }
            }
            toJResult.setText(translatedText + addText);
        }



        if(weight==0){
            both_background_reset();
        }

    }

    public void reset_left(){ //Table1削除用

        for(int j=0;j<6;j++){
            flag1[j]=0;
        }

        this.findViewById(R.id.button1).setActivated(false);
        this.findViewById(R.id.button2).setActivated(false);
        this.findViewById(R.id.button3).setActivated(false);
        this.findViewById(R.id.button4).setActivated(false);
        this.findViewById(R.id.button5).setActivated(false);
        this.findViewById(R.id.button6).setActivated(false);

//        //移った段階で，total変数をプラス．
//        total++;
    }

    public void reset_right(){ //Table2削除用

        for(int j=0;j<6;j++){
            flag2[j]=0;
        }
        this.findViewById(R.id.button7).setActivated(false);
        this.findViewById(R.id.button8).setActivated(false);
        this.findViewById(R.id.button9).setActivated(false);
        this.findViewById(R.id.button10).setActivated(false);
        this.findViewById(R.id.button11).setActivated(false);
        this.findViewById(R.id.button12).setActivated(false);

//        //移った段階で，total変数をプラス．
//        total++;
    }

    public void del1(View v){ //一文字削除

        if(translatedText.length() > 0) {
            if(addText.isEmpty()){
                translatedText = translatedText.substring(0, translatedText.length()-1);
            }else{
                translatedText += addText;
                translatedText = translatedText.substring(0, translatedText.length()-1);
                addText = "";
            }
        }

        toJResult.setText(translatedText);

        both_background_reset();
        both_flags_reset();

    }

    public void delAll(View v){

        both_background_reset();
        both_flags_reset();

        addText = "";
        translatedText = "";

        toJResult.setText("");
    }

    public void copy(View v){

        copy = translatedText + addText; //今

        if(!copy.isEmpty()){
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
}



