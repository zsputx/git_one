package server;

import java.util.List;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Tio;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;

import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.handler.IWsMsgHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import mysql_server.entity.User;
import mysql_server.sys.sys;



/**
 * @author tx 2017年6月28日 下午5:32:38
 */
public class ShowcaseWsMsgHandler implements IWsMsgHandler {
	private static Logger log = LoggerFactory.getLogger(ShowcaseWsMsgHandler.class);
	public static final ShowcaseWsMsgHandler me = new ShowcaseWsMsgHandler();
	public static ConcurrentHashMap<String, String> list = new ConcurrentHashMap<String, String>();
    public static int count=0;//总人数
	private ShowcaseWsMsgHandler() {

	}

	/**
	 * 握手时走这个方法，业务可以在这里获取cookie，request参数等
	 */
	@Override
	public HttpResponse handshake(HttpRequest request, HttpResponse httpResponse, ChannelContext channelContext)
			throws Exception {
//		String clientip = request.getClientIp();
		return httpResponse;
	}
	
	
	public void localBlind(String s,HttpRequest request, HttpResponse httpResponse, ChannelContext channelContext) {
		Tio.bindUser(channelContext, s);
		if (!list.containsKey(s)) {
			list.put(s, channelContext.getId());
			channelContext.setUserid(s);
		}
		
		
	}
	/**
	 * 握手业务
	 * @param req
	 * @param res
	 * @param ct
	 */
	public void hand_action(HttpRequest req, HttpResponse res, ChannelContext ct) {
		String cmd=req.getParam("cmd");
		if(null!=cmd) {
			if(cmd.equals("lg")) {
				//登录业务
				String namel=req.getParam("name");
				String pwdl=req.getParam("pwd");
				if(null!=namel&&null!=pwdl) {
					//查询用户名存在与否，
					int state_l=sys.login(namel,pwdl);
					if(state_l==1) {
						//登录成功
						sys_message(1, ct);
						sys.change_state(1, namel);
						localBlind(namel, req, res, ct);
						sys_message(8, ct);
					}else if(state_l==2) {
						//用户名或密码错误
						sys_message(2, ct);
					}else if(state_l==0) {
						//用户已登陆
						sys_message(0, ct);
						ChannelContext ctss=getChannelById(ct.groupContext,namel);
						Tio.close(ctss, "close");
      					Tio.remove(ctss,"remove");
      					sys.change_state(0, namel);
      					list.remove(namel);
						
						
					}
					
				}
			}else if(cmd.equals("zc")) {
				//注册业务
				String name=req.getParam("name");
				String pwd=req.getParam("pwd");
				String email=req.getParam("email");
				if(null!=name&&null!=pwd&&null!=email) {
					//查询用户名存在与否，
					int state_k=sys.zc(name,pwd,email);
					if(state_k==1) {
						//注册成功 3
						sys_message(3, ct);
						localBlind(name, req, res, ct);
						
					}else if(state_k==2) {
						//用户名重复 4
						sys_message(4, ct);
					}
					
				}
				
				
			}else if(cmd.equals("test")) {
				//测试业务
				
				
			}
			
			
			
		}
		
		
	}

