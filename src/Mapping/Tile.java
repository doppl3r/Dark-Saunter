package mapping;

public class Tile {
    private int id;
    private static final int idLength = 4;

    public Tile(int id){
        this.id=id;
    }
    public int getID(){ return id; }
    public String getIDtoString(){
        int zeros = idLength;
        String stringID = ""+id;
        for (int i = id; i > 10; i/=10) zeros--;
        for (int i = 1; i < zeros; i++) stringID = "0"+stringID;
        return stringID;
    }
    //setters
    public void setID(int id){ this.id=id; }
    public static int getIdLength(){ return idLength; }
}
