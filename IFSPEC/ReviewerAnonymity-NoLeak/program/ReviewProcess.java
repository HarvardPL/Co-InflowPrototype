import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

public class ReviewProcess {
	private class Review implements Comparable<Review> {
		int reviewer_id;
		int score;
		String content;

		public int compareTo(Review r) {
			if (this.score != r.score) {
				return (this.score < r.score) ? -1 : 1;
			} else {
				return this.content.compareTo(r.content);
			}
		}
	}

	private List<Review> reviews;

	ReviewProcess() {
		reviews = new LinkedList<Review>();
	}

	public void addReview(int reviewer_id, int score, String content) {
		Review r = new Review();
		r.reviewer_id = reviewer_id;
		r.score = score;
		r.content = content;
		reviews.add(r);
	}

	public void sendNotifications() {
		Collections.sort(reviews);
		for (Review r : reviews) {
			System.out.println("---");
			System.out.println("Score: " + r.score);
			System.out.println("Review: " + r.content);
			System.out.println("---");
		}
	}

	public static void main(String args[]) {
		ReviewProcess rp = new ReviewProcess();

		rp.addReview(42, 1, "Little novelty.");
		rp.addReview(5, 3, "Borderline paper.");
		rp.addReview(7, 4, "Significant contribution.");

		rp.sendNotifications();
	}
}

