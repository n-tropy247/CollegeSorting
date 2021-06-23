/*
 * Copyright (C) 2021 Ryan Castelli
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ntropy.collegecrossreferencing;

import java.util.ArrayList;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * A program to take in a CSV of universities for 3 different professions and
 * output a list sorted by distance.
 *
 * @author Ryan Castelli
 * @version 6.23.2021
 * @since 6.22.2021
 */
public class Program {

    /**
     * Lists of schools, sorted by name.
     */
    private static final ArrayList<School> med = new ArrayList<>();
    private static final ArrayList<School> pharm = new ArrayList<>();
    private static final ArrayList<School> mfa = new ArrayList<>();

    /**
     * Triplets of schools within named distances (miles).
     */
    private static final ArrayList<School[]> d25 = new ArrayList<>();
    private static final ArrayList<School[]> d50 = new ArrayList<>();
    private static final ArrayList<School[]> d75 = new ArrayList<>();
    private static final ArrayList<School[]> d100 = new ArrayList<>();

    /**
     * Maximum length of a school name.
     */
    private static int maxSchoolLen = 0;

    /**
     * Call helper functions to populate each array and created pruned triplets.
     *
     * @param args command-line arguments; unused here
     */
    public static void main(String args[]) {
        try {
            populateSchools(); //initialize school arrays

            //group
            groupByDist(d25, 25.);
            groupByDist(d50, 50.);
            groupByDist(d75, 75.);
            groupByDist(d100, 100.);

            //prune
            d100.removeIf(x -> d75.stream().anyMatch(a -> (a[0] == x[0] && a[1]
                    == x[1] && a[2] == x[2])));
            d75.removeIf(x -> d50.stream().anyMatch(a -> (a[0] == x[0] && a[1]
                    == x[1] && a[2] == x[2])));
            d50.removeIf(x -> d25.stream().anyMatch(a -> (a[0] == x[0] && a[1]
                    == x[1] && a[2] == x[2])));
            String format = "\n%-" + 5 + "s | %-" + maxSchoolLen
                    + "s | %s | %s";

            //sort
            d25.sort((School[] arr1, School[] arr2) -> {
                double avg1 = distance(arr1[0], arr1[1]) + distance(arr1[1],
                        arr1[2]) + distance(arr1[0], arr1[2]) / 3.;
                double avg2 = distance(arr2[0], arr2[1]) + distance(arr2[1],
                        arr2[2]) + distance(arr2[0], arr2[2]) / 3.;
                return Double.compare(avg1, avg2);
            });
            d50.sort((School[] arr1, School[] arr2) -> {
                double avg1 = distance(arr1[0], arr1[1]) + distance(arr1[1],
                        arr1[2]) + distance(arr1[0], arr1[2]) / 3.;
                double avg2 = distance(arr2[0], arr2[1]) + distance(arr2[1],
                        arr2[2]) + distance(arr2[0], arr2[2]) / 3.;
                return Double.compare(avg1, avg2);
            });
            d75.sort((School[] arr1, School[] arr2) -> {
                double avg1 = distance(arr1[0], arr1[1]) + distance(arr1[1],
                        arr1[2]) + distance(arr1[0], arr1[2]) / 3.;
                double avg2 = distance(arr2[0], arr2[1]) + distance(arr2[1],
                        arr2[2]) + distance(arr2[0], arr2[2]) / 3.;
                return Double.compare(avg1, avg2);
            });
            d100.sort((School[] arr1, School[] arr2) -> {
                double avg1 = distance(arr1[0], arr1[1]) + distance(arr1[1],
                        arr1[2]) + distance(arr1[0], arr1[2]) / 3.;
                double avg2 = distance(arr2[0], arr2[1]) + distance(arr2[1],
                        arr2[2]) + distance(arr2[0], arr2[2]) / 3.;
                return Double.compare(avg1, avg2);
            });

            //output to file formatted data
            PrintWriter fileOut = new PrintWriter(new FileWriter(
                    "output/grouping.txt"));
            fileOut.printf("Within 25 miles (%d):", d25.size());
            d25.forEach((School[] arr) -> {
                fileOut.printf(format, arr[0].getProf(), arr[0].getName(),
                        arr[0].getState(), arr[0].getCity());
                fileOut.printf(format, arr[1].getProf(), arr[1].getName(),
                        arr[1].getState(), arr[1].getCity());
                fileOut.printf(format, arr[2].getProf(), arr[2].getName(),
                        arr[2].getState(), arr[2].getCity());
                fileOut.printf("\nAverage Distance: %.4f miles",
                        (distance(arr[0], arr[1]) + distance(arr[1], arr[2])
                        + distance(arr[0], arr[2])) / 3.);
                fileOut.printf("\n");
            });
            fileOut.printf("\nWithin 50 miles (%d):", d50.size());
            d50.forEach((School[] arr) -> {
                fileOut.printf(format, arr[0].getProf(), arr[0].getName(),
                        arr[0].getState(), arr[0].getCity());
                fileOut.printf(format, arr[1].getProf(), arr[1].getName(),
                        arr[1].getState(), arr[1].getCity());
                fileOut.printf(format, arr[2].getProf(), arr[2].getName(),
                        arr[2].getState(), arr[2].getCity());
                fileOut.printf("\nAverage Distance: %.4f miles",
                        (distance(arr[0], arr[1]) + distance(arr[1], arr[2])
                        + distance(arr[0], arr[2])) / 3.);
                fileOut.printf("\n");
            });
            fileOut.printf("\nWithin 75 miles (%d):", d75.size());
            d75.forEach((School[] arr) -> {
                fileOut.printf(format, arr[0].getProf(), arr[0].getName(),
                        arr[0].getState(), arr[0].getCity());
                fileOut.printf(format, arr[1].getProf(), arr[1].getName(),
                        arr[1].getState(), arr[1].getCity());
                fileOut.printf(format, arr[2].getProf(), arr[2].getName(),
                        arr[2].getState(), arr[2].getCity());
                fileOut.printf("\nAverage Distance: %.4f miles",
                        (distance(arr[0], arr[1]) + distance(arr[1], arr[2])
                        + distance(arr[0], arr[2])) / 3.);
                fileOut.printf("\n");
            });
            fileOut.printf("\nWithin 100 miles (%d):", d100.size());
            d100.forEach((School[] arr) -> {
                fileOut.printf(format, arr[0].getProf(), arr[0].getName(),
                        arr[0].getState(), arr[0].getCity());
                fileOut.printf(format, arr[1].getProf(), arr[1].getName(),
                        arr[1].getState(), arr[1].getCity());
                fileOut.printf(format, arr[2].getProf(), arr[2].getName(),
                        arr[2].getState(), arr[2].getCity());
                fileOut.printf("\nAverage Distance: %.4f miles",
                        (distance(arr[0], arr[1]) + distance(arr[1], arr[2])
                        + distance(arr[0], arr[2])) / 3.);
                fileOut.printf("\n");
            });
            fileOut.close();
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Populate each array of schools from a CSV and sort by name.
     */
    private static void populateSchools() {
        final String FILENAME = "resources/colleges.csv";
        try {
            CSVReader csv = new CSVReader(new FileReader(FILENAME));
            csv.readNext(); //dispose of headers
            String[] row = csv.readNext();
            while (row != null) {
                maxSchoolLen = maxSchoolLen > row[1].length() ? maxSchoolLen : row[1].length();
                switch (row[0]) {
                    case "Pharmacy":
                        pharm.add(new School("Pharm", row[1],
                                new Coord(Double.parseDouble(row[2]),
                                        Double.parseDouble(row[3])), row[5],
                                row[4]));
                        break;
                    case "Medical":
                        med.add(new School("Med", row[1],
                                new Coord(Double.parseDouble(row[2]),
                                        Double.parseDouble(row[3])), row[5],
                                row[4]));
                        break;
                    case "MFA":
                        mfa.add(new School("MFA", row[1],
                                new Coord(Double.parseDouble(row[2]),
                                        Double.parseDouble(row[3])), row[5],
                                row[4]));
                        break;
                }
                row = csv.readNext();
            }
        }
        catch (IOException | CsvValidationException e) {
            System.err.println(e);
        }
    }

    /**
     * Get distance between two schools in miles.
     *
     * @param s1 first school
     * @param s2 second school
     * @return distance in miles
     */
    private static double distance(School s1, School s2) {
        return haversine(s1.getCoords(), s2.getCoords());
    }

    private static double haversine(Coord c1, Coord c2) {
        final double R = 3961; //radius of Earth in miles
        double lon1 = Math.toRadians(c1.getLong());
        double lon2 = Math.toRadians(c2.getLong());
        double lat1 = Math.toRadians(c1.getLat());
        double lat2 = Math.toRadians(c2.getLat());
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + (Math.cos(lat1)
                * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    private static void groupByDist(ArrayList<School[]> arr, double dist) {
        med.forEach((School s1) -> {
            pharm.forEach((School s2) -> {
                mfa.forEach((School s3) -> {
                    if (distance(s1, s2) < dist && distance(s2, s3) < dist
                            && distance(s1, s3) < dist) {
                        arr.add(new School[]{s1, s2, s3});
                    }
                });
            });
        });
    }
}
