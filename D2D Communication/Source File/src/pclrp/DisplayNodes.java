/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pclrp;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
/**
 *
 * @author admin
 */
public class DisplayNodes extends JPanel 
{
    int no_nodes;
    int x,y;
    ArrayList Node_Names=null;
    JSplitPane splitpane=null;
   
    Details dts=new Details();
    
    DisplayNodes(int no_nodes,ArrayList names,JSplitPane splitpane)
    {
	super();
	this.no_nodes=no_nodes;
	this.x=x;
	this.y=y;
	this.Node_Names=names;		
	this.splitpane=splitpane;	
         
        
    }
    
    public void paintComponent(Graphics g)
    {
        try
        {
            super.paintComponent(g);
            g.setColor(Color.black);
        
            Graphics2D gd=(Graphics2D)g;
             
            ImageIcon ic1=new ImageIcon("s1.jpg");
            Image img1=ic1.getImage();
        
            ImageIcon ic2=new ImageIcon("g1.jpg");
            Image img2=ic2.getImage();
        
            ImageIcon ic3=new ImageIcon("c1.jpg");
            Image img3=ic3.getImage();
            
            ImageIcon ic4=new ImageIcon("n1.jpg");
            Image img4=ic4.getImage();
        
            ImageIcon ic5=new ImageIcon("ln1.jpg");
            Image img5=ic5.getImage();
        
            for(int i=0;i<no_nodes;i++)
            {
                
                if(dts.nodeType[i].equals("sink"))
                {            
                    g.drawImage(img1, (int)dts.nodePos[i][0], (int)dts.nodePos[i][1],144,50, null);
                }
                if(dts.nodeType[i].startsWith("GW"))
                {               
                    g.drawImage(img2, (int)dts.nodePos[i][0], (int)dts.nodePos[i][1],45,65, null);
                    g.drawString((String)Node_Names.get(i), (int)dts.nodePos[i][0], (int)dts.nodePos[i][1]);
                }
                if(dts.nodeType[i].startsWith("C"))
                {               
                    g.drawImage(img3, (int)dts.nodePos[i][0], (int)dts.nodePos[i][1],27,43, null);
                    g.drawString((String)Node_Names.get(i), (int)dts.nodePos[i][0]+25, (int)dts.nodePos[i][1]+10);
                }
                if(dts.nodeType[i].startsWith("N"))
                {               
                    g.drawImage(img4, (int)dts.nodePos[i][0], (int)dts.nodePos[i][1],20,20, null);
                    g.drawString((String)Node_Names.get(i), (int)dts.nodePos[i][0]+20, (int)dts.nodePos[i][1]+10);
                }
            }
            
            if(dts.sense)
            {
                ActionListener animate = new ActionListener() 
                {
                    public void actionPerformed(ActionEvent ae) 
                    {
                        repaint();
                    }
                };
                Timer timer = new Timer(1000,animate);
                timer.start();
                for(int j=0;j<10;j++)
                {
                    for(int i=0;i<no_nodes;i++)
                    {
                        if(dts.nodeType[i].startsWith("N"))
                        {               
                            g.setColor(Color.black);
                            g.drawImage(img4, (int)dts.nodePos[i][0], (int)dts.nodePos[i][1],20,20, null);                        
                            timer.setDelay(50);
                            g.setColor(Color.red);
                            g.drawString((String)Node_Names.get(i), (int)dts.nodePos[i][0]+20, (int)dts.nodePos[i][1]+10);
                            
                        }
                    }
                    timer.setDelay(1000);
                }
                dts.sense=false;
            }
            if(dts.intra)
            {
                for(int i=0;i<dts.ndList.size();i++)
                {
                    String f1=dts.ndList.get(i).toString();
                    String t1=dts.Node_NextHop[i][0];
                    String ty=dts.Node_NextHop[i][1];
                    
                    int ind1=dts.NodeNames.indexOf(f1);
                    int ind2=dts.NodeNames.indexOf(t1);
                    
                    if(ty.equals("RG"))               
                    {
                        double x1=dts.nodePos[ind1][0];
                        double y1=dts.nodePos[ind1][1];
                        double d1=10;
                        double ws=d1+d1;
                        double hs=d1+d1;
                        gd.setColor(Color.MAGENTA);
                        gd.draw(new Ellipse2D.Double(x1 - ws/2, y1 - hs/2, ws, hs));
                    }
                    g.setColor(Color.black);
                    g.drawLine((int)dts.nodePos[ind1][0],(int)dts.nodePos[ind1][1],(int)dts.nodePos[ind2][0],(int)dts.nodePos[ind2][1]);
                    g.setColor(Color.red);
                    g.drawString("*", (int)dts.nodePos[ind2][0], (int)dts.nodePos[ind2][1]+10);
                    g.setColor(Color.black);
                    
                }
            }
            if(!dts.gwLink.isEmpty())
            {
                for(int i=0;i<dts.gwLink.size();i++)
                {
                    String g1[]=dts.gwLink.get(i).toString().split("#");
                    String gw1=g1[0];
                    String c1=g1[1];
                    
                    int ind1=dts.NodeNames.indexOf(gw1);
                    int ind2=dts.NodeNames.indexOf(c1);
                    
                    double p1=(dts.nodePos[ind1][0]+dts.nodePos[ind2][0])/2;
                    double p2=(dts.nodePos[ind1][1]+dts.nodePos[ind2][1])/2;
                    //g.drawImage(img5, (int)p1, (int)p2,70,20, null);
                    g.setColor(Color.BLUE);
                    g.drawLine((int)dts.nodePos[ind1][0],(int)dts.nodePos[ind1][1],(int)dts.nodePos[ind2][0],(int)dts.nodePos[ind2][1]);
                    g.drawLine((int)dts.nodePos[ind1][0]+5,(int)dts.nodePos[ind1][1]+5,(int)dts.nodePos[ind2][0]+5,(int)dts.nodePos[ind2][1]+5);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
