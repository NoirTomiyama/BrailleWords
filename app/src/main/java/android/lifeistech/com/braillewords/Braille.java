package android.lifeistech.com.braillewords;

public class Braille {

    private String code;
    private int weight;
    private char c_japanese;
    private String s_japanese;
    private int res;
    private String number;

    Braille() {
    }

    Braille(int weight,String number) {
        this.number = number;
        this.weight = weight;
    }

    public String getNumber() {
        return number;
    }
    Braille(String code, char japanese) {
        this.code = code;
        this.c_japanese = japanese;
    }

    Braille(String japanese,int weight) {
        this.s_japanese = japanese;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    Braille(int res, char japanese) {
        this.res = res;
        this.c_japanese = japanese;
    }

    public String getCode() {
        return code;
    }

    public char getC_japanese() {
        return c_japanese;
    }

    public String getS_japanese() {
        return s_japanese;
    }

    public int getRes(){
        return res;
    }
}
