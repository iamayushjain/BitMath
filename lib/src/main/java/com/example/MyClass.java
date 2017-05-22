package com.example;

import java.util.Random;
import java.util.Scanner;

public class MyClass {

    static int probLowLowLimit = 5,
            probLowAvgLimit = 6,
            probAvgAvgLimit = 5,
            probAvgHighLimit = 25,
            probHighHighLimit = 4;

    static int leftOperand = 0, rightOperand = 0;
    static int prevLeftOperand = 0, prevRightOperand = 0;

    public static void main(String args[]) {
        long time = System.currentTimeMillis();
        out("Start");

        Scanner scanner = new Scanner(System.in);
        int score = 1;
        int addLowLowLimit = getRandomNearBy(5),
                addLowAvgLimit = getRandomNearBy(12),
                addAvgAvgLimit = getRandomNearBy(17),
                addAvgHighLimit = getRandomNearBy(20);


        do {
            System.out.print(score + ":  ");
            if (score <= addLowLowLimit) {
                if (!isSurpriseTest(probLowLowLimit))
                    addLowLow();
                else {
                    System.out.print("Random");

                    int x = chooseRandomSequence(2);
                    if (x == 1) {
                        addLowAvg();
                    } else {
                        addAvgAvg();
                    }

                }
            } else if (score <= addLowAvgLimit) {
                if (!isSurpriseTest(probLowAvgLimit))
                    addLowAvg();
                else {
                    System.out.print("Random");

                    int x = chooseRandomSequence(8);
                    if (x < 4) {
                        addLowLow();
                    } else if (x == 4 || x == 8) {
                        addAvgHigh();
                    } else {
                        addAvgAvg();
                    }
                }
            } else if (score <= addAvgAvgLimit) {
                if (!isSurpriseTest(probAvgAvgLimit))
                    addAvgAvg();
                else {
                    System.out.print("Random");

                    int x = chooseRandomSequence(4);
                    if (x == 1) {
                        addLowLow();
                    } else if (x == 2) {
                        addLowAvg();
                    } else if (x == 3) {
                        addAvgHigh();
                    } else {
                        addHighHigh();
                    }
                }
            } else if (score <= addAvgHighLimit) {
                if (!isSurpriseTest(probAvgHighLimit))
                    addAvgHigh();
                else {
                    System.out.print("Random");


                    int x = chooseRandomSequence(4);
                    if (x == 1) {
                        addLowLow();
                    } else if (x == 2) {
                        addLowAvg();
                    } else if (x == 3) {
                        addAvgAvg();
                    } else {
                        addHighHigh();
                    }
                }
            } else {
                if (!isSurpriseTest(probHighHighLimit))
                    addHighHigh();
                else {
                    System.out.print("Random");

                    int x = chooseRandomSequence(4);
                    if (x == 1) {
                        addLowLow();
                    } else if (x == 2) {
                        addLowAvg();
                    } else if (x == 3) {
                        addAvgAvg();
                    } else {
                        addAvgHigh();
                    }
                }
            }

            int output = leftOperand + rightOperand;
            int outputReceiver = Integer.parseInt(scanner.nextLine());
            if (output == outputReceiver)
                score++;
        }
        while (System.currentTimeMillis() - time <= 30000 && score <= 30);
        out(score);
        out("Stop");


    }


    private static boolean isRepetition() {
        if (leftOperand == prevLeftOperand && rightOperand == prevRightOperand)
            return true;
        else if (leftOperand == prevRightOperand && rightOperand == prevLeftOperand)
            return true;
        else
            return false;

    }

    private static void addLowLow() {
        prevLeftOperand = getRandomNumberLow();
        prevRightOperand = getRandomNumberLow();
        if (isRepetition()) {
            addLowLow();
        } else {
            leftOperand = prevLeftOperand;
            rightOperand = prevRightOperand;
            out(leftOperand + "+" + rightOperand);
        }
    }

    private static void addLowAvg() {
        if (chooseRandomSequence(2) == 1) {
            prevLeftOperand = getRandomNumberAverage();
            prevRightOperand = getRandomNumberLow();
        } else {
            prevLeftOperand = getRandomNumberLow();
            prevRightOperand = getRandomNumberAverage();
        }
        if (isRepetition()) {
            addLowAvg();
        } else {
            leftOperand = prevLeftOperand;
            rightOperand = prevRightOperand;
            out(leftOperand + "+" + rightOperand);
        }

    }

    private static void addAvgAvg() {
        prevLeftOperand = getRandomNumberAverage();
        prevRightOperand = getRandomNumberAverage();
        if (isRepetition()) {
            addAvgAvg();
        } else {
            leftOperand = prevLeftOperand;
            rightOperand = prevRightOperand;
            out(leftOperand + "+" + rightOperand);
        }
    }

    private static void addAvgHigh() {
        if (chooseRandomSequence(2) == 1) {
            prevLeftOperand = getRandomNumberAverage();
            prevRightOperand = getRandomNumberHigh();
        } else {
            prevLeftOperand = getRandomNumberHigh();
            prevRightOperand = getRandomNumberAverage();
        }
        if (isRepetition()) {
            addAvgHigh();
        } else {
            leftOperand = prevLeftOperand;
            rightOperand = prevRightOperand;
            out(leftOperand + "+" + rightOperand);
        }
    }

    private static void addHighHigh() {
        prevLeftOperand = getRandomNumberHigh();
        prevRightOperand = getRandomNumberHigh();
        if (isRepetition()) {
            addHighHigh();
        } else {
            leftOperand = prevLeftOperand;
            rightOperand = prevRightOperand;
            out(leftOperand + "+" + rightOperand);
        }
    }


    private static boolean isSurpriseTest(int high) {
        Random r = new Random();
        int lowerLimit = 1;
        int upperLimit = high;
        int result = r.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
        return (result % high == 0);
    }


    private static int getRandomNearBy(int value) {
        Random r = new Random();
        int lowerLimit = value - 1;
        int upperLimit = value + 1;
        int result = r.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
        return result;
    }


    private static int chooseRandomSequence(int high) {
        Random r = new Random();
        int lowerLimit = 1;
        int upperLimit = high;
        int result = r.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
        return result;
    }

    private static int getRandomNumberSwap() {
        Random r = new Random();
        int lowerLimit = 0;
        int upperLimit = 1;
        int result = r.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
        return result;
    }


    private static int getRandomNumberLow() {
        Random r = new Random();
        int lowerLimit = 1;
        int upperLimit = 9;
        int result = r.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
        return result;
    }

    private static int getRandomNumberAverage() {
        Random r = new Random();
        int lowerLimit = 10;
        int upperLimit = 19;
        int result = r.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
        return result;
    }

    private static int getRandomNumberHigh() {
        Random r = new Random();
        int lowerLimit = 20;
        int upperLimit = 29;
        int result = r.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
        return result;
    }

    public static void out(Object object) {
        System.out.println(object);
    }
}
