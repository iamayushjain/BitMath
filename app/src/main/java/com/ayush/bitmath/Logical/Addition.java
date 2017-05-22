package com.ayush.bitmath.Logical;

/**
 * Created by ayush on 21/5/17.
 */

import java.util.Random;

public class Addition {

    int probLowLowLimit = 5,
            probLowAvgLimit = 6,
            probAvgAvgLimit = 5,
            probAvgHighLimit = 25,
            probHighHighLimit = 4;

    int leftOperand = 0, rightOperand = 0;
    int prevLeftOperand = 0, prevRightOperand = 0;

    int addLowLowLimit = getRandomNearBy(5),
            addLowAvgLimit = getRandomNearBy(12),
            addAvgAvgLimit = getRandomNearBy(17),
            addAvgHighLimit = getRandomNearBy(20);
    int score = 0;

    public Addition() {
    }

    public void innerLoop() {
        out("Start");
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

//            scanner.nextLine();
        score++;

    }

    public int getOutput() {
        return leftOperand + rightOperand;
    }

    public String getEquation() {
        return leftOperand + "+" + rightOperand;
    }

    public boolean isRepetition() {
        if (leftOperand == prevLeftOperand && rightOperand == prevRightOperand)
            return true;
        else if (leftOperand == prevRightOperand && rightOperand == prevLeftOperand)
            return true;
        else
            return false;

    }

    public void addLowLow() {
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

    public void addLowAvg() {
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

    public void addAvgAvg() {
        prevLeftOperand = getRandomNumberAverage();
        prevRightOperand = getRandomNumberAverage();
        out(leftOperand + "+" + rightOperand);
        if (isRepetition()) {
            addAvgAvg();
        } else {
            leftOperand = prevLeftOperand;
            rightOperand = prevRightOperand;
            out(leftOperand + "+" + rightOperand);
        }
    }

    public void addAvgHigh() {
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

    public void addHighHigh() {
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


    public static boolean isSurpriseTest(int high) {
        Random r = new Random();
        int lowerLimit = 1;
        int upperLimit = high;
        int result = r.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
        return (result % high == 0);
    }


    public static int getRandomNearBy(int value) {
        Random r = new Random();
        int lowerLimit = value - 1;
        int upperLimit = value + 1;
        int result = r.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
        return result;
    }


    public static int chooseRandomSequence(int high) {
        Random r = new Random();
        int lowerLimit = 1;
        int upperLimit = high;
        int result = r.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
        return result;
    }

    public static int getRandomNumberSwap() {
        Random r = new Random();
        int lowerLimit = 0;
        int upperLimit = 1;
        int result = r.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
        return result;
    }


    public static int getRandomNumberLow() {
        Random r = new Random();
        int lowerLimit = 1;
        int upperLimit = 9;
        int result = r.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
        return result;
    }

    public static int getRandomNumberAverage() {
        Random r = new Random();
        int lowerLimit = 10;
        int upperLimit = 19;
        int result = r.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
        return result;
    }

    public static int getRandomNumberHigh() {
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
