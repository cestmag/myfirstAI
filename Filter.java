public class Filter {
    //note that futuremap.length%poolscale must be 0 otherwise fail /lenOffutureMap=lenOfphoto-lenOffilter+1
    private double[][] filter;
    private double[][] futureMap;
    private double[][] zfs;
    private double[][] pool;
    private String[][] poolMap;
    private double inherentBias;
    private int sampleOkisa;
    private int lenOffutureMap;
    private int poolscale;//2 usually 
    private int poolbig;
    ActivationFunc shiki=new ActivationFunc();
    //double activationFunction;
    //5*5のfilter
    Filter(double[][] Thefilter,double omomi,int poolscale,int numOfoutputgen, int sampleNookisa){
       this.filter=Thefilter;
  /*     System.out.println("footloose");
      for(int ad=0;ad<this.filter.length;ad++){
        for(int sd=0;sd<this.filter[ad].length;sd++){
          System.out.print(this.filter[ad][sd]+" ");
        }
        System.out.println("");
      }*/

       this.inherentBias=omomi;
       
       this.poolscale=poolscale;
       this.sampleOkisa=sampleNookisa;

       this.lenOffutureMap=this.sampleOkisa-this.filter.length+1;

       this.zfs=new double[this.lenOffutureMap][this.lenOffutureMap];
       this.futureMap=new double[this.lenOffutureMap][this.lenOffutureMap];

       this.poolbig=(int)this.lenOffutureMap/this.poolscale;

       this.pool=new double[this.poolbig][this.poolbig];
       this.poolMap=new String[this.poolbig][this.poolbig];
    }
    double[][] getpool(double[][] photo, int filnum){//[[1,2,3],[4,5,6],[7,8,9]]
        int lenOfphoto=this.sampleOkisa;//photo.length;//=photo[n].length  n=1~3
        int lenOffilter=this.filter.length;
        //System.out.println(this.inherentBias);
        //int lenOffutureMap=this.lenOffutureMap;
  /* System.out.println("photo");
       for(int ay=0;ay<photo.length;ay++){
        for(int qw=0;qw<photo[ay].length;qw++){
          System.out.print(photo[ay][qw]+" ");
        }
        System.out.println("");
       }*/

        for(int i=0; i<this.lenOffutureMap;i++){
            for(int j=0; j<this.lenOffutureMap;j++){
                //i~lenOfphoto+i-1, j~lenOfphoto+j-1
                double total=0.0;
                for(int k=i;k<lenOffilter+i;k++){//k<=lenOfphoto+i-1
                   for(int l=j;l<lenOffilter+j;l++){
                    total+=photo[k][l]*this.filter[k-i][l-j]; //!!!!!!photo ok 
                  //  System.out.println("to:"+filter[k-i][l-j]+" "+i+" "+j+" "+photo[k][l]+" "+total+" bias:"+this.inherentBias);
                   }
                }
             // System.out.println("aaa"+this.inherentBias);//!!!!!!!!!!!!4になる
                this.zfs[i][j]=total+this.inherentBias;//this.zfs is null? whyyyyyy
                this.futureMap[i][j]=this.shiki.activation_function(total+this.inherentBias);//zf
               /*Seems there's no problem here*/ //System.out.println("q:"+String.valueOf(this.futureMap[i][j])+" w:"+this.zfs[i][j]);
            }
        }
     //futureMapは偶数長さ
    // this.pooling();
      //return this.futureMap;
      this.pooling(filnum);

    /*   System.out.println("footloose");
      for(int ad=0;ad<this.zfs.length;ad++){
        for(int sd=0;sd<this.zfs[ad].length;sd++){
          System.out.print(this.zfs[ad][sd]+" ");
        }
        System.out.println("");
      }*/




      return this.pool;
    }

    private void pooling(int num){
        for(int i=0;i<this.lenOffutureMap;i=i+this.poolscale){
          for(int j=0;j<this.lenOffutureMap;j=j+this.poolscale ){

             double max=0.0;
             int first=0;
             int second=0;
            // System.out.println("a"+num);
            for(int k=i;k<i+this.poolscale;k++){//p199
              
               for(int l=j;l<j+this.poolscale;l++){
                //futureMap[k][l]
                double eachValue=this.futureMap[k][l];
              //  System.out.print(eachValue+" ");
                if((l==j&&k==i&&max>=eachValue)||max<eachValue){
                  max=eachValue;
                  first=k;
                  second=l;
                }
               }
              // System.out.println("");
            }
            //choose the biggest->max
            this.poolMap[(int)(i/this.poolscale)][(int)(j/this.poolscale)]=String.valueOf(first)+String.valueOf(second);
            this.pool[(int)(i/this.poolscale)][(int)(j/this.poolscale)]=max;
            /*Seems there's no problem here*/ //System.out.println("a:"+String.valueOf(first)+String.valueOf(second)+" d:"+max);
          }
        }
       //return this.pool;
    }

    double[][] getter(String classy){
      if(classy=="zf"){
        return this.zfs;
      }/*else if(classy=="pmap"){
      return this.poolMap;
      }*/else{
        return this.zfs;
      }
    }

    String[][] getter2(){
      return this.poolMap;
    }

    void reset(double[][] Thefilter,double omomi,int poolscale,int numOfoutputgen,int sampleNookisa){
       this.clearr();
       this.filter=Thefilter;
       this.inherentBias=omomi;
       this.poolscale=poolscale;
       this.sampleOkisa=sampleNookisa;

    }
    private void clearr(){
      for(int zxc=0;zxc<this.lenOffutureMap;zxc++){
        for(int iop=0;iop<this.lenOffutureMap;iop++){
          this.zfs[zxc][iop]=0;
          this.futureMap[zxc][iop]=0;
        }
      }

      for(int zxc=0;zxc<this.poolbig;zxc++){
        for(int iop=0;iop<this.poolbig;iop++){
          this.pool[zxc][iop]=0;
          this.poolMap[zxc][iop]="";
        }
      }
    }
    

}
