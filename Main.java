import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    
    public static void main(String[] args) throws IOException{

       Fileloadd fileload=new Fileloadd();

       System.out.println("Learning process:0, dissect process:1");
       int input=new java.util.Scanner(System.in).nextInt();

        switch(input){
        case 0:
           learning_process(fileload);
        break;

        case 1:
          double[][] samplephoto1={{0,0,1,1,0,0},
                                   {0,1,0,0,1,0},
                                   {0,0,0,0,1,0},
                                   {0,1,1,1,1,0},
                                   {0,0,0,0,1,0},
                                   {1,1,1,1,1,0}};

          dissect_process(fileload, samplephoto1);
        break;
       }
      // learning_process(fileload);

       /*double[][] samplephoto1={{0,1,1,1,1,0},
                                 {0,0,0,0,1,0},
                                 {0,0,0,0,1,0},
                                 {0,0,1,1,1,0},
                                 {0,0,0,0,1,0},
                                 {0,1,1,1,1,0}};*/
  /*    int numOfOutputs=3;
      int ScaleOfSample=6;
      int numOfSamples=96;

      ArrayList<double[][]> samples=new ArrayList<>(); //sampe 不変
      ArrayList<int[]> corrects=new ArrayList<>();

      Object[] answers=fileload.load(numOfSamples, ScaleOfSample,numOfOutputs);

      samples=(ArrayList)answers[0];
      corrects=(ArrayList)answers[1];

      for(int a=0; a<numOfSamples;a++){
        System.out.println("num:"+a);
        dissect_process(fileload, samples.get(a));
      }*/

      

       
    }

    public static void learning_process(Fileloadd fileload) throws IOException{
       final double N=0.2;

      int numOfOutputs=3;
      int numOfFilters=3;
      int poolscalee=2;//usually two but not based on a certain logic
      int scaleOffilter=3;
      int ScaleOfSample=6;

      int tatamikomiOkisa=ScaleOfSample-scaleOffilter+1;

      int poolOkisa=(int)tatamikomiOkisa/poolscalee;

      int numOfSamples=96;

      ActivationFunc equation=new ActivationFunc();

      //Fileloadd fileload=new Fileloadd();

      Rando randomy=new Rando();

      ArrayList<double[][]> samples=new ArrayList<>(); //sampe 不変
      ArrayList<int[]> corrects=new ArrayList<>();//各サンプルの正解が入るex)output 3で正解が 1なら{1,0,0}　不変
      
      Object[] answers=fileload.load(numOfSamples, ScaleOfSample,numOfOutputs);

      samples=(ArrayList)answers[0];
      corrects=(ArrayList)answers[1];
    //読み込み・格納に問題はない
      
    //  System.out.println("oli");
    //   System.out.println(samples.get(0)[0][3]);
      //samples=fileload.load(numOfSamples, ScaleOfSample);
      //note (ScaleOfSample-scaleOffilter+1)/poolscalee must be integer, which determines the size Of omomi
      
      ArrayList<double[][]> ConOfFilters=new ArrayList<>();//p211 式(1) w 変化する
      ArrayList<double[][]> ConofFiltersGrad=new ArrayList<>();//変化する

      for(int asap=0;asap<numOfFilters;asap++){
       double[][] stayc=new double[scaleOffilter][scaleOffilter];
       double[][] argen=new double[scaleOffilter][scaleOffilter];
       for(int yone=0;yone<stayc.length;yone++){
           for(int zu=0;zu<stayc.length;zu++){
               argen[yone][zu]=randomy.randomy();
               stayc[yone][zu]=0.0;

           }
       }
       ConOfFilters.add(argen);
       ConofFiltersGrad.add(stayc);
       
      }

      double[][] filter11={{0,0,1},
                           {0,1,0},
                           {1,0,0}};
       
       //ConOfFilters.add(filter11);

      double[][] filter22={{0,1,0},
                           {0,1,0},
                           {0,1,0}};
      // ConOfFilters.add(filter22);

      double[][] filter33={{0,0,0},
                           {0,0,0},
                           {1,1,1}};
      // ConOfFilters.add(filter33);

      ArrayList<ArrayList<double[][]>> omomis=new ArrayList<>();//p211 式(3) w
      ArrayList<ArrayList<double[][]>> omomiGrad=new ArrayList<>();

      double[][] outputbiases=new double[2][numOfOutputs];
      double[][] filterbiases=new double[2][numOfFilters];


      for(int aes=0;aes<outputbiases.length;aes++){
       for(int toe=0;toe<outputbiases[aes].length;toe++){
         switch(aes){
           case 0:
             outputbiases[aes][toe]=randomy.randomy();//正規分布に従った乱数
           break;

           case 1:
           outputbiases[aes][toe]=0;
           break;

         }
       }
      }

      
      for(int pa=0;pa<filterbiases.length;pa++){
         for(int twice=0;twice<filterbiases[pa].length;twice++){
           switch(pa){
             case 0:
               filterbiases[pa][twice]=randomy.randomy();//正規分布に従った乱数
              // System.out.println(filterbiases[pa][twice]);
             break;
 
             case 1:
               filterbiases[pa][twice]=0;
             break;

             default:
 
           }
         }
      }

      //double[][] outputbiases={{1,1,1},{0,0,0}};//[1][] ni Grad ga hairu p211 (3) b
      //double[] outputbiases={1,1,1};//数列の大きさはoutputの数
      //double[] outputbiasesGrad={0,0,0};
      //double[][] filterbiases={{1,1,1},{0,0,0}};//[1][] ni Grad ga hairu p211 (1) b
      //数列の大きさはfilterの数
     

      for(int i=0;i<numOfOutputs;i++){//filterNum outputNum 

       ArrayList<double[][]> omomis1=new ArrayList<>();
       ArrayList<double[][]> omomis1grad=new ArrayList<>();

        for(int j=0;j<numOfFilters;j++){
           //(lenOfSample-lenOffilter+1)/poolscale must be integer
           int sizeOf=poolOkisa;
           double[][] omo=new double[sizeOf][sizeOf];
           double[][] omograd=new double[sizeOf][sizeOf];
           for(int l=0;l<sizeOf;l++){
               for(int m=0;m<sizeOf;m++){
                   omo[l][m]=randomy.randomy();//正規分布に従う乱数にすればよい
                   //System.out.println(omo[l][m]);
                   omograd[l][m]=0;
               }
           }
        //   double[][] omomi1to1={{1,1},{1,1}};//最初の重みづけは適当wwww正規分布に従う乱数にすればよい?
           omomis1.add(omo);
           omomis1grad.add(omograd);
        }
        omomis.add(omomis1);
        omomiGrad.add(omomis1grad);
      }

     

      ArrayList<Filter> filters=new ArrayList<>();

      for(int i=0;i<numOfFilters;i++){
      // System.out.println(filterbiases[0][i]);
         Filter filter=new Filter(ConOfFilters.get(i), filterbiases[0][i],poolscalee,numOfOutputs,ScaleOfSample);
         filters.add(filter);
      }   
   /*  System.out.println(filters.get(0).inherentBias);
    System.out.println(filters.get(1).inherentBias);
    System.out.println(filters.get(2).inherentBias);*/

      ArrayList<Outputlayer> outputs=new ArrayList<>();

      for(int i=0;i<numOfOutputs;i++){
        Outputlayer output=new Outputlayer(outputbiases[0][i]);//正規分布に従う乱数
        outputs.add(output);
      }

     int attemptsnumbers=150;
     int qwe=0;

     //array for costTs
     //double costT=0.0;      

while(qwe<attemptsnumbers){
 double costT=0.0; 
 double losstotall=0.0;   
 //  double[] costs=new double[numOfSamples];
   //   for(int i=0;i<costs.length;i++){
     //  costs[i]=0;
     // }
for(int ii=0;ii<numOfSamples;ii++){

      //ArrayList<double[][]> zf=new ArrayList<>(); 
      double costs=0.0;
      double loss=0.0;

      ArrayList<double[][]> pools=new ArrayList<>();

      for(int i=0;i<numOfFilters;i++){
        pools.add(filters.get(i).getpool(samples.get(ii),i));// p199 上図 特徴マップからプーリング層を生成
        //zf.add(filters.get(i).getter("zf"));
        //filters.get(i).getter("pmap")でp221の畳み込み層のz34(F1)を特定k,lし例5のramdaを求める
      }
      //omomis.get(numOfoutputここを変えていく).get(numOffilter)[pool no tate fixed][pool no yoko fixed]
      

    

      int siz=poolOkisa;
      double[][][][] WandZ=new double[numOfFilters][siz][siz][numOfOutputs+1+2];//p221 ex5 wとzを入れるための3次元配列 +2は畳み込みシート上の座標xy

      //さらにWandZは×フィルタターの数しなければいけない!!!!!!!!!!
      //double[] omomis={omomi1to1,omomi1to2,omomi1to3};
      double[] ramdas=new double[numOfOutputs];

   for(int i=0;i<numOfOutputs;i++){

       Outputlayer o=outputs.get(i);
       o.calc(pools, omomis.get(i),i);//出力 a0
       double a=o.getter(1);
       costs+=Math.pow(corrects.get(ii)[i]-a, 2);//x=numOfsamples corrects.get(ii)[i]
       loss+=(-corrects.get(ii)[i]*Math.log(a)-(1-corrects.get(ii)[i])*(1-a));

       //-corrects.get(ii)[i]
       //System.out.println("ha");
       //System.out.println("+ "+Math.pow(corrects.get(ii)[i]-a, 2));
       
       //System.out.println("cost:"+costs);
   /*  System.out.println("1:"+String.valueOf(corrects.get(ii)[i])+" 2:"+String.valueOf(a));
       System.out.print(i);
       System.out.print("/");
       System.out.print(ii);
       System.out.print("\n");
       System.out.println(costs); */    //!!!!!!!!!not infinity
       double ramda=(a-corrects.get(ii)[i])*o.shiki.activation_function_dash(o.getter(0));//output=3 no toki p218(17)

       ramdas[i]=ramda;
       
       outputbiases[1][i]+=ramda; //p215 (8) migi !!!!!!!!!!!!!!!!!ここら辺までチェックしたここから下よろ

     //  ArrayList<double[][]> omomis1Grad=new ArrayList<>();

        for(int j=0;j<numOfFilters;j++){
           //(lenOfSample-lenOffilter+1)/poolscale must be integer
           String[][] mapOfpool=filters.get(i).getter2();
           double[][] zfss=filters.get(i).getter("zf");

           int sizeOf=poolOkisa;
        /*    double[][] omo=new double[sizeOf][sizeOf];

           for(int taylor=0;taylor<omo.length;taylor++){//0 ni syokika 
               for(int harry=0;harry<omo[taylor].length;harry++){
                   omo[taylor][harry]=0;
               }
           }*/

           for(int l=0;l<sizeOf;l++){
               for(int m=0;m<sizeOf;m++){
                   //
                   omomiGrad.get(i).get(j)[l][m]+=pools.get(j)[l][m]*ramda;//p215 式(8) hidari
                 //  omo[l][m]+=pools.get(j)[l][m]*ramda;//p215 式(8) hidari
                   //pools.get(j)[l][m]
                      //mapOfpool[l][m]で p221図 同じ座標をzfss[][]によりzを得る
                      //収納

                      for(int sac=0;sac<omomis.size();sac++){//=numOfoutputs=omomis.size()
                         //double[] warray= new double[omomis.size()];
                         //double w=omomis.get(sac).get(j)[l][m];
                         //warray[sac]=omomis.get(sac).get(j)[l][m];//収納
                         WandZ[j][l][m][sac]=omomis.get(sac).get(j)[l][m];// p221 no w1-22 w1-22 nadoireru
                      }

                      int x=Integer.parseInt(mapOfpool[l][m].substring(0, 1));  
                      int y=Integer.parseInt(mapOfpool[l][m].substring(1, 2));

                      WandZ[j][l][m][omomis.size()]=zfss[x][y];//p221 no z34 wo ireru
                      //j filter
                      WandZ[j][l][m][omomis.size()+1]=x;

                      WandZ[j][l][m][omomis.size()+2]=y;
                      
                    //omomis.get(numOfoutputここを変えていく).get(numOffilter)[pool no tate fixed][pool no yoko fixed]でp221 図w1-22とかを得る
                   }
           }
        //   double[][] omomi1to1={{1,1},{1,1}};//最初の重みづけは適当wwww正規分布に従う乱数にすればよい?
         //  omomis1Grad.add(omo);
           
        }
      //  omomiGrad.add(omomis1Grad);


        

      }
      //costs*=(1/2);//なくてもよい cost*= だと0.0 にcost/=だとinfinity になるwhyyyyy
      //System.out.println("pollination");
     // System.out.println("costs before 1/2 "+String.valueOf(costs));
     // costs=(costs*(0.5));
     // System.out.println("costs after 1/2 "+String.valueOf(costs));
      costT+=costs;//costs infinity whyyyy
      losstotall+=loss;
      //System.out.println("total:"+costT);//!!!!!!!!!!!!!
      //System.out.println("a");
      //System.out.println("l:"+costT);//infinity
      //ramdaがoutputの個数分出そろった後 ramdasとwandzを使いfunyafunyaを導出
    for(int lese=0;lese<numOfFilters;lese++){
    //double fulltotall=0.0;
    
    //double totall=0.0;
     for(int q=0;q<poolOkisa;q++){
       for(int r=0;r<poolOkisa;r++){
           //(WandZ[q][r][0]*ramdas[0]+   ... +WandZ[2]*ramdas[2])*filter.shiki.dash(WandZ[q][r][4])
           double totall=0.0;
           for(int ski=0;ski<numOfOutputs;ski++){//=omomis.size() p218 p220
                totall+=WandZ[lese][q][r][ski]*ramdas[ski];//p221 ex5 p220 (25)

           }
           totall*=equation.activation_function_dash(WandZ[lese][q][r][numOfOutputs]);

           filterbiases[1][lese]+=totall;

           int x=(int) WandZ[lese][q][r][numOfOutputs+1];//p221 zu 34 no 3
           int y=(int) WandZ[lese][q][r][numOfOutputs+2];//p221 zu 34 no 4 

          //ConofFiltersGrad

           for(int look=0;look<scaleOffilter;look++){//p217 (12)
               for(int what=0;what<scaleOffilter;what++){
                  
                   for(int made=look;made<tatamikomiOkisa;made++){
                     for(int doo=what;doo<tatamikomiOkisa;doo++){
                             //p217 (12)
                             //ConofFiltersGrad.get(numOfFilters) double[][]
                             //made-look, doo-what
                             if(x==made-look&&y==doo-what){
                               ConofFiltersGrad.get(lese)[look][what]+=samples.get(ii)[made][doo]*totall;//p217 (12)
                             }
                     }
                   }
               }
             }
          
       }
     }
     

   
   }
   
   
      //omomisgradをarraylistに格納
      //for(int j=0;j<costs.length;j++){
      
    //}
  if(ii!=numOfSamples-1){
    for(int ger=0;ger<numOfFilters;ger++){
     filters.get(ger).reset(ConOfFilters.get(ger), filterbiases[0][ger], poolscalee, numOfOutputs,ScaleOfSample);
 }
 

for(int spa=0;spa<numOfOutputs;spa++){
    outputs.get(spa).reset(outputbiases[0][spa]);
}
 }

   }
//全サンプル画像の捜査終了
  System.out.println(qwe+" "+costT+" "+losstotall/numOfSamples/*/numOfSamples*/);
 
   //costT

   //ConofFiltersGrad,omomiGrad,filterbiases[1],outputbiases[1] は初期化!!!!!!!!
   //係数の更新 and 初期化
   for(int i=0;i<numOfOutputs;i++){//filterNum outputNum 

        outputbiases[0][i]+=outputbiases[1][i]*(-N);//更新
        outputbiases[1][i]=0.0;//初期か

        for(int j=0;j<numOfFilters;j++){
           //(lenOfSample-lenOffilter+1)/poolscale must be integer
           int sizeOf=poolOkisa;
      
           for(int l=0;l<sizeOf;l++){
               for(int m=0;m<sizeOf;m++){
               //w とbをw=w+delta w, b=b+delta bにしてさらにサンプル回数繰り返す(costTが最小になるまで)
                   omomis.get(i).get(j)[l][m]+=omomiGrad.get(i).get(j)[l][m]*(-N);//更新
                   omomiGrad.get(i).get(j)[l][m]=0.0;//初期化
               }
           }
      
       }
      
      }
       //係数の更新 and 初期化
      for(int asap=0;asap<numOfFilters;asap++){
       
       filterbiases[0][asap]+=filterbiases[1][asap]*(-N);//更新
       filterbiases[1][asap]=0.0;//初期化

       for(int yone=0;yone<scaleOffilter;yone++){
           for(int zu=0;zu<scaleOffilter;zu++){
               ConOfFilters.get(asap)[yone][zu]+=ConofFiltersGrad.get(asap)[yone][zu]*(-N);//更新
               //System.out.println("k:"+String.valueOf(ConofFiltersGrad.get(asap)[yone][zu]*(-N)));
               ConofFiltersGrad.get(asap)[yone][zu]=0.0;//初期化
           }
       }
       
      }

   //filterのreset
   //outputlayerのreset
   for(int ger=0;ger<numOfFilters;ger++){
       filters.get(ger).reset(ConOfFilters.get(ger), filterbiases[0][ger], poolscalee, numOfOutputs,ScaleOfSample);
   }
   

  for(int spa=0;spa<numOfOutputs;spa++){
      outputs.get(spa).reset(outputbiases[0][spa]);
  }



   //costTサンプル回数1っかい終わった時のcostT, 
   
   //w とbをw=w+delta w, b=b+delta bにしてさらにサンプル回数繰り返す(costTが最小になるまで)
   //costT=0.0;
   qwe++;
}
//omomis   ArrayList<ArrayList<double[][]>>
//filterbiases  double[0][]
//outputbiases  double[0][]
//ConOfFilters ArrayList<double[][]>

//決定した係数を保存
//係数を更新するかしないか　するならkesu.txtを書き換える

//preserve or not 0: no 1:yes
    System.out.println("Preserve? Input 0 or 1, 0:NO 1:YES");
    int input=new java.util.Scanner(System.in).nextInt();

     while((input!=0 && input!=1)){
      System.out.println("hey fucking Dumb asshole I said if you wanna preserve the data, input 1, otherwise input 0");
      input=new java.util.Scanner(System.in).nextInt();
      }

      switch(input){
        case 0:
          System.out.println("OK, the data not preserved:(");
        break;

        case 1:
         
          //ファイルに書き込み
         fileload.startREwrite(ConOfFilters, omomis, filterbiases[0], outputbiases[0] );
         System.out.println("the data preserved succesfully:)");
        break;

        default:
        break;
      }
      
    }

    public static void dissect_process(Fileloadd fileload ,double[][] target) throws IOException{
    //ファイルから係数を読み込みtargetを解析
      Object[] eru=fileload.loadData();//filterbias,outputsbias,filterr,poolssの順
      
      double[] filterbias=(double[])eru[0];
      double[] outputbias=(double[])eru[1];
      ArrayList<ArrayList<double[][]>> omomis=(ArrayList<ArrayList<double[][]>>)eru[3]; //ArrayList<double[][]> omomis1
      ArrayList<double[][]> ConOffilters=(ArrayList<double[][]>)eru[2];    //ArrayList<double[][]> ConOfFilters

      ArrayList<Filter> filters=new ArrayList<>();

      for(int i=0;i<ConOffilters.size();i++){
      // System.out.println(filterbiases[0][i]); poolの大きさ縦横:omomis.get(0).get(0).length
         Filter filter=new Filter(ConOffilters.get(i), filterbias[i],omomis.get(0).get(0).length,omomis.size(),target.length);
         filters.add(filter);
      }   

      ArrayList<Outputlayer> outputs=new ArrayList<>();

      for(int i=0;i<omomis.size();i++){
        Outputlayer output=new Outputlayer(outputbias[i]);
        outputs.add(output);
      }

      ArrayList<double[][]> pools=new ArrayList<>();

      for(int i=0;i<filters.size();i++){
        pools.add(filters.get(i).getpool(target,i));// p199 上図 特徴マップからプーリング層を生成
        //zf.add(filters.get(i).getter("zf"));
        //filters.get(i).getter("pmap")でp221の畳み込み層のz34(F1)を特定k,lし例5のramdaを求める
      }

      double[] correct=new double[omomis.size()];

      for(int i=0;i<omomis.size();i++){
        Outputlayer o=outputs.get(i);
       // System.out.println("poolsize:"+pools.size()+" omomis"+omomis.get(i).size());
        o.calc(pools, omomis.get(i),i);//出力 a0 error!!!!!!!!
        double a=o.getter(1);
        correct[i]=a;
        
      }

     // System.out.println("result:");
      for(int j=0;j<correct.length;j++){
        System.out.print("output"+j+":"+correct[j]+" ");
      }








    }

   
    //
   
}
