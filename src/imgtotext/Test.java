package imgtotext;

public class Test {
	public static void main(String[] args) {
		Source s = new Source("test1.jpg");
//		s.mergeLevels(140, 200);
//		s.mergeLevels(115, 140);
//		s.mergeLevels(70, 115);
//		s.mergeLevels(0, 70);
		s.save("testing");
		
//		int[] levels = s.getBWLevels();
//		for (int i = 0; i < levels.length; i++) {
//			System.out.println(levels[i]);
//		}
	}
}
