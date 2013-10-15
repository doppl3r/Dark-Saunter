import java.awt.*;
import java.util.LinkedList;

public class EnemyHandler {
    private LinkedList<Enemy> enemies;
    private int spawnTime;
    private int maxSpawnTime;

    public EnemyHandler(){
        enemies = new LinkedList<Enemy>();
        spawnTime = maxSpawnTime = 100;
    }
    public void draw(Graphics2D g){
        int size = enemies.size();
        for (int i = 0; i < size; i++){
            enemies.get(i).draw(g);
        }
    }
    public void update(double mod){
        //spawn random enemies
        if (spawnTime > 0) spawnTime--;
        else {
            spawnTime = maxSpawnTime;
            maxSpawnTime-=1;
            if (maxSpawnTime < 0) maxSpawnTime = 0;
            addEnemy(-1,-1,new Enemy1());
        }
        //update the location of each enemy
        for (int i = 0; i < enemies.size(); i++){
            enemies.get(i).update(mod);
            if (enemies.get(i).isDead()){
                enemies.remove(i);
            }
        }
    }
    public void addEnemy(double x, double y, Enemy enemy){
        int index = enemies.size();
        //randomize if specified
        if (x == -1) x = (int)(Math.random()*Window.getOriginalWidth());
        if (y == -1) y = (int)(Math.random()*Window.getOriginalHeight());
        enemies.add(enemy);
        enemies.get(index).setXY(x,y);
    }
    public void removeAll(){
        int size = size();
        for (int i = 0; i < size; i++){
            enemies.remove(0);
        }
    }
    public Enemy getEnemyAt(int i){ return enemies.get(i); }
    public int size(){ return enemies.size(); }
}