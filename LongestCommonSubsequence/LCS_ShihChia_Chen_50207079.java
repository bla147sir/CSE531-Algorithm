import java.io.*;
import java.util.*;  

public class LCS_ShihChia_Chen_50207079
{
    public static void main(String[] args) throws IOException{
    
        //load the file
        InputStreamReader in =new InputStreamReader(new FileInputStream("input.txt"));
        String line = "";

        BufferedReader b = new BufferedReader(in);
        
        line = b.readLine();
        String []s1 = line.split("");
        line = b.readLine();
        String []s2 = line.split("");        

        int [][][]dp_table = new int[s1.length+1][s2.length+1][2];
        // 0 : end ; 1: <-  ; 2: the upper left arrow ; 3: the upper arrow
        int i, j, k, flag;

        //initialize table
        for (i = 0; i <= s1.length; i++) {
            for (j = 0; j <= s2.length; j++) {
                for (k = 0; k < 2; k++) {
                    dp_table[i][j][k] = 0;
                }
            }            
        }

        //build the optimal table
        for (i = 1; i <= s1.length; i++) {
            for (j = 1; j <= s2.length; j++) {
                flag = s1[i-1].compareTo(s2[j-1]);
                if (flag == 0) {
                    dp_table[i][j][0] = dp_table[i-1][j-1][0] + 1;
                    dp_table[i][j][1] = 2;  //the upper left arrow 
                }
                else if (dp_table[i][j-1][0] >= dp_table[i-1][j][0]){
                    dp_table[i][j][0] = dp_table[i][j-1][0];
                    dp_table[i][j][1] = 1;  // <-
                }
                else{
                    dp_table[i][j][0] = dp_table[i-1][j][0];
                    dp_table[i][j][1] = 3;  // upper arrow
                }
            }            
        }

        //print table
        /*
        for (i = 0; i <= s1.length; i++) {
            for (j = 0; j <= s2.length; j++) {
                System.out.print(dp_table[i][j][0] + "," + dp_table[i][j][1]+ "     ");
            }
            System.out.println(" ");
        }*/
        
        //find the longest common subsequence
        String []sub_s = new String[dp_table[s1.length][s2.length][0]];
        int num_s = 0;
        i = s1.length;
        j = s2.length;

        while (i>0 && j>0){
            if (dp_table[i][j][1] == 2){  //upper left arrow
                sub_s[num_s] = s1[i-1];
                num_s++;
                i--;
                j--;
            }
            else if (dp_table[i][j][1] == 3)  //upper arrow
                i--;
            else
                j--;
        }

        i = 0;
        //output file

        OutputStream output = new FileOutputStream("output.txt");
        Writer outputStreamWriter = new OutputStreamWriter(output);

        outputStreamWriter.write(dp_table[s1.length][s2.length][0] + System.getProperty("line.separator"));
        for (i = sub_s.length ; i > 0; i--) {
            outputStreamWriter.write(sub_s[i-1]);
        }
        outputStreamWriter.close();

    
    }

}


