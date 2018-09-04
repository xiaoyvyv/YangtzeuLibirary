package net.custom.FireWorkView;

/**
 * Created by wayww on 2016/9/8.
 * 火花
 */
public class Element {
	public int color;
	public Double direction;
	public float speed;
	public float x = 0;
	public float y = 0;

	public Element(int color, Double direction, float speed) {
		this.color = color;
		this.direction = direction;
		this.speed = speed;
	}
}
