/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timet;

import java.util.List;
import static timet.TimeT2.*;

/**
 *
 * @author molu
 */
public class labs {
    public void allotLabs() {
        int i, j, k, flag;
        List<List<String>> l;

        //System.out.println(lab_map.size());
        for (String s : lab_map.keySet()) {
            l = lab_map.get(s);

            int class_id = classes_id.get(s);

            int count[] = new int[5];
            flag = 0;

            for (int uu = 0; uu < 5; uu++) {
                count[uu] = 0;
            }

            for (i = 0; i < l.size(); i++) {
                int r;
                //System.out.println(l.get(i));
                while (true) {
                    r = randomGenerator.nextInt(5);

                    if (count[r] == 0) {
                        count[r] = 1;
                        break;
                    }
                }

                for (k = r; k < r + 5; k++) {
                    int lk = k % 5;

                    for (j = 0; j < t; j++) {
                        if (lab[class_id][j][lk] == 1) {
                            break;
                        }
                    }
                    if (j != t) {
                        continue;
                    }

                    for (j = 1; j < l.get(i).size(); j++) {

                        //System.out.println(class_id + " : " + l.get(i).get(j));
                        int ii;
                        for (ii = 0; ii < c; ii++) {

                            if (lab[ii][teacher_id.get(l.get(i).get(j)) - 1][lk] == 1) {
                                break;
                            }
                        }

                        if (ii != c) {
                            break;
                        }
                    }

                    if (j < l.get(i).size()) {
                        continue;
                    }

                    for (j = 1; j < l.get(i).size(); j++) {
                        //System.out.println(class_id + " : " + l.get(i).get(j));
                        lab[class_id][teacher_id.get(l.get(i).get(j)) - 1][lk] = 1;
                    }
                    break;
                }
            }
        }
    }
   
}