	/**
	 * @param httpRequest
	 * @param httpResponse
	 * @param channelContext
	 * @throws Exception
	 * @author tanyaowu
	 */
	@Override
	public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext)
			throws Exception {
		
		// 绑定到群组，后面会有群发
		Tio.bindGroup(channelContext, Const.GROUP_ID);
		 count = Tio.getAllChannelContexts(channelContext.groupContext).getObj().size();

//		String msg = "{name:'admin',message:'" + channelContext.userid + " 进来了，共【" + count + "】人在线" + "'}";
//		// 用tio-websocket，服务器发送到客户端的Packet都是WsResponse
//		WsResponse wsResponse = WsResponse.fromText(msg, ShowcaseServerConfig.CHARSET);
//		// 群发
//		Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);
		
		
		 hand_action(httpRequest,  httpResponse,  channelContext);
		
		
	
	}

	/**
	 * 字节消息（binaryType = arraybuffer）过来后会走这个方法
	 */
	@Override
	public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
		return null;
	}

	/**
	 * 当客户端发close flag时，会走这个方法
	 */
	@Override
	public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
		String s=channelContext.userid;
		sys.change_state(0, s);
		list.remove(s);
		Tio.remove(channelContext, "receive close flag");
		
		return null;
	}

	/*
	 * 字符消息（binaryType = blob）过来后会走这个方法
	 */
	@Override
	public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
		WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();
		HttpRequest httpRequest = wsSessionContext.getHandshakeRequestPacket();// 获取websocket握手包
		if (log.isDebugEnabled()) {
			log.debug("握手包:{}", httpRequest);
		}
		log.info("收到ws消息:{}", text);

		if (Objects.equals("心跳内容", text)) {
			return null;
		}

		String msg = "{name:'" + channelContext.userid + "',message:'" + text + "'}";
		// 用tio-websocket，服务器发送到客户端的Packet都是WsResponse

		// 群发
		/**
		 * 文字消息调度处
		 */
		messageHandler(channelContext, text);
		return null;

		// 返回值是要发送给客户端的内容，一般都是返回null
	}

	/**
	 * 单独发送文字
	 * 
	 * @param channelContext
	 * @param text
	 */
	public void sendToClient(ChannelContext channelContext, String text) {
		WsResponse packet = new WsResponse();
		packet = WsResponse.fromText(text, "UTF-8");
		Tio.send(channelContext, packet);

	}

	/**
	 * 单独发送二进制
	 * 
	 * @param channelContext
	 * @param by
	 */
	public void sendToClient(ChannelContext channelContext, byte[] by) {
		WsResponse packet = new WsResponse();
		packet = WsResponse.fromBytes(by);
		Tio.send(channelContext, packet);

	}

	/**
	 * 群发消息 text内容
	 * 
	 * @param channelContext
	 * @param toId
	 * @param text
	 */
	public void sendToGroup(ChannelContext channelContext, String toId, String text) {
		WsResponse toClientBody = WsResponse.fromText(text, "UTF-8");
		Tio.sendToGroup(channelContext.getGroupContext(), toId, toClientBody);
	}

	/**
	 * 群发消息 二进制内容
	 * 
	 * @param channelContext
	 * @param toId
	 * @param by
	 */
	public void sendToGroup(ChannelContext channelContext, String toId, byte[] by) {
		WsResponse toClientBody = new WsResponse();
		toClientBody = WsResponse.fromBytes(by);
		Tio.sendToGroup(channelContext.getGroupContext(), toId, toClientBody);
	}

	public void sendList(ChannelContext channelContext, String toId) {

		System.out.println("列表" + list.toString());
		sendToGroup(channelContext, toId, "ml" + list.toString());
	}

	public ChannelContext getChannelById(GroupContext groupContext, String id) {
		return Tio.getChannelContextById(groupContext, list.get(id));

	}

	public String getIdByChannel(ChannelContext channelContext) {

		return channelContext.userid;

	}

	public void messageHandler(ChannelContext channelContext, String text) {
		if (messageFilter(text)) {

			try {
				
				JSONObject ds = JSON.parseObject(text);
				
				if (null != ds) {
					WsResponse wsResponse = WsResponse.fromText(text, ShowcaseServerConfig.CHARSET);

					if (ds.getString("k").equals("gb")) {
						
						Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);
						// 群发

					} else if (ds.getString("k").equals("sr")) {
						// danfa
						Tio.sendToUser(channelContext.groupContext, (String) ds.getString("t"), wsResponse);
						

					} else if (ds.getString("k").equals("yq")) {
						// danfa
						Tio.sendToUser(channelContext.groupContext, (String) ds.getString("t"), wsResponse);
						

					}else if (ds.getString("k").equals("sys")) {
						// 系统消息
						System.out.println("系统消息");

					} else if (ds.getString("k") .equals("data")) {
						// 数据处理

					} else if (ds.getString("k") .equals("dxgb")) {
						// 向特定的群发
						if(messageFilter((String)ds.getString("q"))) {
							Tio.sendToGroup(channelContext.groupContext, (String)ds.getString("q"), wsResponse);
						}

					} else if (ds.getString("k").equals("blind")) {
						// 绑定群
						if(messageFilter((String)ds.getString("q"))) {
						Tio.bindGroup(channelContext, (String)ds.getString("q"));
						}

					}else if (ds.getString("k").equals("unblind")) {
						// 解开绑定群
						if(messageFilter((String)ds.getString("q"))) {
						Tio.unbindGroup((String)ds.getString("q"),channelContext);
						}

					}

				}

			} catch (Exception e) {
				System.out.println("json解析错误！");
				e.printStackTrace();
			}
		}

	}

	// 消息过滤器
	public boolean messageFilter(String text) {
		if (null != text && text.length() > 1) {
			return true;
		} else {
			return false;
		}

	}
	
	public String get_list() {
		 List<User> list=sys.get_all_user();
		 String jsonArray = JSON.toJSONString(list);
		
		return jsonArray;
		
	}
	
	public JSONArray getList() {
		return null;
	}
	
	public void sys_message(int a,ChannelContext channelContext) {
		//
		Message m=new Message();
		if(a==1) {
			m.setK("sys");
			m.setM("1");//登录成功
		}else if(a==2) {
			m.setK("sys");
			m.setM("2");//用户名或密码错误
		}else if(a==3) {
			m.setK("sys");
			m.setM("3");//注册成功
		}else if(a==4) {
			m.setK("sys");
			m.setM("4");//用户名重复
		}else if(a==0) {
			m.setK("sys");
			m.setM("0");//用户已登陆
		}else if(a==7) {
			m.setK("sys");
			m.setM("7");//用户在其他地方登陆
		}
		
		
		sendToClient(channelContext, JSON.toJSONString(m));
		
		
		if(a==8) {
			m.setK("sys");
			m.setM("8");//更新好友列表
			String list=get_list();
			if(null!=list) {
				m.setQ(list);
				System.out.println(list);
			sendToGroup(channelContext, Const.GROUP_ID,JSON.toJSONString(m));
		
			}
			
		}
	}
	

}
