package mapping;

import java.util.LinkedList;

public class TileMap {
    private LinkedList<LinkedList<Tile>> map;
    private int savedCols;

    public TileMap(int cols, int rows){
        savedCols = cols; //used to recover empty rows
        map = makeNewMap(cols, rows);
    }
    public void addRow(){
        map.add(new LinkedList<Tile>()); //adds an empty row
        if (getCols() <= 0){
            for (int col = 0; col < savedCols; col++){
                map.get(getRows()-1).add(new Tile(0)); //adds a column (to each row)
            }
        }
        else {
            for (int col = 0; col < getCols(); col++){
                map.get(getRows()-1).add(new Tile(0)); //adds a column (to each row)
            }
        }
    }
    public void addCol(){
        for (int row = 0; row < getRows(); row++){
            map.get(row).add(new Tile(0));
        }
    }
    public void removeLastRow(){
        int rows = getRows();
        if (rows > 2){
            savedCols = getCols();
            map.remove(getRows()-1);
        }
    }
    public void removeLastCol(){
        int cols = getCols();
        if (cols > 2){
            for (int row = 0; row < getRows(); row++){
                map.get(row).remove(cols-1);
            }
        }
    }
    public void resetMap(){
        deleteMap();
        map = makeNewMap(10,8);
    }
    public void clearMap(){
        for (int row = 0; row < getRows(); row++){
            for (int col = 0; col < getCols(); col++){
                map.get(row).get(col).setID(0);
            }
        }
    }
    public void deleteMap(){
        if (map != null){
            while (map.size() > 0) map.remove(0);
        }
    }
    public LinkedList<LinkedList<Tile>> makeNewMap(int cols, int rows){
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
            map.get(row).get(col).setID(id);
        }
    }
    public void mapToString(){
        for (int row = 0; row < getRows(); row++){
            for (int col = 0; col < getCols(); col++){
                System.out.print(map.get(row).get(col).getIDtoString());
            }
            System.out.println("");
        }
    }
    public Tile getTile(int row, int col){
        return map.get(row).get(col);
    }
    //getters
    public int getRows(){ return map.size(); }
    public int getCols(){ return getRows() > 0 ? map.get(0).size() : -1; }
}
