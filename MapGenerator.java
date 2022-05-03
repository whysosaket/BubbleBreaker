import java.awt.*;

public class MapGenerator {
    public int[][] map;
    public int brickWidth;
    public int brickHeight;

    public MapGenerator(int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;                    //adding this one to each and every brick ensures that it has not been intersected
            }
        }

        brickHeight = 150 / row;
        brickWidth = 540 / col;
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(map[i][j]>0){
                    g.setColor(Color.WHITE);
                    g.fillRect(j*brickWidth+80,i*brickHeight+50,brickWidth,brickHeight);

                    //for borders
                    g.setStroke( new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j*brickWidth+80,i*brickHeight+50,brickWidth,brickHeight);
                }
            }
        }}
        public void setBrickValue(int value,int row,int column){
            map[row][column]= value;

    }
}
