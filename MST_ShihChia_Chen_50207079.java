import java.io.*;
import java.util.*;  

class Edge
{
    int ver, ver_incident, weight;

    //constructor
    Edge(int ver, int ver_incident, int weight){
        this.ver = ver;
        this.ver_incident = ver_incident;
        this.weight = weight;

    }

    int getVer(){
        return this.ver;
    }

    int getVer_incident(){
        return this.ver_incident;
    }

    int getWeight(){
        return this.weight;
    }

}

class Heap
{
    int size = 0;  //number of the current nodes(which have already inserted into the heap)
    int e_num;
    Edge[] h_edges; 

    Heap(int e_num){
        this.e_num = e_num;
        h_edges = new Edge[this.e_num];     
    }

    void insert(Edge edge){     //add the edge into heap and modify the heap
        size++;
        h_edges[size] = edge;    //add
      //  System.out.println("insert,size:" + size);
      //  System.out.println("insert edge:(" + h_edges[size].getVer() + ", " + h_edges[size].getVer_incident() + ", " + h_edges[size].getWeight() + ")");
        heapify_up(size);        //modify
    }

    void heapify_up(int i){     //modify the order of the heap
        int j;
        Edge temp = new Edge(0, 0, 0);

        while(i>1){
            j = i/2;
            //compare the wieght of the edge
            if(h_edges[i].getWeight() < h_edges[j].getWeight()){          //if the parent edge weight is bigger than the child weight
                //swap
                temp = h_edges[i];
                h_edges[i] = h_edges[j];
                h_edges[j] = temp;
                i = j;
            }
            else
                break;
        }
    }

    Edge extract_min(){                  //take the smallest weight egde out of the heap
        Edge ret = new Edge(0, 0, 0);

        ret = h_edges[1];
        h_edges[1] = h_edges[size];      //move the lastest edge to the first
        size = size-1;
        if( size >=1 )
            heapify_down(1);             //modify the order of the heap

        return ret;
    }

    void heapify_down(int i){           //modify the order of the heap
        int j;
        Edge temp = new Edge(0, 0, 0);

        while( 2*i <= size ){
            if( (2*i==size)||(h_edges[2*i].getWeight() <= h_edges[2*i+1].getWeight()) )
                j = 2*i;
            else
                j = 2*i+1;
           // System.out.println("heapify_down, j:" + j);
            if( h_edges[j].getWeight() < h_edges[i].getWeight() ){     //if the parent edge weight is bigger than the child weight
                //swap
                temp = h_edges[i];
                h_edges[i] = h_edges[j];
                h_edges[j] = temp;
                i = j;
            }
            else{
                //System.out.println("i, j:" + i + "," + j);
                break;
            }
        }
    }
/*
    void show(){
        for (int i = 1 ;i <=size ; i++) {
            System.out.println("Node:" + "vertexs:(" +h_edges[i].getVer() + "," + h_edges[i].getVer_incident()+"),"+h_edges[i].getWeight());
        }
    }
*/

}

class MST_Prim{

