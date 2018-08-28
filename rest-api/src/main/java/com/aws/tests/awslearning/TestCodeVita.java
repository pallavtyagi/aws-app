package com.aws.tests.awslearning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestCodeVita {

    public static void main(String s[]) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(reader.readLine());
        List<Integer> input = toIntSortedList(reader.readLine(), ",");
        if (N >= 4) {


            int count = 0;

            for (int i = 0; i < N - 1; i++) {
                if ((input.get(i) >=-89 && input.get(i) <=90 && input.get(i+1) >=-89 && input.get(i+1) <=90) && (input.get(i).equals(input.get(i + 1)))) {
                    i++;
                    count++;
                } else {

                }
            }
            int result = 0;
            if (N < 4)
                result = 0;
            else if (count == 2)
                result = 1;
            else if (count == 1)
                result = 0;
            else
                result = combination(count, 2);

            System.out.print(result);
        } else {
            System.out.print(0);
        }

    }

    static List<Integer> toIntSortedList(String input, String seperator) {

        return Arrays.stream(input.split(seperator)).map(Integer::parseInt).sorted().collect(Collectors.toList());


    }

    static public int combination(int n, int k) {
        return permutation(n) / (permutation(k) * permutation(n - k));
    }

    static public int permutation(int i) {
        if (i == 1) {
            return 1;
        }
        return i * permutation(i - 1);
    }

}
