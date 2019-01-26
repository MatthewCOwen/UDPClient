package app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Scanner;

public class UDPClient
{
    DatagramSocket socket;

    private static final int PORT_NUMBER = 8765;

    private byte[] RIO_IP;
        
    public UDPClient(String ipAddress)
    {
    	RIO_IP = new byte[4];
    	
    	String[] numbers = ipAddress.split("\\.");
    	
    	int i = 0;
    	for (String number : numbers)
    	{	
    		RIO_IP[i++] = (byte)Integer.parseInt(number);
    		
    		System.out.println(RIO_IP[i - 1]);
    	}
    }

    public void createAndListenSocket()
    {
        try
        {
            socket = new DatagramSocket();
            
            InetAddress IPAddress = InetAddress.getByAddress(RIO_IP);
            
            System.out.println(IPAddress);

            VisionObject vo = new VisionObject(26, 37);

            //ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //ObjectOutputStream os = new ObjectOutputStream(baos);
            //os.writeObject(vo);
            //byte[] data = baos.toByteArray();
            
            StringBuilder sb = new StringBuilder();
            
            Gson gson = new GsonBuilder().create();
            gson.toJson(vo, sb);
            
            String json = sb.toString();
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(baos);
            os.writeObject(json);
            
            byte[] data = baos.toByteArray();
            
            DatagramPacket sendPacket = new DatagramPacket(data, data.length,
                                                           IPAddress, PORT_NUMBER);
   
            System.out.println(json);
            
            socket.send(sendPacket);
            System.out.println("Sent Packet");

            byte[] incomingData = new byte[1024];
            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            socket.receive(incomingPacket);

            System.out.println("Server Response: " + incomingPacket.getData());
            Thread.sleep(2000);
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
    	System.out.println("Enter the IP of the RIO:");
    	
    	Scanner scan = new Scanner(System.in);
    	
        UDPClient client = new UDPClient(scan.nextLine());
        
        scan.close();

        while (true)
        {
            client.createAndListenSocket();
        }
    }
}