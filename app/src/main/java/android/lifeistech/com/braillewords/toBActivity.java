package android.lifeistech.com.braillewords;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class toBActivity extends AppCompatActivity {

    ImageView[] imageView;

    public static Braille[] brailles;

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
            '。','、','・','？','！',
    };

    private int[] res = {
            R.drawable.a,R.drawable.i,R.drawable.u,R.drawable.e,R.drawable.o,
            R.drawable.ka,R.drawable.ki,R.drawable.ku,R.drawable.ke,R.drawable.ko,
            R.drawable.sa,R.drawable.shi,R.drawable.su,R.drawable.se,R.drawable.so,
            R.drawable.ta,R.drawable.chi,R.drawable.tsu,R.drawable.te,R.drawable.to,
            R.drawable.na,R.drawable.ni,R.drawable.nu,R.drawable.ne,R.drawable.no,
            R.drawable.ha,R.drawable.hi,R.drawable.hu,R.drawable.he,R.drawable.ho,
            R.drawable.ma,R.drawable.mi,R.drawable.mu,R.drawable.me,R.drawable.mo,
            R.drawable.ya,R.drawable.yu,R.drawable.yo,
            R.drawable.ra,R.drawable.ri,R.drawable.ru,R.drawable.re,R.drawable.ro,
            R.drawable.wa,R.drawable.wyi,R.drawable.wye,R.drawable.wo,
            R.drawable.n,R.drawable.ltu,R.drawable.haihun,
            R.drawable.kuten,R.drawable.touten,R.drawable.ten,R.drawable.question,R.drawable.exclamation
    };

    EditText editText; //翻訳する原文


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
        brailles = new Braille[55];
        for(int i = 0;i<res.length;i++){
            Braille braille = new Braille(res[i],japaneses[i]);
            brailles[i] = braille;
        }

        editText =(EditText)findViewById(R.id.editText);

    }

    public void translate(View v){

        //EditTextから文字列取得
        String str = editText.getText().toString();

        //文字列をchar型に変換
        char[] charArray = str.toCharArray();

        //Log.d("toBActivity:",str);

        //char型からint型の画像データを検索

        int[] res_data = new int[16];

        for(int i = 0; i < charArray.length;i++){
            for(int j = 0; j < res.length;j++){
                if(charArray[i] == brailles[j].getJapanese()){
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



}
