package mapping;

import java.util.LinkedList;

public class TileMap {
    private LinkedList<LinkedList<LinkedList<Tile>>> map; //maps -> rows -> cols
    private int savedCols;
    private int index;
    private boolean enableHistory;

    public TileMap(int cols, int rows){
        enableHistory = true;
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
        map.set(index, newMap(4, 4));
    }
    public void setNewMap(int cols, int rows){
        deleteMap();
        map.set(index, newMap(cols, rows));
    }
    public boolean floodFill(int row, int col, int oldVal, int newVal){
        if (row >= 0 && row <= getRows()-1 &&
                col >= 0 && col <= getCols()-1){
            if (oldVal == -1) oldVal = getTile(row,col).getID();
            if (getTile(row,col).getID() != oldVal) return true; //breaker
            if (oldVal == newVal) return true; //breaker
            setTileID(row,col,newVal);
            if (col > 0) floodFill(row, col-1, oldVal, newVal);
            if (row > 0) floodFill(row-1, col, oldVal, newVal);
            if (col < getCols()-1) floodFill(row, col+1, oldVal, newVal);
            if (row < getRows()-1) floodFill(row+1, col, oldVal, newVal);
        }
        return true;
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
    public void defaultMap(){
        int[][] map=
            {{0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,12,12,12,0},
            {11,11,11,0,0,0,12,12,12,0},
            {11,51,11,0,0,12,12,12,12,12},
            {11,52,11,0,0,0,0,9,0,0},
            {1,1,1,1,1,0,0,9,0,23},
            {5,5,2,2,2,1,1,1,1,1},
            {5,6,5,5,5,5,2,2,2,2}
        };
        setNewMap(map[0].length,map.length);
        for (int row = 0; row < getRows(); row++){
            for (int col = 0; col < getCols(); col++){
                setTileID(row,col,map[row][col]);
            }
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
    public boolean setTileID(int row, int col, int id){
        boolean inBounds = false;
        if (row >= 0 && row < getRows() &&
            col >= 0 && col < getCols()){
            map.get(index).get(row).get(col).setID(id);
            inBounds = true;
        }
        return inBounds;
    }
    public void mapToConsole(int i){
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
    public String mapToRawString(){
        StringBuilder mapString = new StringBuilder();
        for (int row = 0; row < getRows(); row++){
            for (int col = 0; col < getCols(); col++){
                mapString.append(map.get(index).get(row).get(col).getIDtoString());
            }
            mapString.append(System.getProperty("line.separator"));
        }
        return mapString.toString();
    }
    public String mapToCPlusPlus2dString(){
        StringBuilder mapString = new StringBuilder();
        String lb = System.getProperty("line.separator");
        mapString.append("//c++ 2D version"+lb);
        mapString.append("int map["+getRows()+"]["+getCols()+"]="+lb);
        mapString.append("{");
        for (int row = 0; row < getRows(); row++){
            mapString.append("{");
            for (int col = 0; col < getCols(); col++){
                mapString.append(map.get(index).get(row).get(col).getID());
                if (col != getCols()-1) mapString.append(",");
            }
            mapString.append("}");
            if (row != getRows()-1) mapString.append(","+lb);
        }
        mapString.append("};");
        return mapString.toString();
    }
    public String mapToCPlusPlus1dString(){
        StringBuilder mapString = new StringBuilder();
        String lb = System.getProperty("line.separator");
        mapString.append("//c++ 1D version"+lb);
        mapString.append("int map["+(getCols()*getRows())+"]="+lb);
        mapString.append("{");
        for (int row = 0; row < getRows(); row++){
            //mapString.append("{");
            for (int col = 0; col < getCols(); col++){
                mapString.append(map.get(index).get(row).get(col).getID());
                if (col != getCols()-1) mapString.append(",");
            }
            //mapString.append("}");
            if (row != getRows()-1) mapString.append(","+lb);
        }
        mapString.append(lb+"};");
        return mapString.toString();
    }
    public String mapToJava2dString(){
        StringBuilder mapString = new StringBuilder();
        String lb = System.getProperty("line.separator");
        mapString.append("//java 1D version"+lb);
        mapString.append("int[][] map="+lb);
        mapString.append("{");
        for (int row = 0; row < getRows(); row++){
            mapString.append("{");
            for (int col = 0; col < getCols(); col++){
                mapString.append(map.get(index).get(row).get(col).getID());
                if (col != getCols()-1) mapString.append(",");
            }
            mapString.append("}");
            if (row != getRows()-1) mapString.append(","+lb);
        }
        mapString.append("};");
        return mapString.toString();
    }
    public String mapToJava1dString(){
        StringBuilder mapString = new StringBuilder();
        String lb = System.getProperty("line.separator");
        mapString.append("//java 1D version"+lb);
        mapString.append("int map["+"]="+lb);
        mapString.append("{");
        for (int row = 0; row < getRows(); row++){
            //mapString.append("{");
            for (int col = 0; col < getCols(); col++){
                mapString.append(map.get(index).get(row).get(col).getID());
                if (col != getCols()-1) mapString.append(",");
            }
            //mapString.append("}");
            if (row != getRows()-1) mapString.append(","+lb);
        }
        mapString.append(lb+"};");
        return mapString.toString();
    }
    public String mapToActionScript(){
        StringBuilder mapString = new StringBuilder();
        String lb = System.getProperty("line.separator");
        mapString.append("//actionscript3 version"+lb);
        mapString.append("var map:Array = new Array("+lb);
        //mapString.append("{");
        for (int row = 0; row < getRows(); row++){
            mapString.append("new Array(");
            for (int col = 0; col < getCols(); col++){
                mapString.append(map.get(index).get(row).get(col).getID());
                if (col != getCols()-1) mapString.append(",");
            }
            mapString.append(")");
            if (row != getRows()-1) mapString.append(","+lb);
        }
        mapString.append(");");
        return mapString.toString();
    }
    public Tile getTile(int row, int col){
        return map.get(index).get(row).get(col);
    }
    //getters
    public int getRows(){ return map.get(index).size(); }
    public int getCols(){ return getRows() > 0 ? map.get(index).get(0).size() : -1; }
    public LinkedList<LinkedList<Tile>> getTileMap(){ return map.get(index); }
    public double saveMap(){
        double startTime = System.currentTimeMillis();
        if (enableHistory){
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
        return (System.currentTimeMillis()-startTime)/1000; //returns save time
    }
    public void undo(){
        if (index > 0 && enableHistory) index--;
    }
    public void redo(){
        if (index < map.size()-1 && enableHistory) index++;
    }
    public void setEnableHistory(boolean enableHistory){ this.enableHistory=enableHistory; }
}
