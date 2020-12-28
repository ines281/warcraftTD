package warcraftTD;

public class Mainex {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StdDraw.setScale(-2, +2);
		  // StdDraw.enableDoubleBuffering();

		   for (double t = 0.0; true; t += 0.02) {
		       double x = Math.sin(t);
		       double y = Math.cos(t);
		    //   StdDraw.clear();
		       StdDraw.filledCircle(x, y, 0.05);
		       StdDraw.filledCircle(-x, -y, 0.05);
		    //   StdDraw.show();
		    //   StdDraw.pause(20);
		   }
		  

	}

}
