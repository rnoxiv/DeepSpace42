/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Enumeration;

public class Arduino implements SerialPortEventListener {

    SerialPort serialPort;

    private static int m;

    private static ArrayList<String> events = new ArrayList<>();

    /**
     * The port we're normally going to use.
     */
    private static final String PORT_NAMES[] = {
        "/dev/tty.usbserial-A9007UX1", // Mac OS X
        "/dev/ttyACM0", // Raspberry Pi
        "/dev/ttyUSB0", // Linux
        "COM3", // Windows
    };
    /**
     * A BufferedReader which will be fed by a InputStreamReader converting the
     * bytes into characters making the displayed results codepage independent
     */
    public static BufferedReader input;
    /**
     * The output stream to the port
     */
    public static OutputStream output;
    /**
     * Milliseconds to block while waiting for port open
     */
    private static final int TIME_OUT = 2000;
    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 9600;

    public void initialize() {
        // the next line is for Raspberry Pi and 
        // gets us into the while loop and was suggested here was suggested http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
        // System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");

        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * This should be called when you stop using the port. This will prevent
     * port locking on platforms like Linux.
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    @Override
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = input.readLine();
                System.out.println(inputLine);
                if (inputLine.equals("31")) {
                    Keys.keySet(KeyEvent.VK_ENTER, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_ENTER, false);
                } else if (inputLine.equals("33")) {
                    Keys.keySet(KeyEvent.VK_ENTER, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_ENTER, false);
                } else if (inputLine.equals("35")) {
                    Keys.keySet(KeyEvent.VK_S, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_S, false);
                } else if (inputLine.equals("37")) {
                    Keys.keySet(KeyEvent.VK_C, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_C, false);
                } else if (inputLine.equals("39")) {
                    Keys.keySet(KeyEvent.VK_B, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_B, false);
                } else if (inputLine.equals("41")) {
                    Keys.keySet(KeyEvent.VK_E, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_E, false);
                } else if (inputLine.equals("43")) {
                    Keys.keySet(KeyEvent.VK_A, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_A, false);
                } else if (inputLine.equals("45")) {
                    Keys.keySet(KeyEvent.VK_N, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_N, false);
                } else if (inputLine.equals("47")) {
                    Keys.keySet(KeyEvent.VK_T, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_T, false);
                } else if (inputLine.equals("48")) {
                    Keys.keySet(KeyEvent.VK_CONTROL, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_CONTROL, false);
                } else if (inputLine.equals("49")) {
                    Keys.keySet(KeyEvent.VK_Y, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_Y, false);
                } else if (inputLine.equals("50")) {
                    Keys.keySet(KeyEvent.VK_P, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_P, false);
                } else if (inputLine.equals("HAUT1")) {
                    Keys.keySet(KeyEvent.VK_UP, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_UP, false);
                } else if (inputLine.equals("BAS1")) {
                    Keys.keySet(KeyEvent.VK_DOWN, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_DOWN, false);
                } else if (inputLine.equals("HAUT2")) {
                    Keys.keySet(KeyEvent.VK_LEFT, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_LEFT, false);
                } else if (inputLine.equals("BAS2")) {
                    Keys.keySet(KeyEvent.VK_RIGHT, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_RIGHT, false);
                } else if (inputLine.equals("K1")) {
                    Keys.keySet(KeyEvent.VK_NUMPAD1, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_NUMPAD1, false);
                } else if (inputLine.equals("K2")) {
                    Keys.keySet(KeyEvent.VK_NUMPAD2, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_NUMPAD2, false);
                } else if (inputLine.equals("K3")) {
                    Keys.keySet(KeyEvent.VK_NUMPAD3, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_NUMPAD3, false);
                } else if (inputLine.equals("K4")) {
                    Keys.keySet(KeyEvent.VK_NUMPAD4, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_NUMPAD4, false);
                } else if (inputLine.equals("K5")) {
                    Keys.keySet(KeyEvent.VK_NUMPAD5, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_NUMPAD5, false);
                } else if (inputLine.equals("K6")) {
                    Keys.keySet(KeyEvent.VK_NUMPAD6, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_NUMPAD6, false);
                } else if (inputLine.equals("K7")) {
                    Keys.keySet(KeyEvent.VK_NUMPAD7, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_NUMPAD7, false);
                } else if (inputLine.equals("K8")) {
                    Keys.keySet(KeyEvent.VK_NUMPAD8, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_NUMPAD8, false);
                } else if (inputLine.equals("K9")) {
                    Keys.keySet(KeyEvent.VK_NUMPAD9, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_NUMPAD9, false);
                } else if (inputLine.equals("K0")) {
                    Keys.keySet(KeyEvent.VK_NUMPAD0, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_NUMPAD0, false);
                } else if (inputLine.equals("K*")) {
                    Keys.keySet(KeyEvent.VK_MULTIPLY, true);
                    sleep(50);
                    Keys.keySet(KeyEvent.VK_MULTIPLY, false);
                }
                addEvent(inputLine);
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.

    }

    public static synchronized void writeData(String data) {
        //System.out.println("Sent: " + data);
        try {
            output.write(data.getBytes());
        } catch (Exception e) {
            //System.out.println("could not write to port");
        }
    }

