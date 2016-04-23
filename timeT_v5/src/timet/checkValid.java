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
public class checkValid {
    public boolean checkValid() {
        int i, j;
        List<String> subjects;

        for (i = 0; i < c; i++) {
            for (j = 0; j < 20; j++) {
                subjects = out_new1[i][j];

                if (subjects.size() <= 1) {
                    // do nothing
                } else if (_electives.areAllParallellyRunningElectives(subjects)) {
                    // do nothing
                } else {
                    System.out.println("Sub " + subjects);
                    return false;
                }
            }
        }

        return true;        // if everything goes well
    }

}
