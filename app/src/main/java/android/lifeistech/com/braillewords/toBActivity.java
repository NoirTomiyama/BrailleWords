package android.lifeistech.com.braillewords;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import org.atilika.kuromoji.Tokenizer.Builder;
import org.atilika.kuromoji.Tokenizer.Mode;

public class toBActivity extends AppCompatActivity {

    ImageView[] imageView;

    public Braille[] brailles;

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

    private int[] res = {
            R.drawable.a_1,R.drawable.i_3,R.drawable.u_9,R.drawable.e_11,R.drawable.o_10,
            R.drawable.ka_33,R.drawable.ki_35,R.drawable.ku_41,R.drawable.ke_43,R.drawable.ko_42,
            R.drawable.sa_49,R.drawable.shi_51,R.drawable.su_57,R.drawable.se_59,R.drawable.so_58,
            R.drawable.ta_21,R.drawable.chi_23,R.drawable.tsu_29,R.drawable.te_31,R.drawable.to_30,
            R.drawable.na_5,R.drawable.ni_7,R.drawable.nu_13,R.drawable.ne_15,R.drawable.no_14,
            R.drawable.ha_37,R.drawable.hi_39,R.drawable.hu_45,R.drawable.he_47,R.drawable.ho_46,
            R.drawable.ma_53,R.drawable.mi_55,R.drawable.mu_61,R.drawable.me_63,R.drawable.mo_62,
            R.drawable.ya_12,R.drawable.yu_44,R.drawable.yo_28,
            R.drawable.ra_17,R.drawable.ri_19,R.drawable.ru_25,R.drawable.re_27,R.drawable.ro_26,
            R.drawable.wa_4,R.drawable.wyi_6,R.drawable.wye_22,R.drawable.wo_20,
            R.drawable.n_52,R.drawable.ltu_2,R.drawable.haihun_18,
            R.drawable.kuten_50,R.drawable.touten_48,R.drawable.ten_16,R.drawable.question_34,R.drawable.exclamation_22
    };

    EditText editText; //翻訳する原文

    private int lang;

    private static final int REQUEST_CODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_b);

        imageView = new ImageView[]{
                (ImageView)findViewById(R.id.toBI1),
                (ImageView)findViewById(R.id.toBI2),
                (ImageView)findViewById(R.id.toBI3),
                (ImageView)findViewById(R.id.toBI4),
                (ImageView)findViewById(R.id.toBI5),
                (ImageView)findViewById(R.id.toBI6),
                (ImageView)findViewById(R.id.toBI7),
                (ImageView)findViewById(R.id.toBI8),
                (ImageView)findViewById(R.id.toBI9),
                (ImageView)findViewById(R.id.toBI10),
                (ImageView)findViewById(R.id.toBI11),
                (ImageView)findViewById(R.id.toBI12),
                (ImageView)findViewById(R.id.toBI13),
                (ImageView)findViewById(R.id.toBI14),
                (ImageView)findViewById(R.id.toBI15),
                (ImageView)findViewById(R.id.toBI16)
        };

        //braillesの初期化
        brailles = new Braille[64];
        for(int i = 0;i<res.length;i++){
            Braille braille = new Braille(res[i],japaneses[i]);
            brailles[i] = braille;
        }

        editText =(EditText)findViewById(R.id.editText);

        // 言語選択 0:日本語、1:英語、2:オフライン、その他:General
        lang = 0;
    }

    public void translate(View v){

        //EditTextから文字列取得
        String str = editText.getText().toString().trim();

        //文字列をchar型に変換
        char[] charArray = str.toCharArray();

        //Log.d("toBActivity:",str);

        //char型からint型の画像データを検索

        int[] res_data = new int[16]; //16画像の表示制限

        for(int i = 0; i < charArray.length;i++){
            for(int j = 0; j < res.length;j++){
                if(charArray[i] == brailles[j].getC_japanese()){
                    res_data[i] = brailles[j].getRes();
                    break;
                }
            }
        }

        //画像データの表示

        for(int k = 0;k < res_data.length;k++){
            imageView[k].setImageResource(res_data[k]);
        }
    }

    public void voice(View v){
        // 音声認識が使えるか確認する
        try {
            // 音声認識の Intent インスタンス
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

            if(lang == 0){
                // 日本語
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.JAPAN.toString() );
            }
            else if(lang == 1){
                // 英語
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH.toString() );
            }
            else if(lang == 2){
                // Off line mode
                intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
            }
            else{
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            }

            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "音声を入力");
            // インテント発行
            startActivityForResult(intent, REQUEST_CODE);
        }
        catch (ActivityNotFoundException e) {
            //textView.setText("No Activity " );
        }
    }

    // 結果を受け取るために onActivityResult を設置
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // 認識結果を ArrayList で取得
            ArrayList<String> candidates = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (candidates.size() > 0) {
                // 認識結果候補で一番有力なものを表示
//                textView.setText(candidates.get(0));
//                textView2.setText(getKatakana(candidates.get(0)));
                editText.setText(zenkakuHiraganaToZenkakuKatakana(getKatakana(candidates.get(0))));
//                textView2.setText(candidates.get(1));
//                textView3.setText(candidates.get(2));
                for(int i = 0; i < candidates.size() ;i++){
                    System.out.println(candidates.get(i));
                }
            }
        }

        //textView2.setText(getKatakana("初日の出"));
        System.out.println(getKatakana("東京特許許可局"));
        System.out.println(zenkakuHiraganaToZenkakuKatakana("ジャヴァ・プログラミング"));

    }

    public static String getKatakana(String word) {
//        if (String.isNullOrEmpty(word))
//            return null;
        Builder builder = Tokenizer.builder();
        builder.mode(Mode.NORMAL);
        Tokenizer tokenizer = builder.build();
        List<Token> tokens = tokenizer.tokenize(word);
        StringBuilder sb = new StringBuilder();
        for (Token token : tokens)
            sb.append(token.getReading());
        return sb.toString();
    }

    public static String zenkakuHiraganaToZenkakuKatakana(String s) {
        StringBuffer sb = new StringBuffer(s);
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (c >= 'ァ' && c <= 'ン') {
                sb.setCharAt(i, (char)(c - 'ァ' + 'ぁ'));
            } else if (c == 'ヵ') {
                sb.setCharAt(i, 'か');
            } else if (c == 'ヶ') {
                sb.setCharAt(i, 'け');
            } else if (c == 'ヴ') {
                sb.setCharAt(i, 'う');
                sb.insert(i + 1, '゛');
                i++;
            }
        }
        return sb.toString();
    }



}
