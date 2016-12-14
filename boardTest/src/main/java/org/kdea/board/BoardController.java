package org.kdea.board;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.kdea.uploadMyBatis.FileValidator;
import org.kdea.uploadMyBatis.PdsVO2;
import org.kdea.uploadMyBatis.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/board")
@Controller
public class BoardController {
	@Autowired
	private BoardService svc;
	@Autowired
	private FileValidator fileValidator;
	
	@RequestMapping("/list")
	public String list(SearchPageVO vo,
			Model model,@RequestParam(value="pageNum",defaultValue="1") int page) {
		vo.setPageNum(page);
		List<BoardVO> list = svc.getList(vo);
		if (list.size() == 0) {
		     model.addAttribute("pageNum", page);
		     model.addAttribute("total", 0);
		     model.addAttribute("list", null);
		     return "board/boardList";
			}
		model.addAttribute("list",list);
		model.addAttribute("pageNum",page);
		model.addAttribute("dropBox", vo.getDropBox());
		model.addAttribute("searchText", vo.getSearchText());
		model.addAttribute("pageNum",vo.getPageNum());
	
		// totalRow 계산
		int total = list.get(0).getTotalRows();
		double tatalRow = Math.ceil(total / 10.0);
		model.addAttribute("total", (int) tatalRow);
		
		return "board/boardList";
	}
	@RequestMapping("/detail")
	public String detail(BoardVO vo,Model model){
		BoardVO vo2 = svc.getDetail(vo);
		model.addAttribute("detail",vo2);
		
		PdsVO2 filedetail = svc.getfileDetail(vo.getBoardNum());
		model.addAttribute("filedetail", filedetail);
		return "board/boardDetail";
	}
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public boolean delete(BoardVO vo,Model model){
		int boardnum = vo.getBoardNum();
		boolean result = svc.getDelete(boardnum);
		return result;
	}
	@RequestMapping("/updateform")
	public String updateForm(@RequestParam(value="boardnum") int boardnum,
			@RequestParam(value="updateid") String updateid,
			Model model){
		model.addAttribute("boardnum", boardnum);
		model.addAttribute("updateid", updateid);
		return "board/boardUpdate";
	}
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public boolean update(BoardVO vo,Model model){	
		System.out.println(vo);
		boolean result = svc.getUpdate(vo);
		return result;
	}
	@RequestMapping("/writingform")
	public String writingForm(){
		return "board/boardWriting";
	}
	@RequestMapping(value="/writing",method=RequestMethod.POST)
	@ResponseBody
	public String writing(BoardVO vo,Model model,@RequestParam(value="boardcontent") String content,
@ModelAttribute("uploadedFile") UploadedFile uploadedFile,
			BindingResult result){
		
		System.out.println("컨트롤 들어옴");
		System.out.println("vo : "+vo.getBoardcontent());
		System.out.println("vo2 : "+content);
		System.out.println("vo3 : "+vo.getBoardtitle());
		
		boolean result2 = svc.getWriting(vo);
		BoardVO vo2 = svc.writingbefore();
		int boardnum = vo2.getBoardNum();
		JSONObject jsObj = new JSONObject();
	
		boolean svcResult;
		if (result.hasErrors()) {
			svcResult = false;
			jsObj.put("svcResult", svcResult);
		}else {
			fileValidator.validate(uploadedFile, result);
			svcResult = svc.getFileInput(uploadedFile,boardnum);
			jsObj.put("svcResult", svcResult);
			System.out.println(svcResult);
		}
		jsObj.put("result", result2);
		jsObj.put("boardnum", boardnum);
		return jsObj.toJSONString();
	}
	@RequestMapping("/commentform")
	public String commentForm(@RequestParam(value="boardnum") int boardnum,
			Model model){
		model.addAttribute("boardnum", boardnum);
		return "board/boardComment";
	}
	@RequestMapping(value="/comment",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject comment(BoardVO vo,Model model){
		boolean result = svc.getComment(vo);
		BoardVO vo2 = svc.writingbefore();
		int boardnum = vo2.getBoardNum();
		JSONObject jsObj = new JSONObject();
		jsObj.put("result", result);
		jsObj.put("boardnum", boardnum);
		return jsObj;
	}
	 @RequestMapping("/download")
	 @ResponseBody
	 //@ResponseBody 응답을 구성한다 , view 에 바로 전달될 데이터
	 public byte[] getImage(HttpServletResponse response,
	            @RequestParam (value="upNum")int num){
		 	System.out.println(num);
		 	PdsVO2 vo = svc.getDownload(num);
		 	String filename = vo.getFilename();
		 	System.out.println(vo);
	        File file = new File("C:/test/upload/"+filename);
	        byte[] bytes = null;
	        String fn = null;
			try {
				bytes = org.springframework.util.FileCopyUtils.copyToByteArray(file);
				 fn = new String(file.getName().getBytes(), "iso_8859_1");
				 //한글이라면 영문조합으로 변경 -"iso_8859_1"
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + fn + "\"");
	        response.setContentLength(bytes.length);
	        response.setContentType("image/jpeg");
	 
	        return bytes;
	 } 
	
}