package controller.reserve;

import java.io.IOException;
import java.util.List;
import model.Lodging.Lodging_reserve;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Lodgingadmin.LodgingDao;
import dao.Lodgingadmin.LodgingDaoImpl;
import dao.air.AirReserveDao;
import dao.air.AirReserveDaoImpl;
import dao.air.AirlineDao;
import dao.air.AirlineDaoImpl;
import dao.car.CarDao;
import dao.car.CarDaoImpl;
import dao.member.MemberDao;
import dao.member.MemberDaoImpl;
import dao.reserve.ReserveDao;
import dao.reserve.ReserveDaoImpl;
import model.Lodging.Lodgingadmin;
import model.air.AirReserve;
import model.air.Airline;
import model.car.Car;
import model.car.CarReserve;
import model.manager.Member;
import model.manager.Reservation;

@WebServlet(name = "ReserveController", urlPatterns = { "/res_detail", "/shopping_cart", "/reserve", "/cancle", "/reserve_delete" })
public class ReserveController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		System.out.println(uri);
		int lastIndex = uri.lastIndexOf("/");
		String action = uri.substring(lastIndex + 1);

		// logic
		if (action.equals("res_detail")) {

			int resNo = Integer.parseInt(req.getParameter("resNo"));
			System.out.println("占쎌굙占쎈튋甕곕뜇�깈" + resNo);

			ReserveDao dao = new ReserveDaoImpl();
			Reservation res = dao.selectByResNo(resNo);
			System.out.println("占쎈툧占쎈�욑옙釉�占쎄쉭占쎌뒄");
			System.out.println(res.toString());

			AirReserve airRes = dao.selectAirResByResNo(res.getairResNo());
			Airline air = dao.selectAirByResNo(res.getairResNo());

			Lodging_reserve roomRes = dao.selectRoomResByResNo(res.getroomResNo());
			Lodgingadmin room = dao.selectRoomByResNo(res.getroomResNo());

			CarReserve carRes = dao.selectCarResByResNo(res.getcarResNo());
			Car car = dao.selectCarByResNo(res.getcarResNo());

			req.setAttribute("resNo", resNo);
			req.setAttribute("air", air);
			req.setAttribute("car", car);
			req.setAttribute("airRes", airRes);
			req.setAttribute("carRes", carRes);
			req.setAttribute("room", room);
			req.setAttribute("roomRes", roomRes);

			req.setAttribute("res", res);

		} else if (action.equals("shopping_cart")) {

			HttpSession session = req.getSession();

			// int resNo = (int)session.getAttribute("resNo");

			// System.out.println("占쎌굙占쎈튋甕곕뜇�깈" + resNo);

			ReserveDao dao = new ReserveDaoImpl();
			// Reservation res = dao.selectByResNo(resNo);
			// System.out.println(res.toString());

			if (session.getAttribute("airRes") != null) {
				AirReserve airRes = (AirReserve) session.getAttribute("airRes");
				Airline air = dao.selectAirByNo(airRes.getAirNo());
				req.setAttribute("air", air);
				req.setAttribute("airRes", airRes);
			}

			if (session.getAttribute("lodgingReserve") != null) {
				Lodging_reserve roomRes = (Lodging_reserve) session.getAttribute("lodgingReserve");
				Lodgingadmin room = dao.selectRoomByNo(roomRes.getLodging_no());
				req.setAttribute("room", room);
				req.setAttribute("roomRes", roomRes);
			}

			if (session.getAttribute("carReserve") != null) {
				CarReserve carRes = (CarReserve) session.getAttribute("carReserve");
				Car car = dao.selectCarByNo(carRes.getCar_no());
				req.setAttribute("car", car);
				req.setAttribute("carRes", carRes);
			}

			
			//LodgingDao roomDao = new LodgingDaoImpl();
			//Lodgingadmin room = roomDao.selectBylodging_no(res.getroomResNo());

			//req.setAttribute("resNo", resNo);
		
		
			//req.setAttribute("res", res);
		} else if(action.equals("reserve")) {
	         HttpSession session = req.getSession();
	         
	         //占쎄쉭占쎈�� 占쎌젟癰귨옙 獄쏆룇釉섓옙�궎疫뀐옙
	         Reservation res = (Reservation)session.getAttribute("reserve");
	         //占쎈퉮�⑨옙,占쎈땿獄쏉옙,占쎌젉占쎈뱜燁삼옙
	        
	         Lodging_reserve roomRes = (Lodging_reserve) session.getAttribute("lodgingReserve");
	         CarReserve carRes = (CarReserve) session.getAttribute("carReserve");
	         //占쎌돳占쎌뜚
	         String id = (String)session.getAttribute("member");
	         //占쎌굙占쎈튋占쎌뵬
	         String startDay = (String)session.getAttribute("startDay");
	         String endDay = (String)session.getAttribute("endDay");
	         MemberDao memDao = new MemberDaoImpl();
	         Member member = memDao.selectById(id);
	         
	         int airResNo = 0;
	         int roomResNo = 0;
	         int carResNo = 0;
	         int totalPrice = 0 ;
	        
	         if(session.getAttribute("airRes") != null) {
	        	 AirReserve airRes = (AirReserve) session.getAttribute("airRes");
	             AirReserveDao dao = new AirReserveDaoImpl();
	             dao.insert(airRes.getTakeOff(), airRes.getLanding(), airRes.getPersonnel(), airRes.getAirNo());
	             airResNo = dao.recentAirReserve();	  
	             System.out.println("�뜮袁る뻬疫꿸퀣�굙占쎈튋甕곕뜇�깈1234�뜮占�"+airResNo);
	         }
	         
	         if(roomRes != null) {
	        	 //占쎈땿占쎈꺖 占쎌굙占쎈튋 筌ㅼ뮇�뻿 甕곕뜇�깈
	        	 Lodging_reserve room = (Lodging_reserve) session.getAttribute("lodgingReserve");
	        	 LodgingDao dao = new LodgingDaoImpl();
	        	 dao.Reserveroominsert(room);
	        	 roomResNo = dao.recentlodgingReserve(); 
	            
	         }
	         
	         //System.out.println("carRes");
	         //System.out.println(carRes.toString());
	         if(carRes != null) {
	        	CarReserve car=  (CarReserve) session.getAttribute("carReserve");
	        	CarDao dao = new CarDaoImpl();
				dao.CarReserve(car);  //
	            carResNo = dao.recentcarReserve();
	           
	            System.out.println("筌ㅼ뮇�뻿 占쎌젉占쎈뱜燁삼옙 占쎈퓠占쎈튋 甕곕뜇�깈 : " + carResNo);

	         }
	         
	         System.out.println("-----------------------------------------");
	         //System.out.println(res.toString());
	         System.out.println("-----------------------------------------");
	         
	         System.out.println("airNo : " + airResNo + " roomNo : " + roomResNo + "carNo: " + carResNo );
	         
	         ReserveDao dao = new ReserveDaoImpl();
	         dao.insert(member.getNo(), startDay, endDay, totalPrice, airResNo, roomResNo, carResNo);
	         
	         
	         
	         session.removeAttribute("reserve");
	         session.removeAttribute("res");
	         session.removeAttribute("airReserve");
	         session.removeAttribute("lodgingReserve");
	         session.removeAttribute("carReserve");
	         session.removeAttribute("startDay");
	         session.removeAttribute("endDay");
	         session.removeAttribute("airRes");
	         
	      } else if(action.equals("reserve_delete")) {
	    	  

			int resNo = Integer.parseInt(req.getParameter("resNo"));

			ReserveDao dao = new ReserveDaoImpl();
			Reservation res = dao.selectByResNo(resNo);

			if (res.getairResNo() != 0) {
				dao.deleteAirRes(res.getairResNo());
			}
			if (res.getroomResNo() != 0) {
				dao.deleteLodgingRes(res.getroomResNo());
			}
			if (res.getcarResNo() != 0) {
				dao.deleteCarRes(res.getcarResNo());
			}

			dao.deleteRes(res.getResNo());

		}else if(action.equals("cancle")) {
			HttpSession session = req.getSession();
			
			session.removeAttribute("reserve");
			session.removeAttribute("res");
			session.removeAttribute("airReserve");
			session.removeAttribute("lodgingReserve");
			session.removeAttribute("carReserve");
			session.removeAttribute("startDay");
			session.removeAttribute("endDay");

		} 


		//
		String dispatchUrl = null;
		if (action.equals("res_detail")) {
			dispatchUrl = "/jsp/reserve/res_detail.jsp";
		} else if (action.equals("shopping_cart")) {
			dispatchUrl = "/jsp/reserve/shoppingbasket.jsp";
		} else if (action.equals("reserve")) {
			dispatchUrl = "mypage";
		} else if (action.equals("reserve_delete")) {
			dispatchUrl = "mypage";
		} else if(action.equals("cancle")) {
			dispatchUrl = "index.jsp";
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher(dispatchUrl);
		dispatcher.forward(req, resp);
	}
}
