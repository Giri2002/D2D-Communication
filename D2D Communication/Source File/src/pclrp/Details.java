/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pclrp;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Details {
    static String node_path="node.txt";
    static String link_path="link.txt";
    static int no_nodes=0;
    static double nodePos[][];
    static String nodeType[];
    
    static double linkPos[][];
    
    static ArrayList nodeList=new ArrayList();
    static ArrayList NodeNames=null;
    static ArrayList nodeLink=new ArrayList();
    
    static ArrayList gwLink=new ArrayList();
    
    static int Coord=0;
    static ArrayList coordList=new ArrayList();
    static int GW=0;    
    static ArrayList gwList=new ArrayList();
    
    static String name1[]={"a","b","c","d","e","f"};
    
    static ArrayList CLS[];
    static String cordInfo[][];
    
    static String Nodes[][];
    static String Node_Data[][];
    static Boolean Node_Beacon[];
    static String Node_NextHop[][];
    static ArrayList ndList=new ArrayList();
    
    static boolean sense=false;
    static boolean intra=false;
    
    static String pack_Type[]={"EM","DS","GM"};
    static double pack_eng[]={250.82,150.23,100.71};
    static String h_data[]={"EC","BP","NIC","CA","CO2","Tm"};
    static double bit_rate[]={50,15,0.05,1,1,0.3};
    static String delay[]={"0#10","120#200","30#120","0#10","30#120","120#200"};
    static double sample_rate[]={100,63,0.025,63,63,0.02};
    static double confid[]={1,0.75,0.5,0};
}
