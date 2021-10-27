package org.OppenheimerTest.utility;

import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.Robot;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class RobotHelper {

    public static void uploadFile(String directory) throws AWTException {

        Robot robot = new Robot();
        StringSelection str = new StringSelection(directory);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

        robot.delay(250);
        // Ctrl+V
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);

        // Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(250);
        robot.keyRelease(KeyEvent.VK_ENTER);

    }
}
