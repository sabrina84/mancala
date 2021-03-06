import java.util.Scanner;

/**
 * Created by Nowrin on 30-Nov-17.
 */
public class first {
     static int w1=15, w2=0,w3=0,singleLength=6,doubleLength=13;

    //copies the array
    private static int[] copyAr(int[] a1){
        int a2[] = new int[a1.length];
        for (int i = 0; i <a1.length ; i++) {
            a2[i] = a1[i];
        }
        return a2;
    }

    //initializes the field
    private static int[] init(){
        int ar[] = new int[doubleLength+1];
        for (int i = 0; i <doubleLength ; i++) {
            if(i!=singleLength )ar[i]=4;
        }
        return ar;
    }

    //prints the current field
   private static void printField(int[] field, int turn){
        if(turn==0) System.out.println("Initial field: ");
        else if(turn==3) System.out.println("Final state:");
        else {
            System.out.print("Current move by: ");
            if(turn==1) System.out.println("player");
            else System.out.println("Computer");
        }
     //  System.out.println("           12   11   10   9   8   7");
       System.out.println("           ---------------------------");

       System.out.print("        ");
        for (int i = doubleLength-1; i >singleLength ; i--) {
            System.out.printf("%4d",field[i]);
        }
        System.out.println();
        System.out.print("C-" + field[doubleLength] + "                       " +
                "             " + " P-" + field[singleLength]);

        System.out.println();
        System.out.print("        ");
        for (int i = 0; i <singleLength ; i++) {
            System.out.printf("%4d", field[i]);
        }
       System.out.println();
       System.out.println("           --------------------------");
      // System.out.println("           1   2   3   4   5   6");

   }

    //checks whether the game is over
    private static boolean isFinished(int field[]){
        int i;
        for ( i = 0; i <singleLength ; i++) {
            if(field[i]!=0)break;

        }
        if(i==singleLength){
            for (int j = singleLength+1; j <doubleLength ; j++) {
                field[doubleLength]+=field[j];
                field[j]=0;
            }
            return true;
        }
        for ( i = singleLength+1; i <doubleLength ; i++) {
            if(field[i]!=0)break;
        }
        if(i==doubleLength){
            for (int j = 0; j < singleLength; j++) {
                field[singleLength]+=field[j];
                field[j]=0;
            }
            return true;
        }
        return false;
    }

    //calculates the field after a move
    private static int calculateField(int[] field, int move,int turn){
        int a = field[move];
        field[move] = 0;
        move++;
        while (a>0){
            if(move>doubleLength)move=0;
            field[move]++;
            move++;
            a--;
        }
        move--;
        if(turn==1 && (move>=0 && move<singleLength) && field[move]==1 && field[2*singleLength-move]>0){
            field[singleLength]= field[singleLength]+1+field[2*singleLength-move];
            field[2*singleLength-move]=0;
            field[move]=0;
        }
        else if(turn==2 &&(move>singleLength && move<doubleLength) && field[move]==1 && field[2*singleLength-move]>0){
            field[doubleLength] = field[doubleLength]+1+field[2*singleLength-move];
            field[2*singleLength-move]=0;
            field[move]=0;
        }
        return move;
    }

    //finds most optimal move for computer
     static int findMostOptimal(int[] ar){
        int m=0,max=0;
        for (int i = singleLength+1; i <doubleLength ; i++) {
            int field[]= copyAr(ar);
            int a = field[i];
            field[i]=0;
            int move=i+1;
            while (a>0){
                if(move>doubleLength)move=0;
                field[move]++;
                move++;
                a--;
            }
            move--;
            //gets a free turn
            if(move==doubleLength){
                if(max<w1){
                    max=w1;
                    m=i;
                }
            }

            //captures opponent's balls
            if(move>singleLength && move<doubleLength && field[move]==1){
                int k = field[2*singleLength-move];
                if(k>0){
                    int b = k*w2;
                    if(b>max){
                        max=b;
                        m=i;
                    }
                }
            }

            //prevents capture
            if(field[2*singleLength-i]==0){
                int b = field[i]*w3;
                if(b>max){
                    max=b;
                    m=i;
                }
            }

        }

        //if none of these happens
        if(max==0){
            int m2=0,mm=0;
            for (int j = doubleLength-1; j >singleLength ; j--) {
                if(ar[j]>mm){
                    mm=ar[j];
                    m2=j;
                }
            }
            m=m2;
        }
        return m;
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int field[] = init();
        printField(field, 0);
        int turn=1,move,rpt;      //1 for player, 2 for computer
        /*move = in.nextInt();
        int a = calculateField(field,move,turn);
        printField(field,turn);
        System.out.println(a);*/
        while (!isFinished(field)){
            if(turn==1) {
                /*System.out.print("Make a move: ");
                move = in.nextInt();
                System.out.println();
                move--;
                while (move < 0 || move > 5 || field[move] == 0) {
                    System.out.println("Invalid move. Please give another move.");
                    move = in.nextInt();
                    move--;
                }
                rpt = calculateField(field, move, turn);
                printField(field,turn);
                if(rpt==singleLength){
                    System.out.println("You've got a free move.");
                    turn=1;
                }
                else turn=2;*/
                move = findMostOptimal(field);
                rpt = calculateField(field, move, turn);
                printField(field,turn);
                if(rpt==doubleLength)turn=1;
                else turn=2;

            } else{
                move = findMostOptimal(field);
                rpt = calculateField(field, move, turn);
                printField(field,turn);
                if(rpt==doubleLength)turn=2;
                else turn=1;

            }
        }
        printField(field,3);
        if(field[singleLength]>field[doubleLength]) System.out.println("player 1!");
        else if(field[doubleLength]>field[singleLength]) System.out.println("player 2");
        else System.out.println("Match drawn");
    }
}