    public static byte[] stringToBytesASCII(String str) {
        char[] buffer = str.toCharArray();
        byte[] b = new byte[buffer.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) buffer[i];
        }
        return b;
    }

    public static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) {
                    return false;
                } else {
                    continue;
                }
            }
            if (Character.digit(s.charAt(i), radix) < 0) {
                return false;
            }
        }
        return true;
    }

    private boolean addEvent(String event) {
        String eventToAdd = event;
        if (events.contains(eventToAdd)) {
            return false;
        } else {
            events.add(eventToAdd);

            return true;
        }
    }

    public static boolean checkEvent(String event) throws InterruptedException {
        for (int i = 0; i < events.size(); i++) {
            if (event.equals(events.get(i))) {
                System.out.println(events.toString());
                events.remove(i);
                System.out.println(events.toString());
                return true;
            }
        }
        return false;
    }

    public static void printLCD(int message) throws IOException {
        clearLCD();
        if (message == 1) { // WELCOME TO DEEP SPACE 42
            writeData(Character.toString((char) 97));
            writeData(Character.toString((char) 66));
        } else if (message == 2) {// WARNING FIRE

            writeData(Character.toString((char) 99));
            writeData(Character.toString((char) 69));
        } else if (message == 3) {// WARNING FIGHT

            writeData(Character.toString((char) 99));
            writeData(Character.toString((char) 70));
        } else if (message == 4) {// ASTEROID INCOMING

            writeData(Character.toString((char) 102));
            writeData(Character.toString((char) 74));
        } else if (message == 5) {// SHIP INCOMING

            writeData(Character.toString((char) 102));
            writeData(Character.toString((char) 73));
        } else if (message == 6) {// SHIELD ON

            writeData(Character.toString((char) 103));
        } else if (message == 7) {// SHIP INCOMING

            writeData(Character.toString((char) 104));
        }

        m = message;
    }

    public int getMessage() {
        return m;
    }

    public static void clearLCD() throws IOException {
        writeData(Character.toString((char) 126));
    }

    public static void turnOnLed(int message) throws IOException, InterruptedException {

        if (message == 1) {
            writeData(Character.toString((char) 33));
        } else if (message == 2) {
            writeData(Character.toString((char) 34));
        } else if (message == 3) {
            writeData(Character.toString((char) 35));
        } else if (message == 4) {
            writeData(Character.toString((char) 36));
        } else if (message == 5) {
            writeData(Character.toString((char) 37));
        } else if (message == 6) {
            writeData(Character.toString((char) 38));
        } else if (message == 7) {
            writeData(Character.toString((char) 39));
        } else if (message == 8) {
            writeData(Character.toString((char) 40));
        } else if (message == 9) {
            writeData(Character.toString((char) 41));
        } else if (message == 10) {
            writeData(Character.toString((char) 42));
        } else if (message == 11) {
            writeData(Character.toString((char) 43));
        } else if (message == 12) {
            writeData(Character.toString((char) 44));
        } else if (message == 13) {
            writeData(Character.toString((char) 45));
        } else if (message == 14) {
            writeData(Character.toString((char) 46));
        } else if (message == 15) {
            writeData(Character.toString((char) 47));
        } else if (message == 16) {
            writeData(Character.toString((char) 48));
        } else if (message == 17) {
            writeData(Character.toString((char) 49));
        }

        writeData(Character.toString((char) 34));
    }

    public static void turnOffLed(int message) throws IOException {

        if (message == 1) {
            writeData(Character.toString((char) 33));
        } else if (message == 2) {
            writeData(Character.toString((char) 34));
        } else if (message == 3) {
            writeData(Character.toString((char) 35));
        } else if (message == 4) {
            writeData(Character.toString((char) 36));
        } else if (message == 5) {
            writeData(Character.toString((char) 37));
        } else if (message == 6) {
            writeData(Character.toString((char) 38));
        } else if (message == 7) {
            writeData(Character.toString((char) 39));
        } else if (message == 8) {
            writeData(Character.toString((char) 40));
        } else if (message == 9) {
            writeData(Character.toString((char) 41));
        } else if (message == 10) {
            writeData(Character.toString((char) 42));
        } else if (message == 11) {
            writeData(Character.toString((char) 43));
        } else if (message == 12) {
            writeData(Character.toString((char) 44));
        } else if (message == 13) {
            writeData(Character.toString((char) 45));
        } else if (message == 14) {
            writeData(Character.toString((char) 46));
        } else if (message == 15) {
            writeData(Character.toString((char) 47));
        } else if (message == 16) {
            writeData(Character.toString((char) 48));
        } else if (message == 17) {
            writeData(Character.toString((char) 49));
        }

        writeData(Character.toString((char) 33));
    }

    public static void main(String[] args) throws Exception {
        Arduino main = new Arduino();
        main.initialize();
        Thread t;
        t = new Thread() {
            @Override
            public void run() {
//the following line will keep this app alive for 1000 seconds,
//waiting for events to occur and responding to them (printing incoming messages to console).
                try {
                    Thread.sleep(1500);
                    writeData("2");
                } catch (InterruptedException ie) {
                }
            }
        };
        t.start();
        System.out.println("Started");
    }
}
