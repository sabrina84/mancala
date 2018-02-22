import java.util.Scanner;

/**
 * Created by Nowrin on 17-Dec-17.
 */
public class fourth {
    static int w1=10, w2=15,w3=7,singleLength=6,doubleLength=13,moveNo=10;

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
            if(turn==1) System.out.println("player 1");
            else System.out.println("player 2");
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
        if(a==0)return 0;
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

    static int utility(int[] ar){
        int a = ar[doubleLength]- ar[singleLength],c=0,d=0;
        for (int i = 0; i <singleLength ; i++) {
            c+=ar[i];
        }
        for (int i = singleLength+1; i < doubleLength; i++) {
            d+=ar[i];
        }
        c-=d;
        d = 40*a +10*c;
        return d;

    }

    static int[] maxValue(int ar[], int alpha, int beta, int count,int k){
        //System.out.println("inside maxValue. count = "+count);
        if(count==moveNo || isFinished(ar)){
            int a = utility(ar);
            return new int[]{a,k};
        }
        int v[] = {Integer.MIN_VALUE,0};
        for (int i = singleLength+1; i <doubleLength ; i++) {
            if(ar[i]!=0) {
                int arr[] = copyAr(ar);
                calculateField(arr, i, 2);
                v[1] = i;
                int t[] = minValue(arr, alpha, beta, count + 1, i);
                v[0] = Math.max(v[0], t[0]);
                if (v[0] >= beta) return v;
                alpha = Math.max(alpha, v[0]);
            }
        }

        return v;
    }
    static int[] minValue(int ar[], int alpha, int beta, int count,int k){
        // System.out.println("inside minValue. count = "+count);

        if(count==moveNo || isFinished(ar)){
            int a = utility(ar);
            return new int[]{a,k};
        }
        int v[] = {Integer.MAX_VALUE,0};
        for (int i = 0; i <singleLength ; i++) {
            if(ar[i]!=0) {
                int arr[] = copyAr(ar);
                calculateField(arr, i, 1);
                v[1] = i;
                int t[] = maxValue(arr, alpha, beta, count + 1, i);
                v[0] = Math.min(v[0], t[0]);
                if (v[0] <= alpha) return v;
                beta = Math.max(beta, v[0]);
            }
        }

        return v;
    }

    static int mancala(int[] ar){
        // System.out.println("inside mancala");
        int a[] = maxValue(ar,Integer.MIN_VALUE,Integer.MAX_VALUE,1,-1);
        //  System.out.println("possible move = "+a[1]);
        return a[1];
    }


    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        //Scanner in = new Scanner(new File("input.txt"));
        int field[] = init();
        first f = null;
        printField(field, 0);
        int turn=1,move,rpt;      //1 for player, 2 for computer
        /*move = in.nextInt();
        int a = calculateField(field,move,turn);
        printField(field,turn);
        System.out.println(a);*/
        while (!isFinished(field)){
            if(turn==1) {
               /* System.out.print("Make a move: ");
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
                }*/
                move = mancala(field);
                rpt = calculateField(field, move, turn);
                printField(field,turn);
                if(rpt==doubleLength)turn=1;
                else turn=2;

            } else{
                move =f.findMostOptimal(field); //mancala(field);
                rpt = calculateField(field, move, turn);
                printField(field,turn);
                if(rpt==doubleLength)turn=2;
                else turn=1;

            }
        }
        printField(field,3);
        if(field[singleLength]>field[doubleLength]) System.out.println("Player 1 wins!");
        else if(field[doubleLength]>field[singleLength]) System.out.println("Player 2 wins!");
        else System.out.println("Match drawn");

    }
}
