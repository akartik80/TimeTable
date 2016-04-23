/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static timet.TimeT2.*;

/**
 *
 * @author molu
 */
public class input {
    public void getElectives() throws FileNotFoundException {
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

    public void generateMapping() throws FileNotFoundException {
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

    public void generateLabMap() throws FileNotFoundException {
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

}
