var ws_protocol = 'ws'; // ws 或 wss
var ip = '127.0.0.1'
var port = 8787

var heartbeatTimeout = 10000; // 心跳超时时间，单位：毫秒
var reconnInterval = 5000; // 重连间隔时间，单位：毫秒

var binaryType = 'blob'; // 'blob' or 'arraybuffer';//arraybuffer是字节
var handler = new DemoHandler()
//var name="webs"+Math.floor(Math.random()*100)+"cd"+Math.floor(Math.random()*100)+"bd"+Math.floor(Math.random()*100);
var name="dddd&cmd=lg&pwd=dddd";
var tiows
var info_kind='gb';
var info_to="any";

function initWs () {


  var queryString="name="+name;
  var param = "";
  tiows = new tio.ws(ws_protocol, ip, port, queryString, param, handler, heartbeatTimeout, reconnInterval, binaryType)
  tiows.connect()
}



function send () {
var msg = document.getElementById('textId')
//  tiows.send(msg.value)
var s=new Message(msg.value,name,info_to,"qun",info_kind);
var k=JSON.stringify(s)
	console.log(k);
tiows.send(k);
}

function clearMsg () {
  document.getElementById('contentId').innerHTML = ''
}

initWs()


function Message(m,f,t,q,k){
    this.f=f;//from
	this.t=t;//to
	this.q=q;//群对象名称
	this.m=m;//消息内容
	this.k=k;//消息种类     sr:私人信息， gb:群发  dxgb:特定群发   sys系统信息   data:数据操作
   
}