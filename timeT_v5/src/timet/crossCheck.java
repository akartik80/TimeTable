/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timet;

import java.util.ArrayList;
import static timet.TimeT2.*;

/**
 *
 * @author molu
 */
public class crossCheck {
    
    public void getOutput(Pair mat[][]) {

        System.out.println("Checking");

        int ii = 0;
        while (!_checkValid.checkValid()) {

            System.out.println("re-checking");

            out_new = new ArrayList[c][20];
            out_new1 = new ArrayList[c][20];
            lab_new = new ArrayList[c][5];

            for (int i = 0; i < c; i++) {
                for (int j = 0; j < t; j++) {
                    for (int k = 0; k < 20; k++) {
                        out[i][j][k] = 1;
                        out1[i][j][k] = "-";

//                        out_new[i][k].clear();
//                        out_new1[i][k].clear();
//                        lab_new[i][k/4].clear();
                        if (k < 5) {
                            lab[i][j][k] = 0;
                        }
                    }
                }
            }

            _labs.allotLabs();
            _electives.allotElectiveWish();
            _electives.allotReservedElectives(mat);
            _electives.onlyECE();
            //printOutput();
            _computeTimeTable.computeNew(mat, 0);
            //printOutput();
            //allotReservedElectives(mat);
            _reCheck.recheck(mat, 0);

            //allotCommon_core();
            _output.printOutput();
            _electives.allotExtraECE(mat);
            _output.teachList();
        }
    }

}
