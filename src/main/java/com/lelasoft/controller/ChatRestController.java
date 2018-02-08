package com.lelasoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lelasoft.dao.ChatDao;
import com.lelasoft.dao.MemberDao;
import com.lelasoft.model.ChatMessage;
import com.lelasoft.model.Member;
import com.lelasoft.tools.BaseResponse;
import com.lelasoft.tools.Membership;
import com.lelasoft.tools.Util;

@Controller
@RequestMapping("/rest/chat")
public class ChatRestController {
	@Autowired
	private ChatDao chatDao;

	@Autowired
	private MemberDao memberDao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<List<ChatMessage>> getAppointments() {
		BaseResponse<List<ChatMessage>> response = new BaseResponse<List<ChatMessage>>();
		try {
			List<ChatMessage> chats = chatDao.findAllChats();
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("List of All chats!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(chats);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't load Chats, " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	/**
	 * Get chat messages between to user based on message id to return
	 * before(load from archive) or after(refresh new messages) according to top
	 * flag.
	 * 
	 * @param uid
	 *            Caller user id
	 * @param oppid
	 *            Hospital team id
	 * @param msgid
	 *            reference message id.
	 * @param top
	 *            flag to check archive or new messages.
	 * @return
	 */
	@RequestMapping(value = "/{autor}/{oppid}/{msgid}/{top}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<List<ChatMessage>> getChats(
			@PathVariable("autor") Long autor,
			@PathVariable("oppid") Long oppid,
			@PathVariable("msgid") Long msgid, @PathVariable("top") Boolean top) {
		BaseResponse<List<ChatMessage>> response = new BaseResponse<List<ChatMessage>>();
		try {
			List<ChatMessage> chats = chatDao.findChats(autor, oppid, msgid,
					top);
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("List of Chat messages!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(chats);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Load chat error, " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(value = "/list/{uid}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<List<ChatMessage>> getChats(
			@PathVariable("uid") Long uid) {
		BaseResponse<List<ChatMessage>> response = new BaseResponse<List<ChatMessage>>();
		try {
			List<ChatMessage> chats = chatDao.findChats(uid);
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("List of Chat Users!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(chats);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't list chat members, " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(value = "/{autor}/{oppid}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<List<ChatMessage>> getChats(
			@PathVariable("autor") Long autor, @PathVariable("oppid") Long oppid) {
		BaseResponse<List<ChatMessage>> response = new BaseResponse<List<ChatMessage>>();
		try {
			List<ChatMessage> chats = chatDao.findChats(autor, oppid);
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("List Chat messages!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(chats);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't list chat!, " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody BaseResponse<ChatMessage> saveChatMessage(
			@RequestBody ChatMessage message) {
		BaseResponse<ChatMessage> response = new BaseResponse<ChatMessage>();
		try {
			/**
			 * Save message...
			 */
			chatDao.save(message);
			/**
			 * Send push notification.
			 */
			Member autor = memberDao.findById(message.getAutor());

			Member opponent = memberDao.findById(message.getOpponent());
			String type = opponent.getMembership() == Membership.USER ? "Hospital Team: "
					: "Patient: ";
			String title = "Message from " + type + autor.getFname();
			Util.sendChatNotification(title, String.valueOf(opponent.getId()),
					 message.getText(), opponent.getToken());
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Message has been saved successfully");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(message);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't save chat message,  " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}
}