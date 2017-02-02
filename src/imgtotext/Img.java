package imgtotext;

import java.lang.reflect.Array;

public interface Img {
	public Img split(int x1, int y1, int x2, int y2);
	public Img mergeLevels(int[] list);
	public String getDesc();
}
