package com.nirlocorp.snake.engine;

import com.nirlocorp.snake.classes.Coordinate;
import com.nirlocorp.snake.enums.Direction;
import com.nirlocorp.snake.enums.GameState;
import com.nirlocorp.snake.enums.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {

    public static  int GameWidth = 28;
    public static  int GameHeigh = 42;

    private List<Coordinate> walls = new ArrayList<>();
    private List<Coordinate> snake = new ArrayList<>();
    private List<Coordinate> apples = new ArrayList<>();

    private Direction currentDirection = Direction.Est;

    private GameState currentGameState = GameState.Running;

    private Random random = new Random();

    private boolean increase = false;

    public static int nbApple = 0;

    public static int apl;


    private Coordinate getSnakeHead(){

        return snake.get(0);
    }

    public GameEngine(){
        super();
    }

    public void initGame(){

        AddSnake();
        AddWalls();
        AddApples();

    }

    public void updateDirection(Direction newDirection){
            currentDirection = newDirection;
    }

    public Direction getCurrentDirection(){
        return currentDirection;
    }

    public void update(){
        switch (currentDirection){

            case Nord:
                UpdateSnake(0,-1);
                //System.out.println("go to nord");
                break;
            case Sud:
                UpdateSnake(0,1);
//                System.out.println("go to sud");
                break;
            case Est:
                UpdateSnake(1,0);
//                System.out.println("go to est");
                break;
            case Ouest:
                UpdateSnake(-1,0);
//                System.out.println("go to ouest");
                break;
        }

        //Condition de défaite
        for (Coordinate w: walls){
            if(snake.get(0).equals(w)){
                currentGameState = GameState.Lost;
            }
        }

        for ( int i=1; i<snake.size(); i++ ){
            if (getSnakeHead().equals(snake.get(i))){
                currentGameState=GameState.Lost;
                return;
            }
        }

        Coordinate appleToRemove = null;
        for ( Coordinate apple : apples){
            if (getSnakeHead().equals(apple)){
                appleToRemove=apple;
                nbApple+=1;
//                System.out.println("nb pomme :"+nbApple);

                increase = true;
            }
        }
        if (appleToRemove!=null){
            apples.remove(appleToRemove);
            AddApples();
        }

        if (currentGameState==GameState.Lost){
            apl = nbApple;
            nbApple = 0;

        }



    }


    public TileType[][] getMap(){
        TileType[][] map = new TileType[GameWidth][GameHeigh];

        for (int x=0; x<GameWidth; x++){
            for (int y=0; y<GameHeigh; y++){
                map[x][y]=TileType.Nothing;
            }
        }

        for(Coordinate wall: walls){
            map[wall.getX()][wall.getY()] = TileType.Wall;
        }


        for (Coordinate s: snake){
            map[s.getX()][s.getY()]= TileType.SnakeTail;
        }

        for (Coordinate a: apples){
            map[a.getX()][a.getY()]= TileType.Apple;
        }

        map[snake.get(0).getX()][snake.get(0).getY()] = TileType.SnakeHead;



        return map;
    }

    private void UpdateSnake(int x, int y) {

     int AppleEatX = snake.get(snake.size() - 1).getX();
     int AppleEatY = snake.get(snake.size() - 1).getY();

        for (int i = snake.size() - 1; i > 0; i--) {
            snake.get(i).setX(snake.get(i - 1).getX());
            snake.get(i).setY(snake.get(i - 1).getY());
        }

        if (increase){
            snake.add(new Coordinate(AppleEatX, AppleEatY));
            increase = false;
        }

        snake.get(0).setX(snake.get(0).getX() + x);
        snake.get(0).setY(snake.get(0).getY() + y);


    }

    private void AddSnake(){
        //snake.clear();

        //snake.add(new Coordinate(7,6));
        //snake.add(new Coordinate(6,6));
        snake.add(new Coordinate(5,6));
        snake.add(new Coordinate(4,6));
        snake.add(new Coordinate(3,6));
        snake.add(new Coordinate(2,6));
    }

    public void AddWalls(){

        for (int x=0; x< GameWidth; x++){
            walls.add(new Coordinate(x,0));
            walls.add((new Coordinate(x, GameHeigh-1)));
        }

        for (int y=1; y< GameHeigh; y++){
            walls.add(new Coordinate(0,y));
            walls.add(new Coordinate(GameWidth - 1, y));
        }
    }

    private void AddApples(){
        Coordinate coordinate = null;

        boolean added = false;
        while (!added){
            int x = 1 + random.nextInt( GameWidth - 2 );
            int y = 1 + random.nextInt( GameHeigh - 2 );

            coordinate = new Coordinate(x,y);
            boolean collision = false;

            for (Coordinate s: snake){
                if (s.equals(coordinate)){
                    collision=true;
                    snake.add(new Coordinate(x-1,y-1));
                    //break;
                }
            }


            for (Coordinate a: apples){
                if (a.equals(coordinate)){
                    collision=true;
                    snake.add(new Coordinate(x-1,y-1));
                    //break;
                }
            }

            added = !collision;
        }

        apples.add(coordinate);
    }

    public GameState getCurrentGameState(){
        return currentGameState;
    }


}
