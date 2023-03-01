package com.zerobase.community.post.controller;


import com.zerobase.community.post.dto.PostDto;
import com.zerobase.community.post.model.PostInput;
import com.zerobase.community.post.model.PostParam;
import com.zerobase.community.post.repository.PostRepository;
import com.zerobase.community.post.service.PostService;
import com.zerobase.community.user.entity.User;
import com.zerobase.community.user.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class PostController extends BaseController {

	private final PostService postService;
	private final UserRepository userRepository;

	// 게시글 작성
	@GetMapping("/post/add")
	public String add(Model model, Principal principal) {
		return "post/add";
	}

	@PostMapping("/post/add")
	public String addSubmit(Model model, PostInput parameter, Principal principal) {
		String userEmail = principal.getName();
		Optional<User> user = userRepository.findByUserEmail(userEmail);
		parameter.setUserId(user.get().getUserId());
		parameter.setUserName(user.get().getUserName());

		boolean result = postService.add(parameter);

		return "redirect:/post/list";
	}

	@GetMapping("/post/list")
	public String list(Model model, PostParam parameter) {
		parameter.init();
		List<PostDto> posts = postService.list(parameter);

		long totalCount = 0;
		if (posts != null && posts.size() > 0) {
			totalCount = posts.get(0).getTotalCount();
		}
		String queryString = parameter.getQueryString();
		String pagerHtml = getPaperHtml(totalCount, parameter.getPageSize(),
			parameter.getPageIndex(), queryString);

		model.addAttribute("list", posts);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pager", pagerHtml);

		return "post/list";
	}

	@GetMapping("/post/detail")
	public String postDetail(Model model, PostParam parameter) {
		PostDto detail = postService.getById(parameter.getPostId());
		model.addAttribute("detail", detail);

		return "post/detail";
	}


}
