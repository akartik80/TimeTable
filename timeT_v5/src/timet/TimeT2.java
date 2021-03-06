package timet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import static timet.GUI.time;
import static timet.Teach.teachers;

public class TimeT2 {

    static int out[][][];           // (c x t x 20)
    static String out1[][][];       // similar to out -> gives subject
    static int free_classes;
    static List out_new[][];        // class x level list of teachers (c x 20) 
    static List out_new1[][];       // similar to out_new -> gives subject
    static List lab_new[][];        // lab x level list of teachers (c x 5)
    static List tt[][];             // teacher x level list of classes (t * 20)
    static List tt_lab[][];
    static int lab[][][];           // c * t * 5
    static int c, t;
    static String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    static Random randomGenerator = new Random();
    static List it= new ArrayList<>();
    static List ece = new ArrayList<>();
    static HashMap<String, List< List<String>>> class_map = new HashMap<>();  //class -> (list of (sub, credit, teachers)
    static HashMap<String, HashMap<String, String>> teacher_map = new LinkedHashMap<>();  //teacher -> (list of (class, subject)
    static HashMap<Integer, String> id_teacher = new HashMap<>();       //id - > teacher
    static HashMap<String, Integer> teacher_id = new HashMap<>();       //teacher -> id
    static HashMap<String, List< List<String>>> lab_map = new HashMap<>(); // similar to class_map
    static HashMap<String, HashMap<String, String>> teacher_lab_map = new LinkedHashMap<>();

    static int basket_it, basket_ece;           // no of baskets
    static List<Integer> sub_IT_basket, sub_ECE_basket;     // number of subjects in each basket
    //static HashMap<Integer, List<List<String>>> electives_it, electives_ece;
    static HashMap<String, List<String>> electives_it = new HashMap<>();    // list of elective subjects (eg ML -> #basketno., credits, teachers list)
    //this conatins Subject -> (basket , credit, Teacher)
    static HashMap<String, List<String>> electives_ece = new HashMap<>();   // similar to electives_it

    static List<String> common_sub_elective = new ArrayList<>();        // it + ece (both elective)
    static List<String> common_sub_core = new ArrayList<>();            // it -> core + ece -> elective
    static List<String> only_it = new ArrayList<>();                    // it -> elective
    static List<String> only_ece = new ArrayList<>();                   // ece -> elective
    static List<List<String>> allot = new ArrayList<>();                // which all can be run in parallel
    static List<List<Pair>> allot1 = new ArrayList<>();                 // pair of basket numbers of each sub (it, ece)
    static List<List<String>> allot2 = new ArrayList<>();               // which all should be run in parallel
    static List<List<Integer>> core_level = new ArrayList<>();

    static String[] classes = {"3A", "3B", "3E", "2A", "2B", "2E", "1A", "1B", "1E"}; // class indexing
    static HashMap<String, Integer> classes_id = new HashMap<>();       // mapping of classes with indexing

    public static void generate() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        c = time.c;
        t = teachers.size();
      //  c = sc.nextInt();
       // t = sc.nextInt();
      //  it.add(6);
       // it.add(2);
        //ece.add(5);
        //ece.add(5);
        Pair mat[][] = new Pair[c][t];
        Pair mat1[][] = new Pair[c][t];

        out = new int[c][t][21];
        out1 = new String[c][t][21];
        lab = new int[c][t][5];

        int i, j;

        for (i = 0; i < c; i++) {
            for (j = 0; j < t; j++) {
                for (int k = 0; k < 21; k++) {
                    out[i][j][k] = 0;
                    out1[i][j][k] = "-";
                    if (k < 5) {
                        lab[i][j][k] = 0;
                    }
                }
            }
        }

        generateTeacherList();
        generateMapping();
        generateMatrix(mat, mat1);
        buildTeacherMap();

        classes_to_id();
        generateLabMap();
        buildTeacherLabMap();

//        for (String s : lab_map.keySet()) {
//            for (i = 0; i < lab_map.get(s).size(); i++) {
//                System.out.println(lab_map.get(s).get(i));
//            }
//        }
        allotLabs();

