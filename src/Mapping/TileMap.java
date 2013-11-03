package mapping;

import java.util.LinkedList;

public class TileMap {
    private LinkedList<LinkedList<LinkedList<Tile>>> map; //maps -> rows -> cols
    private int savedCols;
    private int index;

    public TileMap(int cols, int rows){
        savedCols = cols; //used to recover empty rows
        map = new LinkedList<LinkedList<LinkedList<Tile>>>();
        map.add(newMap(10, 8));
    }
    public void addRow(){
        map.get(index).add(new LinkedList<Tile>()); //adds an empty row
        for (int col = 0; col < getCols(); col++){
            map.get(index).get(getRows()-1).add(new Tile(0)); //adds a column (to each row)
        }
    }
    public void addCol(){
        for (int row = 0; row < getRows(); row++){
            map.get(index).get(row).add(new Tile(0));
        }
    }
    public void removeLastRow(){
        if (getRows() > 2){
            savedCols = getCols();
            map.get(index).remove(getRows()-1);
        }
    }
    public void removeLastCol(){
        if (getCols() > 2){
            for (int row = 0; row < getRows(); row++){
                map.get(index).get(row).removeLast();
            }
        }
    }
    public void setRows(int rows){
        if (rows < 2) rows = 2;
        if (rows > getRows()){ while (rows > getRows()){ addRow(); } }
        else if (rows < getRows()){ while (rows < getRows()){ removeLastRow(); } }
    }
    public void setCols(int cols){
        if (cols < 2) cols = 2;
        if (cols > getCols()){ while (cols > getCols()){ addCol(); } }
        else if (cols < getCols()){ while (cols < getCols()){ removeLastCol(); } }
    }
    public void resetMap(){
        deleteMap();
        map.set(index, newMap(10, 8));
    }
    public void clearMap(){
        for (int row = 0; row < getRows(); row++){
            for (int col = 0; col < getCols(); col++){
                map.get(index).get(row).get(col).setID(0);
            }
        }
    }
    public void deleteMap(){
        if (map.get(index) != null){
            while (map.get(index).size() > 0) map.get(index).remove(0);
        }
    }
    public LinkedList<LinkedList<Tile>> newMap(int cols, int rows){
        LinkedList<LinkedList<Tile>> newMap = new LinkedList<LinkedList<Tile>>();
        for (int row = 0; row < rows; row++){
            newMap.add(new LinkedList<Tile>()); //adds an empty row
            for (int col = 0; col < cols; col++){
                newMap.get(row).add(new Tile(0)); //adds a column (to each row)
            }
        }
        return newMap;
    }
    public void setTileID(int row, int col, int id){
        if (row >= 0 && row < getRows() &&
            col >= 0 && col < getCols()){
            map.get(index).get(row).get(col).setID(id);
        }
    }
    public void mapToString(int i){
        //draw to console
        System.out.println(i);
        for (int row = 0; row < getRows(); row++){
            for (int col = 0; col < getCols(); col++){
                System.out.print(map.get(i).get(row).get(col).getIDtoString());
            }
            System.out.println("");
        }
        System.out.println("");
    }
    public void mapToString(LinkedList<LinkedList<Tile>> newMap){
        for (int row = 0; row < newMap.size(); row++){
            for (int col = 0; col < newMap.get(0).size(); col++){
                System.out.print(newMap.get(row).get(col).getIDtoString());
            }
            System.out.println("");
        }
        System.out.println("");
    }
    public void historyToString(){
        for (int i = 0; i < map.size(); i++){
            mapToString(i);
        }
    }
    public Tile getTile(int row, int col){
        return map.get(index).get(row).get(col);
    }
    //getters
    public int getRows(){ return map.get(index).size(); }
    public int getCols(){ return getRows() > 0 ? map.get(index).get(0).size() : -1; }
    public LinkedList<LinkedList<Tile>> getTileMap(){ return map.get(index); }
    public void saveMap(){
        //create new temporary map!
        LinkedList<LinkedList<Tile>> newMap = new LinkedList<LinkedList<Tile>>();
        int rows = getRows();
        int cols = getCols();
        int id;
        for (int row = 0; row < rows; row++){
            newMap.add(new LinkedList<Tile>());
            for (int col = 0; col < cols; col++){
                id = map.get(index).get(row).get(col).getID();
                newMap.get(row).add(new Tile(id));
            }
        }
        //slice [BROKEN]
        while (map.size()-1 > index) map.removeLast();
        index = map.size();
        map.add(newMap);
    }
    public void undo(){
        if (index > 0) index--;
    }
    public void redo(){
        if (index < map.size()-1) index++;
    }
}
