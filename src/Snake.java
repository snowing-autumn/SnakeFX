public class Snake {
    private int length;
    private int[] x;
    private int[] y;
    private boolean isLive;
    int foodx;

    public int getFoodx() {
        return foodx;
    }

    public int getFoody() {
        return foody;
    }

    int foody;
    int oldFoodx;
    int oldFoody;

    enum Tendition{L,R,U,D}

    private Tendition tendition;


    public Tendition getTendition() {
        return tendition;
    }

    public void setTendition(Tendition tendition) {
        this.tendition = tendition;
    }
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int[] getX() {
        return x;
    }

    public void setX(int index,int x) {
        this.x [index]= x;
    }

    public int[] getY() {
        return y;
    }

    public void setY(int index,int y) {
        this.y[index] = y;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public int getOldFoodx() {
        return oldFoodx;
    }

    public int getOldFoody() {
        return oldFoody;
    }

    public Snake(){
        x=new int[1000];
        y=new int[1000];
        for(int i=0;i<1000;i++){
            x[i]=1000;
            y[i]=1000;
        }
        length=3;
        isLive=true;
        tendition=Tendition.R;
        x[0]=80;
        x[1]=60;
        y[0]=80;
        y[1]=80;
        x[2]=40;
        y[2]=80;
        foodx=120;
        foody=220;
        oldFoody=1000;
        oldFoodx=1000;

    }

    public void move(){
        for(int i=length-1;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (tendition){
            case U:y[0]-=20;if (y[0]<0)y[0]=620;if(x[0]<0)x[0]=780;if(y[0]>620)y[0]=0;if(x[0]>780)x[0]=0;break;
            case L:x[0]-=20;if (y[0]<0)y[0]=620;if(x[0]<0)x[0]=780;if(y[0]>620)y[0]=0;if(x[0]>780)x[0]=0;break;
            case D:y[0]+=20;if (y[0]<0)y[0]=620;if(x[0]<0)x[0]=780;if(y[0]>620)y[0]=0;if(x[0]>780)x[0]=0;break;
            case R:x[0]+=20;if (y[0]<0)y[0]=620;if(x[0]<0)x[0]=780;if(y[0]>620)y[0]=0;if(x[0]>780)x[0]=0;break;
        }

    }
    public void oldFood(){
        oldFoodx=foodx;
        oldFoody=foody;
    }

    public void newFood(){
        oldFood();
        foodx=((int)(Math.random()*780)/20*20);
        foody=((int)(Math.random()*620)/20*20);

    }

    public void eat(){
        if(x[0]==foodx&&y[0]==foody){
            newFood();
            length++;
        }
        for(int i=1;i<=length;i++){
            if(x[0]==x[i]&&y[0]==y[i])
                isLive=false;
        }
    }

}
