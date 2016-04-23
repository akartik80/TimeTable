/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timet;

import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Math.ceil;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimeT {

    public static int out[][][];
    public static int c, t;
    public static String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    public static Random randomGenerator = new Random();

    public static String[] classes = {"3A", "3B", "3E", "2A", "2B", "2E", "1A", "1B", "1E"};

    public void generate() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("input.txt"));

        c = sc.nextInt();
        t = sc.nextInt();
        //System.out.println(-1/4);
        Pair mat[][] = new Pair[c][t];

        out = new int[c][t][21];

        int i, j, k;
        for (i = 0; i < c; i++) {
            for (j = 0; j < t; j++) {
                //mat stores the 
                mat[i][j] = new Pair(sc.nextInt(), -1);

                for (k = 0; k < 20; k++) {
                    out[i][j][k] = 0;
                }
            }
        }
        compute(mat, 0);
        print();
    }

    static void print() {
        System.out.println();
        int i, j, k, flag;
        /*
         for (i = 0; i < c; i++) {
         for (j = 0; j < t; j++) {
         for (k = 0; k < 20; k++) {
         if (out[i][j][k] == 1) {
         System.out.println("class: " + (i + 1) + " teacher: " + (j + 1) + " at day: " + days[(k / 4)] + " time: " + (k % 4 + 9));
         }
         }
         }
         }*/

        for (i = 0; i < c; i++) {
            System.out.println();
            System.out.println("For the class : " + classes[i]);
            //System.out.println();
            for (j = 0; j < 20; j++) {
                if (j % 4 == 0) {
                    System.out.print(days[(j / 4)]);
                    for (int x = days[j / 4].length(); x <= 15; x++) {
                        System.out.print(" ");
                    }
                }

                flag = 0;

                if (j % 4 == 3) {
                    flag = 1;
                }
                int flag1 = 0;

                for (k = 0; k < t; k++) {
                    //System.out.print(" yeah "+ j +" ");
                    if (out[i][k][j] == 1) {
                        flag1 = 1;
                        if (k <= 9) {
                            System.out.print(k + 1 + "\t");
                        } else {
                            System.out.print(k + 1 + "\t");
                        }
                    }
                }
                if (flag1 == 0) {
                    System.out.print("free\t");
                }
                if (flag == 1) {
                    System.out.println();
                }
            }
        }
    }

    //level indicates the hour number starting from 1 upto 20
    //intially level = 0
    static void compute(Pair mat[][], int level) {
        if (level == 20) {
            return;
        }

        int i, j, k;
        boolean ta[] = new boolean[t];

        for (i = 0; i < t; i++) {
            ta[i] = true;
        }

        for (i = 0; i < c; i++) {
            int temp = randomGenerator.nextInt(41);
            int count = 0;
            for (j = temp; count < t; j++, count++) {
                j = (j % t);
                if (mat[i][j].first > 0 && ta[j]) {
                    if (mat[i][j].second != -1) {
                        /*
                         if (ceil((double) mat[i][j].second / 4) * 4 >= level ) {
                         continue;
                         }
                         */
                        if (mat[i][j].second == level / 4) {
                            continue;
                        }
                    }
                    mat[i][j].first--;
                    out[i][j][level] = 1;
                    //class i teacher j and level incdicates the time
                    ta[j] = false;
                    mat[i][j].second = level / 4;
                    //System.out.println("class : " + (i + 1) + " teacher: " + (j + 1) + " at day: " + days[(level / 4)] + " time: " + level);
                    break;
                }
            }
        }
        compute(mat, level + 1);
    }
}

class Pair {

    int first, second;

    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }
}

class Teacher {

    String id;
    String name;
    int load;

    public Teacher(String id, String name) {
        this.id = id;
        this.name = name;
        load = 0;
    }
}

class Classes {

    String room_no;
    String name;

    public Classes(String room_no, String name) {
        this.room_no = room_no;
        this.name = name;
    }
}
