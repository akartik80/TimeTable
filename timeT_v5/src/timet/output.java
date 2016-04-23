/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timet;

import java.util.ArrayList;
import java.util.List;
import static timet.TimeT2.*;

/**
 *
 * @author molu
 */
public class output {
    public void printMat(Pair mat[][]) {
        int i, j, k;

        for (i = 0; i < c; i++) {
            for (j = 0; j < t; j++) {
                System.out.print(mat[i][j].first + " ");
            }
            System.out.println("");
        }
    }

    public void printOutput_new() {
        //out_new = new List[c][t][21];
        int i, j, k, flag;
        for (i = 0; i < c; i++) {
            System.out.println("For class " + classes[i] + " " + i);

            for (k = 0; k < 20; k++) {
                if (k % 4 == 0) {
                    System.out.print(days[(k / 4)]);
                    for (int x = days[k / 4].length(); x <= 15; x++) {
                        System.out.print(" ");
                    }
                }

                flag = 0;
                //System.out.print(out_new[i][k] + "\t");
                System.out.print(out_new1[i][k] + "\t");

//                if (k % 4 == 0) {
//                    System.out.print(lab_new[i][k/4]);
//                }
                if (flag == 0) {
                    //System.out.print("free\t");
                }

                if (k % 4 == 3) {
                    System.out.print("\t\t" + lab_new[i][k / 4]);
                    System.out.println();
                }
            }

        }
    }

    public void printOutput() {
        out_new = new ArrayList[c][21];
        out_new1 = new ArrayList[c][21];
        lab_new = new ArrayList[c][5];
        int i, j, k, flag;
        for (i = 0; i < c; i++) {
            //System.out.println("For class " + classes[i] + " " + i);

            for (k = 0; k < 20; k++) {
                out_new[i][k] = new ArrayList();
                out_new1[i][k] = new ArrayList();
                if (k % 4 == 0) {
                    //System.out.print(days[(k / 4)]);
                    lab_new[i][k / 4] = new ArrayList();
                    for (int x = days[k / 4].length(); x <= 15; x++) {
                        //System.out.print(" ");
                    }
                    for (j = 0; j < t; j++) {
                        if (lab[i][j][k / 4] == 1) {
                            //System.out.print(j + "\t");
                            lab_new[i][k / 4].add(j);
                            flag = 1;
                        }
                    }
                }

                flag = 0;
                for (j = 0; j < t; j++) {
                    if (out[i][j][k] == 1) {
                        //System.out.print(j + "\t");
                        out_new[i][k].add(j);
                        if (!out_new1[i][k].contains(out1[i][j][k])) {
                            out_new1[i][k].add(out1[i][j][k]);
                        }
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    //System.out.print("free\t");
                }

                if (k % 4 == 3) {
                    //System.out.println();
                }
            }

        }
    }
    
    public void teachList() {
        int i, j, k;
        tt = new List[t][20];
        tt_lab = new List[t][5];

        for (i = 0; i < t; i++) {
            for (j = 0; j < 20; j++) {
                tt[i][j] = new ArrayList();
                for (k = 0; k < c; k++) {
                    if (out[k][i][j] == 1) {
                        tt[i][j].add(k);
                    }
                }

                if (j % 4 == 0) {
                    tt_lab[i][j / 4] = new ArrayList();
                    for (k = 0; k < c; k++) {
                        if (lab[k][i][j / 4] == 1) {
                            tt_lab[i][j / 4].add(k);
                        }
                    }
                }
            }
        }
    }

}
