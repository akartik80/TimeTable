/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timet;

import java.util.List;
import static timet.TimeT2.*;

public class computeTimeTable {
    public void compute(Pair mat[][], int level) {
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
                if (mat[i][j].first > 0 && out[i][j][level] == 0 && level >= mat[i][j].second) {
                    //-----------------------------

                    List<List<String>> l = class_map.get(classes[i]);

                    int ii;
                    for (ii = 0; ii < l.size(); ii++) {
                        if (j == teacher_id.get(l.get(ii).get(2)) - 1) {
                            break;
                        }
                    }

                    if (ii == l.size()) {
                        System.out.println("Error : Size less");
                    }

                    //System.out.println(i + " here first: " + ii);
                    int ij;
                    //System.out.println("ii : " + ii + " - " + classes[i] + " -- " + l.get(ii).get(0));
                    for (ij = 2; ij < l.get(ii).size(); ij++) {
                        if (ta[teacher_id.get(l.get(ii).get(ij)) - 1] == false) {
                            break;
                        }
                    }

                    //System.out.println("here : " + ij);
                    if (ij != l.get(ii).size()) {
                        continue;
                    }

                    for (ij = 2; ij < l.get(ii).size(); ij++) {
                        ta[teacher_id.get(l.get(ii).get(ij)) - 1] = false;
                        out[i][teacher_id.get(l.get(ii).get(ij)) - 1][level] = 1;
                        //out[i][teacher_id.get(l.get(ii).get(ij)) - 1][level] = 1;
                    }
                    //&& ta[j] == true 
                    //-----------------------------
                    mat[i][j].first--;
                    //ta[j] = false;
                    mat[i][j].second = (level / 4) * 4 + 4;
                    //System.out.println("i : " + i + " j : " + j + " level : " + level);
                    //out[i][j][level] = 1;
                    break;
                }
            }
        }
        compute(mat, level + 1);
    }

    public void computeNew(Pair mat[][], int level) {
        if (level == 20) {
            return;
        }

        int i, j, k;
        boolean ta[] = new boolean[t];

        for (i = 0; i < t; i++) {
            ta[i] = true;
        }

        for (i = 0; i < c; i++) {
            int temp = randomGenerator.nextInt(46);
            int count = 0;

            for (j = temp; count < t; j++, count++) {
                j = (j % t);

                if (mat[i][j].first > 0) {
                    List<List<String>> l = class_map.get(classes[i]);

                    int ii;
                    for (ii = 0; ii < l.size(); ii++) {
                        if (j == teacher_id.get(l.get(ii).get(2)) - 1) {
                            break;
                        }
                    }

                    String sub = class_map.get(classes[i]).get(ii).get(0);
                    if (electives.common_sub_core.contains(sub)) {
                        continue;
                    }

                    int zz;
                    for (zz = 0; zz < t; zz++) {
                        if (out[i][zz][level] == 1) {
                            break;
                        }
                    }
                    if (zz != t) {
                        continue;
                    }

                    if (mat[i][j].first > 0 && out[i][j][level] == 0 && level >= mat[i][j].second) {
                        //-----------------------------

                        l = class_map.get(classes[i]);

                        for (ii = 0; ii < l.size(); ii++) {
                            if (j == teacher_id.get(l.get(ii).get(2)) - 1) {
                                break;
                            }
                        }

                        if (ii == l.size()) {
                            System.out.println("Error : Size less");
                        }

                        //System.out.println(i + " here first: " + ii);
                        int ij;
                        //System.out.println("ii : " + ii + " - " + classes[i] + " -- " + l.get(ii).get(0));
                        for (ij = 2; ij < l.get(ii).size(); ij++) {

//                            if (i == 8) {
//                                System.out.println("_____________" + (teacher_id.get(l.get(ii).get(ij)) - 1) + " ___" + level);
//                            }
                            //if (ta[teacher_id.get(l.get(ii).get(ij)) - 1] == false) {
                            int vj;
                            for (vj = 0; vj < c; vj++) {
                                if (out[vj][teacher_id.get(l.get(ii).get(ij)) - 1][level] == 1 || ta[teacher_id.get(l.get(ii).get(ij)) - 1] == false) {
                                    //System.out.println("............." + (teacher_id.get(l.get(ii).get(ij)) - 1) + " ___" + level);
                                    break;
                                }
                            }
                            if (vj < c) {
                                break;
                            }

                        }

                        //System.out.println("here : " + ij);
                        if (ij != l.get(ii).size()) {
                            continue;
                        }

                        for (ij = 2; ij < l.get(ii).size(); ij++) {
                            ta[teacher_id.get(l.get(ii).get(ij)) - 1] = false;
                            out[i][teacher_id.get(l.get(ii).get(ij)) - 1][level] = 1;
                            out1[i][teacher_id.get(l.get(ii).get(ij)) - 1][level] = teacher_map.get(l.get(ii).get(ij)).get(classes[i]);
                        }
                        //&& ta[j] == true 
                        //-----------------------------
                        mat[i][j].first--;
                        //ta[j] = false;
                        mat[i][j].second = (level / 4) * 4 + 4;
                        //System.out.println("i : " + i + " j : " + j + " level : " + level);
                        //out[i][j][level] = 1;
                        break;
                    }
                }
            }
        }
        computeNew(mat, level + 1);
    }
}
