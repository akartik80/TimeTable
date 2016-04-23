/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import static timet.TimeT2.*;

/**
 *
 * @author molu
 */
public class electives {
    
    public static List<String> common_sub_elective = new ArrayList<>();        // it + ece (both elective)
    public static List<String> common_sub_core = new ArrayList<>();            // it -> core + ece -> elective
    public static List<String> only_it = new ArrayList<>();                    // it -> elective
    public static List<String> only_ece = new ArrayList<>();                   // ece -> elective
    public static List<List<String>> allot = new ArrayList<>();                // which all can be run in parallel
    public static List<List<Pair>> allot1 = new ArrayList<>();                 // pair of basket numbers of each sub (it, ece)
    public static List<List<String>> allot2 = new ArrayList<>();               // which all should be run in parallel
    public static List<List<Integer>> core_level = new ArrayList<>();
    
    public void allotElectives() {

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

    public void getParallel() {
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

    public void onlyECE() {
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

    public void allotReservedElectives(Pair mat[][]) {

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

    public void allotElectiveWish() {
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

    public boolean haveSameBasketNumbers(List<String> subjects, List<StringPair> list) {
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

    public void allotExtraECE(Pair mat[][]) {
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

    public boolean areAllParallellyRunningElectives(List<String> subjects) {
        int i;
        List<String> subs;
        List<StringPair> list = new ArrayList<StringPair>();
        List<String> list2 = new ArrayList<String>();

        for (i = 0; i < allot2.size(); i++) {
            if (allot2.get(i).containsAll(subjects)) {
                return true;
            }
        }
//public static HashMap<String, List<String>> electives_it = new HashMap<>();    // list of elective subjects (eg ML -> #basketno., credits, teachers list)        
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

        if (_electives.haveSameBasketNumbers(subjects, list)) {
            return true;
        }

        return false;
    }

    public void allotCommon_core() {
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
     
    public void printCore() {
        System.out.println(common_sub_core);
    }
    
    public void addElectives() {
        int i, j, flag;

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

        _electives.getParallel();
        _electives.allotElectives();
    }
}
