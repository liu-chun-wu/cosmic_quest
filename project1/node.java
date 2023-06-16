package project1;

import javafx.util.Duration;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;

class node {

	String pos;
	boolean NodeTerminated = false;

	Circle c = new Circle(-30, -30, 20);
	PathTransition pt = new PathTransition();

	Timeline tl = new Timeline(
			new KeyFrame(Duration.millis(1),
					new KeyValue(c.opacityProperty(), 1)),
			new KeyFrame(Duration.millis(100),
					event -> {
						if (NodeTerminated == false) {
							if (c.getFill() != Color.TRANSPARENT) {
								configuration.MissCounter++;
								configuration.MaxComboCounter = Math.max(configuration.ComboCounter,
										configuration.MaxComboCounter);
								configuration.ComboCounter = 0;
							}
							switch (pos) {
								case "up":
									if (play_scene_controller.UpIndex != node_controller.UpNodeList.size()
											- 1) {
										play_scene_controller.UpIndex++;
									}
									break;
								case "down":
									if (play_scene_controller.DownIndex != node_controller.DownNodeList.size()
											- 1) {
										play_scene_controller.DownIndex++;
									}
									break;
								case "left":
									if (play_scene_controller.LeftIndex != node_controller.LeftNodeList.size()
											- 1) {
										play_scene_controller.LeftIndex++;
									}
									break;
								case "right":
									if (play_scene_controller.RightIndex != node_controller.RightNodeList.size()
											- 1) {
										play_scene_controller.RightIndex++;
									}
									break;
							}
						}
					}),
			new KeyFrame(Duration.millis(300),
					new KeyValue(c.translateXProperty(), 330), new KeyValue(c.translateYProperty(), 330),
					new KeyValue(c.opacityProperty(), 0)));
	double timing;
	boolean PtIsRunning = false;
	boolean TlIsRunning = false;
	boolean PtHasEnded = false;
	boolean TlHasEnded = false;

	public static enum Cue {
		IGNORE,
		PERFECT,
		GREAT,
		BAD,
		MISS
	}

	node(Path path, AnchorPane pane, double timing, String pos) {
		if (NodeTerminated == false) {
			this.timing = timing;
			this.pos = pos;
			c.setFill(Color.BLACK);
			c.setStroke(Color.WHITE);
			pane.getChildren().add(c);
			pt.setPath(path);
			pt.setNode(c);
			pt.setDuration(Duration.millis(configuration.NodeDuration));
			pt.setAutoReverse(false);
			pt.setInterpolator(new CustomInterpolator());
			pt.setOnFinished(event1 -> {
				if (NodeTerminated == false) {
					PtHasEnded = true;
					tl.setOnFinished(event2 -> {
						TlHasEnded = true;
					});
					tl.play();
				} else {
					tl.pause();
				}
			});
			pt.setDelay(new Duration(this.timing));
		} else {
			pt.pause();
		}
	}

	private static class CustomInterpolator extends Interpolator {
		@Override
		protected double curve(double t) {
			return t;
		}
	}

	public void StartAndStop() {
		if (NodeTerminated == false) {
			if (PtHasEnded == false) {
				if (PtIsRunning == true) {
					pt.pause();
					PtIsRunning = false;
				} else {
					pt.play();
					PtIsRunning = true;
				}
			} else {
				if (TlHasEnded == false) {
					if (TlIsRunning == true) {
						tl.pause();
						TlIsRunning = false;
					} else {
						tl.play();
						TlIsRunning = true;
					}
				}
			}
		}
	}

	public Cue GetTiming() {
		if (timing - timer.time >= 100) {
			return Cue.IGNORE;
		} else if (timing - timer.time >= 60 && timing - timer.time < 100) {
			return Cue.BAD;
		} else if (timing - timer.time >= 30 && timing - timer.time < 60) {
			return Cue.GREAT;
		} else if (timing - timer.time >= -30 && timing - timer.time < 30) {
			return Cue.PERFECT;
		} else if (timing - timer.time >= -60 && timing - timer.time < -30) {
			return Cue.GREAT;
		} else if (timing - timer.time >= -100 && timing - timer.time < -60) {
			return Cue.BAD;
		} else if (timing - timer.time < -100) {
			return Cue.MISS;
		}
		return null;
	}

	public void SetTransparent() {
		c.setFill(Color.TRANSPARENT);
		c.setStroke(Color.TRANSPARENT);
	}

	public void RestoreColor() {
		c.setFill(Color.BLACK);
		c.setStroke(Color.WHITE);
	}

	public void terminate() {
		NodeTerminated = true;
	}
}