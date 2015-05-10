package de.aquariumshow.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String author;
	
	private String text;

	public Comment(){
		
	};
	
	public Comment(final String author, final String text) {
		this.author = author;
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(final String author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}
	
	public String toString() {
		return new ToStringBuilder(this).append("ID", id).append("Author", author).append("Text", text).toString();
	}
	
}
