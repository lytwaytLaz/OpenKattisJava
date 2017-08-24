package TourGuide;

import java.util.Scanner;

/**
 * @author László Hágó
 * @since 2017-08-23
 */
public class TourGuide {

    static int tourists;
    static double speedGuide, speedThem, angle, guideDistance, x, y, returnDistance, totalTime,
            sumTimeChase, xx, yy;
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
            xx = 0;
            yy = 0;

            for (int i = 0; i < tourists; i++) {
                x = sc.nextDouble();
                y = sc.nextDouble();
                speedThem = sc.nextDouble();
                angle = sc.nextDouble();
                guideDistance = Math.sqrt((x - xx) * (x - xx) + (y - yy) * (y - yy));
                returnDistance = 0;

                calcTourist(i);
            }
            System.out.println(calculateTime());
        }
    }


    public static void calcTourist(int tourist) {
        double guessTime = guideDistance / speedGuide;
        double xOrig = x;
        double yOrig = y;
        double trackMax = 1000000;
        double trackMin = 0;

//        REMOVE!!
//        int c = 0;

        while (true) {
            x = xOrig + Math.cos(angle) * guessTime * speedThem;
            y = yOrig + Math.sin(angle) * guessTime * speedThem;
            guideDistance = Math.sqrt((x - xx) * (x - xx) + (y - yy) * (y - yy));

//            //REMOVE!!!
//            double temp = guideDistance / guessTime;
//            System.out.println(c + ": " + temp);
//            c++;

            if (speedGuide > guideDistance / guessTime + 0.0001) {
                trackMax = guessTime;
                guessTime -= (trackMax - trackMin) / 2;
            } else if (speedGuide < guideDistance / guessTime - 0.0001) {
                trackMin = guessTime;
                guessTime += (trackMax - trackMin) / 2;
            } else
                break;
        }
        timeChase[tourist] = guessTime - sumTimeChase;
        sumTimeChase = guessTime;
        returnDistance = Math.sqrt(x * x + y * y);
        timeReturn[tourist] = returnDistance / speedThem;

        xx = x;
        yy = y;
    }

    private static long calculateTime() {
        totalTime = sumTimeChase + timeReturn[timeReturn.length - 1];
        double timeCount = 0;
        for (int i = 0; i < timeReturn.length; i++) {
            timeCount += timeChase[i];
            if (timeReturn[i] >= totalTime - timeCount) {
                totalTime = timeReturn[i] + timeCount;
            }
        }
        return Math.round(totalTime);
    }
}
