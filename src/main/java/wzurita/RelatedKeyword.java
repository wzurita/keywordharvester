package wzurita;

public class RelatedKeyword implements Comparable<RelatedKeyword> {
	private String keyword;
	private String relatedKeyword;
	private float relevance;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getRelatedKeyword() {
		return relatedKeyword;
	}

	public void setRelatedKeyword(String relatedKeyword) {
		this.relatedKeyword = relatedKeyword;
	}

	public float getRelevance() {
		return relevance;
	}

	public void setRelevance(float relevance) {
		this.relevance = relevance;
	}

	@Override
	public int compareTo(RelatedKeyword o) {
		if (this.relevance < o.relevance)
			return -1;
		else if (this.relevance == o.relevance)
			return 0;
		else
			return 1;
	}

	public String toString() {
		return "This is a related keyword: (\"" + this.relatedKeyword + "\" with relevance " + this.relevance + ")";
	}
}
