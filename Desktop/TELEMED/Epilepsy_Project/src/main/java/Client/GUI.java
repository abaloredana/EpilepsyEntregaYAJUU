/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package Client;

import java.io.*;

/**
 *
 * @author loredanaabalo
 */
public class GUI {
    private static BufferedReader bufferReader;
    public static void main(String[] args) throws IOException {
        
    }
    public static String getMacAddress() throws IOException{
        String macAddress;
        bufferReader = new BufferedReader(new InputStreamReader(System.in));
        macAddress = bufferReader.readLine();
        return macAddress;
    }
    public static int[] getChannels() throws IOException{
        int[] channels = new int[2];
        int channel1, channel2;
        bufferReader = new BufferedReader(new InputStreamReader(System.in));
        channel1 = Integer.parseInt(bufferReader.readLine());
        bufferReader = new BufferedReader(new InputStreamReader(System.in));
        channel2 = Integer.parseInt(bufferReader.readLine());      
        channels[0] = channel1;
        channels[1] = channel2;
        return channels;
    }
}
