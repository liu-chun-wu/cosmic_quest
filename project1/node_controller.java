package project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Path;

class node_controller {

	public static ArrayList<node> UpNodeList = new ArrayList<node>();
	public static ArrayList<node> LeftNodeList = new ArrayList<node>();
	public static ArrayList<node> DownNodeList = new ArrayList<node>();
	public static ArrayList<node> RightNodeList = new ArrayList<node>();

	public static boolean NodeIsLoaded = false;

	public void LoadNode(String name, AnchorPane pane, Path up, Path left, Path down, Path right)
			throws FileNotFoundException {
		if (NodeIsLoaded == false) {
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
					UpNodeList.add(new node(up, pane, time, "up"));
				if (notes.charAt(2) == '1')
					LeftNodeList.add(new node(left, pane, time, "left"));
				if (notes.charAt(4) == '1')
					DownNodeList.add(new node(down, pane, time, "down"));
				if (notes.charAt(6) == '1')
					RightNodeList.add(new node(right, pane, time, "right"));

				time += len;
			}
			NodeIsLoaded = true;
			sc.close();
		}
	}

	public static void StartAndStop() {
		for (int i = 0; i < node_controller.UpNodeList.size(); i++) {
			node_controller.UpNodeList.get(i).StartAndStop();
		}
		for (int i = 0; i < node_controller.DownNodeList.size(); i++) {
			node_controller.DownNodeList.get(i).StartAndStop();
		}
		for (int i = 0; i < node_controller.LeftNodeList.size(); i++) {
			node_controller.LeftNodeList.get(i).StartAndStop();
		}
		for (int i = 0; i < node_controller.RightNodeList.size(); i++) {
			node_controller.RightNodeList.get(i).StartAndStop();
		}
	}

	public static void SetCurrentNodeTransparent() {
		for (int i = 0; i < node_controller.UpNodeList.size(); i++) {
			node_controller.UpNodeList.get(i).SetTransparent();
		}
		for (int i = 0; i < node_controller.DownNodeList.size(); i++) {
			node_controller.DownNodeList.get(i).SetTransparent();
		}
		for (int i = 0; i < node_controller.LeftNodeList.size(); i++) {
			node_controller.LeftNodeList.get(i).SetTransparent();
		}
		for (int i = 0; i < node_controller.RightNodeList.size(); i++) {
			node_controller.RightNodeList.get(i).SetTransparent();
		}
	}

	public static void RestoreNodeColor() {
		for (int i = 0; i < node_controller.UpNodeList.size(); i++) {
			node_controller.UpNodeList.get(i).RestoreColor();
		}
		for (int i = 0; i < node_controller.DownNodeList.size(); i++) {
			node_controller.DownNodeList.get(i).RestoreColor();
		}
		for (int i = 0; i < node_controller.LeftNodeList.size(); i++) {
			node_controller.LeftNodeList.get(i).RestoreColor();
		}
		for (int i = 0; i < node_controller.RightNodeList.size(); i++) {
			node_controller.RightNodeList.get(i).RestoreColor();
		}
	}

	public static void ResetNodeList() {
		for (int i = 0; i < node_controller.UpNodeList.size(); i++) {
			node_controller.UpNodeList.get(i).terminate();
		}
		for (int i = 0; i < node_controller.DownNodeList.size(); i++) {
			node_controller.DownNodeList.get(i).terminate();
		}
		for (int i = 0; i < node_controller.LeftNodeList.size(); i++) {
			node_controller.LeftNodeList.get(i).terminate();
		}
		for (int i = 0; i < node_controller.RightNodeList.size(); i++) {
			node_controller.RightNodeList.get(i).terminate();
		}
		UpNodeList.clear();
		DownNodeList.clear();
		LeftNodeList.clear();
		RightNodeList.clear();
		NodeIsLoaded = false;
	}
}