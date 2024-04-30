package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PageingVO;
import handler.FileRemoveHandler;
import handler.PagingHandler;
import net.coobird.thumbnailator.Thumbnails;
import service.BoardService;
import service.BoardServiceImpl;

@WebServlet("/brd/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	private RequestDispatcher rdp;
	private String destPage;
	private int isOk;
	private BoardService bsv;
	private String savePath; // 파일 업로드 저장 경로
	
    public BoardController() {
    	bsv = new BoardServiceImpl();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String uri = request.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/")+1);
		
		switch(path) {
		case "register" :
			destPage = "/board/register.jsp";
			break;
			
		case "insert" :
			try {
				// 파일을 업로드할 물리적인 경로 설정
				savePath = getServletContext().getRealPath("/_fileUpload"); // realpath 실제 물리적인 경로 >> wapapp
				log.info(">>> savePath >>> {}", savePath);
				File fileDir = new File(savePath);
				log.info(">>> fileDir >>> {}", fileDir);
				
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				fileItemFactory.setRepository(fileDir); // 저장할 위치를 File 객체로 지정
				fileItemFactory.setSizeThreshold(1024*1024*3); // 파일 저장을 위한 임시 메모리
				
				BoardVO bvo = new BoardVO();
				
				// multipart/form-data 형식으로 넘어온 request 객체를 다루기 쉽게 변환해주는 역할
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				
				// 각 값들을 하나씩 itemList에 저장
				List<FileItem> itemList = fileUpload.parseRequest(request);
				for(FileItem item : itemList) {
					// title, writer, content >> text
					// imageFile >> image
					switch (item.getFieldName()) {
					case "title":
						String title = item.getString("utf-8");
						bvo.setTitle(title);
						break;
						
					case "writer" :
						bvo.setWriter(item.getString("utf-8"));
						break;
						
					case "content" :
						bvo.setContent(item.getString("utf-8"));
						break;
						
					case "imageFile" :
						// image가 있는지 없는지 부터 체크
						if(item.getSize() > 0) {
							// 파일 이름 추출
							// getName() : 순수 파일 이름 + 경로
							String fileName = item.getName()
									.substring(item.getName().lastIndexOf(File.separator)+1);
							// File.separator : 파일 경로에 대한 기호 >> 운영체제마다 다를 수 있어서 자동 변환
							// 시스템의 시간을 이용하여 파일을 구분 / 시간_파일명
							fileName = System.currentTimeMillis()+"_"+fileName;
							
							File uploadFilePath = new File(fileDir+File.separator+fileName);
							log.info(">>> uploadFilePath >>>{}", uploadFilePath);
							
							// 저장
							try {
								item.write(uploadFilePath); // 들고온 객체를 디스크에 쓰기
								bvo.setImageFile(fileName); // bvo에 저장할 값 (DB에 저장할 값)
								
								// 썸네일 작업 : List 페이지에서 트레픽 과다 사용을 방지하기 위해
								Thumbnails.of(uploadFilePath)
								.size(75, 75)
								.toFile(new File(fileDir+File.separator+"_th_"+fileName));
								
							} catch (Exception e) {
								log.info(" >>> file writer on disk error");
								e.printStackTrace();
							}
						}
						break;
					}

				}
				log.info(">>> bvo >>> {}",bvo);
				int isOk = bsv.insert(bvo);
				log.info("insert >> {}",(isOk>0?"성공":"실패"));
				
				destPage = "/index.jsp";
				
				
//				첨부파일 multipart/form-data 가 없을 경우
//				String title = request.getParameter("title");
//				String writer = request.getParameter("writer");
//				String content = request.getParameter("content");
//				
//				BoardVO bvo = new BoardVO(title, writer, content);
//				int isOk = bsv.insert(bvo);
//				log.info("insert >> {}",(isOk>0?"성공":"실패"));
//				
//				destPage = "/index.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
			
		case "list" :
			try {
				//Pageing 객체 설정
				
				PageingVO pgvo = new PageingVO(); // 1page, 10개, 시작개수 0 / type, keyword 까지 같이
				
				if(request.getParameter("pageNo") != null) {					
					int pageNo = Integer.parseInt(request.getParameter("pageNo"));
					int qty = Integer.parseInt(request.getParameter("qty"));
					String type = request.getParameter("type");
					String keyword = request.getParameter("keyword");
					
					pgvo = new PageingVO(pageNo, qty, type, keyword);
				}

//				List<BoardVO> list = bsv.getList();
				// Pageing을 반영한 리스트 추출
				List<BoardVO> list = bsv.getList(pgvo);
//				log.info("list >> {}", list);
//				totalCount DB에서 검색해서 가져오기
				int totalCount = bsv.getTotal(pgvo);
				log.info(">> totalCount >> {}", totalCount);
				
				PagingHandler ph = new PagingHandler(pgvo, totalCount);
				log.info(">> ph >> {}", ph);
				
				request.setAttribute("list", list);
				request.setAttribute("ph", ph);
				
				destPage = "/board/list.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "contentList":
			try {
				String id = request.getParameter("id");
				List<BoardVO> list = bsv.selectList(id);
				log.info("list >> {}", list);
				request.setAttribute("list", list);
				destPage = "/board/contentList.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "detail" : case "modify" :
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				if (path.equals("detail") ) {
					int readCount = bsv.readCount(bno);
					log.info(">>> imageFileName >> {}", readCount);
				}
				BoardVO bvo = bsv.detail(bno);
				request.setAttribute("bvo", bvo);
				destPage = "/board/" +path+".jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

	
		case "update" :
			try {
				// 이미지 파일 있을때 처리 구문
				savePath = getServletContext().getRealPath("/_fileUpload");
				File fileDir = new File(savePath);
				
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				fileItemFactory.setRepository(fileDir); // 저장하고자 하는 주소의 경로를 세팅
				fileItemFactory.setSizeThreshold(1024*1024*3); // 3메가
				
				BoardVO bvo = new BoardVO();
				
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				
				List<FileItem> itemList = fileUpload.parseRequest(request);
				
				String old_file = null;
				for (FileItem item : itemList) {
					switch (item.getFieldName()) {
					case "bno":
						bvo.setBno(Integer.parseInt(item.getString("utf-8")));
						break;
						
					case "title" :
						bvo.setTitle(item.getString("utf-8"));
						break;
						
					case "content" : 
						bvo.setContent(item.getString("utf-8"));
						break;
						
					case "imageFile" :
						// 기존파일 >> 있을 수도 있고, 없을 수도 있음
						// old_file >> 데이터가 없으면 빈문자 or null 이 들어옴
						old_file = item.getString("utf-8");
						break;
						
					case "newFile" :
						// 새로 추가된 파일 >> 있을 수도 있고, 없을 수도 있음
						if(item.getSize() > 0) {
							// 새로운 등록파일이 있다면,
							if(old_file != null) {
								// old_file 삭제작업 (기존 파일)
								// fileRemoveHandler 통해서 파일 삭제 작업 진행
								
								FileRemoveHandler fileHandler = new FileRemoveHandler();
								isOk = fileHandler.deleteFile(path, old_file);
							}
							// 새로운 파일 등록작업
							String fileName = item.getName()
									.substring(item.getName().lastIndexOf(File.separator)+1);
							log.info(">>> new File Name >>> {}",fileName);
							
							fileName = System.currentTimeMillis()+"_"+fileName;
							File uploadFilePath = new File(fileDir+File.separator+fileName); // 실제 저장되는 껍데기의 값
							
							try {
								 item.write(uploadFilePath);
								 bvo.setImageFile(fileName);
								 
								 Thumbnails.of(uploadFilePath)
								 .size(75, 75)
								 .toFile(new File(fileDir+File.separator+"_th_"+fileName));
							} catch (Exception e) {
								log.info("File Writer Update Error");
								e.printStackTrace();
							}
						} else {
							// 기존 파일은 있지만, 새로운 이미지 파일이 없다면,
							bvo.setImageFile(old_file); // 기존 객체 bvo에 담기
						}
						break;
					}
				}
				
				int isOk = bsv.update(bvo);
				log.info("update >> {}",(isOk>0?"성공":"실패"));
				destPage = "list";
				
//				이미지 파일 없을 떄 처리 구문
//				int bno = Integer.parseInt(request.getParameter("bno"));
//				String title = request.getParameter("title");
//				String content = request.getParameter("content");
//				
//				BoardVO bvo = new BoardVO(bno, title, content);
//				int isOk = bsv.update(bvo);
//				log.info("update >> {}",(isOk>0?"성공":"실패"));
//				destPage = "list";
			} catch (Exception e) {
				e.printStackTrace();
			}	
			break;
			
		case "delete" :
			try {
				savePath = getServletContext().getRealPath("/_fileUpload");
				int bno = Integer.parseInt(request.getParameter("bno"));
				// 댓글, 파일 같이 삭제
				// bno 주고 파일이름 찾아오기 (DB에서)
				// 찾아온 이름이 있다면, FileRemoveHandler를 이용하여 삭제
				String imageFileName = bsv.getFileName(bno);
				log.info(">>> imageFileName >> {}", imageFileName);
				
				int isDel = 0;
				if (imageFileName !=null) {
					FileRemoveHandler fh = new FileRemoveHandler();
					isDel = fh.deleteFile(savePath, imageFileName);
				}

				// bno로 댓글 / 게시글 삭제 요청 => serviceImpl에서 cdao에게 요청
				log.info("bno >> "+bno);
				int isOk = bsv.delete(bno);
				log.info("delete >> {}",(isOk>0?"성공":"실패"));
				destPage = "list";
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		
		rdp = request.getRequestDispatcher(destPage);
		rdp.forward(request, response);
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
