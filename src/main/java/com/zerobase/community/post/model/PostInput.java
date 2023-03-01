package com.zerobase.community.post.model;

import java.time.LocalDate;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class PostInput {
	private Long postId;
	private String title;
	private String contents;
	private Long userId;

	private String userName;

}
