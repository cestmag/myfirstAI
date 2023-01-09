import java.util.ArrayList;
import java.io.*;
import java.nio.file.Files;

public class Fileloadd{
  private String path1="preservedKS\\";
  private String[] preserveds={path1+"filterbias.txt",
                               path1+"outputbias.txt",
                               path1+"filters.txt",
                               path1+"pools.txt"};


    Object[] load(int numOffiles,int sizeOfSample, int outputsnumber) throws IOException{

         ArrayList<double[][]> samples=new ArrayList<>();
         ArrayList<int[]> corrects=new ArrayList<>();

        

         for(int i=0;i<numOffiles;i++){

            int[] correctNum=new int[outputsnumber];

            for(int d=0;d<correctNum.length;d++){
              correctNum[d]=0;
            }

           int cor=1;

           String dreaming=String.valueOf(i)+"_"+String.valueOf(cor)+".txt";

           String path="datasamples\\"+dreaming;//o

           File prefw=new File(path);

          while(!prefw.exists()){
              cor+=1;
              dreaming=String.valueOf(i)+"_"+String.valueOf(cor)+".txt";
              path="datasamples\\"+dreaming;
              prefw=new File(path);
          }

           correctNum[cor-1]=1;
           corrects.add(correctNum);

           FileReader fw=new FileReader(path);

           BufferedReader br=new BufferedReader(fw);

           String line=null;

           double[][] sample=new double[sizeOfSample][sizeOfSample];

           int count=0;

           while((line=br.readLine())!=null){
            

        

                for(int jr=0;jr<sizeOfSample;jr++){//sample[count][jr]

                    String oneletter=line.substring(jr, jr+1);

                    double one=Double.parseDouble(oneletter);

                     sample[count][jr]=one;  
                }
             
                count++;
           }

           samples.add(sample);

           fw.close();

         }

         Object[] revalue={samples,corrects};

        return revalue;
    }

    void startREwrite(ArrayList<double[][]> filters,ArrayList<ArrayList<double[][]>> pools,double[] filterbiases, double[] outputbias) throws IOException{

     
        
    /*   File file1=new File(path+"filterbias.txt");
      File file2=new File(path+"outputbias.txt");
      File file3=new File(path+"filters.txt");
      File file4=new File(path+"pools.txt");*/
      
      FileWriter fw1=new FileWriter(this.preserveds[0]);
      FileWriter fw2= new FileWriter(this.preserveds[1]);
      FileWriter fw3= new FileWriter(this.preserveds[2]);
      FileWriter fw4= new FileWriter(this.preserveds[3]);

   
      
      for(int oliver=0;oliver<filterbiases.length;oliver++){
        fw1.write(String.valueOf(filterbiases[oliver])+"/");
      }
      fw1.flush();
      fw1.close();

      for(int oliver=0;oliver<outputbias.length;oliver++){
        fw2.write(String.valueOf(outputbias[oliver])+"/");
      }
      fw2.flush();
      fw2.close();

      for(int oliver=0;oliver<filters.size();oliver++){
        double[][] qaz=filters.get(oliver);
        for(int hel=0;hel<qaz.length;hel++){
          for(int y=0;y<qaz[hel].length;y++){
            fw3.write(String.valueOf(qaz[hel][y])+"/");
          }
          fw3.write("\n");
        }
        fw3.write("c");
        fw3.write("\n");
      }

      fw3.flush();
      fw3.close();

      for(int oliver=0;oliver<pools.size();oliver++){//pools.size output no kazu
        for(int taylor=0;taylor<pools.get(oliver).size();taylor++){//filter no kazu
          double[][] qaz=pools.get(oliver).get(taylor);
          for(int hel=0;hel<qaz.length;hel++){
            for(int y=0;y<qaz[hel].length;y++){
            fw4.write(String.valueOf(qaz[hel][y])+"/");
            //System.out.print(qaz[hel][y]+" ");
            }
          fw4.write("\n");
          //System.out.println("\n");
        }
        fw4.write("c");
        fw4.write("\n");
       // System.out.println("");
        }
       fw4.write("cc");
       fw4.write("\n");
      // System.out.println("");
      }

      fw4.flush();
      fw4.close();

      
    }

