package android.lifeistech.com.braillewords;

public class Braille {

    private String code;
    private int weight;
    private char japanese;
    private int res;

    Braille() {
    }

    Braille(String code, char japanese) {
        this.code = code;
        this.japanese = japanese;
    }

    Braille(char japanese,int weight) {
        this.japanese = japanese;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
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
