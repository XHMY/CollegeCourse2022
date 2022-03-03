package com.yokey;

import java.util.Scanner;

import static java.lang.Float.max;
import static java.lang.Float.min;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt(); // m 个班级
        int stu_cnt = 0;
        float all_sum = 0;
        float all_min = Float.MAX_VALUE;
        float all_max = Float.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            int n = scanner.nextInt(); // n 个学生
            float class_sum = 0;
            float class_min = Float.MAX_VALUE;
            float class_max = Float.MIN_VALUE;
            for (int j = 0; j < n; j++) {
                float s = scanner.nextFloat();
                class_sum += s;
                class_min = min(class_min, s);
                class_max = max(class_max, s);
            }
            System.out.printf("avg %.2f  min %.2f  max %.2f\n", class_sum / n, class_min, class_max);
            all_sum += class_sum;
            all_min = min(all_min, class_min);
            all_max = max(all_max, class_max);
            stu_cnt += n;
        }
        System.out.printf("avg %.2f  min %.2f  max %.2f\n", all_sum / stu_cnt, all_min, all_max);
    }
}