    Object[] loadData() throws IOException{

      FileReader fr=new FileReader(this.preserveds[0]);//filterbias
      FileReader fr1=new FileReader(this.preserveds[1]);//outputsbias
      FileReader fr2=new FileReader(this.preserveds[2]);//filters
      FileReader fr3=new FileReader(this.preserveds[3]);//0 3 pools

      

      BufferedReader br=new BufferedReader(fr);
      BufferedReader br1=new BufferedReader(fr1);
      BufferedReader br2=new BufferedReader(fr2);
      BufferedReader br3=new BufferedReader(fr3);

      
     double[] filterbias={};
     double[] outputsbias={};//{};
     ArrayList<double[][]> filterr=new ArrayList<>();
     ArrayList<ArrayList<double[][]>> poolss=new ArrayList<>();
     
      //filterbias
      String line=null;

      int x=0;

      while((line=br.readLine())!=null){
        String[] valu=line.split("[/]");
     
          if(x==0){
            filterbias=new double[valu.length];
          }
          
          //System.out.print(valu[2]);
          
          for(int alice=0;alice<filterbias.length;alice++){
            //System.out.println(Double.parseDouble(valu[alice]));
            filterbias[alice]=Double.parseDouble(valu[alice]);//Double.parseDouble(valu[alice]);
          }
        x++;
      }

     x=0;

      while((line=br1.readLine())!=null){
        
        String[] valu=line.split("[/]");
        if(x==0){
          outputsbias=new double[valu.length];
        }
        
        for(int alice=0;alice<outputsbias.length;alice++){
          outputsbias[alice]=Double.parseDouble(valu[alice]);
        }
      
    }
    //filters ArrayList<double[][]>

  x=0;
  //int y=0;
  int zen=0;
  //int leng;

  double[][] ah={};
  while((line=br2.readLine())!=null){
    // System.out.println("x:"+line);
    // System.out.println((line!="c"));

     if(line.length()!=1){//input c ???????????????? なぜかcが来てもline="c"とならないからline.lengthで判別する
     String[] valu=line.split("[/]");
    // System.out.println("yo");
     if(zen==0){
     ah=new double[valu.length][valu.length];
     }
     
     for(int hung=0;hung<valu.length;hung++){
    //  System.out.println(x+" "+hung);
      ah[x][hung]=Double.parseDouble(valu[hung]);
     }

      x++;

     zen++;
    }else if(line.length()==1){
     filterr.add(ah);
     zen=0;
     x=0;
     
    }

    
}

 x=0;
 //y=0;
 zen=0;
 
 ArrayList<double[][]> oh=new ArrayList<>();

 while((line=br3.readLine())!=null){

  if(line.length()!=1&&line.length()!=2){
  String[] valu=line.split("[/]");
  if(zen==0){
  ah=new double[valu.length][valu.length];
  }
  /*if(si==0){
  oh=new ArrayList<>();
  }*/
  for(int mozart=0;mozart<valu.length;mozart++){
    //System.out.println(mozart+" "+x);
    ah[x][mozart]=Double.parseDouble(valu[mozart]);
  }
 
   x++;
   zen++;
 
 }else if(line.length()==1){
  oh.add(ah);
  zen=0;
  x=0;
  //y=0;
 }else if(line.length()==2){
  poolss.add(oh);
  //si=0;
  oh=new ArrayList<>();
 }

 
}

//System.out.println("size:"+filterr.size());
/*for(int hyn=0;hyn<poolss.size();hyn++){
for(int sdf=0;sdf<poolss.get(hyn).size();sdf++){
  double[][] see=poolss.get(hyn).get(sdf);
  for(int qwe=0;qwe<see.length;qwe++){
    for(int rfv=0;rfv<see[qwe].length;rfv++){
      System.out.print(see[qwe][rfv]+" ");
    }
    System.out.println("");
  }
  System.out.println("");
}
    }*/


    

//1.3
      
      



      Object[] a={filterbias,outputsbias,filterr,poolss};
      return a;
    }


}