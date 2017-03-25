package android.lifeistech.com.braillewords;

/**
 * Created by RyotaTomiyama on 17/02/19.
 */
public class Braille {

    private String code;
    private char japanese;
    private int res;

    Braille() {
    }

    Braille(String code, char japanese) {
        this.code = code;
        this.japanese = japanese;
    }

    Braille(int res, char japanese) {
        this.res = res;
        this.japanese = japanese;
    }

    public String getCode() {
        return code;
    }

    public char getJapanese() {
        return japanese;
    }

    public int getRes(){
        return res;
    }
}