        for (i = 0; i < c; i++) {
            for (j = 0; j < t; j++) {
                if (mat[i][j] == null) {
                    mat[i][j] = new Pair(0, -1);
                    mat1[i][j] = new Pair(0, -1);
                }
//                System.out.print(mat[i][j].first + " ");
            }

//            System.out.println("");
        }

        getElectives();
        addElectives();
//        compute(mat, 0);

        //  printCor();
        //compute(mat, 0);
        allotElectiveWish();
        allotReservedElectives(mat);
        onlyECE();
        //printOutput();
        computeNew(mat, 0);
        //printOutput();
        //allotReservedElectives(mat);
        recheck(mat, 0);

        //allotCommon_core();
        printOutput();
        allotExtraECE(mat);
        teachList();

        getOutput(mat);
        //printOutput_new();

        //System.out.println("helloworls");
        if (checkValid()) {
            System.out.println("-------------------All is well------------------------");
        } else {
            System.out.println("We are sorry :( , something bad happened. Please run the application again");
        }
        //checkValid();
    }

    static void buildTeacherMap() {
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

    static void buildTeacherLabMap() {
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

    static boolean checkValid() {
        int i, j;
        List<String> subjects;

        for (i = 0; i < c; i++) {
            for (j = 0; j < 20; j++) {
                subjects = out_new1[i][j];

                if (subjects.size() <= 1) {
                    // do nothing
                } else if (areAllParallellyRunningElectives(subjects)) {
                    // do nothing
                } else {
                    System.out.println("Sub " + subjects);
                    return false;
                }
            }
        }

        return true;        // if everything goes well
    }

    static boolean areAllParallellyRunningElectives(List<String> subjects) {
        int i;
        List<String> subs;
        List<StringPair> list = new ArrayList<StringPair>();
        List<String> list2 = new ArrayList<String>();

        for (i = 0; i < allot2.size(); i++) {
            if (allot2.get(i).containsAll(subjects)) {
                return true;
            }
        }
//static HashMap<String, List<String>> electives_it = new HashMap<>();    // list of elective subjects (eg ML -> #basketno., credits, teachers list)        
        for (String s : electives_ece.keySet()) {
            //System.out.println("string " + s);
            subs = electives_ece.get(s);
            //System.out.println("dd" + subs.size());
            list.add(new StringPair(s, subs.get(0)));
        }

        for (i = 0; i < list.size(); i++) {
            list2.add(list.get(i).first);
        }

        if (!list2.containsAll(subjects)) {
            return false;
        }

        if (haveSameBasketNumbers(subjects, list)) {
            return true;
        }

        return false;
    }

    static void getOutput(Pair mat[][]) {

        System.out.println("Checking");

        int ii = 0;
        while (!checkValid()) {

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

            allotLabs();
            allotElectiveWish();
            allotReservedElectives(mat);
            onlyECE();
            //printOutput();
            computeNew(mat, 0);
            //printOutput();
            //allotReservedElectives(mat);
            recheck(mat, 0);

            //allotCommon_core();
            printOutput();
            allotExtraECE(mat);
            teachList();
        }
    }

    static boolean haveSameBasketNumbers(List<String> subjects, List<StringPair> list) {
        int i, j;
        HashMap<String, String> m = new HashMap<String, String>();

        for (i = 0; i < subjects.size(); i++) {
            for (j = 0; j < list.size(); j++) {
                if (subjects.get(i).equals(list.get(j).first)) {
                    m.put(list.get(j).second, list.get(j).first);
                }
            }
        }

//        System.out.println("subjects " + subjects);
//        System.out.println("list ");
//        for (i = 0; i < list.size(); i++) {
//            System.out.print(list.get(i).first + " ");
//        }
//        
//        System.out.println("Map ");
//        for (String s : m.keySet()) {
//            System.out.println(s + " " + m.get(s));
//        }
        if (m.size() == 1) {
            return true;
        }

        return false;
    }

    static void allotExtraECE(Pair mat[][]) {
        int i, j, k;

        for (i = 0; i < t; i++) {
            if (mat[2][i].first > 0) {

                for (j = 0; j < 5; j++) {
                    if (lab_new[2][j].size() == 0) {
                        lab_new[2][j].add(-1);
                        lab_new[2][j].add(i);
                    }
                }

            }
        }
    }

    static void classes_to_id() {
        for (int i = 0; i < classes.length; i++) {
            classes_id.put(classes[i], i);
        }
    }

    static void allotLabs() {
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

    static void onlyECE() {
        int i, j, k;
        String sub_basket;

        int set[] = new int[common_sub_core.size()];

        for (i = 0; i < set.length; i++) {
            set[i] = 0;
        }

        for (i = 0; i < only_ece.size(); i++) {

            sub_basket = electives_ece.get(only_ece.get(i)).get(0);

            for (j = 0; j < common_sub_core.size(); j++) {
                String sub_basket1 = electives_ece.get(common_sub_core.get(j)).get(0);
                if (sub_basket.equals(sub_basket1)) {
                    set[j] = 1;
                    break;
                }
            }

            if (j < common_sub_core.size()) {
                List<String> l = electives_ece.get(only_ece.get(i));
                for (int ii = 0; ii < 3; ii++) {
                    for (k = 2; k < l.size(); k++) {
                        out[2][teacher_id.get(l.get(k)) - 1][core_level.get(j).get(ii)] = 1;
                        out1[2][teacher_id.get(l.get(k)) - 1][core_level.get(j).get(ii)] = only_ece.get(i);
                    }
                }

                continue;
            }
        }
    }

    static void allotReservedElectives(Pair mat[][]) {

        for (int i = 0; i < common_sub_core.size(); i++) {
            List<Integer> l = new ArrayList<>();
            core_level.add(l);
        }

        for (int k = 0; k < 3; k++) {

            for (int i = 0; i < common_sub_core.size(); i++) {

                List<Integer> id = new ArrayList<>();
                List<Integer> id1 = new ArrayList<>();
                List<Integer> id2 = new ArrayList<>();

                String temp = common_sub_core.get(i);

                List<List<String>> l = class_map.get(classes[0]);
                List<List<String>> l1 = class_map.get(classes[1]);
                List<String> l2 = electives_ece.get(temp);

                int ii, ii1, ii2;
                for (ii = 0; ii < l.size(); ii++) {
                    if (l.get(ii).get(0).equals(temp)) {
                        break;
                    }
                }

                for (ii1 = 0; ii1 < l1.size(); ii1++) {
                    if (l1.get(ii1).get(0).equals(temp)) {
                        break;
                    }
                }

                for (int i1 = 2; i1 < l.get(ii).size(); i1++) {
                    String teach = l.get(ii).get(i1);
                    id.add(teacher_id.get(teach));
                }

                for (int i1 = 2; i1 < l1.get(ii1).size(); i1++) {
                    String teach = l1.get(ii1).get(i1);
                    id1.add(teacher_id.get(teach));
                }

                for (int i1 = 2; i1 < l2.size(); i1++) {
                    String teach = l2.get(i1);
                    id2.add(teacher_id.get(teach));
                }

                int z;
                for (int j = 0; j < 20; j++) {
                    for (z = 0; z < id.size(); z++) {
                        int q = id.get(z);

                        int z1;
                        for (z1 = 0; z1 < t; z1++) {
                            if (out[i][z1][j] == 1) {
                                break;
                            }
                        }
                        if (z1 != t) {
                            break;
                        }

                        if (out[0][q - 1][j] != 0) {
                            break;
                        }
                    }
                    if (z != id.size()) {
                        continue;
                    }

                    for (z = 0; z < id1.size(); z++) {
                        int q = id1.get(z);
                        int z1;
                        for (z1 = 0; z1 < t; z1++) {
                            if (out[i][z1][j] == 1) {
                                break;
                            }
                        }
                        if (z1 != t) {
                            break;
                        }
                        if (out[1][q - 1][j] != 0) {
                            break;
                        }
                    }
                    if (z != id1.size()) {
                        continue;
                    }

                    for (z = 0; z < id2.size(); z++) {
                        int q = id2.get(z);
                        int z1;
                        for (z1 = 0; z1 < t; z1++) {
                            if (out[i][z1][j] == 1) {
                                break;
                            }
                        }
                        if (z1 != t) {
                            break;
                        }
                        if (out[2][q - 1][j] != 0) {
                            break;
                        }
                    }
                    if (z != id2.size()) {
                        continue;
                    }

                    for (z = 0; z < id.size(); z++) {
                        int q = id.get(z);
                        out[0][q - 1][j] = 1;
                        out1[0][q - 1][j] = temp;
                        mat[0][q - 1].first--;
                    }
                    for (z = 0; z < id1.size(); z++) {
                        int q = id1.get(z);
                        out[1][q - 1][j] = 1;
                        out1[1][q - 1][j] = temp;
                        mat[1][q - 1].first--;
                    }
                    for (z = 0; z < id2.size(); z++) {
                        int q = id2.get(z);
                        out[2][q - 1][j] = 1;
                        out1[2][q - 1][j] = temp;
                        //mat[2][q - 1].first--;
                    }
                    //System.out.println("here");

                    core_level.get(i).add(j);
                    break;

                }
            }

        }
    }

    static void allotElectiveWish() {
        //System.out.println(teacher_id);
        //System.out.println(only_it);
        //System.out.println(only_ece);

        //Set<String> key_it = only_it.keySet();
        //Set<String> key_ece = only_ece.keySet();
        int count[] = new int[20];
        int count1[] = new int[5];

        for (int uu = 0; uu < 20; uu++) {
            count[uu] = 0;
        }

        for (int i = 0; i < allot2.size(); i++) {
            for (int uu = 0; uu < 5; uu++) {
                count1[uu] = 0;
            }
            for (int k = 0; k < 3; k++) {
                List<String> temp = allot2.get(i);
                //System.out.println(temp); 
                int r;
                while (true) {
                    int rr1 = randomGenerator.nextInt(5);
                    int rr2 = randomGenerator.nextInt(4);

                    r = rr1 * 4 + rr2;
                    if (count1[rr1] == 0 && count[r] == 0) {
                        count1[rr1] = 1;
                        count[r] = 1;
                        break;
                    }

                }

                //System.out.println(temp);
                for (int j = 0; j < temp.size(); j++) {

                    List<Integer> id = new ArrayList<>();
                    List<Integer> id1 = new ArrayList<>();
                    List<Integer> id2 = new ArrayList<>();

                    if (only_it.contains(temp.get(j))) {
                        List<String> kk = electives_it.get(temp.get(j));
                        //String teach = kk.get(2);
                        for (int i3 = 2; i3 < kk.size(); i3++) {
                            String teach = kk.get(i3);
                            id.add(teacher_id.get(teach));
                        }

                        //if(out[0][id-1][r] == 0 && out[1][id-1][r] == 0) {
                        for (int pp = 0; pp < id.size(); pp++) {
                            int ppp = id.get(pp);
                            out[0][ppp - 1][r] = 1;
                            out[1][ppp - 1][r] = 1;
                            out1[0][ppp - 1][r] = temp.get(j);
                            out1[1][ppp - 1][r] = temp.get(j);
                        }

                        //}
                    } else if (only_ece.contains(temp.get(j))) {
                        List<String> kk = electives_ece.get(temp.get(j));

                        for (int i3 = 2; i3 < kk.size(); i3++) {
                            String teach = kk.get(i3);
                            id1.add(teacher_id.get(teach));
                        }

                        //if(out[2][id-1][r] == 0) {
                        for (int pp = 0; pp < id1.size(); pp++) {
                            int ppp = id1.get(pp);
                            out[2][ppp - 1][r] = 1;
                            out1[2][ppp - 1][r] = temp.get(j);
                        }

                        System.out.println("---------------");

                        //}
                    } else {
                        List<String> kk = electives_ece.get(temp.get(j));
                        for (int i3 = 2; i3 < kk.size(); i3++) {
                            String teach = kk.get(i3);
                            id2.add(teacher_id.get(teach));
                        }

                        //if(out[2][id-1][r] == 0) {
                        //System.out.println(temp.get(j));
                        for (int pp = 0; pp < id2.size(); pp++) {

                            int ppp = id2.get(pp);

                            out[0][ppp - 1][r] = 1;
                            out[1][ppp - 1][r] = 1;
                            out[2][ppp - 1][r] = 1;
                            out1[0][ppp - 1][r] = temp.get(j);
                            out1[1][ppp - 1][r] = temp.get(j);
                            out1[2][ppp - 1][r] = temp.get(j);
                        }

                        //}
                    }
                }
            }
        }

    }

    static void printCore() {
        System.out.println(common_sub_core);
    }

    static void getElectives() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        Scanner sc1 = new Scanner(new File("electives.txt"));

      //  System.out.println("Enter the number of baskets for IT");
      //  basket_it = sc.nextInt();

        sub_IT_basket = new ArrayList<>(basket_it);

        int i, j, k;
        String line;
        List<String> l;

        for (i = 0; i < it.size(); i++) {
            System.out.println("Enter the number of subjects for basket " + (i + 1));
            sub_IT_basket.add((int)it.get(i));
        }

        List<List<String>> ll;

//        for (i = 0; i < basket_it; i++) {
//            ll = new ArrayList<>();
//            for (j = 0; j < sub_IT_basket.get(i); i++) {
//                line = sc1.nextLine();
//
//                String tmp[] = line.split("\t");
//                l = new ArrayList<>();
//                
//                for (k = 0; k < tmp.length; k++) {
//                    l.add(tmp[k]);
//                }
//
//                ll.add(l);
//            }
//            electives_it.put((i+1), ll);
//        }
        for (i = 0; i < basket_it; i++) {
            //ll = new ArrayList<>();
            for (j = 0; j < sub_IT_basket.get(i); j++) {
                line = sc1.nextLine();

                String tmp[] = line.split("\t");
                l = new ArrayList<>();

                l.add("" + (i + 1));
                for (k = 1; k < tmp.length; k++) {
                    l.add(tmp[k].trim().toLowerCase());
                }

                if (tmp[0].length() > 0) {
                    electives_it.put(tmp[0].trim().toLowerCase(), l);
                } else {
                    System.out.println("Not a valid subject");
                }
            }
        }

     //   System.out.println("Enter the number of baskets for ECE");
    //    basket_ece = sc.nextInt();

        sub_ECE_basket = new ArrayList<>(basket_ece);

        for (i = 0; i < ece.size(); i++) {
            System.out.println("Enter the number of subjects for basket " + (i + 1));
            sub_ECE_basket.add((int)ece.get(i));
        }

//        for (i = 0; i < basket_ece; i++) {
//            ll = new ArrayList<>();
//            for (j = 0; j < sub_ECE_basket.get(i); i++) {
//                line = sc1.nextLine();
//
//                String tmp[] = line.split("\t");
//                l = new ArrayList<>();
//                
//                for (k = 0; k < tmp.length; k++) {
//                    l.add(tmp[k]);
//                }
//
//                ll.add(l);
//            }
//            electives_ece.put((i+1), ll);
//        }
        for (i = 0; i < basket_ece; i++) {
            //ll = new ArrayList<>();
            for (j = 0; j < sub_ECE_basket.get(i); j++) {
                line = sc1.nextLine();

                String tmp[] = line.split("\t");
                l = new ArrayList<>();

                l.add("" + (i + 1));
                for (k = 1; k < tmp.length; k++) {
                    l.add(tmp[k].trim().toLowerCase());
                }

                if (tmp[0].length() > 0) {
                    electives_ece.put(tmp[0].trim().toLowerCase(), l);
                } else {
                    System.out.println("Not a valid subject");
                }
            }
        }
    }

    static void addElectives() {
        int i, j, flag;

//        for (Integer k : electives_ece.keySet()) {
//            for (i = 0; i < electives_ece.get(k).size(); i++) {
//                if (class_map.get("3A").contains(electives_ece.get(k).get(i)) || class_map.get("3B").contains(electives_ece.get(k).get(i))) {
//                    common_sub_core.put(electives_ece.get(k).get(i).get(0), electives_ece.get(k).get(i));
//                } else {
//                    flag = 0;
//                    for (Integer k1 : electives_it.keySet()) {
//                        if (electives_it.get(k1).contains(electives_ece.get(k))) {
//                            common_sub_elective.put(electives_ece.get(k).get(i).get(0), electives_ece.get(k).get(i));
//                            flag = 1;
//                            break;
//                        }
//                    }
//                    if (flag == 0) {
//                        only_ece.put(electives_ece.get(k).get(i).get(0), electives_ece.get(k).get(i));
//                    }
//                }
//            }
//        }
//        
//        for (Integer k : electives_it.keySet()) {
//            for (i = 0; i < electives_it.get(k).size(); i++) {
//                if (!common_sub_elective.containsKey(electives_it.get(k).get(i))) {
//                    only_it.put(electives_it.get(k).get(i).get(0), electives_it.get(k).get(i));
//                }
//            }
//        }
        for (String s : electives_ece.keySet()) {
            List<List<String>> ll = class_map.get("3A");

            for (i = 0; i < ll.size(); i++) {
                //System.out.println("hhhh : " + ll.get(i).get(0));
                if (s.equals(ll.get(i).get(0))) {
                    common_sub_core.add(s);
                    //System.out.println("here " + s);
                    break;
                }
            }

            if (i < ll.size()) {
                continue;
            }

            if (electives_it.containsKey(s)) {
                common_sub_elective.add(s);
            } else {
                only_ece.add(s);
            }
        }

        for (String s : electives_it.keySet()) {
            if (!common_sub_elective.contains(s)) {
                only_it.add(s);
            }
        }

        getParallel();
        allotElectives();
    }

    static void allotCommon_core() {
        int i, j, k, p;

        for (i = 0; i < common_sub_core.size(); i++) {
            List l1 = class_map.get(classes[0]);
            List l2 = class_map.get(classes[1]);

            List<String> l = new ArrayList<>();

            l.add(common_sub_core.get(i));
            for (j = 1; j < electives_ece.get(common_sub_core.get(i)).size(); j++) {
                l.add(electives_ece.get(common_sub_core.get(i)).get(j));
            }

            //System.out.println("--------------" + l.size() + " -- - " + l.get(2));
            p = teacher_id.get(l.get(2));
            if (l1.contains(l)) {
                for (j = 0; j < 20; j++) {
                    if (out[0][p][j] == 1) {
                        break;
                    }
                }
                for (k = 2; k < l.size(); k++) {
                    p = teacher_id.get(l.get(k));
                    out[2][p][j] = 1;
                    //System.out.println("heheheheh");
                }
            } else {
                for (j = 0; j < 20; j++) {
                    if (out[1][p][j] == 1) {
                        break;
                    }
                }
                for (k = 2; k < l.size(); k++) {
                    p = teacher_id.get(l.get(k));
                    out[2][p][j] = 1;
                    //System.out.println("heheheheh000000000000");
                }
            }
        }
    }

    static void allotElectives() {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of free classes :");
     //   free_classes = sc.nextInt();

        boolean a[] = new boolean[allot.size()];
        int i, j, k, flag = 0, p = 0;

        for (i = 0; i < a.length; i++) {
            a[i] = false;
        }

        List<String> l = new ArrayList<>();

        for (i = 0; i < allot.size(); i++) {
            allot.get(i).add("-");
        }

        while (p != free_classes) {
            flag = 0;
            for (j = 0; j < allot.size(); j++) {
                if (a[j] == false && (allot.get(j).size() - 1) % free_classes == p) {
                    l = new ArrayList<>();
                    //System.out.println("j : " + j + " - " + allot.get(j).size());
                    for (k = 0; k < allot.get(j).size() - 1; k++) {
                        //System.out.println("............... " + allot.get(j).get(k));
                        if (l.size() == free_classes) {
                            allot2.add(l);
                            l = new ArrayList<>();
                        }
                        l.add(allot.get(j).get(k));

                        for (int k1 = 0; k1 < allot.size(); k1++) {
                            if (k1 != j && allot.get(k1).contains(allot.get(j).get(k))) {
                                allot.get(k1).remove(allot.get(j).get(k));
                            }
                        }
                    }
                    allot2.add(l);
                    a[j] = true;
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                p++;
            }
        }

//        System.out.println("These can be alloted in parallel");
//        for (i = 0; i < allot2.size(); i++) {
//            System.out.println("i : " + i);
//            for (j = 0; j < allot2.get(i).size(); j++) {
//                System.out.println(allot2.get(i).get(j));
//            }
//            System.out.println();
//        }
    }

    static void getParallel() {
        int i, j, k = 0, flag;

        List<String> l = new ArrayList<>();
        List<Pair> lp = new ArrayList<>();
        Pair p;

        int i_it = 0, i_ece;
        for (i = 0; i < common_sub_elective.size(); i++) {
            i_it = Integer.parseInt(electives_it.get(common_sub_elective.get(i)).get(0));
            i_ece = Integer.parseInt(electives_ece.get(common_sub_elective.get(i)).get(0));

            //System.out.println("hello " + common_sub_elective.get(i));
            //checking for fisrt common subject
            if (i == 0) {
                l.add(common_sub_elective.get(i));
                allot.add(l);

                p = new Pair(i_it, i_ece);
                lp.add(p);
                allot1.add(lp);

                continue;
            }

            flag = 0;

            //checking if that subject can be added to any list<list>
            for (j = 0; j < allot.size(); j++) {
                for (k = 0; k < allot.get(j).size(); k++) {
                    if (allot1.get(j).get(k).first != i_it || allot1.get(j).get(k).second != i_ece) {
                        //System.out.println("hello 1 " + allot.get(j).get(k) + " j : " + j + " k : " + k);
                        break;
                    }
                }
                //if it can be added in list -> j
                if (k == allot.get(j).size()) {
                    allot.get(j).add(common_sub_elective.get(i));
                    p = new Pair(i_it, i_ece);
                    allot1.get(j).add(p);
                    //System.out.println("hello 2 " + allot.get(j).get(k) + " j : " + j + " k : " + k);
                    //break;
                    flag = 1;
                }
            }
            //if it cannnot be added in any list
            if (flag == 0) {
                l = new ArrayList<>();
                l.add(common_sub_elective.get(i));
                allot.add(l);

                p = new Pair(i_it, i_ece);
                lp = new ArrayList<>();
                lp.add(p);
                allot1.add(lp);
            }
        }

        for (i = 0; i < only_it.size(); i++) {
            i_it = Integer.parseInt(electives_it.get(only_it.get(i)).get(0));

            flag = 0;
            for (j = 0; j < allot.size(); j++) {
                for (k = 0; k < allot.get(j).size(); k++) {
                    if (allot1.get(j).get(k).first != -1 && allot1.get(j).get(k).first != i_it) {
                        //System.out.println("hello 1 " + allot.get(j).get(k) + " j : " + j + " k : " + k);
                        break;
                    }
                }
                //if it can be added in list -> j
                if (k == allot.get(j).size()) {
                    allot.get(j).add(only_it.get(i));
                    p = new Pair(i_it, -1);
                    allot1.get(j).add(p);
                    //System.out.println("hello 2 " + allot.get(j).get(k) + " j : " + j + " k : " + k);
                    //break;
                    flag = 1;
                }
            }

            if (flag == 0) {
                l = new ArrayList<>();
                l.add(only_it.get(i));
                allot.add(l);

                p = new Pair(i_it, -1);
                lp = new ArrayList<>();
                lp.add(p);
                allot1.add(lp);
            }
        }

//        System.out.println("All");
//        for (i = 0; i < allot.size(); i++) {
//            System.out.println("i : " + i);
//            for (j = 0; j < allot.get(i).size(); j++) {
//                System.out.println(allot.get(i).get(j) + " ");
//            }
//            System.out.println();
//        }
    }

    static void generateMatrix(Pair mat[][], Pair mat1[][]) {
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

    static void generateMapping() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("inputfile.txt"));
        int i, j;

        String line, tmp_s = "aaa";
        List<String> l;
        List< List<String>> ll = new ArrayList<>();
        int flag = 0;

        while (sc.hasNextLine()) {
            line = sc.nextLine();

            String tmp[] = line.split("\t");
            l = new ArrayList<>();

            if (!tmp_s.equals(tmp[0])) {
                if (flag == 0) {
                    flag = 1;
                } else {
                    class_map.put(tmp_s, ll);
                }
                ll = new ArrayList<>();
                tmp_s = tmp[0];
            }

            for (i = 1; i < tmp.length; i++) {
                l.add(tmp[i].trim().toLowerCase());
            }

            ll.add(l);
        }
        class_map.put(tmp_s, ll);

        //System.out.println("Hello yovan");
    }

    static void generateLabMap() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("labInput.txt"));
        int i, j;

        String line, tmp_s = "aaa";
        List<String> l;
        List< List<String>> ll = new ArrayList<>();
        int flag = 0;

        while (sc.hasNextLine()) {
            line = sc.nextLine();

            String tmp[] = line.split("\t");
            l = new ArrayList<>();

            if (!tmp_s.equals(tmp[0])) {
                if (flag == 0) {
                    flag = 1;
                } else {
                    lab_map.put(tmp_s, ll);
                }
                ll = new ArrayList<>();
                tmp_s = tmp[0];
            }

            for (i = 1; i < tmp.length; i++) {
                l.add(tmp[i].trim().toLowerCase());
            }

            ll.add(l);
        }
        lab_map.put(tmp_s, ll);
    }

    static void generateTeacherList() throws FileNotFoundException {
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

    static void recheck(Pair mat[][], int level) {
        int i, j, k, m, m1, flag = 0;

        for (i = 0; i < c; i++) {

            for (j = 0; j < t; j++) {

                if (mat[i][j].first > 0) {
                    //flag = 0;
                    for (k = 0; k < 20; k++) {

                        for (m1 = 0; m1 < t; m1++) {
                            if (out[i][m1][k] == 1) {
                                break;
                            }
                        }

                        if (m1 == t) {
                            //System.out.println("free " + " class " + i + " at level " + k);
                            //System.out.println("free class" + i + " at level " + k);

                            List<List<String>> l = class_map.get(classes[i]);

                            int ii;
                            for (ii = 0; ii < l.size(); ii++) {
                                if ((teacher_id.get(l.get(ii).get(2)) - 1) == j) {
                                    break;
                                }
                            }

                            int i1;
                            for (i1 = 2; i1 < l.get(ii).size(); i1++) {

                                for (m = 0; m < c; m++) {
                                    if (out[m][teacher_id.get(l.get(ii).get(i1)) - 1][k] == 1) {
                                        break;
                                    }
                                }
                                if (m != c) {
                                    break;
                                }
                            }
                            if (i1 == l.get(ii).size()) {
                                //System.out.println("Teacher " + j + " assigned to class " + i + " at level " + k);

                                for (i1 = 2; i1 < l.get(ii).size(); i1++) {
                                    out[i][teacher_id.get(l.get(ii).get(i1)) - 1][k] = 1;
                                    out1[i][teacher_id.get(l.get(ii).get(i1)) - 1][k] = teacher_map.get(l.get(ii).get(i1)).get(classes[i]);
                                    mat[i][teacher_id.get(l.get(ii).get(i1)) - 1].first--;
                                    //mat[i][teacher_id.get(l.get(ii).get(i1)) - 1].second = 1;
                                }
                                break;
                            }
                        }
                    }

                }

            }
        }
    }

    static void printMat(Pair mat[][]) {
        int i, j, k;

        for (i = 0; i < c; i++) {
            for (j = 0; j < t; j++) {
                System.out.print(mat[i][j].first + " ");
            }
            System.out.println("");
        }
    }

    static void printOutput_new() {
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

    static void printOutput() {
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

    static void teachList() {
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

    static void computeNew(Pair mat[][], int level) {
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
                    if (common_sub_core.contains(sub)) {
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

class Pair2 {

    int first, second;

    public Pair2(int first, int second) {
        this.first = first;
        this.second = second;
    }
}

class StringPair {

    String first, second;

    public StringPair(String first, String second) {
        this.first = first;
        this.second = second;
    }
}

class Teacher2 {

    String id;
    String name;
    int load;

    public Teacher2(String id, String name) {
        this.id = id;
        this.name = name;
        load = 0;
    }
}

class Classes2 {

    String room_no;
    String name;

    public Classes2(String room_no, String name) {
        this.room_no = room_no;
        this.name = name;
    }
}
