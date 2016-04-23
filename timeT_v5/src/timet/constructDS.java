/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import static timet.TimeT2.*;

/**
 *
 * @author molu
 */
public class constructDS {
    public void buildTeacherMap() {
        int i, j, k;

//        for (i = 0; i < t; i++) {
//            teacher_map.put(id_teacher.get(i+1), temp);
//        }
        for (String s : class_map.keySet()) {
            List<List<String>> kk;
            kk = class_map.get(s);

            for (j = 0; j < kk.size(); j++) {
                List<String> l = kk.get(j);
                //System.out.println(l);

                for (k = 2; k < l.size(); k++) {
                    //System.out.print(l.get(k) + " ");
                    //temp.clear();

                    if (teacher_map.containsKey(l.get(k))) {
                        HashMap<String, String> tmp = teacher_map.get(l.get(k));

                        if (!tmp.containsKey(s)) {
                            tmp.put(s, l.get(0));
                        } else {
                            tmp.replace(s, l.get(0));
                        }
                        //temp.put(s, l.get(0));
                        //System.out.println(s + " -- " + temp.size() + " __ " + l.get(0) + " , " + l.get(k));
                        //teacher_map.replace(l.get(k), temp);

                        teacher_map.replace(l.get(k), tmp);
                    } else {
                        HashMap<String, String> tmp = new LinkedHashMap<>();

                        tmp.put(s, l.get(0));

                        teacher_map.put(l.get(k), tmp);
                    }
                }

            }
            //System.out.println("");
        }

        for (String s : teacher_map.keySet()) {
            teacher_map.get(s).remove("__");
        }

//        for (String s : teacher_map.keySet()) {
//            //System.out.println(teacher_map.get(s).size());
//            for (String s1 : teacher_map.get(s).keySet()) {
//                System.out.println(s + " -> " + s1 + " : " + teacher_map.get(s).get(s1));
//            }
//            
//        }
    }

    public void buildTeacherLabMap() {
        int i, j, k;

//        for (i = 0; i < t; i++) {
//            teacher_map.put(id_teacher.get(i+1), temp);
//        }
        for (String s : lab_map.keySet()) {
            List<List<String>> kk;
            kk = lab_map.get(s);

            for (j = 0; j < kk.size(); j++) {
                List<String> l = kk.get(j);
                //System.out.println(l);

                for (k = 1; k < l.size(); k++) {
                    //System.out.print(l.get(k) + " ");
                    //temp.clear();

                    if (teacher_lab_map.containsKey(l.get(k))) {
                        HashMap<String, String> tmp = teacher_lab_map.get(l.get(k));

                        if (!tmp.containsKey(s)) {
                            tmp.put(s, l.get(0));
                        } else {
                            tmp.replace(s, l.get(0));
                        }
                        //temp.put(s, l.get(0));
                        //System.out.println(s + " -- " + temp.size() + " __ " + l.get(0) + " , " + l.get(k));
                        //teacher_map.replace(l.get(k), temp);

                        teacher_lab_map.replace(l.get(k), tmp);
                    } else {
                        HashMap<String, String> tmp = new LinkedHashMap<>();

                        tmp.put(s, l.get(0));

                        teacher_lab_map.put(l.get(k), tmp);
                    }
                }

            }
//            System.out.println("");
        }

//        for (String s : teacher_lab_map.keySet()) {
//            teacher_lab_map.get(s).remove("__");
//        }
        for (String s : teacher_lab_map.keySet()) {
//            System.out.println(teacher_lab_map.get(s).size());
            for (String s1 : teacher_lab_map.get(s).keySet()) {
                System.out.println(s + " -> " + s1 + " : " + teacher_lab_map.get(s).get(s1));
            }
        }
    }

    public void classes_to_id() {
        for (int i = 0; i < classes.length; i++) {
            classes_id.put(classes[i], i);
        }
    }

    public void generateMatrix(Pair mat[][], Pair mat1[][]) {
        int i, j;

        for (String ss : class_map.keySet()) {
            List temp = class_map.get(ss);

            for (i = 0; i < 10; i++) {
                if (classes[i].equals(ss)) {
                    break;
                }
            }

            int class_id = i;
            //System.out.println("temp size" + temp.size());

            for (i = 0; i < temp.size(); i++) {
                List temp2 = (List) temp.get(i);
                String name = (String) temp2.get(2);
                //System.out.print("name " + name + " ");
                int id = teacher_id.get(name);
                //System.out.println("id " + id);
                mat[class_id][id - 1] = new Pair(Integer.parseInt((String) temp2.get(1)), -1);
                mat1[class_id][id - 1] = new Pair(Integer.parseInt((String) temp2.get(1)), -1);
                //System.out.println("setting mat[" + class_id + "][" + (id - 1) + "]");
            }
        }
    }

    public void generateTeacherList() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("teachers.txt"));

        int i = 1;
        String line;

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            id_teacher.put(i, line.trim().toLowerCase());
            teacher_id.put(line.trim().toLowerCase(), i);
            i++;
        }
    }

}
