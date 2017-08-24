package TourGuide;

import java.util.Scanner;

/**
 * @author László Hágó
 * @since 2017-08-23
 */
public class TourGuide {

    static int tourists;
    static double speedMe, speedThem, angle, guideDistance, x, y, returnDistance, timeTracker, totalTime,
            sumTimeChase, xx, yy, timeStep = 0.05;
    static double[] timeChase, timeReturn;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            tourists = sc.nextInt();
            if (tourists == 0)
                break;

            speedMe = sc.nextDouble();
            timeTracker = 0;
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
                guideDistance = 0;
                returnDistance = 0;

                calculateTourist(i);
            }
            System.out.println(calculateTime());
        }

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

    public static void calculateTourist(int tourist) {

        x += Math.cos(angle) * timeTracker * speedThem;
        y += Math.sin(angle) * timeTracker * speedThem;

        do {
            x += speedThem * timeStep * Math.cos(angle);
            y += speedThem * timeStep * Math.sin(angle);
            timeTracker += timeStep;
            guideDistance = Math.sqrt((x - xx) * (x - xx) + (y - yy) * (y - yy));
        }
        while (guideDistance / speedMe < timeTracker - 0.1 || guideDistance / speedMe > timeTracker + 0.1);
        timeChase[tourist] = timeTracker - sumTimeChase;
        sumTimeChase = timeTracker;
        returnDistance = Math.sqrt(x * x + y * y);
        timeReturn[tourist] = returnDistance / speedThem;

        xx = x;
        yy = y;
    }

}
