package structures;

public class pair {
    public char ch;
    public int count;

    public pair(char ch, int count){
        this.ch = ch;
        this.count = count;
    }

    @Override
    public String toString(){
        return " ["+this.ch+"-"+this.count+"] ";
    }
}
