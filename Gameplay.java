import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play= false;        //so game doesn't play by itself
    private int score=0;                //initial score set to zero

    private int totalBrick= 21;         //number of intial bricks

    private Timer time;                 //imported timer
    private int delay=8;

    Random rn= new Random();

    private int playerX= 310;           //setting position
    private int ballposX= rn.nextInt(50,650);
    private int ballposY= 350;
    private int ballXdir=-1;
    private int ballYdir=-2;
    private boolean inc=true;
    private boolean bc=true;
    private int count=0;


    private MapGenerator map;

    public Gameplay(){
        map = new MapGenerator(3,7);    //map generator
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay, this);
        time.start();
    }

    public void paint(Graphics g){
        // background
        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);

        //drawing map
        map.draw((Graphics2D)g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);        //idk y error

        //the paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550,100,8);

        //the ball
        g.setColor(Color.RED);
        g.fillOval(ballposX, ballposY,20,20);

        //scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,590,30);

        //Winning Case
        if(totalBrick<=0){
            play=false;
            ballYdir=0;
            ballposY=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You Won!! ",280,300);

            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",260,350);
        }
        //losingCase
        if(ballposY>570){
            play=false;
            ballYdir=0;
            ballXdir=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over, Scores: ",190,300);

            g.setColor(Color.WHITE);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString(""+score,450,300);

            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",230,350);

        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();
        if(play){
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballYdir=-ballYdir;
                count++;
            }

            if(count%2==0&&count!=0){
                    int m=rn.nextInt(0,2),n=rn.nextInt(0,6);
                    if(map.map[m][n]==0) {
                        map.setBrickValue(1,m,n);
                        totalBrick++;
                    }
                    count++;
            }
            A:
            for(int i=0;i<map.map.length;i++){              //two maps because we are accessing the 2d array with obj map
                for(int j=0;j<map.map[0].length;j++){
                    if(map.map[i][j]>0){
                        int brickX=j*map.brickWidth+80;
                        int brickY=i*map.brickHeight+50;

                        int brickWidth=map.brickWidth;
                        int brickHeight=map.brickHeight;

                        Rectangle rect= new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect= new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect= rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBrick--;
                            score+=5;

                            if(ballposX+19<=brickRect.x||ballposX+1>=brickRect.x+brickRect.width){
                                ballXdir=-ballXdir;
                            }
                            else {
                                ballYdir=-ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            if(score==50&&inc){
                ballYdir=2*ballYdir;
                ballXdir*=2;
                inc=false;
            }

            ballposX+=ballXdir;
            ballposY+=ballYdir;
            if(ballposX>670){
                ballXdir=-ballXdir;
            }
            if(ballposX<0){
                ballXdir=-ballXdir;
            }
            if(ballposY<0){
                ballYdir=-ballYdir;
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){
                play=true;
                ballposX=rn.nextInt(50,650);
                ballposY=350;
                ballXdir=-1;
                ballYdir=-2;
                playerX=310;
                score=0;
                totalBrick= 21;
                inc=true;
                map= new MapGenerator(3,7);

                repaint();
            }
        }
        if(e.getKeyCode()== KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX= 600;
            }
            else {
                moveRight();
            }
        }
        if(e.getKeyCode()== KeyEvent.VK_LEFT){
            if(playerX <=10){
                playerX= 10;
            }
            else {
                moveLeft();
            }
        }
    }
    public void moveRight(){
        play=true;
        playerX+=20;
    }
     public void moveLeft(){
        play=true;
        playerX+=-20;
    }



    @Override
    public void keyTyped(KeyEvent e) {
        //this method is also not necessary
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //this interface method is not necessary
    }
}
