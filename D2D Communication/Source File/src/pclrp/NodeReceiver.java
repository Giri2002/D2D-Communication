/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pclrp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;
/**
 *
 * @author admin
 */
public class NodeReceiver extends Thread
{
    int nid;
    String ndname;
    int nport;
    String coname;
    long Tr=0;
    long T0=0;
    long Tb=0;
    String mydata="";
    
    Details dt=new Details();
    
    NodeReceiver(int id,String nn,String na)
    {
        nid=id;
        ndname=nn;
        coname=na;
        nport=id+9000;
    }
    
    public void run()
    {
        try
        {
            DatagramSocket ds=new DatagramSocket(nport);
            Random rn=new Random();
            int n1=rn.nextInt(dt.pack_Type.length);
            int v1=(nid%6);
            
            String d1[]=dt.delay[v1].split("#");
            
            int min=Integer.parseInt(d1[0]);
            int max=Integer.parseInt(d1[1]);           
			
            int del = rn.nextInt((max - min) + 1) + min;
            
            mydata=dt.pack_Type[n1]+"#"+dt.h_data[v1]+"#"+dt.bit_rate[v1]+"#"+del+"#"+dt.sample_rate[v1];
            System.out.println(ndname+" = "+mydata);
            dt.Node_Data[nid-1][0]=mydata;
            double eng1=Double.parseDouble(dt.Node_Data[nid-1][2])-dt.pack_eng[n1];            
            dt.Node_Data[nid-1][2]=String.valueOf(eng1);
            dt.Node_Data[nid-1][3]=String.valueOf(del);
            System.out.println(ndname+" ======"+dt.Node_Data[nid-1][3]);
            
            while(true)
            {
                byte data[]=new byte[1000];
                DatagramPacket dp1=new DatagramPacket(data,0,data.length);
                ds.receive(dp1);
                
                String str=new String(dp1.getData()).trim();
                String req[]=str.split("#");
               // System.out.println(ndname+" = "+str);
                if(req[0].equals("Beacon1"))
                {
                    dt.Node_Beacon[nid-1]=true;
                    T0=Long.parseLong(req[1]);
                    int pt1=Integer.parseInt(req[2]);
                    Tr=System.currentTimeMillis();
                    long s1=Tr-T0;
                    dt.Node_Data[nid-1][1]=String.valueOf(s1);
                    int c1=Integer.parseInt(dt.Node_Data[nid-1][4])+1;
                    dt.Node_Data[nid-1][4]=String.valueOf(c1);
                    System.out.println(ndname+" : "+s1);
                    
                    String ms1="Beacon2#"+T0+"#"+Tr+"#"+s1+"#"+ndname;
                    byte bt[]=ms1.getBytes();
                    DatagramPacket dp=new DatagramPacket(bt,0,bt.length,InetAddress.getByName("127.0.0.1"),pt1);
                    ds.send(dp);
                    
                    double eng2=Double.parseDouble(dt.Node_Data[nid-1][2])-180.56;            
                    dt.Node_Data[nid-1][2]=String.valueOf(eng2);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
