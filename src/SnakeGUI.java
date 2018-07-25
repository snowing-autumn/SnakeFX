import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class SnakeGUI extends Application {
    private Scene primaryScene;
    private Scene openScene;
    private Scene startScene;
    private Scene endScene;
    private Stage tempStage=new Stage();
    private Snake snake;
    private Image Up;
    private Image Down;
    private Image Left;
    private Image Right;
    private Image food;
    private Image tail;
    private Canvas SnakeCanvas;
    private Button next;
    private Button pause;
    private Label goals;
    private Label name;
    private Label Space;
    private TextField Goals;
    private Label Name;
    private HBox pane;
    private Font font=new Font(19);
    private Boolean running=true;
    private static int Score=0;
    private static int flash=300;


    @Override
    public void start(Stage primaryStage)  {
        tempStage.getIcons().add(new Image("rsc/food.png"));
        tempStage.setTitle("牛肉面与贪吃蛇   姓名：丁志成  学号：320170940351");
        initOpen();
        tempStage.setScene(primaryScene);
        tempStage.setWidth(900);
        tempStage.setHeight(600);
        tempStage.show();
    }

    private void initOpen(){
        BorderPane base=new BorderPane();
        base.setMaxSize(900,600);
        base.setStyle("-fx-background-image: url(rsc/Open2.jpg)");
        Button OK=new Button("Start");
        OK.setStyle("-fx-background-radius: 20;-fx-background-color: #00EEBB;-fx-border-radius:6 ; -fx-font: bold italic 20pt Arial; -fx-effect: dropshadow(one-pass-box,black ,8,0.0,2,0)");
        OK.setMinHeight(50);
        OK.setMinWidth(150);
        OK.setOnAction(e->{
            initGame();
            tempStage.setScene(startScene);
            tempStage.show();
        });
        base.setCenter(OK);
        openScene=new Scene(base);
        primaryScene=openScene;
    }

    private void initGame(){
        HBox hBox=new HBox();
        Space=new Label();
        goals=new Label("得分：");
        goals.setFont(font);
        name=new Label("姓名：");
        name.setFont(font);
        next=new Button("",new ImageView(new Image("rsc/Start.png")));
        next.setMinHeight(110);
        next.setFocusTraversable(false);
        next.setMinWidth(110);
        next.setOnAction(e->{
            running=true;
        });
        pause=new Button("",new ImageView(new Image("rsc/pause.png")));
        pause.setMinWidth(110);
        pause.setFocusTraversable(false);
        pause.setMinHeight(110);
        pause.setOnAction(e->{
            running=false;
        });
        Goals=new TextField(""+Score);
        Goals.setDisable(true);
        Goals.setFocusTraversable(false);
        Goals.setMaxWidth(120);
        Goals.setFont(font);
        Name=new Label("Admin");
        Name.setFont(font);
        Name.setFocusTraversable(false);
        Name.setMaxWidth(120);
        GridPane gridPane=new GridPane();
        gridPane.setPadding(new Insets(200,0,200,20));
        gridPane.addRow(4,name,Name);
        gridPane.addRow(5,goals,Goals);
        gridPane.addRow(7,pause,next);
        snake=new Snake();
        pane=new HBox();
        drawSnake(snake);
        pane.getChildren().add(SnakeCanvas);
        hBox.getChildren().addAll(pane,gridPane);
        startScene=new Scene(hBox);
        tempStage.setWidth(1080);
        tempStage.setHeight(680);
        primaryScene=startScene;
    }

    private void drawSnake(Snake snake){
        SnakeCanvas=new Canvas();
        SnakeCanvas.setHeight(680);
        SnakeCanvas.setWidth(800);
        pane.setFocusTraversable(true);
        pane.setOnKeyPressed(e->{
            KeyCode key=e.getCode();
            switch (key){
                case UP:if(snake.getY()[1]!=snake.getY()[0]-20) snake.setTendition(Snake.Tendition.U);break;
                case DOWN:if(snake.getY()[1]!=snake.getY()[0]+20) snake.setTendition(Snake.Tendition.D);break;
                case LEFT:if(snake.getX()[1]!=snake.getX()[0]-20) snake.setTendition(Snake.Tendition.L);break;
                case RIGHT:if(snake.getX()[1]!=snake.getX()[0]+20) snake.setTendition(Snake.Tendition.R);break;
                case SPACE:running=!running;break;
            }
        });
        Up=new Image("rsc/up.png");
        Down=new Image("rsc/down.png");
        Left=new Image("rsc/left.png");
        Right=new Image("rsc/right.png");
        food=new Image("rsc/food.png");
        tail=new Image("rsc/tile.png");
        pane.setStyle("-fx-background-image: url(rsc/background.png)");
        GraphicsContext graphicsContext=SnakeCanvas.getGraphicsContext2D();
        Thread draw=new Thread(()->{
            try{
                while (snake.isLive()) {
                    if (running) {
                        graphicsContext.clearRect(0, 0, 800, 680);
                        switch (snake.getTendition()) {
                            case R:
                                graphicsContext.drawImage(Right, snake.getX()[0], snake.getY()[0], 20, 20);
                                break;
                            case D:
                                graphicsContext.drawImage(Down, snake.getX()[0], snake.getY()[0], 20, 20);
                                break;
                            case L:
                                graphicsContext.drawImage(Left, snake.getX()[0], snake.getY()[0], 20, 20);
                                break;
                            case U:
                                graphicsContext.drawImage(Up, snake.getX()[0], snake.getY()[0], 20, 20);
                                break;
                        }
                        for (int i = 1; i < snake.getLength(); i++)
                            graphicsContext.drawImage(tail, snake.getX()[i], snake.getY()[i], 20, 20);

                        snake.eat();
                        if(300-(int)Math.sqrt(Score)*25>50)
                        flash=300-(int)Math.sqrt(Score)*25;
                        graphicsContext.drawImage(food,snake.getFoodx(),snake.getFoody(),20,20);
                    }
                    Thread.sleep(50);
                }
                Platform.runLater(()->{
                    initEnd();
                });
            }catch (Exception e){
               System.out.print("A\n");
            }
        });
        Thread moving=new Thread(()->{
            try{
                while (snake.isLive()) {
                    if (running) {

                        snake.move();
                        Score=snake.getLength()-1;
                        System.out.println(Score);
                        Goals.setText(Score+"");
                    }
                    Thread.sleep(flash);
                }

            }catch (Exception e){
                System.out.print("B\n");
            }
        });
        draw.start();
        moving.start();
    }

    private void initEnd(){
        BorderPane base=new BorderPane();
        Label congratulation=new Label("\t\t\t\t\t你获得了"+Score+"分！");
        congratulation.setFont(new Font(30));
        base.setMaxSize(900,600);
        base.setStyle("-fx-background-image: url(rsc/Open2.jpg)");
        Button OK=new Button("重新开始");
        OK.setStyle("-fx-background-radius: 20;-fx-background-color: #00EEBB;-fx-border-radius:6 ; -fx-font: bold italic 20pt Arial; -fx-effect: dropshadow(one-pass-box,black ,8,0.0,2,0)");
        OK.setMinWidth(150);
        OK.setMinHeight(50);
        OK.setOnAction(e->{
            Score=0;
            initGame();
            tempStage.setScene(startScene);
            tempStage.show();
        });
        base.setTop(congratulation);
        base.setCenter(OK);
        endScene=new Scene(base);
        tempStage.setScene(endScene);
        tempStage.setWidth(900);
        tempStage.setHeight(600);
        tempStage.show();
    }


    @Override
    public void stop() throws Exception {
        super.stop();
        snake.setLive(false);
    }
}
