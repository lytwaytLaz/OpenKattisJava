package TourGuide;

import java.util.Scanner;

/**
 * @author László Hágó
 * @since 2017-08-23
 */
public class TourGuide {

    static int numOfTourists;
    static double speedGuide, speedTourist, angle, guideDistance, xT, yT, totalTime, totalTimeMin, xG, yG;
    static double[] timeChase, timeReturn;
    static double[][] tourists;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            numOfTourists = sc.nextInt();
            if (numOfTourists == 0)
                break;

            speedGuide = sc.nextDouble();
            tourists = new double[numOfTourists][4];
            totalTimeMin = 1000000;

            for (int i = 0; i < numOfTourists; i++) {
                tourists[i][0] = sc.nextDouble();
                tourists[i][1] = sc.nextDouble();
                tourists[i][2] = sc.nextDouble();
                tourists[i][3] = sc.nextDouble();
            }
            permute(tourists, 0);
            System.out.println(Math.round(totalTimeMin));
        }
    }


    static void permute(double[][] a, int k) {
        timeChase = new double[numOfTourists];
        timeReturn = new double[numOfTourists];
        xG = 0;
        yG = 0;
        if (k == a.length) {
            for (int i = 0; i < a.length; i++) {
                tourists[i] = a[i];
            }
            for (int t = 0; t < numOfTourists; t++) {
                xT = tourists[t][0];
                yT = tourists[t][1];
                speedTourist = tourists[t][2];
                angle = tourists[t][3];

                calcTourist(t);
            }
            double time = calculateTime();
            totalTimeMin = totalTimeMin > time ? time : totalTimeMin;
        } else {
            for (int i = k; i < a.length; i++) {
                double[] temp = a[k];
                a[k] = a[i];
                a[i] = temp;

                permute(a, k + 1);

                temp = a[k];
                a[k] = a[i];
                a[i] = temp;
            }
        }
    }

    public static void calcTourist(int tourist) {
        double guessTime = 0;
        double xOrig = xT;
        double yOrig = yT;
        double trackMax = 10000000;
        double trackMin = 0;

//        REMOVE!!
//        int c = 0;

        while (true) {
            xT = xOrig + Math.cos(angle) * guessTime * speedTourist;
            yT = yOrig + Math.sin(angle) * guessTime * speedTourist;
            guideDistance = Math.sqrt((xT - xG) * (xT - xG) + (yT - yG) * (yT - yG));

////            //REMOVE!!!
//            double temp = guideDistance / guessTime;
//            System.out.println(c + ": " + temp);
//            c++;

            if (speedGuide > guideDistance / guessTime + 0.000001) {
                trackMax = guessTime;
                guessTime -= (trackMax - trackMin) / 2;
            } else if (speedGuide < guideDistance / guessTime - 0.000001) {
                trackMin = guessTime;
                guessTime += (trackMax - trackMin) / 2;
            } else
                break;
        }
        timeChase[tourist] = guessTime;
        timeReturn[tourist] = Math.sqrt(xT * xT + yT * yT) / speedTourist;

        xG = xT;
        yG = yT;
    }

    private static double calculateTime() {
        totalTime = 0;
        double timeCount = 0;
        for (int i = 0; i < timeReturn.length; i++) {
            timeCount += timeChase[i];
            if (timeReturn[i] > totalTime - timeCount) {
                totalTime = timeReturn[i] + timeCount;
            }
        }
        return totalTime;
    }
}
