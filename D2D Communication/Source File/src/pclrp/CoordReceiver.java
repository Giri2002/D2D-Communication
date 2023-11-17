/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pclrp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
/**
 *
 * @author admin
 */
public class CoordReceiver extends Thread
{
    int cid;
    String cname;
    int cport;
    long T0=0;
    long max1=0;
    Details dt=new Details();
    Parameters pm=new Parameters();
    
    int mc1=0;
    
    
    CoordReceiver(int id,String na)
    {
        cid=id;
        cname=na;
        cport=id+8000;
    }
    
    public void run()
    {
        try
        {
            DatagramSocket ds=new DatagramSocket(cport);
            T0=System.currentTimeMillis();
            
            String ms1="Beacon1#"+T0+"#"+cport;
            byte bt1[]=ms1.getBytes();
            ArrayList at=dt.CLS[cid-1];
            //System.out.println(cname+" = at = "+at.size());
            for(int i=0;i<at.size();i++)
            {
                String g1[]=at.get(i).toString().split("#");
                int pt=Integer.parseInt(g1[0])+9000;
               // System.out.println(cname+" : "+pt);
                DatagramPacket dp=new DatagramPacket(bt1,0,bt1.length,InetAddress.getByName("127.0.0.1"),pt);
                ds.send(dp);
            }
            
            while(true)
            {
                byte data[]=new byte[1000]; 
                DatagramPacket dp1=new DatagramPacket(data,0,data.length);
                ds.receive(dp1);
                
                String str=new String(dp1.getData()).trim();
                String req[]=str.split("#");
                
                if(req[0].equals("Beacon2"))
                {
                    long tack=System.currentTimeMillis();
                    long s1=tack-T0;
                    max1=Math.max(max1, s1);
                    System.out.println(cname+" ==  "+s1+" : "+max1);
                    mc1++;
                    if(mc1==6)
                    {
                        pm.CFP = pm.ASD - pm.DLD -pm.CCAP_min - pm.NCAP_min;
                        pm.slot_dur=max1+((pm.p_data +pm.ov + 4)/pm.data_rate);
                        pm.NB_S=pm.CFP/pm.slot_dur;
                        
                    }
                    
                    double eng1=Double.parseDouble(dt.cordInfo[cid-1][1])-180.55;
                    dt.cordInfo[cid-1][1]=String.valueOf(eng1);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
