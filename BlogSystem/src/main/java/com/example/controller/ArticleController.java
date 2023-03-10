package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestHeader;
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
import com.example.model.Systemadmin;
import com.example.model.Target;
import com.example.model.TriggerBlog;
import com.example.service.ArticleService;
import com.example.service.MessageService;
import com.example.service.PhotoService;
import com.example.service.SystemadminService;
import com.example.service.TargetService;
import com.example.service.TriggerBlogService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

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
	@Autowired
	private SystemadminService systemadminService;
	@Autowired
	private TriggerBlogService triggerBlogService;
	
	
	
	private static final Logger logger
    = LoggerFactory.getLogger(ArticleController.class);
	
	//??????????????????
	@GetMapping("/Article/getAll")
	public List<Article> getAllArticle(){
		logger.info("????????????????????????");
		return articleService.getALL();
	}
	
	//????????????--->????????????????????????
	@GetMapping("/Article/get/{id}")
	public Article getArticle(@PathVariable Integer id) {
		System.out.println("??????Article: "+id);
		return articleService.getArticle(id);
	}
	
	//??????id?????????????????????????????????
	@GetMapping("/Article/getlist/{id}")
	public List<Article> getArticlebyTarget(@PathVariable Integer id) {
		System.out.println("??????ArticlebyTarget: "+id);
		return articleService.getALLbyTarget(targetService.getTarget(id));
	}
	
	//??????????????????
	@GetMapping("/Target/get/all")
	public List<Target> getallTarget() {
		System.out.println("??????????????????");
		return targetService.getAll();
	}
	
	//????????????
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
            logger.info("????????????: "+name);
            return photoService.onloadPhoto(p);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file");
        }
        
	}
	
	//??????????????????
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
	
	
	//?????????????????????
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
		logger.info("????????????: "+(String)Data.get("title"));
		
		return true;
	}
	
	//????????????
	@PostMapping("/Article/update/")
	public boolean updateArticle(@RequestBody Map<String, Object> Data) {
		Article article=articleService.getArticle((Integer)Data.get("aid"));
		article.setTitle((String)Data.get("title"));
		article.setContent((String)Data.get("content"));
		
		List<Integer> tagsid=(List<Integer>)Data.get("currenttid");
		Set<Target> tags=new LinkedHashSet<Target>();
		for(Integer t:tagsid) {
			tags.add(targetService.getTarget(t));
		}
		article.setTargets(tags);
		if(Data.get("pid")!=null&&!Data.get("pid").equals("")) { //??????????????????????????????
			article.setPhoto(photoService.getPhoto((Integer)Data.get("pid")));
		}
		logger.info("????????????: "+(Integer)Data.get("aid"));
		return articleService.updateArticle(article);
	}
	
	
	//????????????
	@PutMapping("/Target/add/{name}")//put
	public boolean addTarget(@PathVariable String name) {
		Target t=new Target();
		boolean isrepeat=true;
		t.setTname(name);
		List<Target> tags=targetService.getAll();

		for(Target target:tags) {
			if(target.getTname().equals(name)) {
				isrepeat=false;
			}
		}
		
		if(isrepeat) {
			targetService.addTarget(t);
			logger.info("????????????: "+name);
			return true;
		}else {
			return false;
		}
	}
	
	//?????????????????????
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
	
	//???????????????????????????
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
	
	//????????????
	@PostMapping("/message/add/")
	public boolean addmessage(@RequestBody Map<String, Object> Data) {
		Date date = new Date();
		Timestamp ts=new Timestamp(date.getTime());
		
		Message message =new Message();
		message.setMcontent((String)(Data.get("mcontent")));
		message.setMname((String)(Data.get("mname")));
		message.setAid((Integer)(Data.get("aid")));
		message.setCreatetime(ts);
		
		return messageService.addmessage(message);
	}
	
	//????????????
	@DeleteMapping("/article/delete/{aid}")
	public boolean deletearticle(@PathVariable Integer aid) {
		logger.warn("????????????: "+aid);
		return articleService.deleteArticle(aid);
	}
	
	//????????????aid?????????
	@GetMapping("/message/getbyaid/{aid}")
	public List<Message> getmessagebyaid(@PathVariable Integer aid){
		return messageService.getmessagebyaid(aid);
	}
	
	//????????????
	@DeleteMapping("/message/delete/{mid}")
	public boolean deletemessage(@PathVariable Integer mid) {
		logger.warn("????????????: "+mid);
		return messageService.deletemessage(mid);
	}
	
	//????????????????????????????????????????????????
	@GetMapping("/article/getbymonth/{year}/{month}")
	public List<Article> getarticlebymonth(
			@PathVariable Integer year,
			@PathVariable Integer month){
		 List<Article> articles=articleService.getALL();
		 for(Article a:articles) {
			 a.getCreatetime().getMonth();
			 a.getCreatetime().getYear();
		 }
		return articles
				.stream()
				.filter(t->t.getCreatetime().getMonth()==(month-1)&&t.getCreatetime().getYear()==(year-1900))
				.collect(Collectors.toList());
	}
	
	//?????????????????????
	@GetMapping("/admin/getall")
	public List<Systemadmin> getalladmin(@RequestHeader HttpHeaders  headers,HttpServletRequest request){
		String authorHeader = request.getHeader(AUTHORIZATION);
		try {
			Date date = new Date();
			Timestamp ts=new Timestamp(date.getTime());
			String token =authorHeader;
			Claims claims = Jwts.parser().setSigningKey("MySecret").parseClaimsJws(token).getBody();
			Systemadmin admin=systemadminService.getSystemadmin((String)(claims.get("sub")));
			admin.setLastlogin(ts);
			systemadminService.updateSystemadmin(admin);
			if(!claims.get("jti").equals("?????????")&&!claims.get("jti").equals("??????")) {
				System.out.println("??????!?????????????????????????????????????????????????????????????????????");
				return null;
			}
			return systemadminService.getALL();
		}catch (Exception e) {
			System.err.println("Error : "+e);
			return null;
		}
		
	}
	
	//????????????->????????????
	@PostMapping("/admin/login/")
	public String adminlogin(@RequestBody Map<String, Object> Data) {
		Data.get("adminaccount");
		Data.get("adminpassword");
		Map<String, String> test = new HashMap<String, String>();
		Systemadmin systemadmin = systemadminService.getSystemadmin((String)Data.get("adminaccount")) ;
		if(!systemadminService.checkadmin((String)Data.get("adminaccount"), (String)Data.get("adminpassword"))) {
			return null;
		}else {
			System.out.println("?????????:"+(String)Data.get("adminaccount")+"=>????????????!");
			//??????jwt tocken???????????????tocken
			Date expireDate = new Date(System.currentTimeMillis()+5*60*1000); //tocken?????????5??????
			String jwtTocken = Jwts.builder()
								.setClaims(Data)
								.setSubject((String)Data.get("adminaccount")) //(String)Data.get("adminaccount")
								.setId(systemadmin.getLevel())
								.setExpiration(expireDate)
								.signWith(SignatureAlgorithm.HS256,"MySecret")
								.compact();
			return jwtTocken;
		}
	}
	
	//?????????????????????????????????
	@PostMapping("/admin/get/login")
	public Systemadmin getadmin(@RequestHeader HttpHeaders  headers,HttpServletRequest request) {
		String authorHeader = request.getHeader(AUTHORIZATION);
		try {
			String token =authorHeader;
			Claims claims = Jwts.parser().setSigningKey("MySecret").parseClaimsJws(token).getBody();
			//Systemadmin admin=systemadminService.getSystemadmin((String)(claims.get("sub")));
			Systemadmin admin=systemadminService.getSystemadmin((String)(claims.get("adminaccount")));
			System.out.println(claims);//claims={adminaccount=admin0214, adminpassword=2023201401, sub=admin0214, jti=?????????, exp=1676597406}
			return admin;
		}catch (Exception e) {
			System.err.println("Error : "+e);
			return null;
		}
	}
	
	//???????????????
	@PostMapping("/admin/add")
	public boolean addadmin(@RequestBody Map<String, Object> Data) {
		Date date = new Date();
		Timestamp ts=new Timestamp(date.getTime());
		Systemadmin systemadmin=new Systemadmin();
		
		if(Data.get("aid")==null) {
			systemadmin.setRegdate(date);
		}else {
			systemadmin=systemadminService.getSystemadmin((String)Data.get("adminaccount"));
		}
		systemadmin.setAdminaccount((String)Data.get("adminaccount"));
		systemadmin.setAdminname((String)Data.get("adminname"));
		systemadmin.setAdminpassword((String)Data.get("adminpassword"));
		systemadmin.setLevel((String)Data.get("level"));
		
		return systemadminService.addSystemadmin(systemadmin);
	}
	
	//???????????????
	@DeleteMapping("/admin/delete/{aid}")
	public boolean deleteadmin (@PathVariable Integer aid) {
		return systemadminService.deleteSystemadmin(aid);
	}
	
	//?????????????????????
	@PostMapping("/message/reply")
	public boolean messageReply(@RequestBody Map<String, Object> Data) {
		//??????????????????+????????????mname
		//?????????????????????=>mcontent
		//??????????????????????????????=>replyid
		Date date = new Date();
		Timestamp ts=new Timestamp(date.getTime());
		
		Message message =new Message();
		message.setMcontent((String)(Data.get("mcontent")));
		message.setMname((String)(Data.get("mname")));
		message.setAid((Integer)(Data.get("aid")));
		message.setReplyid((Integer)(Data.get("replyid")));
		message.setCreatetime(ts);
		
		return messageService.addmessage(message);
	}
	
	@GetMapping("/blog/test")
	public List<TriggerBlog> testBlog(@RequestHeader HttpHeaders  headers,HttpServletRequest request){
		
		String authorHeader = request.getHeader(AUTHORIZATION);
		try {
			Date date = new Date();
			Timestamp ts=new Timestamp(date.getTime());
			String token =authorHeader;
			Claims claims = Jwts.parser().setSigningKey("MySecret").parseClaimsJws(token).getBody();
			Systemadmin admin=systemadminService.getSystemadmin((String)(claims.get("adminaccount")));
			if(admin.getLastlogin()==null) {
				admin.setLastlogin(ts);//???????????????????????????
				systemadminService.updateSystemadmin(admin);
				return null;
			}
			List<TriggerBlog> ans=triggerBlogService.getall().stream().filter(t->t.getTime().compareTo(admin.getLastlogin())>0).collect(Collectors.toList());
			admin.setLastlogin(ts);//???????????????????????????
			systemadminService.updateSystemadmin(admin);
			return ans;
		}catch (Exception e) {
			System.err.println("Error : "+e);
			return null;
		}
		
	}
	
}
