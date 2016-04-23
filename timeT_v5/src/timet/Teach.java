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
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author JP
 */

public class Teach {
//public  String teachers[] = new String[100];
 static List teachers= new ArrayList();
 static List teachers1 = new ArrayList();
 TimeT time = new TimeT();

int generate_teacherlist()
{
    int i = 0;
    int t = 0;
    int i1 = 0;
    int t1 = 0;
  File file = new File("teachers.txt");
  File file1 = new File("teacherName.txt");
 
  //teachers[99] = "free" ;   
    try {

        Scanner sc = new Scanner(file);
        Scanner sc1 = new Scanner(file1);
        while (sc.hasNextLine()&& sc1.hasNextLine()) {
            String a = sc.nextLine();
            String a1 = sc1.nextLine();
            //System.out.println(a);
            teachers.add(a);
            teachers1.add(a1);
            System.out.println(i + " " +a1);
            
            i++;
        }
        t = teachers.size();
        t1 = teachers1.size();
        System.out.println(i +" " + t1);
        teachers.add("free");
        t = teachers.size();
        t1 = teachers1.size();
        System.out.println(t1);
        sc.close();
        sc1.close();
        print1();
    } 
    catch (FileNotFoundException e) {
        e.printStackTrace();
    }
  return (t);
}
  void print1() throws FileNotFoundException {
        System.out.println("..................");
        int i, j, k,flag;
        time.generate();
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
        System.out.println(time.t);
        for(i=0;i<time.t;i++) {
            System.out.println();
            System.out.println("For the class : "+ teachers.get(i));
            //System.out.println();
            for(j=0;j<20;j++) {
                if(j%4==0) {
                    System.out.print(time.days[(j/4)]);
                    for(int x = time.days[j/4].length();x <= 15; x++) {
                        System.out.print(" ");
                    }
                }
                
                flag=0;
                
                if(j%4 == 3){
                    flag=1;
                }
                int flag1=0;
                
                for(k=0;k<time.c;k++) {
                    //System.out.print(" yeah "+ j +" ");
                    if(time.out[k][i][j] == 1) {
                        flag1 = 1;
                        if(k <= 9)
                            System.out.print(time.classes[k] + "\t");
                        else
                            System.out.print(k+1 + "\t");
                    }
                }
                if(flag1 == 0) {
                    System.out.print("free\t");
                }
                if(flag == 1) {
                    System.out.println();
                } 
            }
        }
    }
 
  
 }

