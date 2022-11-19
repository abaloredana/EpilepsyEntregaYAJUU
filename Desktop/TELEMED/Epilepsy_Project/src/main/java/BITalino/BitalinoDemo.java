package BITalino;

import static Client.GUI.getMacAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

import javax.bluetooth.RemoteDevice;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BitalinoDemo {

    public static Frame[] frame;
    public static ArrayList<Integer> list1 = new ArrayList<Integer>();
    public static ArrayList<Integer> list2 = new ArrayList<Integer>();
    public int i = 3;
    private static BufferedReader bufferReader;
    private static String username;
    private static String password;
    private static String MAC;

    public int getI() {
        return i;
    }

    public void recordSignal(String MAC) {
        BITalino bitalino = null;
        try {
            bitalino = new BITalino();
            Vector<RemoteDevice> devices = bitalino.findDevices();
            System.out.println(devices);

            //Sampling rate, should be 10, 100 or 1000
            int SamplingRate = 10;
            int[] channelsToAcquire = {3, 5};
            System.out.print("\nTo start reading connect your device to the Bitalino by bluetooth and attach de eleectrodes to your head accordingly\n");
            System.out.print("When ready press any key and enter:");
            String startReading = (new BufferedReader(new InputStreamReader(System.in))).readLine();

            // Start acquisition on analog channels A2 and A6
            // For example, If you want A1, A3 and A4 you should use {0,2,3}
            if (startReading != null) {
                bitalino.open(MAC, SamplingRate);
                bitalino.start(channelsToAcquire);

                //Read in total 10000000 times
                for (int j = 0; j < 5; j++) {

                    //Each time read a block of 10 samples 
                    int block_size = 10;
                    frame = bitalino.read(block_size);

                    System.out.println("size block: " + frame.length);

                    //Print the samples
                    for (int i = 0; i < frame.length; i++) {
                        System.out.println((j * block_size + i) + " seq: " + frame[i].seq + " "
                                + frame[i].analog[0] + " "
                                + frame[i].analog[1] + " "
                        //  + frame[i].analog[2] + " "
                        //  + frame[i].analog[3] + " "
                        //  + frame[i].analog[4] + " "
                        //  + frame[i].analog[5]
                        );
                        list1.add(frame[i].analog[0]);
                        list2.add(frame[i].analog[1]);
                    }

                }
                setList1(list1);
                setList2(list2);
                //stop acquisition
                bitalino.stop();

            } 
        } catch (BITalinoException ex) {
            Logger.getLogger(BitalinoDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(BitalinoDemo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                //close bluetooth connection
                if (bitalino != null) {
                    bitalino.close();
                }
            } catch (BITalinoException ex) {
                Logger.getLogger(BitalinoDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getList1() {
        return list1;
    }

    public static void setList1(ArrayList<Integer> list1) {
        BitalinoDemo.list1 = list1;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getList2() {
        return list2;
    }

    public static void setList2(ArrayList<Integer> list2) {
        BitalinoDemo.list2 = list2;
    }

}
