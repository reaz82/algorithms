/**
 * @author Reaz H.
 */

public class PercolationStats {
   
    private int T;            // Number of tests.
    private double[] fractionOfOpens;
    
        
   /**
    * perform T independent computational experiments on an N-by-N grid 
    * @param T number of experiments
    * @param N dimension of grid
    */
    public PercolationStats(int N, int T) {
        if ((N <= 0) || (T <= 0)) throw new IllegalArgumentException("Invalid Inputs"); 
        
        Percolation grid;
        fractionOfOpens = new double[T];
        this.T = T;
        int upperBound = N+1;
        int lowerBound = 1;
        for (int i = 1; i <= T; i++) {
            int row, column;
            int count = 0;
            grid = new Percolation(N);
            while (!grid.percolates()) {
                do {
                    row = StdRandom.uniform(1, upperBound);
                    column = StdRandom.uniform(1, upperBound); 
                } while (grid.isOpen(row, column));
                grid.open(row, column);
                count++;
            }
            fractionOfOpens[i-1] = ((double) count)/(N*N);
         }
    }   
   
   /**
    * sample mean of percolation threshold
    * @return the mean value derived over T experiments on NxN grid
    */
    public double mean() {
        return StdStats.mean(fractionOfOpens);
    }
    
   /**
    * sample standard deviation of percolation threshold  
    * @return
    */
    public double stddev() {
       if (T == 1) return Double.NaN;
       
       return StdStats.stddev(fractionOfOpens);
       
    }      
    
   /**
    * returns lower bound of the 95% confidence interval
    * @return
    */
  
   public double confidenceLo()  {
       double mu = StdStats.mean(fractionOfOpens);
       double sigma = StdStats.stddev(fractionOfOpens);
       return (mu - (1.96*sigma)/Math.sqrt(T));
   }        
   /**
    * returns upper bound of the 95% confidence interval
    * @return
    */
   public double confidenceHi()   {
       double mu = StdStats.mean(fractionOfOpens);
       double sigma = StdStats.stddev(fractionOfOpens);
       return (mu + (1.96*sigma)/Math.sqrt(T));
   }  
   
   public static void main(String[] args) {
       int N = Integer.parseInt(args[0]);
       int T = Integer.parseInt(args[1]);
     
       StdOut.println("Starting Percolation Stats");
       PercolationStats pStats = new PercolationStats(N, T);
       StdOut.print("mean                    = ");
       StdOut.println(pStats.mean());
       StdOut.print("stddev                  = ");
       StdOut.println(pStats.stddev());
       StdOut.print("95% confidence interval = ");
       StdOut.print(pStats.confidenceLo());
       StdOut.print(", ");
       StdOut.println(pStats.confidenceHi());
   }  
}