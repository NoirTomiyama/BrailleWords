package android.lifeistech.com.braillewords;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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



}
