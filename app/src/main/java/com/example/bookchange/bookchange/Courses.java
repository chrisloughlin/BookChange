package com.example.bookchange.bookchange;

import java.util.ArrayList;

/**
 * Created by christopher on 3/7/17.
 */

public class Courses {

    /*Integer[] AAAScourses = {7, 10, 11, 12, 13, 14, 15, 16, 18, 19, 23, 25, 26, 31, 34, 35, 39,
            40, 42, 44, 45, 46, 50, 51, 52, 53, 54, 55, 56, 60, 61, 62, 65, 67, 80, 81, 82, 83, 86, 87,
            88, 89, 90, 97, 98, 99};
    ArrayList<String> AAAS = new ArrayList<>();
        for(int i = 0; i < AAAScourses.length; i++){
            AAAS.add("AAAS " + AAAScourses[i]);
    }

    Integer[] ANTHcourses = {1, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
            22, 23, 24, 25, 26, 27, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 47,
            48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 70, 72, 73, 74, 75,
            77, 85, 87, 88};
        ArrayList<String> ANTH = new ArrayList<>();
        for(int i = 0; i < ANTHcourses.length; i++){
        ANTH.add("ANTH " + ANTHcourses[i]);
        }

        Integer[] ARTHcourses = {1, 2, 4, 7, 10, 11, 12, 13, 16, 17, 20, 21, 22, 25, 30, 31, 32,
        33, 34, 35, 36, 40, 41, 42, 43, 44, 45, 46, 48, 50, 51, 52, 53, 54, 55, 59, 60, 61, 62, 63,
        64, 65, 66, 67, 68, 70, 71, 75, 76, 82, 83, 84, 85, 86, 89, 90, 91};
        ArrayList<String> ARTH = new ArrayList<>();
        for(int i = 0; i < ARTHcourses.length; i++){
        ARTH.add("ARTH " + ARTHcourses[i]);
        }

        Integer[] AMELcourses = {7, 17, 18, 85, 87};
        ArrayList<String> AMEL = new ArrayList<>();
        for(int i = 0; i < AMELcourses.length; i++){
        AMEL.add("AMEL " + AMELcourses[i]);
        }

        Integer[] BIOLcourses = {2, 3, 5, 6, 10, 11,12, 13, 14, 15, 16, 21, 22, 23, 24, 25, 27, 28,
        29, 31, 32, 33, 36, 37, 38, 40, 42, 43, 45, 46, 47, 48, 49, 51, 52, 53, 55, 56, 57, 60, 63,
                66, 67, 68, 69, 70, 71, 72, 73, 74, 76, 78, 95, 96, 97, 99};
        ArrayList<String> BIOL = new ArrayList<>();
        for(int i = 0; i < BIOLcourses.length; i++){
        BIOL.add("BIOL " + BIOLcourses[i]);
        }

        Integer[] CHEMcourses = {5, 6, 7, 10, 40, 41, 42, 51, 52, 57, 58, 63, 64, 67, 75, 76, 87,
        90, 91, 92, 93, 96};
        ArrayList<String> CHEM = new ArrayList<>();
        for(int i = 0; i < CHEMcourses.length; i++){
        CHEM.add("CHEM " + CHEMcourses[i]);
        }

        Integer[] CLSTcourses = {1, 2, 3, 4, 5, 6, 7, 10, 11, 12, 14, 15, 17, 18, 19, 20, 21, 22,
        24, 25, 26, 29, 30, 31, 40, 85, 87};
        ArrayList<String> CLST = new ArrayList<>();
        for(int i = 0; i < CLSTcourses.length; i++){
        CLST.add("CLST " + " " + CLSTcourses[i]);
        }

        Integer[] COGScourses = {1, 2, 11, 21, 26, 44, 50, 80, 85, 86, 87};
        ArrayList<String> COGS = new ArrayList<>();
        for(int i = 0; i < COGScourses.length; i++){
        COGS.add("COGS " + " " + COGScourses[i]);
        }

        Integer[] COCOcourses = {3, 6, 16, 17, 18};
        ArrayList<String> COCO = new ArrayList<>();
        for(int i = 0; i < COCOcourses.length; i++){
        COCO.add("COCO " + " " + COCOcourses[i]);
        }

        Integer[] COLTcourses = {1, 7, 10, 18, 19, 20, 21, 22, 23, 25, 26, 27, 28, 29, 30, 31, 33,
        34, 35, 36, 37, 39, 40, 41, 42, 45, 46, 47, 49, 50, 51, 52, 53, 54, 55, 56, 57, 60, 61, 62,
        63, 64, 66, 67, 70, 71, 72, 73, 79, 80, 85, 87};
        ArrayList<String> COLT = new ArrayList<>();
        for(int i = 0; i < COLTcourses.length; i++){
        COLT.add("COLT " + " " + COLTcourses[i]);
        }

        Integer[] COSCcourses = {1,2,10,11,16,20,22,24,27,28,29,30,31,35,39,40,49,50,51,52,55,56,
        57,58,59,60,61,63,65,67,69,70,71,73,74,75,76,77,78,81,83,84,86,87,89,94,97,98,99};
        ArrayList<String> COSC = new ArrayList<>();
        for(int i = 0; i < COSCcourses.length; i++){
        COSC.add("COSC " + " " + COSCcourses[i]);
        }

        Integer[] EARScourses = {1,2,3,4,5,6,7,8,9,14,15,16,17,18,19,28,31,33,35,37,38,40,45,46,
        47,51,52,58,59,62,64,65,66,67,70,71,72,73,74,75,76,77,78,79,87,88,89};
        ArrayList<String> EARS = new ArrayList<>();
        for(int i = 0; i < EARScourses.length; i++){
            EARS.add("EARS " + " " + EARScourses[i]);
        }

        Integer[] ECONcourses = {1,2,5,10,15,16,20,21,22,24,25,26,27,28,29,32,35,36,38,39,44,45
        46,47,48,49,70,71,73,76,77,78,79,80,81,82,85,87};
        ArrayList<String> ECON = new ArrayList<>();
        for(int i = 0; i < ECONcourses.length; i++){
            ECON.add("EARS " + " " + ECONcourses[i]);
        }*/
        }
