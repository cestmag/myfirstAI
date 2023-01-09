public class ActivationFunc {
    double activation_function(double z){
        //活性化関数の式
        return 1/(1+Math.pow(Math.E, -z));
    }
    double activation_function_dash(double z){
        double val=this.activation_function(z);
        return val*(1-val);
    }
//added after the coursera course according to it, it's good to use relu for hidden layers
    double activation_function_ReLU(double z){
        return Math.max(0, z);
    }
}
