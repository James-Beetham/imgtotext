package imgtotext;

public class Test {
	public static void main(String[] args) {
		
//		for (int i = 0; i < 256; i++) {
//			int[][] arr = new int[1][1];
//			arr[0][0] = i;
//			BWImg temp = new BWImg("" + i, arr);
//			Util.save("pxls/" + temp.getDesc(), Util.bwToBI(temp));
//		}
//		System.exit(0);
		
		Util.timer();
		System.out.print("[Loading]");
		RGBImg img = Util.biToRGB(Util.load("a.jpg"));
		System.out.print(" (" + Util.timer() + "ms)\n[converting]");
		BWImg bw = Util.rgbToBW(img);
		
		System.out.print(" (" + Util.timer() + "ms)\n[splitting]");
		for (int x = 0; x < bw.getWidth() - 5; x += 5) {
			for (int y = 0; y < bw.getHeight() - 5; y += 5) {
				
				
			}
		}
		
		System.exit(0);
		
//		bw = (BWImg) bw.split(300, 1100, 500, 1300);
		
		// bw = (BWImg) bw.mergeLevels(new int[] { 1, 118, 118, 255 });
		// Util.save("bwTest", Util.bwToBI(bw));
		//Util.save("smartWriteBefore", Util.bwToBI(bw));

		System.out.print(" (" + Util.timer() + "ms)\n[altering]");
		for (int i = 0; i < 1; i++) {
			//bw = bw.getWriting();
		}
		
		bw = (BWImg) bw.mergeLevels(new int[] {0, 120, 120, 256});
		
		System.out.print(" (" + Util.timer() + "ms)\n[saving]");
		Util.save("smartWrite", Util.bwToBI(bw));
		System.out.println(" (" + Util.timer() + "ms)");
		
	}
}
