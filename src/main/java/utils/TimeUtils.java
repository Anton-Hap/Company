package utils;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Date;
import java.util.List;

public abstract class TimeUtils {

    public static void appearLabel(Label label, int milliseconds) {
        List<Double> opacities = List.of(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0);

        label.setOpacity(0.0);

        new Thread(() -> {
            Date oldDate = new Date();
            int index = 0;
            int lastIndex = 10;

            while (true) {
                Date newDate = new Date();

                if (newDate.getTime() - oldDate.getTime() > milliseconds) {
                    int finalIndex = index;

                    Platform.runLater(() -> {
                        label.setOpacity(opacities.get(finalIndex));
                    });

                    if (index == lastIndex) {
                        break;
                    }

                    oldDate = newDate;
                    index++;
                }
            }
        }).start();
    }


    public static void showWaitAndDisappearLabel(Label label, String text, String afterText, int millisecondsWait, int millisecondsDisappear) {
        label.setText(text);

        List<Double> opacities = List.of(1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.0);

        new Thread(() -> {
            Date oldDate = new Date();
            int index = 0;

            wait(millisecondsWait);

            while (true) {
                Date newDate = new Date();

                if (newDate.getTime() - oldDate.getTime() > millisecondsDisappear) {
                    int finalIndex = index;

                    Platform.runLater(() -> {
                        label.setOpacity(opacities.get(finalIndex));
                    });

                    if (index == opacities.size() - 2) {
                        Platform.runLater(() -> {
                            label.setText(afterText);
                            label.setOpacity(opacities.get(0));
                        });

                        break;
                    }

                    oldDate = newDate;
                    index++;
                }
            }
        }).start();
    }

    public static void showWaitAndDisappearLabel(Label label, String text, int millisecondsWait, int millisecondsDisappear) {
        label.setText(text);

        List<Double> opacities = List.of(1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.0);

        new Thread(() -> {
            Date oldDate = new Date();
            int index = 0;

            wait(millisecondsWait);

            while (true) {
                Date newDate = new Date();

                if (newDate.getTime() - oldDate.getTime() > millisecondsDisappear) {
                    int finalIndex = index;

                    Platform.runLater(() -> {
                        label.setOpacity(opacities.get(finalIndex));
                    });

                    if (index == opacities.size() - 2) {
                        Platform.runLater(() -> {
                            label.setText("");
                            label.setOpacity(opacities.get(0));
                        });

                        break;
                    }

                    oldDate = newDate;
                    index++;
                }
            }
        }).start();
    }


    public static void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
