package TourGuide;

import java.util.Scanner;

/**
 * @author László Hágó
 * @since 2017-08-23
 */
public class TourGuide {

    static int tourists;
    static double speedGuide, speedTourist, angle, guideDistance, xT, yT, returnDistance, totalTime,
            sumTimeChase, xG, yG;
    static double[] timeChase, timeReturn;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            tourists = sc.nextInt();
            if (tourists == 0)
                break;

            speedGuide = sc.nextDouble();
            timeChase = new double[tourists];
            timeReturn = new double[tourists];
            sumTimeChase = 0;
            xG = 0;
            yG = 0;

            for (int i = 0; i < tourists; i++) {
                xT = sc.nextDouble();
                yT = sc.nextDouble();
                speedTourist = sc.nextDouble();
                angle = sc.nextDouble();

                calcTourist(i);
            }
            System.out.println(calculateTime());
        }
    }


    public static void calcTourist(int tourist) {
        double guessTime = 0;
        double xOrig = xT;
        double yOrig = yT;
        double trackMax = 1000000;
        double trackMin = 0;

//        REMOVE!!
//        int c = 0;

        while (true) {
            xT = xOrig + Math.cos(angle) * guessTime * speedTourist;
            yT = yOrig + Math.sin(angle) * guessTime * speedTourist;
            guideDistance = Math.sqrt((xT - xG) * (xT - xG) + (yT - yG) * (yT - yG));

//            //REMOVE!!!
//            double temp = guideDistance / guessTime;
//            System.out.println(c + ": " + temp);
//            c++;

            if (speedGuide > guideDistance / guessTime + 0.00001) {
                trackMax = guessTime;
                guessTime -= (trackMax - trackMin) / 2;
            } else if (speedGuide < guideDistance / guessTime - 0.00001) {
                trackMin = guessTime;
                guessTime += (trackMax - trackMin) / 2;
            } else
                break;
        }
//        timeChase[tourist] = guessTime - sumTimeChase;
//        sumTimeChase = guessTime;


        timeChase[tourist] = guessTime;
//        returnDistance = Math.sqrt(xT * xT + yT * yT);
        timeReturn[tourist] = Math.sqrt(xT * xT + yT * yT) / speedTourist;

        xG = xT;
        yG = yT;
    }

    private static int calculateTime() {
        for(double k : timeChase) {
            System.out.print(k + " ");
            sumTimeChase += k;
        }
        System.out.println();
        for(double j : timeReturn)
            System.out.print(j+" ");
        System.out.println();

        totalTime = sumTimeChase + timeReturn[timeReturn.length - 1];
        double timeCount = 0;
        for (int i = 0; i < timeReturn.length; i++) {
            timeCount += timeChase[i];
            if (timeReturn[i] >= totalTime - timeCount) {
                totalTime = timeReturn[i] + timeCount;
            }
        }
        System.out.println(totalTime);
        return (int)Math.round(totalTime);
    }
}
