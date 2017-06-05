package android.lifeistech.com.braillewords;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class toJActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    //使用フィールド変数
    public final static int FIELD_LEFT = 0;
    public final static int FIELD_RIGHT = 1;
    //数字入力モード
    public final static boolean NUMMODE_ON = true;
    public final static boolean NUMMODE_OFF = false;
    //濁点，半濁点入力モード
    public final static boolean VOICEDMODE_ON = true;
    public final static boolean VOICEDMODE_OFF = false;
    public final static boolean SEMIVOICEDMODE_ON = true;
    public final static boolean SEMIVOICEDMODE_OFF = false;

    //ボタン押されたかどうか
    public final static int BUTTON_ON = 1;
    public final static int BUTTON_OFF = 0;

    TextView toJResult;     //翻訳結果(今までの保持する)
    TextView inputNum;      //数字入力中テキスト



    //押下判定
    int[] flag1 = new int[6];
    int[] flag2 = new int[6];

    //現在の翻訳テキスト
    String translatedText = "";
    //追加テキスト
    String addText = "";
    //コピー時に使用
    String copy = "";

    //重み格納変数
    int weight = 0;
    //使用フィールド
    int field = 0;

    //入力モード判定
    boolean numMode;
    boolean voicedMode;
    boolean semi_voicedMode;

    //数字入力制限(4まで)
    int temp_length = -1;
    int numCount = 0;

    LinearLayout linearLayout_left; //左使用フィールド
    LinearLayout linearLayout_right; //右使用フィールド

    //50音が主に入る．
    public static Braille[] brailles1;
    //数字系がおもに入る．
    public static Braille[] brailles2;
    //濁音，半濁音が入る
    public static Braille[] brailles3;
    public static Braille[] brailles4;

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
            4,6,100,20,          //わ行("ゑ"のみ別対処を考える．)
            52,2,18,            //ん行
            50,48,100,34,22      //。、・？！(・について考える．)
    };

    private String[] japaneses1 = {
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

    private int[] weights3 = {
//            1,3,9,11,10,        //あ行
            33,35,41,43,42,     //か行
            49,51,57,59,58,     //さ行
            21,23,29,31,30,     //た行
//            5,7,13,15,14,       //な行
            37,39,45,47,46     //は行
//            53,55,61,63,62,     //ま行
//            12,44,28,           //や行
//            17,19,25,27,26,     //ら行
//            4,6,64,20,          //わ行("ゑ"のみ別対処を考える．)
//            52,2,18,            //ん行
//            50,48,16,34,22      //。、・？！
    };

    private String[] japaneses3 = {
//            "あ","い","う","え","お",
            "が","ぎ","ぐ","げ","ご",
            "ざ","じ","ず","ぜ","ぞ",
            "だ","ぢ","づ","で","ど",
//            "な","に","ぬ","ね","の",
            "ば","び","ぶ","べ","ぼ"
//            "ま","み","む","め","も",
//            "や","ゆ","よ",
//            "ら","り","る","れ","ろ",
//            "わ","ゐ","ゑ","を",
//            "ん","っ","ー",
//            "。","、","・","？","！"
    };

    private int[] weights4 = {
//            1,3,9,11,10,        //あ行
//            33,35,41,43,42,     //か行
//            49,51,57,59,58,     //さ行
//            21,23,29,31,30,     //た行
//            5,7,13,15,14,       //な行
            37,39,45,47,46     //は行
//            53,55,61,63,62,     //ま行
//            12,44,28,           //や行
//            17,19,25,27,26,     //ら行
//            4,6,64,20,          //わ行("ゑ"のみ別対処を考える．)
//            52,2,18,            //ん行
//            50,48,16,34,22      //。、・？！
    };

    private String[] japaneses4 = {
//            "あ","い","う","え","お",
//            "が","ぎ","ぐ","げ","ご",
//            "ざ","じ","ず","ぜ","ぞ",
//            "だ","ぢ","づ","で","ど",
//            "な","に","ぬ","ね","の",
            "ぱ","ぴ","ぷ","ぺ","ぽ"
//            "ま","み","む","め","も",
//            "や","ゆ","よ",
//            "ら","り","る","れ","ろ",
//            "わ","ゐ","ゑ","を",
//            "ん","っ","ー",
//            "。","、","・","？","！"
    };

    private String [] numbers = {
            "0","1","2","3","4","5","6","7","8","9",".",","
    };

    private int[] weights2 = {
            26,1,3,9,25,17,11,27,19,10,2,4
    };

    //id保持用配列
    int[] idList_left;
    int[] idList_right;

    private static int MY_DATA_CHECK_CODE = 1;

    private TextToSpeech mTextToSpeech;

    ImageButton[] left_buttons;
    ImageButton[] right_buttons;

    //ボタンをコードで扱うために導入
    //左ボタン
    ImageButton button1;    ImageButton button2;
    ImageButton button3;    ImageButton button4;
    ImageButton button5;    ImageButton button6;
    //右ボタン
    ImageButton button7;    ImageButton button8;
    ImageButton button9;    ImageButton button10;
    ImageButton button11;   ImageButton button12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_j);

        toJResult=(TextView)findViewById(R.id.toJResult);
        linearLayout_left=(LinearLayout)findViewById(R.id.linearLayout1);
        linearLayout_right=(LinearLayout)findViewById(R.id.linearLayout2);

        //braillesの初期化
        brailles1 = new Braille[64];
        for(int i = 0;i<weights1.length;i++){
            Braille braille1 = new Braille(japaneses1[i],weights1[i]);
            brailles1[i] = braille1;
        }
        brailles2 = new Braille[64];
        for(int i = 0;i<weights2.length;i++){
            Braille braille2 = new Braille(weights2[i],numbers[i]);
            brailles2[i] = braille2;
        }
        brailles3 = new Braille[64];
        for(int i = 0;i<weights3.length;i++){
            Braille braille3 = new Braille(japaneses3[i],weights3[i]);
            brailles3[i] = braille3;
        }
        brailles4 = new Braille[64];
        for(int i = 0;i<weights4.length;i++){
            Braille braille4 = new Braille(japaneses4[i],weights4[i]);
            brailles4[i] = braille4;
        }

        numMode = false;
        inputNum = (TextView)findViewById(R.id.inputNum);
        inputNum.setVisibility(View.INVISIBLE);

        voicedMode = false;
        semi_voicedMode = false;

        //idList
        idList_left = new int[]{R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6};
        idList_right = new int[]{R.id.button7,R.id.button8,R.id.button9,R.id.button10,R.id.button11,R.id.button12};

        left_buttons = new ImageButton[]{button1, button2, button3, button4, button5, button6};
        right_buttons = new ImageButton[]{button7, button8, button9, button10, button11, button12};

        //関連付け
        for(int i = 0; i<6;i++){
            left_buttons[i] = (ImageButton) findViewById(idList_left[i]);
            right_buttons[i] = (ImageButton) findViewById(idList_right[i]);
        }

        //flag1,2の初期化
        both_flags_reset();
        both_background_reset();

        setListener();
    }

    //クリックイベント管理
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("タップしてる？");
            //左か右か判定メソッド
            if(judgeField(v) == FIELD_LEFT){
                button_left(v);
            }else if(judgeField(v) == FIELD_RIGHT){
                button_right(v);
            }
        }
    };

    //ドラッグイベント発生
    private View.OnTouchListener onTouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {

                case MotionEvent.ACTION_MOVE:

                    System.out.println("ドラッグしてる？");

                    v.startDrag(null,new View.DragShadowBuilder(null),v,0);
                    break;

                case MotionEvent.ACTION_BUTTON_PRESS:

                    System.out.println("タップしてる2？");

                    //左か右か判定メソッド
                    if(judgeField(v) == FIELD_LEFT){
                        button_left(v);
                    }else if(judgeField(v) == FIELD_RIGHT){
                        button_right(v);
                    }
            }

            return false;
        }
    };

    //ドラッグイベント管理
    private View.OnDragListener dragListener = new View.OnDragListener(){
        @Override
        public boolean onDrag(View v, DragEvent event) {

            switch (event.getAction()){
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("onDrag", v.getTag().toString() + "タグちゃん");
                    //左か右か判定メソッド
                    if(judgeField(v) == FIELD_LEFT){
                        button_left(v);
                    }else if(judgeField(v) == FIELD_RIGHT){
                        button_right(v);
                    }
                    //TODO 右でも左でもなかったときのエラー処理．
                    //button1.setActivated(true);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    for(int i = 0; i < flag1.length; i++) {
                        if(flag1[i] == 1) Log.d("打たれた場所", i + 1 + "番目");
                    }
                    break;
            }
            return true;
        }
    };

    public int judgeField(View v) {

        int judge_field = -1;

        switch(v.getId()){
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
                judge_field = FIELD_LEFT;
                break;

            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
            case R.id.button10:
            case R.id.button11:
            case R.id.button12:
                judge_field = FIELD_RIGHT;
                break;
        }

        return judge_field;
    }

    private void setListener() {

        for(int i = 0;i<6;i++){

            //left_buttons[i].setOnClickListener(onClickListener);
            left_buttons[i].setOnTouchListener(onTouchListener);
            left_buttons[i].setOnDragListener(dragListener);

            //right_buttons[i].setOnClickListener(onClickListener);
            right_buttons[i].setOnTouchListener(onTouchListener);
            right_buttons[i].setOnDragListener(dragListener);
        }
    }

    public void both_flags_reset() {
        for (int i = 0; i < 6; i++) {
            flag1[i] = 0;
            //this.findViewById(idList_left[i]).setActivated(false);
            left_buttons[i].setActivated(false);

            flag2[i] = 0;
            //this.findViewById(idList_right[i]).setActivated(false);
            right_buttons[i].setActivated(false);
        }
    }

    public void both_background_reset(){
        linearLayout_left.setBackgroundColor(Color.parseColor("#00000000"));
        linearLayout_right.setBackgroundColor(Color.parseColor("#00000000"));
    }

    //結局しようせず．笑
    public void both_background_input(){
        linearLayout_left.setBackgroundColor(Color.parseColor("#BFE0FFFF"));
        linearLayout_right.setBackgroundColor(Color.parseColor("#BFE0FFFF"));
    }

    public void left_background_reset(){
        linearLayout_left.setBackgroundColor(Color.parseColor("#00000000"));
        linearLayout_right.setBackgroundColor(Color.parseColor("#BFE0FFFF"));
    }

    public void right_background_reset(){
        linearLayout_left.setBackgroundColor(Color.parseColor("#BFE0FFFF"));
        linearLayout_right.setBackgroundColor(Color.parseColor("#00000000"));
    }


    //もともとonClickに紐付いていたが，onTouchListener導入で使用場所変更．
    public void button_left(View v){

        //濁点，半濁点モード解除のタイミング：移動してもONだったらOFFにする．
        right_background_reset();

        //使用フィールド判定
        if(field == FIELD_RIGHT){
            reset_right();
            //使用フィールド変更の際に加える．
            translatedText += addText;
            field = FIELD_LEFT;

            //移動してもONだったらOFFに
            voicedMode = VOICEDMODE_OFF;
            semi_voicedMode = SEMIVOICEDMODE_OFF;


            //switch文で綺麗にできる．
            if(weight == 16) voicedMode = VOICEDMODE_ON;
            System.out.println("(button_left)voicedMode:" + voicedMode);

            if(weight == 32) semi_voicedMode = SEMIVOICEDMODE_ON;
            System.out.println("(button_left)semi_voicedMode:" + semi_voicedMode);

            if(weight == 60) {
                numMode = NUMMODE_ON;
                //System.out.println("(button_left)numMode1:" + numMode);
            }else if(numMode == NUMMODE_ON) {
                numCount = translatedText.length() - temp_length;
                System.out.println("(button_left)numCount1:" + numCount);
            }

        }
        //タグ付けした整数の取得
        int index = Integer.parseInt(v.getTag().toString());
        //System.out.println(index);

        //ボタン押下判定(メソッド化したい．)
        if (flag1[index] == BUTTON_OFF) {
            this.findViewById(idList_left[index]).setActivated(true);
            flag1[index] = BUTTON_ON;
        } else if (flag1[index] == BUTTON_ON) {
            this.findViewById(idList_left[index]).setActivated(false);
            flag1[index] = BUTTON_OFF;
        }

        //翻訳判定
        System.out.println("(button_left)numMode2:"+numMode);
        System.out.println("(button_left)numCount2:" + numCount);

        //numModeがOFF，numCountが4以上なら，numCountが0以下の場合(メソッド化すべき)
        if((numMode == NUMMODE_OFF)||(numCount >= 4)||(numCount < 0)){
            temp_length = -1;
            numCount = 0;
            numMode = NUMMODE_OFF;
            judge();
        }else if(numMode == NUMMODE_ON){
            // 4文字を数える処理，
            // 削除した際のエラー処理，
            // 消去時のモード変更

            if(temp_length == -1){
                temp_length = translatedText.length();
            }
            judgeNum();
        }

    }

    public void button_right(View v){

        left_background_reset();

        if(field == FIELD_LEFT){
            reset_left();

            translatedText += addText;
            field = FIELD_RIGHT;

            //移動してもONだったらOFFに
            voicedMode = VOICEDMODE_OFF;
            semi_voicedMode = SEMIVOICEDMODE_OFF;

            if(weight == 16) voicedMode = VOICEDMODE_ON;
            System.out.println("(button_right)voicedMode:" + voicedMode);

            if(weight == 32) semi_voicedMode = SEMIVOICEDMODE_ON;
            System.out.println("(button_right)semi_voicedMode:" + semi_voicedMode);

            if(weight == 60) numMode = NUMMODE_ON;
            System.out.println("(button_right)numMode1:"+numMode);

            if(numMode == NUMMODE_ON) numCount = translatedText.length() - temp_length;
            System.out.println("(button_right)numCount1:" + numCount);

        }

        int index = Integer.parseInt(v.getTag().toString());
        //System.out.println(index);

        //ボタン押下判定(メソッド化しよう．)
        if (flag2[index] == BUTTON_OFF) {
            this.findViewById(idList_right[index]).setActivated(true);
            flag2[index] = BUTTON_ON;
        } else if (flag2[index] == BUTTON_ON) {
            this.findViewById(idList_right[index]).setActivated(false);
            flag2[index] = BUTTON_OFF;
        }
//        switch (v.getId()){
//            case R.id.button7:
//                button7();
//                break;
//            case R.id.button8:
//                button8();
//                break;
//            case R.id.button9:
//                button9();
//                break;
//            case R.id.button10:
//                button10();
//                break;
//            case R.id.button11:
//                button11();
//                break;
//            case R.id.button12:
//                button12();
//                break;
//        }
        System.out.println("(button_right)numMode2:" + numMode);
        System.out.println("(button_right)numCount2:" + numCount);



        //翻訳判定(メソッド化する)
        if((numMode == NUMMODE_OFF)||(numCount == 4)||(numCount < 0)){
            temp_length = -1;
            numCount = 0;
            numMode = NUMMODE_OFF;
            judge();
        }else if(numMode == NUMMODE_ON){

            if(temp_length == -1){
                temp_length = translatedText.length();
            }
            judgeNum();
        }

    }

    //重み計算メソッド
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


    //数値判定メソッド
    public void judgeNum() {
        addText = "";

        //TODO 数符 + 数符のエラー処理 アラートダイアログとか出すべきかな〜，句点読点の処理，50音に加えて，ゃなどの実装．
        //点字がないときどうするか考えねば．
        //空白検知しないといけないかもしれない．https://www.slideshare.net/EijiSato/ss-47944764

        //重み計算
        weight_calc();

        System.out.println(field);

        //使用フィールド変更のタイミングでカウントさせるか

        //ここはメソッド化できる．
        for(int i = 0;i < weights2.length;i++){
            System.out.println("weight2 = " + weight);
            //System.out.println("brailles = " + brailles[i].getCode());

            if(weight == brailles2[i].getWeight()){
                addText = brailles2[i].getNumber();
                System.out.println("brailles = " + brailles2[i].getNumber());
                break;
            }else{
                //TODO 一致しなかった場合，ひらがなを表示しなければならない．その際，数字モードが溶ける(表示のみ，モード変換はaddText格納時)
                addText="";
            }
        }

        toJResult.setText(translatedText + addText);

        if(weight==0) both_background_reset();

    }

    public void judge(){ //翻訳判定

        addText = "";

        //重み計算
        weight_calc();

        //数字モードかどうか判定

        if(weight == 60){
            if(numMode != NUMMODE_ON){
                //numMode = NUMMODE_ON;
                inputNum.setVisibility(View.VISIBLE);
                addText = "";
                toJResult.setText(translatedText);
            }
        }else{
            numMode = NUMMODE_OFF;
            inputNum.setVisibility(View.INVISIBLE);
        }

        if(voicedMode == VOICEDMODE_OFF && semi_voicedMode == VOICEDMODE_OFF){
            if(numMode == NUMMODE_OFF){
                for(int i = 0;i < weights1.length;i++){
                    System.out.println("weight1 = " + weight);
                    //System.out.println("brailles = " + brailles[i].getCode());

                    if(weight == brailles1[i].getWeight()){
                        addText = brailles1[i].getS_japanese();
                        System.out.println("brailles = " + brailles1[i].getS_japanese());
                        break;
                    }else{
                        addText="";
                    }
                }
            }else if(numMode == NUMMODE_ON){
                for(int i = 0;i < weights2.length;i++){
                    System.out.println("weight2 = " + weight);
                    //System.out.println("brailles = " + brailles[i].getCode());

                    if(weight == brailles2[i].getWeight()){
                        addText = brailles2[i].getNumber();
                        System.out.println("brailles = " + brailles2[i].getNumber());
                        break;
                    }else{
                        addText="";
                    }
                }
            }
        }else if(voicedMode == VOICEDMODE_ON){
            for(int i = 0;i < weights3.length;i++){
                System.out.println("weight3 = " + weight);
                //System.out.println("brailles = " + brailles[i].getCode());

                if(weight == brailles3[i].getWeight()){
                    addText = brailles3[i].getS_japanese();
                    System.out.println("brailles = " + brailles3[i].getS_japanese());
                    break;
                }else{
                    addText="";
                }
            }
        }else if(semi_voicedMode == SEMIVOICEDMODE_ON){
            for(int i = 0;i < weights4.length;i++){
                System.out.println("weight4 = " + weight);
                //System.out.println("brailles = " + brailles[i].getCode());

                if(weight == brailles4[i].getWeight()){
                    addText = brailles4[i].getS_japanese();
                    System.out.println("brailles = " + brailles4[i].getS_japanese());
                    break;
                }else{
                    addText="";
                }
            }
        }

        toJResult.setText(translatedText + addText);

        if(weight==0){
            both_background_reset();
        }

    }

    //左テーブル削除
    public void reset_left(){

        for(int i=0;i<6;i++){
            flag1[i]=0;
            left_buttons[i].setActivated(false);
        }

    }

    //右テーブル削除
    public void reset_right(){

        for(int i=0;i<6;i++){
            flag2[i]=0;
            right_buttons[i].setActivated(false);
        }

    }

    //一文字削除
    public void del1(View v){

        if(translatedText.length() > 0) {
            if(addText.isEmpty()){
                translatedText = translatedText.substring(0, translatedText.length()-1);
            }else{
                translatedText += addText;
                translatedText = translatedText.substring(0, translatedText.length()-1);
                addText = "";
            }
        }

        if(numMode == NUMMODE_ON){
            numCount = translatedText.length() - temp_length;
            if(numCount < 0){
                numMode = NUMMODE_OFF;
                temp_length = -1;
                numCount = 0;
                inputNum.setVisibility(View.INVISIBLE);
            }
        }


        toJResult.setText(translatedText);

        both_background_reset();
        both_flags_reset();

    }

    //全削除
    public void delAll(View v){

        both_background_reset();
        both_flags_reset();

        numMode = NUMMODE_OFF;
        temp_length = -1;
        numCount = 0;
        inputNum.setVisibility(View.INVISIBLE);

        addText = "";
        translatedText = "";

        toJResult.setText("");
    }

    public void copy(View v){

        //現在のテキストの取得
        copy = translatedText + addText; 

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TextToSpeech を解放する
        if (mTextToSpeech != null) {
            mTextToSpeech.shutdown();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // テキスト読み上げ可能チェックから戻った場合
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // 音声リソースが見つかったので TextToSpeech を開始する (-> onInit)
                mTextToSpeech = new TextToSpeech(this, this);
            } else {
                // 音声リソースがなければダウンロードする
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }

    public void speak(View v) {
        if (mTextToSpeech == null) {
            // 初回はテキスト読み上げ可能かチェックする
            Intent checkIntent = new Intent();
            checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
            startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
        } else {
            // テキストを読み上げる
            speech();
        }
    }

    /**
     * TextToSpeech のエンジンが初期化されたときに呼ばれます。
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // 日本語に対応していれば日本語に設定する （無くても良い）
            Locale locale = Locale.JAPANESE;
            if (mTextToSpeech.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE) {
                mTextToSpeech.setLanguage(locale);
            } else {
                Toast.makeText(this, "It does not support the Japanese.", Toast.LENGTH_SHORT).show();
            }
            // テキストを読み上げる
            speech();
        } else {
            Toast.makeText(this, "TextToSpeech is not supported.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 入力されたメッセージを読み上げます。
     */
    private void speech() {
        // テキスト読み上げ中であれば停止する
        if (mTextToSpeech.isSpeaking()) {
            mTextToSpeech.stop();
        }
        // テキストを読み上げる
        String message = translatedText + addText;

        mTextToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
    }


}



