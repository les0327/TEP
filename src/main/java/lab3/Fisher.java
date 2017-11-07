package lab3;

public interface Fisher {

    int[] fisherF3 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 22, 24, 26, 28, 30, 40, 60, 120};
    int[] fisherF4 = {1, 2, 3, 4, 5, 6, 12, 24};
    double[][] fisher = {
            {164.4, 199.5, 215.7, 224.6, 230.2, 234.0, 244.9, 249.0, 254.3},
            {18.5, 19.2, 19.2, 19.3, 19.3, 19.3, 19.4, 19.4, 19.5},
            {10.1, 9.6, 9.3, 9.1, 9.0, 8.9, 8.7, 8.6, 8.5},
            {7.7, 6.9, 6.6, 6.4, 6.3, 6.2, 5.9, 5.8, 5.6},
            {6.6, 5.8, 5.4,	5.2, 5.1, 5.0, 4.7, 4.5, 4.4},
            {6.0, 5.1, 4.8,	4.5, 4.4, 4.3, 4.0, 3.8, 3.7},
            {5.5, 4.7, 4.4,	4.1, 4.0, 3.9, 3.6, 3.4, 3.2},
            {5.3, 4.5, 4.1,	3.8, 3.7, 3.6, 3.3, 3.1, 2.9},
            {5.1, 4.3, 3.9,	3.6, 3.5, 3.4, 3.1, 2.9, 2.7},
            {5.0, 4.1, 3.7,	3.5, 3.3, 3.2, 2.9, 2.7, 2.5},
            {4.8, 4.0, 3.6,	3.4, 3.2, 3.1, 2.8, 2.6, 2.4},
            {4.8, 3.9, 3.5,	3.3, 3.1, 3.0, 2.7, 2.5, 2.3},
            {4.7, 3.8, 3.4,	3.2, 3.0, 2.9, 2.6, 2.4, 2.2},
            {4.6, 3.7, 3.3,	3.1, 3.0, 2.9, 2.5, 2.3, 2.1},
            {4.5, 3.7, 3.3,	3.1, 2.9, 2.8, 2.5, 2.3, 2.1},
            {4.5, 3.6, 3.2,	3.0, 2.9, 2.7, 2.4, 2.2, 2.0},
            {4.5, 3.6, 3.2,	3.0, 2.8, 2.7, 2.4, 2.2, 2.0},
            {4.4, 3.6, 3.2,	2.9, 2.8, 2.7, 2.3, 2.1, 1.9},
            {4.4, 3.5, 3.1,	2.9, 2.7, 2.6, 2.3, 2.1, 1.9},
            {4.4, 3.5, 3.1,	2.9, 2.7, 2.6, 2.3, 2.1, 1.9},
            {4.3, 3.4, 3.1,	2.8, 2.7, 2.6, 2.2, 2.0, 1.8},
            {4.3, 3.4, 3.0,	2.8, 2.6, 2.5, 2.2, 2.0, 1.7},
            {4.2, 3.4, 3.0,	2.7, 2.6, 2.5, 2.2, 2.0, 1.7},
            {4.2, 3.3, 3.0,	2.7, 2.6, 2.4, 2.1, 1.9, 1.7},
            {4.2, 3.3, 2.9,	2.7, 2.5, 2.4, 2.1, 1.9, 1.6},
            {4.1, 3.2, 2.9,	2.6, 2.5, 2.3, 2.0, 1.8, 1.5},
            {4.0, 3.2, 2.8,	2.5, 2.4, 2.3, 1.9, 1.7, 1.4},
            {3.9, 3.1, 2.7,	2.5, 2.3, 2.2, 1.8, 1.6, 1.3},
            {3.8, 3.0, 2.6,	2.4, 2.2, 2.1, 1.8, 1.5, 1.0},
            {}
    };
}
