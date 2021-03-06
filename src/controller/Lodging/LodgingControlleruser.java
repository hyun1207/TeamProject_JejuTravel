package controller.Lodging;



import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Lodgingadmin.LodgingDao;
import dao.Lodgingadmin.LodgingDaoImpl;
import dao.reserve.ReserveDao;
import dao.reserve.ReserveDaoImpl;
import model.Lodging.Lodging_reserve;
import model.Lodging.Lodgingadmin;
import model.Lodging.Lodginghoroscope;
import model.car.Carhoroscope;

@WebServlet (name="LodgingControlleruser", urlPatterns= {"/lodging_list_user", "/main_lodging", "/detail_room"})
public class LodgingControlleruser extends HttpServlet{
   //Test임?
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      req.setCharacterEncoding("utf-8");
       process(req,resp);
   }
   
   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.setCharacterEncoding("utf-8");
       process(req,resp);
         
   }
   
   private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
      String uri =req.getRequestURI();
      int lastIndex = uri.lastIndexOf("/");
      String action = uri.substring(lastIndex+1);
      
      //로직
      if(action.equals("lodging_list_user")) {
         System.out.println("list 도착.");
         
         LodgingDao lodgingDao = new LodgingDaoImpl();
         List<Lodgingadmin> lodgingList = lodgingDao.selectAll();
         for (Lodgingadmin lodgingadmin : lodgingList) {
            System.out.println(lodgingadmin.toString());
         }
         //별점
         List<Lodginghoroscope> lodginghoroscopeList = lodgingDao.Lodginghoroscope();
         req.setAttribute("lodginghoroscopeList", lodginghoroscopeList);
         
         req.setAttribute("lodgingList", lodgingList);
         
      }else if(action.equals("detail_room")) {
         //HttpSession session = req.getSession();
         //int resNo = (int)session.getAttribute("resNo");
            
         int lodgingNo = Integer.parseInt(req.getParameter("roomNo"));
         System.out.println("roomNo: " + lodgingNo);
         //ReserveDao resDao = new ReserveDaoImpl();
         
         LodgingDao roomDao = new LodgingDaoImpl();
         
         Lodgingadmin room = roomDao.selectBylodging_no(lodgingNo);
         
         req.setAttribute("room", room);
      }
      
      
      //화면
      String dispatcherUrl = null;
      
      if(action.equals("lodging_list_user")) {
         dispatcherUrl = "/jsp/lodging_user/lodginglistuser.jsp";
      }else if(action.equals("main_lodging")) {
         dispatcherUrl = "/jsp/main/lodging.jsp";
      }else if(action.equals("detail_room")) {
         dispatcherUrl = "/jsp/lodging_user/lodging_detail.jsp";
      }
      
      RequestDispatcher dispatcher = req.getRequestDispatcher(dispatcherUrl);
       dispatcher.forward(req, resp);
   }
   
}