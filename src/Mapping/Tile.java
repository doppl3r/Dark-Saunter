package mapping;

public class Tile {
    private int id;

    public Tile(int id){
        this.id=id;
    }
    public int getID(){ return id; }
    public String getIDtoString(){ return id < 10 ? "0"+id : ""+id; } //0 to 99 digits

    //setters
    public void setID(int id){ this.id=id; }
}
