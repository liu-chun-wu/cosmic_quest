package project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Path;

class load_node {

	public static ArrayList<node> nodes_timing_up = new ArrayList<node>();
	public static ArrayList<node> nodes_timing_left = new ArrayList<node>();
	public static ArrayList<node> nodes_timing_down = new ArrayList<node>();
	public static ArrayList<node> nodes_timing_right = new ArrayList<node>();

	public static boolean node_is_loaded = false;

	public load_node(String name, AnchorPane pane, Path up, Path left, Path down, Path right)
			throws FileNotFoundException {
		if (node_is_loaded == false) {
			File sheet = new File("project1/songs/" + name);
			Scanner sc = new Scanner(sheet);
			float bpm = sc.nextFloat();
			float time_of_1_beat = 60000 / bpm;
			float len = time_of_1_beat;
			double time = 0.0;
			while (sc.hasNext()) {
				String notes = sc.nextLine();
				if (notes.length() < 1)
					continue;
				if (notes.charAt(0) == '(') {
					len = time_of_1_beat * (Float.parseFloat(notes.substring(1, notes.indexOf(")"))));
					continue;
				}
				if (notes.charAt(0) == '1')
					nodes_timing_up.add(new node(up, pane, time, "up"));
				if (notes.charAt(2) == '1')
					nodes_timing_left.add(new node(left, pane, time, "left"));
				if (notes.charAt(4) == '1')
					nodes_timing_down.add(new node(down, pane, time, "down"));
				if (notes.charAt(6) == '1')
					nodes_timing_right.add(new node(right, pane, time, "right"));
				time += len;
			}
			node_is_loaded = true;
			sc.close();
		}
	}
}