    Edge[] MP_main(int[][] v_table, int v_num, int e_num){

        int i;
        Edge[] mst = new Edge[v_num];             //MST set, put the edges
        int mst_num = 0;                          //number of the edges in th mst set
        Edge u = new Edge(0,0,0);                 //the vertex which has the smallest weight and connected with v 
        Heap h = new Heap(e_num);                 //heap
        Edge[] heap_e = new Edge[e_num];          //heap for edges, start from 0
        int he_num = 0;                           //numbers of the edges in heap
        int[] node = new int[v_num+1];            //node in the mst, 1 indicates vertex is in mst, otherwise 0
        int weightsum = 0;                        // the sum of the weight

        // initialize the node set
        for (i = 2 ; i<=v_num; i++) 
            node[i] = 0;
        
        node[1] = 1;     //node[1] is the arbitrary vertex chosen from the graph

        for (i = 1 ; i<v_num ; i++) {
            if (v_table[1][i]!=99999) {      //if there is no edge between two vertexs, then the weight will be infinite(=99999)
                heap_e[he_num] = new Edge(1, i, v_table[1][i]);         
                h.insert(heap_e[he_num]);    //insert all the edges which have the vertex(1)
                he_num++;
            }
        }

        //if the mst_num< v_num-2 means, there are still some vertexs not in the mst set
        //mst_num = number of vertexs-1 , means all vertexs are in the mst, then stop while loop
        while(mst_num < v_num-2){      
            u = h.extract_min();     // pop the smallest weight edge of the heap
            
            //check if the two vertexs(which have the smallest weight edge) are already both in the mst 
            while (node[u.getVer()] == 1 && node[u.getVer_incident()] == 1) {  //if yes, choose the next smallest one
                u = h.extract_min();
            }
            node[u.getVer()] = 1;             //add vertex to the node set
            node[u.getVer_incident()] = 1;    

          //  System.out.println("node:" + u.getVer() +", "+ u.getVer_incident());
            mst[mst_num] = u;                 //add edge to the mst set             
            mst_num++;

            //find other edges that are connected to the vertex(u)
            for ( i = 1; i < v_num ; i++) {
                heap_e[he_num] = new Edge(u.getVer_incident(), i, v_table[u.getVer_incident()][i]);

                //only insert the edges that weight is not infinite(=99999) and is not in the heap
                if (v_table[u.getVer_incident()][i]!=99999 && node[i]==0) {      
                  //  System.out.println("he_num:" + he_num + htable1.containsValue(heap_e[he_num]));
                  //  System.out.println("(" + heap_e[he_num].getVer() + ", " + heap_e[he_num].getVer_incident() + ", " + heap_e[he_num].getWeight()+")");
                    h.insert(heap_e[he_num]);
                    he_num++;
                }
            }
        }
        return mst;
    }
}


public class MST_ShihChia_Chen_50207079
{
    public static void main(String[] args) throws IOException{
    
        //load the file
        InputStreamReader in =new InputStreamReader(new FileInputStream("test6.txt"));
        String line = "";

        BufferedReader b = new BufferedReader(in);
        int v_num, e_num, x, y, w, i;
        
        line = b.readLine();
        String[] s = line.split(" ");
        v_num = Integer.parseInt(s[0]);    //number of the vertexs
        e_num = Integer.parseInt(s[1]);    //number of the edges
        int[][] v_table = new int[v_num + 1][v_num + 1];      //store the weights of the edges
        
        //initialize 
        //there is no edge between two vertexs, x and y, set the weight 99999
        for (x = 0; x <= v_num; x++) {
            for (y = 0; y <= v_num; y++)
                v_table[x][y] = 99999;   
        }

        //build the v_table 
        while((line = b.readLine()) != null) {
            s = line.split(" ");
            x = Integer.parseInt(s[0]);
            y = Integer.parseInt(s[1]);
            w = Integer.parseInt(s[2]);
            // the weight of two vertexs, x and y, is equal to y and x
            v_table[x][y] = w;
            v_table[y][x] = w;
        }

        MST_Prim mstprim = new MST_Prim();
        Edge[] mst = mstprim.MP_main(v_table, v_num+1, e_num+1);
        int weightsum = 0;

        for (y = 0 ; y<v_num-1; y++) {
           // System.out.println("mst:(" + mst[y].getVer() + ", " + mst[y].getVer_incident() + ", " + mst[y].getWeight() + ")");
            weightsum = weightsum + mst[y].getWeight();
        }

       // System.out.println("sum:" + weightsum);

        OutputStream output = new FileOutputStream("output.txt");
        Writer outputStreamWriter = new OutputStreamWriter(output);

        outputStreamWriter.write("weight sum:" + weightsum + System.getProperty("line.separator"));
        for (y = 0 ; y<v_num-1; y++) {
            outputStreamWriter.write("mst:(" + mst[y].getVer() + ", " + mst[y].getVer_incident() + ", " + mst[y].getWeight() + ")");
            outputStreamWriter.write(System.getProperty("line.separator"));
        }
        outputStreamWriter.close();

    
    }

}


