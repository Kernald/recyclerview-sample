package fr.enoent.recyclerviewsample;

public class Item {
	private String title;
	private String subtitle;
	private boolean active;

	Item(String title, String subtitle, boolean active) {
		this.title = title;
		this.subtitle = subtitle;
		this.active = active;
	}

	public String getTitle() {
		return title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public boolean isActive() {
		return active;
	}
}
