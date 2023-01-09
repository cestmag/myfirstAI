import java.util.ArrayList;

public class Outputlayer {
    private double inherentBias;
    private double output=0;
    private double outputthruFilter=0;
    ActivationFunc shiki=new ActivationFunc();
    Outputlayer(double inherentBias){
       this.inherentBias=inherentBias;
       this.output+=this.inherentBias;
    }
    void calc(ArrayList<double[][]> pools, ArrayList<double[][]> omomis, int filnu){//exact same size
        //double a=  pools.get(0)[1][0];
        int sizepools=pools.size();
        int sizeomis=omomis.size();//sizepools must be equal to sizeomis

        if(sizeomis!=sizepools){
            System.out.println("error in calc of Outputlayer ");
        }

        for(int i=0;i<sizepools;i++){
           double[][] pool=pools.get(i);
           double[][] omomy=omomis.get(i);
            //omomis.get(i);
            for(int j=0;j<pool.length;j++){
                for(int k=0;k<pool[j].length;k++){
                  this.output+=(pool[j][k]*omomy[j][k]);
                 // System.out.println("kakeru:"+String.valueOf(pool[j][k]*omomy[j][k])+":"+String.valueOf(filnu));
                 // System.out.println("j k "+String.valueOf(j)+" "+String.valueOf(k)+" output:"+String.valueOf(this.output)+" pool:"+String.valueOf(pool[j][k])+" omoy:"+String.valueOf(omomy[j][k]));
                }
            }
        }
       this.outputthruFilter=this.shiki.activation_function(this.output);//!!!!!!!!!!
       //System.out.println("t:"+String.valueOf(this.outputthruFilter)+"/"+String.valueOf(this.output));
      // return this.shiki.activation_function(this.output);
    }
    double getter(int i){
        if(i==0){
        return this.output;
        }else if(i==1){
        return this.outputthruFilter;
        }else if(i==2){
        return this.inherentBias;
        }else{
        return 0.0;
        }
    }
    void reset(double bias){
       this.output=bias;
       this.inherentBias=bias;
       this.outputthruFilter=0;
    }
     
   
}
