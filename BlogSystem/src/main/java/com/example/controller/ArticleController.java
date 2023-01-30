package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.TargetRepository;
import com.example.model.Article;
import com.example.model.Message;
import com.example.model.Photo;
import com.example.model.Target;
import com.example.service.ArticleService;
import com.example.service.MessageService;
import com.example.service.PhotoService;
import com.example.service.TargetService;

@CrossOrigin
@RestController
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private TargetService targetService;
	@Autowired
	private PhotoService photoService;
	@Autowired
	private MessageService messageService;
	
	//取得所有文章
	@GetMapping("/Article/getAll")
	public List<Article> getAllArticle(){
		return articleService.getALL();
	}
	
	//閱讀文章--->取得單一文章資料
	@GetMapping("/Article/get/{id}")
	public Article getArticle(@PathVariable Integer id) {
		System.out.println("搜尋Article: "+id);
		return articleService.getArticle(id);
	}
	
	//依據id篩選相同標籤的文章列表
	@GetMapping("/Article/getlist/{id}")
	public List<Article> getArticlebyTarget(@PathVariable Integer id) {
		System.out.println("搜尋ArticlebyTarget: "+id);
		return articleService.getALLbyTarget(targetService.getTarget(id));
	}
	
	//取得所有標籤
	@GetMapping("/Target/get/all")
	public List<Target> getallTarget() {
		System.out.println("取得所有標籤");
		return targetService.getAll();
	}
	
	//上傳圖片
	@PostMapping("/File/add")
	@ResponseBody
	public Photo postimg(@RequestParam(name = "image") MultipartFile multipartFile,
			@RequestParam(name = "name") String name) throws IOException {
		//System.out.println(name);
		Path uploadPath = Paths.get("C:/Users/USER/Desktop/images/");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(name);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            Photo p=new Photo();
            p.setPanme(name);
            return photoService.onloadPhoto(p);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file");
        }
        
	}
	
	//取得圖片位置
    @RequestMapping(value = "/getimg/{pid}/{pname}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable Integer pid,@PathVariable String pname) throws IOException {
    	try {
    		Photo photo=photoService.getPhoto(pid);
    		FileSystemResource imgFile = new FileSystemResource("C:/Users/USER/Desktop/images/"+photo.getPanme());
    		byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(bytes);
    	}catch (NoSuchElementException e) {
    		return null;
    	}
    }
	
	
	//新增文章、圖片
	@PostMapping("/Article/add/")
	public boolean addArticle(@RequestBody Map<String, Object> Data) {				
		Date date = new Date();
		Timestamp ts=new Timestamp(date.getTime());
		
		List<Integer> tagsid=(List<Integer>)Data.get("targets");
		Set<Target> tags=new LinkedHashSet<Target>();
		for(Integer t:tagsid) {
			tags.add(targetService.getTarget(t));
		}
		
		Article article=new Article();
		article.setTitle((String)Data.get("title"));
		article.setContent((String)Data.get("content"));
		article.setCreatetime(ts);
		
		article.setTargets(tags);
		article.setPhoto(photoService.getPhoto((Integer)Data.get("pid")));
		
		articleService.addArticle(article);
		
		return true;
	}
	
	//新增標籤
	@PutMapping("/Target/add/{name}")//put
	public String addTarget(@PathVariable String name) {
		Target t=new Target();
		t.setTname(name);
		if(targetService.addTarget(t)) {
			return "新增標籤--->成功";
		}else {
			return "新增標籤--->失敗";
		}
	}
	
	//新增標籤到文章
	@GetMapping("/Article/addTaeget/{aid}/{tid}")
	public String addTargetToArticle(@PathVariable Integer aid,@PathVariable Integer tid) {

		Article a= articleService.getArticle(aid);
		Set<Target> tags=a.getTargets();
		if(!tags.contains(targetService.getTarget(tid))&&targetService.getTarget(tid)!=null) {
			tags.add(targetService.getTarget(tid));
			//tags.remove(targetService.getTarget(tid));
		}
		a.setTargets(tags);
		articleService.updateArticle(a);
		
		
		return "addTargetToArticle";
	}
	
	//刪除文章裡面的標籤
	@GetMapping("/Article/removeTaeget/{aid}/{tid}")
	public String removeTarget(@PathVariable Integer aid,@PathVariable Integer tid) {
		Article a= articleService.getArticle(aid);
		Set<Target> tags=a.getTargets();
		System.out.println("before "+tags.size());
		//
		if(targetService.getTarget(tid)!=null) {
			tags.remove(targetService.getTarget(tid));
		}
		System.out.println("after "+tags.size());
		a.setTargets(tags);
		articleService.updateArticle(a);
		return "removeTarget";
	}
	
	//新增留言
	@PostMapping("/message/add/")
	public boolean addmessage(@RequestBody Map<String, Object> Data) {
		Date date = new Date();
		Timestamp ts=new Timestamp(date.getTime());
		
		Message message =new Message();
		message.setCreatetime(null);
		message.setMcontent((String)(Data.get("mcontent")));
		message.setMname((String)(Data.get("mname")));
		message.setAid((Integer)(Data.get("aid")));
		message.setCreatetime(ts);
		
		return messageService.addmessage(message);
	}
	
	//刪除留言
	@DeleteMapping("/message/delete/{mid}")
	public boolean deletemessage(@PathVariable Integer mid) {
		return messageService.deletemessage(mid);
	}
}
